package org.nwea.oauthproxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nwea.oauthproxy.domain.apigroup.ApiGroup;
import org.nwea.oauthproxy.domain.applications.Applications;
import org.nwea.oauthproxy.domain.mappings.NWEAMappings;
import org.nwea.oauthproxy.domain.scopes.Scopes;
import org.nwea.oauthproxy.domain.target.Target;
import org.nwea.oauthproxy.repository.ApiGroupRepository;
import org.nwea.oauthproxy.repository.ApplicationsRepository;
import org.nwea.oauthproxy.repository.TargetRepository;
import org.nwea.oauthproxy.web.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Application class - For OAuth and ProxyURL Service
 * 
 * convenient way to bootstrap the Spring application - delegates to the static 
 * StringApplication.run() method  - initiated from the main() method
 *	@since		1.0.0
 *	
 */

@SpringBootApplication
public class Application extends SpringBootServletInitializer{
	
	private static Logger logger = LoggerFactory.getLogger(Application.class);

	//@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@RequestMapping(method = RequestMethod.GET, value ="/", produces = {"application/json"})
	String home() {
		return "OAuth-Proxy project";
	}

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}
	
	/*
	 * Added March 15, 2017
	 */

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}	

}

@EnableWebSecurity
@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	ApplicationsRepository applicationsRepository;

	@Autowired
	ApiGroupRepository apiGroupRepository;
	
	@Autowired
	TargetRepository targetRepository;

	private ApiGroup apiGroup;

	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	ServletContext context;
	
	@Autowired
	MongoTemplate mt;

	@Autowired
	MongoDbFactory mongoDbFactory;

	@Autowired
	MongoConverter mongoMappingContext;

	/**
	 * properties - MongoProperties
	 */
	@Autowired
	private MongoProperties properties;
	
	/*
	 * Environment
	 */
	@Autowired
	private Environment env;

	/*
	 * mongoClient
	 */
	MongoClient mongoClient;
	MongoClientURI mc;

	/**
	 * mongoURI - parameter defined in application.properties - URI for MongoDB 
	 */
	@Value("${spring.data.mongodb.uri}")
	private String mongoURI;
	
	private Target target;

	/**
	 * tokenURI - parameter defined in application.properties - URI for tokenStore
	 */
	@Value("${token.uri}")
	private String tokenURI;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	private String[] uriToken = null;
	private Map<String, String> creds = new HashMap<String, String>();
	private String [] credPairs;
	

	@Bean
	public ErrorAttributes errorAttributes() {
		return new DefaultErrorAttributes() {

			@Override
			public Map<String, Object> getErrorAttributes(
					RequestAttributes requestAttributes,
					boolean includeStackTrace) {
				Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
				Throwable error = getError(requestAttributes);
				if (error instanceof ServiceException) {
					errorAttributes.put("errorCode", ((ServiceException)error).getErrorCode());
				}
				return errorAttributes;
			}
		};
	}
	

	private HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			/* (non-Javadoc)
			 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
			 */
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				
				Applications account = applicationsRepository.findByClientId(username);
				
				List<Scopes> listOfScopes;
				List<NWEAMappings> listOfMappings;
				
				Scopes scopes;
				NWEAMappings mapping = null;
					
				DBCollection scopesDocs = null;
				DBCollection mappings = null;

				DBCursor scopesCursor = null;
				DBCursor mappingCursor = null;
				
				BasicDBObject query = new BasicDBObject();

				MongoClientURI mc = null;
				DB db = null;
				
				boolean flag;
				
				URL obj;
				HttpURLConnection con;
				int responseCode = 0;
				
				/*
				 * Added JSONArray
				 */
				
				JSONArray groupsArray = null;
							
				request = getHttpServletRequest();


				if(account != null) {
					if(account.getApiGroupId() != null)
						apiGroup = apiGroupRepository.findByApiGroupId(account.getApiGroupId());

					List<org.nwea.oauthproxy.domain.apigroup.Resources> apiResource = apiGroup.getResources();

					if(request.getRequestURI().contains("/")) {
						uriToken = request.getRequestURI().split("/");
					}

					

					/*
					 * Need a mechanism to figure this out...
					 */

					for (int i = 0; i > creds.size(); i++) {

					}
					
					System.out.println(uriToken.length);
					
					if (uriToken.length > 2) {
						query.put("requestURL", Pattern.compile("/"+uriToken[1]+"/"+uriToken[2], Pattern.CASE_INSENSITIVE));
					} else {
						query.put("requestURL", Pattern.compile("/"+uriToken[1], Pattern.CASE_INSENSITIVE));
					}

					

					//boolean flag = true;
					flag = true;
					List<String> getHttpVerbs = null;
					for (int i = 0; i < apiResource.size(); i++) {
						if (apiResource.get(i).getResource().contains(uriToken[1])) {
							getHttpVerbs = new ArrayList<String>();
							getHttpVerbs = apiResource.get(i).getVerbs();

							if (!getHttpVerbs.contains(request.getMethod())){
								throw new BeanCreationException("Insufficient permissions for this resource");
							} else {
								flag = false;
								continue;
							}
						} else {
							/*
							 * Add logic for HttpVerbs containing "/*"
							 */
						}
					}
					
					/*
					 * April 18, 2017
					 */
					String inputLine;
					StringBuffer responseBuffer = null;
					

					if (request.getHeader("token") != null ) {
						System.out.println(request.getHeader("token"));
						System.out.println("TokenURI: " + tokenURI);

						try {
							obj = new URL(tokenURI);
							con = (HttpURLConnection) obj.openConnection(); 	
							con.setRequestProperty("token", request.getHeader("token"));

							responseCode = con.getResponseCode();

							BufferedReader in = new BufferedReader(
									new InputStreamReader(con.getInputStream()));

							responseBuffer = new StringBuffer();


							while ((inputLine = in.readLine()) != null) {
								responseBuffer.append(inputLine);
							}

							in.close();

						} catch (Exception e){
							e.printStackTrace();
						}
						/*
						 *  April 28, 2017 - will need to check the response codes
						 */

						System.out.println(responseCode);

						if (responseBuffer != null)
							System.out.println(responseBuffer.toString());
						
				           JSONObject jsonObject = new JSONObject(responseBuffer.toString());
				             groupsArray = jsonObject.getJSONArray("groups");


				           System.out.println("groups new ====>" + groupsArray.toString());
						
					}
					
					System.out.println(creds.keySet().toString() + '\n');


			        System.out.println("*************************STARTTTTTTTTTTT");
			        System.out.println("groups oldddd" +creds.get("groups"));

			        System.out.println("*************************Enddddddddddd");

						if (responseBuffer != null)
							System.out.println(responseBuffer.toString());
					
					
					if (apiGroup.getApiGroupName().equalsIgnoreCase("full access") || apiGroup.getApiGroupName().equalsIgnoreCase("read only"))
						flag = false;
					
					if(flag) {
						throw new BeanCreationException("Insufficient permission for this resource");
					} else {
						
					}
					
					return new User(account.getClientId(), account.getClientSecret(), true, true, true, true,
							AuthorityUtils.createAuthorityList("USER"));

				} else {
					throw new UsernameNotFoundException("could not find the user '"
							+ username + "'");
				}	
			}     
		};
	}
}

@EnableWebSecurity
@Configuration
//@Order(1)
//@EnableGlobalMethodSecurity(prePostEnabled = false)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().and().authorizeRequests().antMatchers("/status").
        permitAll().and().authorizeRequests().anyRequest().fullyAuthenticated().and().
		httpBasic().and().
		csrf().disable();
	} 
	
	
	/*
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
		authenticationManager.setTokenServices(tokenService());
		return authenticationManagerBean();
	}
	 
	@Bean
	public ResourceServerTokenServices tokenService() {

		RemoteTokenServices tokenServices = new RemoteTokenServices();

		//tokenServices.setClientId(account.getClientId());
		//tokenServices.setClientSecret(account.getClientSecret());

		//tokenServices.setCheckTokenEndpointUrl("http://" + authServerHost + ":" + authServerPort + "/oauth/check_token");
		tokenServices.setCheckTokenEndpointUrl("https://ca-dev.nwea.org/api/api/session");

		return tokenServices;
	}
	*/	
}