package org.nwea.oauthproxy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * InvalidEpochDateFormat - Invalid request for lastModifiedDate
 * 
 * @author Herman Clark
 *
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid Epoch Date Format")
public class InvalidEpochDateFormat extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2911817823443228094L;
	
}