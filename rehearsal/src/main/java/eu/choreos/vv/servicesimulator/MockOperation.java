package eu.choreos.vv.servicesimulator;

import com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockResponse;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.SoapEnvelopeHelper;
import eu.choreos.vv.common.ItemBuilder;
import eu.choreos.vv.exceptions.ParserException;

public class MockOperation {
	
	private WsdlMockOperation soapUIMockOperation;

	public MockOperation(WsdlMockOperation soapUIMockOperation) {
		this.soapUIMockOperation = soapUIMockOperation;
	}

	public String getName() {
		return soapUIMockOperation.getName();
	}
	
	public void addResponse(String... parameters){
		WsdlMockResponse response = soapUIMockOperation.addNewMockResponse("response 1", true);
		String defaultResponse = response.getResponseContent();
		response.setResponseContent(SoapEnvelopeHelper.generate(defaultResponse, parameters));	
	}
	
	public void addResponse(Item root) throws ParserException{
		WsdlMockResponse response = soapUIMockOperation.addNewMockResponse("response 1", true);
		String defaultResponse = response.getResponseContent();
		response.setResponseContent(new ItemBuilder().buildItem(defaultResponse, root));
	}

}
