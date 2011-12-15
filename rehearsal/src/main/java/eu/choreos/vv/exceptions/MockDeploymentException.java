package eu.choreos.vv.exceptions;

public class MockDeploymentException extends FrameworkException {

	/**
	 * This exception is thrown if the service cannot be mocked due some 
	 * technical problem (e.g., the address is already in use)
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MockDeploymentException(Exception originalException) {
		super(originalException);
	}

	public MockDeploymentException(String message) {
		super(message);
	}

}
