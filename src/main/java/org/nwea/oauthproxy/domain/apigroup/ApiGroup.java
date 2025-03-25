package org.nwea.oauthproxy.domain.apigroup;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ApiGroup class - Entity class for an ApiGroup
 * 
 * @author 		Herman Clark
 * @since 	1.0.2 - profileApi
 * @version	1.0
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "apigroup")
public class ApiGroup {

	@JsonIgnore
	@Id 
	private String id;
	
	@JsonProperty("apiGroupId")
	@Field
	private String apiGroupId;

	@JsonProperty("apiGroupName")
	@Field
	private String apiGroupName;
	
	@JsonProperty("resources")
	@Field
	private List<Resources> resources;
	
	@JsonProperty("modified")
	private Integer modified;

	@JsonProperty("created")
	private Integer created;
	

	/**
	 * 
	 */
	public ApiGroup (){
	}
	
	/**
	 * @param id
	 * @param apiGroupId
	 * @param apiGroupName
	 * @param resources
	 */

	public ApiGroup (String id,
			String apiGroupId,
			String apiGroupName,
			List<Resources> resources) {
		this.id = id;
		this.apiGroupId = apiGroupId;
		this.apiGroupName = apiGroupName;
		this.resources = resources;

	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	@Override
	public String toString(){
		return String.format("ApiGroup[id='%s', apiGroupId='%s', apiGroupName='%s', resources='%s'']",
				id, apiGroupId, apiGroupName, resources);
	}

	
	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the apiGroupName
	 */
	public String getApiGroupName() {
		return apiGroupName;
	}

	/**
	 * @param apiGroupName the apiGroupName to set
	 */
	public void setApiGroupName(String apiGroupName) {
		this.apiGroupName = apiGroupName;
	}

	/**
	 * @return the resources
	 */
	public List<Resources> getResources() {
		return resources;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(List<Resources> resources) {
		this.resources = resources;
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
}
