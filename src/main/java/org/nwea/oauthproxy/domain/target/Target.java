package org.nwea.oauthproxy.domain.target;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Target class - Entity class for a Target
 * 
 * @author 		Herman Clark
 * @version 	1.0
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "target")
public class Target {
	//@JsonIgnore
	@Id 
	private String id;

	@JsonProperty("targetId")
	@Field
	private String targetId;

	@JsonProperty("description")
	@Field
	private String description;

	@JsonProperty("domain")
	@Field
	private String domain;

	@JsonProperty("headers")
	@Field
	private List<TargetHeader> headers;

	@JsonProperty("modified")
	@Field
	private Integer modified;

	@JsonProperty("created")
	@Field
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the headers
	 */
	public List<TargetHeader> getHeaders() {
		return headers;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(List<TargetHeader> headers) {
		this.headers = headers;
	}

	/**
	 * @return the modified
	 */
	public Integer getModified() {
		return modified;
	}

	/**
	 * @param modified the modified to set
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
	public Target (){
	}

	/**
	 * @param id
	 * @param targetId
	 * @param domain
	 * @param headers
	 * @param description
	 * @param modified
	 * @param created
	 */

	public Target (String id,
			String targetId,
			String domain,
			List<TargetHeader> headers,
			String description,
			Integer created,
			Integer modified) {
		this.id = id;
		this.targetId = targetId;
		this.domain = domain;
		this.headers = headers;
		this.description = description;
		this.created = created;
		this.modified = modified;

	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	@Override
	public String toString(){
		return String.format("Target[id='%s', targetId='%s', domain='%s', headers='%s', description='%s'']",
				id, targetId, domain, headers, description);
	}

}
