package org.nwea.oauthproxy.domain.target;

import javax.annotation.Generated;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * TargetHeader class - used with Target for grouping  http headers
 * or more specifically an HttpServletResponse setHeaders() and setDomain()
 * 
 * @author Herman Clark
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@Document
public class TargetHeader {
	@JsonProperty("name")
	private String name;

	@JsonProperty("value")
	private String value;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
