package org.nwea.oauthproxy.repository;

import org.nwea.oauthproxy.domain.apigroup.ApiGroup;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * ApiGroupRepository interface - extension of Spring Data repository abstraction Repository
 * Uses the Docs domain class to manage CRUD operations on the apiGroup collection
 * 
 * @author Herman Clark
 *
 */
@EnableMongoRepositories
@Configuration
@RepositoryRestResource(collectionResourceRel = "apigroup", path = "apigroup")
public interface ApiGroupRepository extends MongoRepository<ApiGroup, String> {

	ApiGroup findById(@Param("id") String id);
	ApiGroup findByApiGroupId(@Param("apiGroupId") String apiGroupId);
	void delete(@Param("apiGroupId") String apiGroupId);
}