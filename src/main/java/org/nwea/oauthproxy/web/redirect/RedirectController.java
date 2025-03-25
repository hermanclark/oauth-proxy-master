package org.nwea.oauthproxy.web.redirect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nwea.oauthproxy.OAPCustomAuthenticationSuccessHandler;
import org.nwea.oauthproxy.domain.apigroup.Resources;
import org.nwea.oauthproxy.domain.mappings.NWEAMappings;
import org.nwea.oauthproxy.domain.scopes.Scopes;
import org.nwea.oauthproxy.domain.target.Target;
import org.nwea.oauthproxy.repository.TargetRepository;
import org.nwea.oauthproxy.service.filter.FilterService;
import org.nwea.oauthproxy.web.exceptions.NoResultsException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


@RepositoryRestController
@RequestMapping("/redirect")
public class RedirectController {
	/**
	 * properties - MongoProperties
	 */
	@Autowired
	private MongoProperties properties;

	@Autowired
	TargetRepository targetRepository;

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

	/**
	 * tokenURI - parameter defined in application.properties - URI for tokenStore
	 */
	@Value("${token.uri}")
	private String tokenURI;

	private String[] uriToken = null;
	private Map<String, String> creds = new HashMap<String, String>();


	private HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
	}

	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	private Target target;
	StringBuffer responseBuffer = null;

	/**
	 * "GET" Method - returns the Mapping based on "targetId"
	 * @param request 
	 * @return @ResponseBody 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value ="/**", produces = {"application/json"})
	public @ResponseBody Map<String, Object>doRedirect() {

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

		/*
		 * Added JSONArray
		 */

		JSONArray groupsArray = null;
		JSONObject jsonObject = null;
		
		Map<String, Object> conInput = null;


		request = getHttpServletRequest();

		if(request.getRequestURI().contains("/")) {
			uriToken = request.getRequestURI().split("/");
		}

		try {
			/*
			 * connection info for Mongo repository
			 */
			mc = new MongoClientURI(mongoURI);
			if (mongoClient == null)
				mongoClient = properties.createMongoClient(mc.getOptions(), env);


			db = mongoClient.getDB(mc.getDatabase());
			//scopesDocs = db.getCollection("scopes");
			mappings = db.getCollection("mappings");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String tmpURI = "";
		if (uriToken.length > 2) 
			for (int i = 0; i < uriToken.length; i++) {
				if ( i == 2)
					tmpURI += uriToken[i];

				if ( i > 2)
					tmpURI += "/"+ uriToken[i];

			}

		if (tmpURI != null)
			query.put("requestURL", Pattern.compile("^" + "/"+tmpURI + "$", Pattern.CASE_INSENSITIVE));



		mappingCursor = mappings.find(query);
		//scopesCursor = scopesDocs.find(query);

		int numberOfDocuments =  0;

		if (mappingCursor.size() != 0)
			numberOfDocuments = mappingCursor.count();

		//if (scopesCursor.size() != 0)
		//numberOfDocuments = scopesCursor.count();

		List<Target> listOfTargets = new ArrayList<Target>();
		listOfMappings = new ArrayList<NWEAMappings>();

		while (mappingCursor.hasNext()){
			BasicDBObject result = (BasicDBObject) mappingCursor.next();
			mapping = new NWEAMappings();

			if (result.getString("targetId") != null){
				mapping.setTargetId(result.getString("targetId"));
			}

			if (result.getString("requestURL") != null){
				mapping.setRequestURL(result.getString("requestURL"));
			}

			if (result.getString("mapTo") != null){
				mapping.setMapTo(result.getString("mapTo"));
			}
			listOfMappings.add(mapping);
		}

		/*
		 *  Need to change this implementation
		 */
		String mappedURL, tmpMapURL = null;
		target = null;
		for (int i = 0; i < listOfMappings.size(); i ++ ) {
			mappedURL = listOfMappings.get(i).getRequestURL();
			if (tmpMapURL == null) {
				tmpMapURL = mappedURL;
			} else if (mappedURL.length() > tmpMapURL.length() ) {
				tmpMapURL = mappedURL;
			}
			String tmpId = listOfMappings.get(i).getTargetId();
			//System.out.println(tmpId);
			target = targetRepository.findByTargetId(listOfMappings.get(i).getTargetId());
			if (target != null)
				listOfTargets.add(target);
		}

		List<Resources> scopesResources = new ArrayList<Resources>();

		/*
		 * Search scopes collection here
		 *
		if (scopesCursor != null)
			while (scopesCursor.hasNext()) {
				scopes = new Scopes();
				BasicDBObject result = (BasicDBObject) scopesCursor.next();

				if (result.getString("resources") != null)
					scopesResources.addAll((List<Resources>)result.get("resources"));	
			}

		 */

		if (request.getHeader("token") != null ) {
			//System.out.println(request.getHeader("token"));
			//System.out.println("TokenURI: " + tokenURI);

			conInput = new HashMap<String, Object>();
			conInput.put("token", request.getHeader("token"));
			
			
			jsonObject =  doURLRedirect("tokenURI", conInput);

			//jsonObject = new JSONObject(responseBuffer.toString());
			groupsArray = jsonObject.getJSONArray("groups");
			
			System.out.println("groups new ====>" + groupsArray.toString());

		}
		
		conInput = new HashMap<String, Object>(); 
		//System.out.println(creds.keySet().toString() + '\n');


		//System.out.println("*************************STARTTTTTTTTTTT");
		//System.out.println("groups oldddd" +creds.get("groups"));

		//System.out.println("*************************Enddddddddddd");

		if (responseBuffer != null)
			System.out.println(responseBuffer.toString());
		
		HashMap<String, Object> headers = new HashMap();
		for (Enumeration names = request.getHeaderNames(); names.hasMoreElements();) {
		    String name = (String)names.nextElement();
		    for (Enumeration values = request.getHeaders(name); values.hasMoreElements();) {
		        String value = (String)values.nextElement();
		        headers.put(name,value);
		    }
		}


		try {
			if (target != null) {
				if (target.getDomain() != null)	{
					/*
					 * need to set header(s) from target collection...
					 */
					for (int i = 0; i < target.getHeaders().size(); i++) {
						conInput.put(target.getHeaders().get(i).getName(), target.getHeaders().get(i).getValue());
					}

					jsonObject = doURLRedirect(target.getDomain(), conInput);

					//System.out.println(headers.toString());
				} else {
					jsonObject = doURLRedirect(mapping.getMapTo(), headers);	
				}
			} else {
				//System.out.println("mapTo: " + mapping.getMapTo());
				jsonObject = doURLRedirect(mapping.getMapTo(), headers);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//List<String> result = new ArrayList<String>(jsonObject.toMap().keySet());
		return jsonObject.toMap();
	}


	/**
	 * 
	 * @param domain
	 * @param target
	 * @return
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	private JSONObject doURLRedirect(String domain, Map<String, Object>target) {
		String inputLine;
		StringBuffer responseBuffer = null;
		URL obj;
		HttpURLConnection con = null;
		int responseCode = 0;
		
		//JSONArray groupsArray = null;
		JSONObject jsonObject = null;
		
		Iterator entries = target.entrySet().iterator();
		String key;
		String value;
		
		try {
			obj = new URL(domain);
			con = (HttpURLConnection) obj.openConnection(); 
			con.setRequestProperty("Content-Type", "application/json");
			
			while (entries.hasNext()) {
				HashMap.Entry entry = (HashMap.Entry) entries.next();
				key = (String)entry.getKey();
				value = (String)entry.getValue();
				if (!key.equals("host"))
					con.setRequestProperty(key, value);

			}
			//con.setRequestMethod("GET");
			con.connect();
			System.out.println(con.getURL());
			responseCode = con.getResponseCode();
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));

			responseBuffer = new StringBuffer();


			while ((inputLine = in.readLine()) != null) {
				responseBuffer.append(inputLine);
			}

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		
		System.out.println(responseCode);

		if (responseBuffer != null)
			System.out.println("The response is ===>" + responseBuffer.toString());
		
		jsonObject = new JSONObject(responseBuffer.toString());
		return jsonObject;
	}

}
