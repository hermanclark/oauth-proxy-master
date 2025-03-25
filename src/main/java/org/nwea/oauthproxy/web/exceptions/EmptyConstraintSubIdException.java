package org.nwea.oauthproxy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * EEmptyConstraintSubIdException- undefined "sub constraints" in Constraints request body
 * 
 * @author Herman Clark
 *
 */
@ResponseStatus(value=HttpStatus.NO_CONTENT, reason="Empty Sub Constraint Id field")
public class EmptyConstraintSubIdException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7997838812731570630L;
	
}
