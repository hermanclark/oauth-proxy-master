package org.nwea.oauthproxy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * InvalidQueryException - Invalid request for role(s) information
 * 
 * @author Herman Clark
 *
 */
@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
public class InvalidQueryException extends RuntimeException{
	
	public InvalidQueryException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -611427428240949344L;


	
}