package eu.choreos.vv.exceptions;

/**
 * This exception is thrown if a mock is created without being defined at 
 * least one response
 * 
 *  @author Felipe Besson
 */
public class NoMockResponseException extends Exception{
	private static final long serialVersionUID = 1L;

	public NoMockResponseException(String message){
		super(message);
	}

}
