package org.nwea.oauthproxy.domain.scopes;

import java.util.List;
import javax.annotation.Generated;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Resource class - used with Scopes for grouping a resource to http verbs
 * or more specifically an HttpServletRequest getRequestURI() to getMethod()
 * 
 * @author 	Herman Clark
 * @since	1.0.5.12 - from profileApi
 * @version 1.0 
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@Document

public class Resources {
	
	@JsonProperty("resourcePath")
	private String resourcePath;
	
	@JsonProperty("verbs")
	private List<String> verbs;

	/**
	 * @return the resource
	 */
	public String getResourcePath() {
		return resourcePath;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	/**
	 * @return the verbs
	 */
	public List<String> getVerbs() {
		return verbs;
	}

	/**
	 * @param verbs the verbs to set
	 */
	public void setVerbs(List<String> verbs) {
		this.verbs = verbs;
	}

}
