package org.nwea.oauthproxy.domain.apigroup;

import java.util.List;
import javax.annotation.Generated;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Resource class - used with ApiGroup for grouping a resource to http verbs
 * or more specifically an HttpServletRequest getRequestURI() to getMethod()
 * 
 * @author Herman Clark
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@Document
public class Resources {
	
	@JsonProperty("resource")
	private String resource;
	
	@JsonProperty("verbs")
	private List<String> verbs;

	/**
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
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
