package org.nwea.oauthproxy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * NoResultsException - "no content" in request body
 * 
 * @author Herman Clark
 *
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No document found")
public class NoResultsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7757440796000077635L;

}
