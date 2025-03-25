package org.nwea.oauthproxy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * PermissionsException - throw 403 "Forbidden" exception
 * 
 * @author 	Herman Clark
 * @since 	1.0.2
 *
 */
@ResponseStatus(value=HttpStatus.FORBIDDEN)
public class PermissionsException extends Exception {
	
	public PermissionsException(String message) {
		super(message);
	}
		

	/**
	 * 
	 */
	private static final long serialVersionUID = -7510347171070093298L;

	
	
}
