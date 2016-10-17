package org.tat.api.core.exceptions;

/**
 * In case of invalid search query
 * @author satish
 *
 */
public class InvalidSearchQueryException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private String message ;
	
	public InvalidSearchQueryException(String input){
		this. message = "400-002:Search input is invalid - " + input;
	}
	@Override
	public String getMessage() {
		
		return this.message;
	}

}
