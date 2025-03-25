package org.nwea.oauthproxy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserAlreadyExistsException - Duplicate user id in Person collection
 * 
 * @author Herman Clark
 *
 */
@ResponseStatus(value=HttpStatus.CONFLICT, reason="This user already exists")
public class UserAlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5111120201036478712L;
	
}
