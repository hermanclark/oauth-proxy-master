package org.nwea.oauthproxy.domain.mappings;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@Document(collection = "mappings")
public class NWEAMappings {

	@JsonIgnore
	@Id 
	private String id;

	@JsonProperty("targetId")
	private String targetId;

	@JsonProperty("mappingId")
	private String mappingId;

	@JsonProperty("requestURL")
	private String requestURL;

	@JsonProperty("mapTo")
	private String mapTo;

	@JsonProperty("modified")
	private Integer modified;

	@JsonProperty("created")
	private Integer created;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the targetId
	 */
	public String getTargetId() {
		return targetId;
	}

	/**
	 * @param targetId the targetId to set
	 */
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	/**
	 * @return the redirectId
	 */
	public String getMappingId() {
		return mappingId;
	}

	/**
	 * @param redirectId the redirectId to set
	 */
	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

	/**
	 * @return the requestURL
	 */
	public String getRequestURL() {
		return requestURL;
	}

	/**
	 * @param requestURL the requestURL to set
	 */
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	/**
	 * @return the mapTo
	 */
	public String getMapTo() {
		return mapTo;
	}

	/**
	 * @param mapTo the mapTo to set
	 */
	public void setMapTo(String mapTo) {
		this.mapTo = mapTo;
	}

	/**
	 * @return the lastModified
	 */
	public Integer getModified() {
		return modified;
	}

	/**
	 * @param lastModified the lastModified to set
	 */
	public void setModified(Integer modified) {
		this.modified = modified;
	}

	/**
	 * @return the created
	 */
	public Integer getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Integer created) {
		this.created = created;
	}

	/**
	 * 
	 */
	public NWEAMappings (){
	}

	/**
	 * @param id
	 * @param targetId
	 * @param redirectId
	 * @param requestURL
	 * @param mapTo
	 * @param modified
	 * @param created
	 */

	public NWEAMappings (String id,
			String targetId,
			String redirectId,
			String requestURL,
			String mapTo,
			Integer created,
			Integer modified) {
		this.id = id;
		this.targetId = targetId;
		this.mappingId = redirectId;
		this.requestURL = requestURL;
		this.mapTo = mapTo;
		this.created = created;
		this.modified = modified;

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	@Override
	public String toString(){
		return String.format("Mapping[id='%s', targetId='%s', redirectId='%s', requestURL='%s', mapTo='%s'']",
				id, targetId, mappingId, requestURL, mapTo);
	}
}
