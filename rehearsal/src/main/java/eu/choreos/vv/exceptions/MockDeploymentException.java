package eu.choreos.vv.exceptions;

/**
 * This exception is thrown if the service cannot be mocked due some 
 * technical problem (e.g., the address is already in use)
 * 
 * @author Felipe Besson
 */
public class MockDeploymentException extends FrameworkException {

	private static final long serialVersionUID = 1L;

	public MockDeploymentException(Exception originalException) {
		super(originalException);
	}

	public MockDeploymentException(String message) {
		super(message);
	}

}
