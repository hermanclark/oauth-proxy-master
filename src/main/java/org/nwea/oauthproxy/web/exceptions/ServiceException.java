package org.nwea.oauthproxy.web.exceptions;

public class ServiceException extends RuntimeException {

  
    /**
	 * 
	 */
	private static final long serialVersionUID = -933797376297998576L;
	
	private String errorCode;

    public ServiceException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode(){
    	return errorCode;
    }

    // other constructors, getter and setters omitted
}
