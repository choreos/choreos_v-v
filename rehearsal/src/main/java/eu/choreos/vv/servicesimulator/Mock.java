package eu.choreos.vv.servicesimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockRunner;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockService;
import com.eviware.soapui.support.SoapUIException;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.common.HttpUtils;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.MockDeploymentException;
import eu.choreos.vv.exceptions.ParserException;
import eu.choreos.vv.exceptions.WSDLException;


public class Mock {

	private String port;
	private String name;
	private String domain;
	private WsdlInterface iface;
	private WsdlMockService service;
	private HashMap<String, MockOperation> operations;
	private WsdlMockRunner runner;
	
	public Mock(String name, String wsdl) throws Exception {
		this.name = name;
		port =  "8088";
		domain = "localhost";
		operations = new HashMap<String, MockOperation>();
		
		buildWsdlPrject(name, wsdl);
	}

	private void buildWsdlPrject(String name, String wsdl) throws Exception {
		try{
			WsdlProject project = new WsdlProject();
			iface = WsdlInterfaceFactory.importWsdl(project, wsdl, true)[0];
			
			service = project.addNewMockService(name);
			service.setPort(Integer.parseInt(port));
			createMockOperations();
		}
		catch(SoapUIException e){
			throw new WSDLException(e);
		}
	}
	
	private void createMockOperations(){
		for(int i=0; i<iface.getOperationCount(); i++){
			WsdlMockOperation soapUIMockOperation = service.addNewMockOperation(iface.getOperationAt(i));
			 soapUIMockOperation.setDispatchStyle("SCRIPT");
			MockOperation rehearsalMockOperation = new MockOperation(iface.getOperationAt(i).getRequestAt(0).getRequestContent(), soapUIMockOperation);
			operations.put(soapUIMockOperation.getName(), rehearsalMockOperation);
		}
	}

	public String getPort() {
		return port;
	}

	public String getWSDL() {
		return "http://" + domain + ":" + port + "/" + name + "?wsdl";
	}

	public String getDomain() {
		return domain;
	}

	public void setPort(String port) {
		service.setPort(Integer.parseInt(port));
		this.port = port;
	}
	
	public void setDomain(String domain){
		this.domain = domain;
	}

	public List<MockOperation> getOperations() {
		return new ArrayList<MockOperation>(operations.values());
	}

	public List<Item> getResponsesFor(String operationName) {
		return null;
	}
	
	public void start() throws MockDeploymentException {
		iface.addEndpoint( service.getLocalEndpoint());
		
		try {
			if(HttpUtils.UriAreUsed("http://" + domain + ":" + port))
				throw new MockDeploymentException("Address already in use");
			
			service.start();
			
		} catch (Exception e) {
			throw new MockDeploymentException(e);
		}
	}

	public void stop()  {
		service.getMockRunner().stop();
	}

	public Mock returnFor(String operation, MockResponse mockReponse) throws ParserException, InvalidOperationNameException {
		if(!operations.containsKey(operation))
			throw new InvalidOperationNameException();
		
		MockOperation mockedOperation = operations.get(operation);
		mockedOperation.addResponse(mockReponse);
		return this;
	}

}
