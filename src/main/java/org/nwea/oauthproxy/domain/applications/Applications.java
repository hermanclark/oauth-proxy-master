package org.nwea.oauthproxy.domain.applications;

import javax.annotation.Generated;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Applications class - Entity class for Applications Document
 * 
 * @author 		Herman Clark
 * @version		1.0
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@Document(collection = "applications")
public class Applications {

	@JsonProperty("id")
	@Id 
	private String id;

	@JsonProperty("applicationId")
	private String applicationId;
	
	@JsonProperty("applicationName")
	private String applicationName;
	
	@JsonProperty("clientId")
	private String clientId;
	
	@JsonProperty("active")
	private String active;
	
	@JsonProperty("clientSecret")
	private String clientSecret;
	
	@JsonProperty("apiGroupId")
	private String apiGroupId;
	
	@JsonProperty("modified")
	private String modified;

	@JsonProperty("created")
	private String created;
	
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
	 * @return the applicationId
	 */
	public String getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * @param applicationName the applicationName to set
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * @return the clientID
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientID the clientID to set
	 */
	public void setClientID(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the clientSecret
	 */
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * @param clientSecret the clientSecret to set
	 */
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	/**
	 * @return the apiGroupId
	 */
	public String getApiGroupId() {
		return apiGroupId;
	}

	/**
	 * @param apiGroupId the apiGroupId to set
	 */
	public void setApiGroupId(String apiGroupId) {
		this.apiGroupId = apiGroupId;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the modified
	 */
	public String getModified() {
		return modified;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(String modified) {
		this.modified = modified;
	}

	/**
	 * Constructor
	 */
	public Applications (){
	}
	
	/**
	 * 
	 * Applications constructor
	 * @param id
	 * @param applicationId
	 * @param applicationName
	 * @param clientId
	 * @param active
	 * @param clientSecret
	 * @param apiGroupId
	 * @param created
	 * @param modified
	 * 
	 */
	@PersistenceConstructor
	public Applications (String id,
			String applicationId,
			String applicationName,
			String clientId,
			String active,
			String clientSecret,
			String apiGroupId,
			String created,
			String modified) {
		this.id = id;
		this.applicationId = applicationId;
		this.applicationName = applicationName;
		this.clientId = clientId;
		this.active = active;
		this.clientSecret = clientSecret;
		this.apiGroupId = apiGroupId;
		this.created = created;
		this.modified = modified;
	}
	


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return String.format("Applications[id='%s', applicationId='%s', applicationName='%s', clientId='%s', active='%s', clientSecret='%s', apiGroupId='%s', created='%s', modified='%s' ]",
				id, applicationId);
	}

}

