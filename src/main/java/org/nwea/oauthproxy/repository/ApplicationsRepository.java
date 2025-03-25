package org.nwea.oauthproxy.repository;


import org.nwea.oauthproxy.domain.applications.Applications;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@EnableMongoRepositories
@Configuration
@RepositoryRestResource(collectionResourceRel = "applications", path = "applications")

/**
 * ApplicationsRepository Interface
 * 
 * @author Herman Clark
 * 
 */
public interface ApplicationsRepository extends MongoRepository<Applications, String> {

	Applications findByApplicationId(@Param("applicationId") String applicationId);
	Applications findByApplicationName(@Param("applicationName") String applicationName);
	Applications findByClientId(@Param("clientId") String clientId);
	Applications findById(@Param("id") String id);
}