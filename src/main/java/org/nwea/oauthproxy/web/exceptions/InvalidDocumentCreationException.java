package org.nwea.oauthproxy.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * InvalidDocumentCreationException - Invalid document creationa
 * 
 * @author Herman Clark
 *
 */
@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
public class InvalidDocumentCreationException extends RuntimeException{
	
	public InvalidDocumentCreationException(String message){
		super (message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6266213451859245117L;
	
}