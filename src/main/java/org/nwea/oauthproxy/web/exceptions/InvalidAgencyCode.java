package org.nwea.oauthproxy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserAlreadyExistsException - Duplicate user id in Person collection
 * 
 * @author Herman Clark
 *
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Invalid Agency Code")
public class InvalidAgencyCode extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4914954903516317919L;
	
	
}