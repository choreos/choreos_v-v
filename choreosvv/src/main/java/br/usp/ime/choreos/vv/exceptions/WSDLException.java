package br.usp.ime.choreos.vv.exceptions;

public class WSDLException extends FrameworkException {

	private static final long serialVersionUID = 2726146634585392449L;

	public WSDLException(Exception originalException) {
		super(originalException);
	}

	public WSDLException(String message) {
	        super(message);
        }

}
