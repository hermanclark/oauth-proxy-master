package org.nwea.oauthproxy.repository;

import org.nwea.oauthproxy.domain.mappings.NWEAMappings;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * MappingpRepository interface - extension of Spring Data repository abstraction Repository
 * Uses the Docs domain class to manage CRUD operations on the mapping collection
 * 
 * @author Herman Clark
 *
 */
@EnableMongoRepositories
@Configuration
@RepositoryRestResource(collectionResourceRel = "mappings", path = "mappings")

public interface MappingRepository extends MongoRepository<NWEAMappings, String>{
	NWEAMappings findById(@Param("id") String id);
	NWEAMappings findByTargetId(@Param("targetId") String targetId);
	NWEAMappings findByMappingId(@Param("mappingId") String mappingId);
	NWEAMappings findByRequestURL(@Param("requestURL") String requestURL);
}
