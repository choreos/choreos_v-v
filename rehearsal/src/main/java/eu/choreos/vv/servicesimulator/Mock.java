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
			runner = new WsdlMockRunner(service, null);
		}
		catch(SoapUIException e){
			throw new WSDLException(e);
		}
	}
	
	private void createMockOperations(){
		for(int i=0; i<iface.getOperationCount(); i++){
			WsdlMockOperation soapUIMockOperation = service.addNewMockOperation(iface.getOperationAt(i));
			MockOperation rehearsalMockOperation = new MockOperation(soapUIMockOperation);
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

	public void start() throws Exception {
		iface.addEndpoint( service.getLocalEndpoint());
		runner.start();
	}

	public void stop() throws Exception {
		runner.stop();
	}

}
