package com.minsait.architecture.model.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 *	Class modeling an error response including code, type and (optionally) a description
 */
@JsonInclude(Include.NON_NULL)
@Data
public class ResponseError {

	private String type;
	
	private String alias;
	
	private String description;
	
	/**
	 * Creates an error response that includes alias, type and description
	 * 
	 * @param alias			error alias
	 * @param type			error type
	 * @param description	error description
	 */
	public ResponseError(String alias, String type, String description) {
		this.alias = alias;
		this.type = type;
		this.description = description;
	}
	
	/**
	 * Creates an error response that includes alias and type
	 * 
	 * @param alias			error alias
	 * @param type			error type
	 */
	public ResponseError(String alias, String type) {
		this(alias, type, null);
	}
	
	public ResponseError() {}
}
