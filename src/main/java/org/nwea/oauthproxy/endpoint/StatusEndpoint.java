package org.nwea.oauthproxy.endpoint;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.nwea.oauthproxy.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Component
@PropertySources({
        @PropertySource("classpath:META-INF/build-info.properties")
})
public class StatusEndpoint implements Endpoint<OAuthProxyStatus> {

    @Value("${info.build.version:null}")
    private String version;

    @Value("${build.time:null}")
    String buildTime;

    @Value("${BUILDDATE:null}")
    private String buildDate;

    @Value("${DEPLOYDATE:null}")
    private String deployDate;

    @Autowired
    private Environment env;

    @Autowired
    private MongoProperties properties;
    @Value("${spring.data.mongodb.uri}")
    private String mongoURI;


    @PostConstruct
    public void init() {
        version = Application.class.getPackage().getImplementationVersion();
    }


    @Override
    public String getId() {
        return "status/details";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isSensitive() {

        return false;
    }



    private String getMongoDetails(String value) {
        MongoClient mongoClient = null;

        MongoClientURI mc = null;
        DB db = null;

        mc = new MongoClientURI(mongoURI);
        if (mongoClient == null)
            try {
                mongoClient = properties.createMongoClient(mc.getOptions(), env);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        db = mongoClient.getDB(mc.getDatabase());
        CommandResult cr = db.command("buildInfo");
        return cr.getString(value);
    }



    @Override
    public OAuthProxyStatus invoke() {
    		OAuthProxyStatus oauthProxyStaus = new OAuthProxyStatus();
    		oauthProxyStaus.setName("Profile API");
        //System.out.println(" FROM Invoke-----" + version);
    		oauthProxyStaus.setVersion(version);
    		oauthProxyStaus.setBuildDate(buildTime);
    		oauthProxyStaus.setDeployDate(deployDate);
    		oauthProxyStaus.setSuccessful(true);
        
        /*
         * January 2, 2018
         * 
         * buildTime to epochDate 
         *

        String input = buildTime;
        try {
            DateTimeFormatter formatter =
                              DateTimeFormatter.ofPattern("yyyy MM dd HH mm ss A");
            LocalDate date = LocalDate.parse(input, formatter);
            System.out.printf("%s%n", date);
        }
        catch (DateTimeParseException exc) {
            System.out.printf("%s is not parsable!%n", input);
            throw exc;      // Re throw the exception.
        }
         */
        
        MongDBStatus dependencyMongoRead = new MongDBStatus();
        dependencyMongoRead.setName("Mongo Read");
        dependencyMongoRead.setVersion(getMongoDetails("version"));
        dependencyMongoRead.setErrors(getMongoDetails("validate.errors"));
        dependencyMongoRead.setBuildDate(buildTime);
        dependencyMongoRead.setDeployDate(deployDate);
        dependencyMongoRead.setSuccessful(true);

        MongDBStatus dependencyMongoWrite = new MongDBStatus();
        dependencyMongoWrite.setName("Mongo Write");
        dependencyMongoWrite.setVersion(getMongoDetails("version"));
        dependencyMongoWrite.setErrors(getMongoDetails("validate.errors"));
        dependencyMongoWrite.setBuildDate(buildTime);
        dependencyMongoWrite.setDeployDate(deployDate);
        dependencyMongoWrite.setSuccessful(true);


        List<MongDBStatus> dependencyList = new ArrayList<>();
        dependencyList.add(dependencyMongoRead);
        dependencyList.add(dependencyMongoWrite);

        oauthProxyStaus.setDependencies(dependencyList);

        return oauthProxyStaus;
    }
}
