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
	}

	public String getName() {
		return soapUIMockOperation.getName();
	}
	
	public void addResponse(MockResponse mockResponse) throws ParserException{
		builder.addConditionFor(mockResponse);
		soapUIResponse.setResponseContent("${message}");
		soapUIResponse.setScript(builder.getScript());
	}
	
}
