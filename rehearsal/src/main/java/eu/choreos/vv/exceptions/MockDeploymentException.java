package eu.choreos.vv.exceptions;

public class MockDeploymentException extends FrameworkException {

	/**
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
