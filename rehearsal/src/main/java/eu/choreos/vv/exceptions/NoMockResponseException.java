package eu.choreos.vv.exceptions;

public class NoMockResponseException extends Exception{
	
	/**
	 * This exception is thrown if a mock is created without being defined at 
	 * least one response
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoMockResponseException(String message){
		super(message);
	}

}
