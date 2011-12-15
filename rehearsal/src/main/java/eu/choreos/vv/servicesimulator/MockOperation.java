package eu.choreos.vv.servicesimulator;

import com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockResponse;

import eu.choreos.vv.exceptions.ParserException;

/**
 * This class represents a Mock Operation
 * @author Felipe Besson
 *
 */
public class MockOperation {
	
	private WsdlMockOperation soapUIMockOperation;
	private String defaultResponse;
	private WsdlMockResponse soapUIResponse;
    private ScriptBuilder builder;
	
    /**
     * Receives the need information for mocking an operation
     * 
     * @param defaultRequest envelope
     * @param soapUIMockOperation object which is used for mocking the operation
     */
	public MockOperation(String defaultRequest, WsdlMockOperation soapUIMockOperation) {
		this.soapUIMockOperation = soapUIMockOperation;
		 soapUIResponse = soapUIMockOperation.addNewMockResponse("response 1", true);
		defaultResponse =  soapUIResponse.getResponseContent();
		builder = new ScriptBuilder();
		builder.setDefaultRequest(defaultRequest);
		builder.setDefaultResponse(defaultResponse);
		soapUIResponse.setResponseContent("${message}");
	}

	/**
	 * Gets the operation name
	 * 
	 * @return operation name
	 */
	public String getName() {
		return soapUIMockOperation.getName();
	}
	
	/**
	 * Gets the SoapUI MockOperation object that represents the operation
	 * 
	 * @return SoapUI MockOperation 
	 */
	public WsdlMockOperation getSoapUIMockOperation(){
		return soapUIMockOperation;
	}
	
	/**
	 * Adds a valid response to be returned for the mocked operation
	 * 
	 * @param mockResponse
	 * @throws ParserException
	 */
	public void addResponse(MockResponse mockResponse) throws ParserException{
		builder.addConditionFor(mockResponse);
		soapUIResponse.setScript(builder.getScript());
	}

	/**
	 * Configures the mocked operation to do not respond
	 */
	public void doNotResponse() {
		String timeoutScript = "Thread.sleep(300000)";
		soapUIResponse.setScript(timeoutScript);
	}
	
}
