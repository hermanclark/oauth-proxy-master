package org.nwea.oauthproxy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * NonExistException - Non valid id in request body
 * 
 * @author Herman Clark
 *
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Non valid id")
public class NonExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8874310079724515600L;
	
}
