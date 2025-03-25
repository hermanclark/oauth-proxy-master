package org.nwea.oauthproxy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * EmptyResourcePathException - undefined "resourcePath" in Scopes request body
 * 
 * @author Herman Clark
 *
 */
@ResponseStatus(value=HttpStatus.NO_CONTENT, reason="Empty resourcePath field")
public class EmptyResourcePathException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7964200068278968093L;	
	
}
