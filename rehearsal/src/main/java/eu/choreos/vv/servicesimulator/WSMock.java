package eu.choreos.vv.servicesimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation;

import eu.choreos.vv.common.MockProject;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.NoMockResponseException;
import eu.choreos.vv.exceptions.ParserException;

public class WSMock extends MockProject {

	private HashMap<String, MockOperation> operations;

	public WSMock(String name, String wsdl) throws Exception {
		super(name, wsdl);
		operations = new HashMap<String, MockOperation>();
		createMockOperations();
	}

	private void createMockOperations() {
		for (int i = 0; i < iface.getOperationCount(); i++) {
			WsdlMockOperation soapUIMockOperation = service.addNewMockOperation(iface.getOperationAt(i));
			soapUIMockOperation.setDispatchStyle("SCRIPT");
			String defaultRequest = iface.getOperationAt(i).getRequestAt(0).getRequestContent();
			MockOperation rehearsalMockOperation = new MockOperation(defaultRequest, soapUIMockOperation);
			operations.put(soapUIMockOperation.getName(), rehearsalMockOperation);
		}
	}

	public List<MockOperation> getOperations() {
		return new ArrayList<MockOperation>(operations.values());
	}

	public void returnFor(String operation, MockResponse... mockResponses) throws ParserException, InvalidOperationNameException, NoMockResponseException {
		if (!operations.containsKey(operation))
			throw new InvalidOperationNameException();
		
		if (mockResponses.length == 0)
			throw new NoMockResponseException("No mock response was defined for the operation " + operation);

		MockOperation mockedOperation = operations.get(operation);

		for (MockResponse mockResponse : mockResponses)
			mockedOperation.addResponse(mockResponse);
	}

	public void doNotRespond(String operation) throws InvalidOperationNameException {
		if (!operations.containsKey(operation))
			throw new InvalidOperationNameException();
		
		MockOperation mockedOperation = operations.get(operation);
		mockedOperation.doNotResponse();
	}

	public void doNotRespondAll() {
		for (MockOperation entry : operations.values()) 
			entry.doNotResponse();
	}

	public void crash(String operation) throws InvalidOperationNameException {
		if (!operations.containsKey(operation))
			throw new InvalidOperationNameException();
		
		service.removeMockOperation(operations.get(operation).getSoapUIMockOperation());		
	}

	public void crashAll() {
		for (MockOperation entry : operations.values()) 
			service.removeMockOperation(entry.getSoapUIMockOperation());
	}
}