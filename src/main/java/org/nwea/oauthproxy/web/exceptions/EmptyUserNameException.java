package org.nwea.oauthproxy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * EmptyUserNameException - undefined "userName" in request body
 * 
 * @author Herman Clark
 *
 */
@ResponseStatus(value=HttpStatus.NO_CONTENT, reason="userName undefined")
public class EmptyUserNameException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -673856185346909274L;
}
