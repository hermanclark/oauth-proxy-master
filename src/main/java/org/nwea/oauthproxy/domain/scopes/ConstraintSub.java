package org.nwea.oauthproxy.domain.scopes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ConstraintSub class  - for Scopes context
 * 
 * @author Herman Clark
 * @since 1.0.5.12
 */

public class ConstraintSub {
	//@Id 
	//@JsonIgnore
	private String scope;
	
	@JsonProperty("field")
	private String field;

	@JsonProperty("fieldOperator")
	private String fieldOperator;
	
	@JsonProperty("compareTo")
	private String compareTo;
	
	@JsonProperty("compareMath")
	private String compareMath;
	
	@JsonProperty("compareConstant")
	private String compareConstant;
	
	@JsonProperty("createdDate")
	private String createdDate;
	
	@JsonProperty("lastModifiedDate")
	private String lastModifiedDate;

	/**
	 * @return the _id
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param id the id to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the fieldOperator
	 */
	public String getFieldOperator() {
		return fieldOperator;
	}

	/**
	 * @param fieldOperator the fieldOperator to set
	 */
	public void setFieldOperator(String fieldOperator) {
		this.fieldOperator = fieldOperator;
	}

	/**
	 * @return the compareTo
	 */
	public String getCompareTo() {
		return compareTo;
	}

	/**
	 * @param compareTo the compareTo to set
	 */
	public void setCompareTo(String compareTo) {
		this.compareTo = compareTo;
	}

	/**
	 * @return the compareMath
	 */
	public String getCompareMath() {
		return compareMath;
	}

	/**
	 * @param compareMath the compareMath to set
	 */
	public void setCompareMath(String compareMath) {
		this.compareMath = compareMath;
	}

	/**
	 * @return the compareConstant
	 */
	public String getCompareConstant() {
		return compareConstant;
	}

	/**
	 * @param compareConstant the compareConstant to set
	 */
	public void setCompareConstant(String compareConstant) {
		this.compareConstant = compareConstant;
	}

	/**
	 * @return the cretedDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param cretedDate the cretedDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	
	/**
	 * 
	 * ConstraintsSub constructor - for the scope(s)
	 * @param scope
	 * @param field
	 * @param fieldOperator
	 * @param compareTo
	 * @param compareMath
	 * @param compareConstant
	 * @param createdDate
	 * @param lastModifiedDate
	 */
	public ConstraintSub(String scope,
			String field,
			String fieldOperator,
			String compareTo,
			String compareMath,
			String compareConstant,
			String createdDate,
			String lastModifiedDate){
		this.scope = scope;
		this.field = field;
		this.fieldOperator = fieldOperator;
		this.compareTo = compareTo;
		this.compareMath = compareMath;
		this.compareConstant = compareConstant;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public ConstraintSub() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return String.format("Constrains[scope='%s', field='%s', fieldOperator='%s', compareTo='%s', compareMath='%s', compareConstant='%s', createdDate='%s', lastModifiedDate='%s']",
				scope, field, fieldOperator, compareTo, compareMath, compareConstant, createdDate, lastModifiedDate);
	}
}
