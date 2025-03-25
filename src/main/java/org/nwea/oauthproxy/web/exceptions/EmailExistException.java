package org.nwea.oauthproxy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * EmailExistsException - Duplicate user id in Person collection
 * 
 * @author Herman Clark
 *
 */

@ResponseStatus(value=HttpStatus.CONFLICT)
public class EmailExistException extends RuntimeException{
	
	public EmailExistException (String message) {
		super("This email address already exist: " + message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6876442443730231312L;


}