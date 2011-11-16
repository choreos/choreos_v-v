package eu.choreos.vv.servicesimulator;

import com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockResponse;

import eu.choreos.vv.exceptions.ParserException;

public class MockOperation {
	
	private WsdlMockOperation soapUIMockOperation;
	private String defaultResponse;
	private WsdlMockResponse soapUIResponse;
    private ScriptBuilder builder;
	
	public MockOperation(String defaultRequest, WsdlMockOperation soapUIMockOperation) {
		this.soapUIMockOperation = soapUIMockOperation;
		 soapUIResponse = soapUIMockOperation.addNewMockResponse("response 1", true);
		defaultResponse =  soapUIResponse.getResponseContent();
		builder = new ScriptBuilder();
		builder.setDefaultRequest(defaultRequest);
		builder.setDefaultResponse(defaultResponse);
		soapUIResponse.setResponseContent("${message}");
	}

	public String getName() {
		return soapUIMockOperation.getName();
	}
	
	public WsdlMockOperation getSoapUIMockOperation(){
		return soapUIMockOperation;
	}
	
	public void addResponse(MockResponse mockResponse) throws ParserException{
		builder.addConditionFor(mockResponse);
		soapUIResponse.setScript(builder.getScript());
	}

	public void doNotResponse() {
		String timeoutScript = "Thread.sleep(300000)";
		soapUIResponse.setScript(timeoutScript);
	}
	
}
