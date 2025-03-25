package org.nwea.oauthproxy.domain.auth;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auth class - Entity class for Auth
 * 
 * @author 		Herman Clark
 * @version		1.0
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")

public class Auth {
	@JsonProperty("access_token")
	private String access_token;
	
	@JsonProperty("token_type")
	private String token_type;
	
	@JsonProperty("expires_in")
	private String expires_in;
	
	@JsonProperty("refresh_token")
	private String refresh_token;

	/**
	 * @return the access_token
	 */
	public String getAccess_token() {
		return access_token;
	}

	/**
	 * @param access_token the access_token to set
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * @return the token_type
	 */
	public String getToken_type() {
		return token_type;
	}

	/**
	 * @param token_type the token_type to set
	 */
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	/**
	 * @return the expires_in
	 */
	public String getExpires_in() {
		return expires_in;
	}

	/**
	 * @param expires_in the expires_in to set
	 */
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	/**
	 * @return the refresh_token
	 */
	public String getRefresh_token() {
		return refresh_token;
	}

	/**
	 * @param refresh_token the refresh_token to set
	 */
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	
}
