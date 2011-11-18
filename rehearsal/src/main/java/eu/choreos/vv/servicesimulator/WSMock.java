package eu.choreos.vv.servicesimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockService;
import com.eviware.soapui.support.SoapUIException;

import eu.choreos.vv.common.HttpUtils;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.MockDeploymentException;
import eu.choreos.vv.exceptions.NoMockResponseException;
import eu.choreos.vv.exceptions.ParserException;
import eu.choreos.vv.exceptions.WSDLException;

public class WSMock {

	private String port;
	private String name;
	private String hostName;
	private WsdlInterface iface;
	private WsdlMockService service;
	private HashMap<String, MockOperation> operations;

	public WSMock(String name, String wsdl) throws Exception {
		this.name = name;
		port = "8088";
		hostName = "localhost";
		operations = new HashMap<String, MockOperation>();

		buildWsdlPrject(name, wsdl);
	}

	private void buildWsdlPrject(String name, String wsdl) throws Exception {
		try {
			WsdlProject project = new WsdlProject();
			iface = WsdlInterfaceFactory.importWsdl(project, wsdl, true)[0];

			service = project.addNewMockService(name);
			service.setPort(Integer.parseInt(port));
			createMockOperations();
		} catch (SoapUIException e) {
			throw new WSDLException(e);
		}
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

	public String getPort() {
		return port;
	}

	public String getWSDL() {
		return "http://" + hostName + ":" + port + "/" + name + "?wsdl";
	}

	public String getHostName() {
		return hostName;
	}

	public void setPort(String port) {
		service.setPort(Integer.parseInt(port));
		this.port = port;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public List<MockOperation> getOperations() {
		return new ArrayList<MockOperation>(operations.values());
	}

	public void start() throws MockDeploymentException {
		iface.addEndpoint(service.getLocalEndpoint());

		try {
			if (HttpUtils.UriAreUsed("http://" + hostName + ":" + port))
				throw new MockDeploymentException("Address already in use");

			service.start();

		} catch (Exception e) {
			throw new MockDeploymentException(e);
		}
	}

	public void stop() {
		service.getMockRunner().stop();
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