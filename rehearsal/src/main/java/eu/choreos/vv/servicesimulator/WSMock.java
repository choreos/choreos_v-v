package eu.choreos.vv.servicesimulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.xmlbeans.XmlException;

import com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemParser;
import eu.choreos.vv.common.MockProject;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.NoMockResponseException;
import eu.choreos.vv.exceptions.NoReplyWithStatementException;
import eu.choreos.vv.exceptions.ParserException;
import eu.choreos.vv.exceptions.WSDLException;
import eu.choreos.vv.interceptor.InterceptedMessagesRegistry;

/**
 * This class provides the Service Mocking features
 * @author Felipe Besson
 *
 */
public class WSMock extends MockProject {

	private HashMap<String, MockOperation> operations;
	private 	InterceptedMessagesRegistry registry;

	/**
	 * 
	 * @param name (address) in which the mocked service will be published 
	 * @param  wsdl of the service that will be mocked
	 * @throws IOException 
	 * @throws XmlException 
	 * @throws WSDLException 
	 * @throws Exception
	 */
	public WSMock(String name, String port, String wsdl) throws WSDLException, XmlException, IOException  {
		super(name, wsdl);
		setPort(port);
		operations = new HashMap<String, MockOperation>();
		createMockOperations(false);
	}
	
	
	/**
	 * 
	 * @param name (address) in which the mocked service will be published 
	 * @param  wsdl of the service that will be mocked
	 * @throws IOException 
	 * @throws XmlException 
	 * @throws WSDLException 
	 * @throws Exception
	 */
	public WSMock(String name, String port, String wsdl, boolean isInterceptor) throws WSDLException, XmlException, IOException  {
		super(name, wsdl);
		setPort(port);
		operations = new HashMap<String, MockOperation>();
		createMockOperations(isInterceptor);
		registry = InterceptedMessagesRegistry.getInstance();
		registry.registerWsdl(getWsdl());
	}

	/**
	 * Mocks each operation found in the real service WSDL
	 */
	private void createMockOperations(boolean isInterceptor) {
		for (int i = 0; i < iface.getOperationCount(); i++) {
			WsdlMockOperation soapUIMockOperation = service.addNewMockOperation(iface.getOperationAt(i));
			soapUIMockOperation.setDispatchStyle("SCRIPT");
			String defaultRequest = iface.getOperationAt(i).getRequestAt(0).getRequestContent();
			MockOperation rehearsalMockOperation = new MockOperation(defaultRequest, soapUIMockOperation, getWsdl(), isInterceptor);
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
	 * @throws NoReplyWithStatementException 
	 */
	public void returnFor(String operation, MockResponse... mockResponses) throws ParserException, InvalidOperationNameException, NoMockResponseException, NoReplyWithStatementException {
		if (!operations.containsKey(operation))
			throw new InvalidOperationNameException();
		
		if (mockResponses.length == 0)
			throw new NoMockResponseException("No mock response was defined for the operation " + operation);

		MockOperation mockedOperation = operations.get(operation);

		for (MockResponse mockResponse : mockResponses){
			
			if (!mockResponse.replyWithExists())
				throw new NoReplyWithStatementException("replyWith statement was not defined for this mock response");
			
			mockedOperation.addResponse(mockResponse);
		}	
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


	public List<Item> getInterceptedMessages() {
		List<Item> itemMessages = new ArrayList<Item>();
		List<String> xmlMessages =  registry.getMessages(getWsdl());
		ItemParser parser = new ItemParser();

		try {
			for (String xmlMessage : xmlMessages)
					itemMessages.add(parser.parse(xmlMessage));
		} catch (ParserException e) {e.printStackTrace();}
		
		return itemMessages;
	}
}