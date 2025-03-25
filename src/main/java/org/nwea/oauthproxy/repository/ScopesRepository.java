package org.nwea.oauthproxy.repository;

import java.util.List;

import org.nwea.oauthproxy.domain.scopes.Scopes;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * ScopesRepository interface - extension of Spring Data repository abstraction Repository
 * Uses the Scopes domain class to manage CRUD operations on the scopes collection
 * 
 * @author 	Herman Clark
 * @since 	1.0.5.12
 *
 */
@EnableMongoRepositories
@Configuration
@RepositoryRestResource(collectionResourceRel = "scopes", path = "scopes")
public interface ScopesRepository extends MongoRepository<Scopes, String> {
	Scopes findByScopeId(@Param("scopeId") String scopeId);
	Page<Scopes>findAll(Pageable page);
	
}
