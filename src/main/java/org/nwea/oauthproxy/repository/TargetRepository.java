package org.nwea.oauthproxy.repository;


import org.nwea.oauthproxy.domain.target.Target;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * TargetRepository interface - extension of Spring Data repository abstraction Repository
 * Uses the Docs domain class to manage CRUD operations on the target collection
 * 
 * @author Herman Clark
 *
 */
@EnableMongoRepositories
@Configuration
@RepositoryRestResource(collectionResourceRel = "target", path = "target")

public interface TargetRepository extends MongoRepository<Target, String>{
	Target findById(@Param("id") String id);
	Target findByTargetId(@Param("targetId") String targetId);
	Target findByDomain(@Param("domain") String domain);
}
