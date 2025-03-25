package org.nwea.oauthproxy.domain.scopes;

import java.util.List;

import org.nwea.oauthproxy.domain.scopes.ConstraintSub;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * Scopes class - Entity class for Scopes Document
 * 
 * @author 		Herman Clark
 * @version 	1.0.5.12
 *
 */

public class Scopes {
	@Id 
	@JsonProperty("scopeId")
	private String scopeId;

	@Field
	@JsonView
	@JsonProperty("displayAs")
	private String displayAs;
	

	@Field
	@JsonView
	@JsonProperty("constraints")
	private List<ConstraintSub> constraints;
	
	@JsonProperty("resources")
	@Field
	private List<Resources> resources;
	
	/*
	 * 
	 */
	public Scopes(){
	}
	
	public Scopes(String scopeId,
			String displayAs,
			List<ConstraintSub> constraints){
		this.scopeId = scopeId;
		this.constraints = constraints;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	@Override
	public String toString(){
		return String.format("Constraint[constraintId='%s', displayAs='%s', constraints='%s']",
				scopeId, displayAs, constraints);
	}
			


	/**
	 * @return the id
	 */
	public String getScopeId() {
		return scopeId;
	}


	/**
	 * @param id the id to set
	 */
	public void setScopeId(String constraintId) {
		this.scopeId = scopeId;
	}


	/**
	 * @return the displayAs
	 */
	public String getDisplayAs() {
		return displayAs;
	}


	/**
	 * @param displayAs the displayAs to set
	 */
	public void setDisplayAs(String displayAs) {
		this.displayAs = displayAs;
	}


	/**
	 * @return the constraints
	 */
	public List<ConstraintSub> getConstraints() {
		return constraints;
	}


	/**
	 * @param constraints the constraints to set
	 */
	public void setConstraints(List<ConstraintSub> constraints) {
		this.constraints = constraints;
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
}
