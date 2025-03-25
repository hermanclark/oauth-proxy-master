package org.nwea.oauthproxy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * ApplicationNotFoundException- application not found
 * 
 * @author Herman Clark
 *
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Application not found")
public class ApplicationNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3931369082812162563L;

}
