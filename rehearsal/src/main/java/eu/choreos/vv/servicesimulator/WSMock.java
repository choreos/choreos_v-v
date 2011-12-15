package eu.choreos.vv.servicesimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation;

import eu.choreos.vv.common.MockProject;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.NoMockResponseException;
import eu.choreos.vv.exceptions.ParserException;

/**
 * This class provides the Service Mocking features
 * @author besson
 *
 */
public class WSMock extends MockProject {

	private HashMap<String, MockOperation> operations;

	/**
	 * 
	 * @param name (address) in which the mocked service will be published 
	 * @param  wsdl of the service that will be mocked
	 * @throws Exception
	 */
	public WSMock(String name, String wsdl) throws Exception {
		super(name, wsdl);
		operations = new HashMap<String, MockOperation>();
		createMockOperations();
	}

	/**
	 * Mocks each operation found in the real service WSDL
	 */
	private void createMockOperations() {
		for (int i = 0; i < iface.getOperationCount(); i++) {
			WsdlMockOperation soapUIMockOperation = service.addNewMockOperation(iface.getOperationAt(i));
			soapUIMockOperation.setDispatchStyle("SCRIPT");
			String defaultRequest = iface.getOperationAt(i).getRequestAt(0).getRequestContent();
			MockOperation rehearsalMockOperation = new MockOperation(defaultRequest, soapUIMockOperation);
			operations.put(soapUIMockOperation.getName(), rehearsalMockOperation);
		}
	}

	/**
	 * Retrieves all mocked operations
	 * 
	 * @return a list containing all operations beloging to a WSMock object
	 */
	public List<MockOperation> getOperations() {
		return new ArrayList<MockOperation>(operations.values());
	}

	/**
	 * Defines which response(s) the WSMock must return for a specific operation
	 * 
	 * @param mock operation 
	 * @param mockResponses specify which responses must be returned when the mock operation is requested
	 * @throws ParserException
	 * @throws InvalidOperationNameException
	 * @throws NoMockResponseException
	 */
	public void returnFor(String operation, MockResponse... mockResponses) throws ParserException, InvalidOperationNameException, NoMockResponseException {
		if (!operations.containsKey(operation))
			throw new InvalidOperationNameException();
		
		if (mockResponses.length == 0)
			throw new NoMockResponseException("No mock response was defined for the operation " + operation);

		MockOperation mockedOperation = operations.get(operation);

		for (MockResponse mockResponse : mockResponses)
			mockedOperation.addResponse(mockResponse);
	}

	/**
	 * Configures  WSMock to do not respond to a specific operation
	 * 
	 * @param operation
	 * @throws InvalidOperationNameException
	 */
	public void doNotRespond(String operation) throws InvalidOperationNameException {
		if (!operations.containsKey(operation))
			throw new InvalidOperationNameException();
		
		MockOperation mockedOperation = operations.get(operation);
		mockedOperation.doNotResponse();
	}

	/**
	 * Configures WSMock to do not respond to any operation
	 */
	public void doNotRespondAll() {
		for (MockOperation entry : operations.values()) 
			entry.doNotResponse();
	}

	/**
	 * Configures WSMock to simulate a crash behavior when a specific operation is invoked
	 * 
	 * @param operation
	 * @throws InvalidOperationNameException
	 */
	public void crash(String operation) throws InvalidOperationNameException {
		if (!operations.containsKey(operation))
			throw new InvalidOperationNameException();
		
		service.removeMockOperation(operations.get(operation).getSoapUIMockOperation());		
	}

	/**
	 * Configures WSMock to simulate a crash behavior when any operation is invoked
	 */
	public void crashAll() {
		for (MockOperation entry : operations.values()) 
			service.removeMockOperation(entry.getSoapUIMockOperation());
	}
}