package eu.choreos.vv.servicesimulator;

import java.util.Map;

import com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockResponse;

import eu.choreos.vv.exceptions.ParserException;

public class MockOperation {
	
	private WsdlMockOperation soapUIMockOperation;
	private Map<MockResponse, WsdlMockResponse> responses;
	private String defaultResponse;

	public MockOperation(WsdlMockOperation soapUIMockOperation) {
		this.soapUIMockOperation = soapUIMockOperation;
		responses = new MockResponseMap();
	}

	public String getName() {
		return soapUIMockOperation.getName();
	}
	
	public void addResponse(MockResponse mockResponse) throws ParserException{
		WsdlMockResponse soapUIResponse;
		
		if (!responses.containsKey(mockResponse)){
			soapUIResponse = soapUIMockOperation.addNewMockResponse("response 1", true);
			responses.put(mockResponse, soapUIResponse);
			defaultResponse =  soapUIResponse.getResponseContent();
		}
		else
			soapUIResponse = responses.get(mockResponse);

		soapUIResponse.setResponseContent(mockResponse.buildContent(defaultResponse));	
	}
	
}
