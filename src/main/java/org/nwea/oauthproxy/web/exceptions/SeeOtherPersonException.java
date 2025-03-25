package org.nwea.oauthproxy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * SeeOtherPersonException - MovedTo <user id> in Person collection
 * 
 * @author Herman Clark
 *
 */
@ResponseStatus(value=HttpStatus.SEE_OTHER)
public class SeeOtherPersonException extends RuntimeException{


	public SeeOtherPersonException(String message) {
		super(message);
	}
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3990753092475015444L;

	
}
