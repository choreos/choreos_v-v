package eu.choreos.vv.servicesimulator;

import com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation;

public class MockOperation {
	
	private WsdlMockOperation soapUIMockOperation;

	public MockOperation(WsdlMockOperation soapUIMockOperation) {
		this.soapUIMockOperation = soapUIMockOperation;
	}

	public String getName() {
		return soapUIMockOperation.getName();
	}

}
