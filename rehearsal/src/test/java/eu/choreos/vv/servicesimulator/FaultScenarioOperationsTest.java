package eu.choreos.vv.servicesimulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Test;

import eu.choreos.vv.clientgenerator.WSClient;

public class FaultScenarioOperationsTest {

	private static final String MOCK_WSDL_URI = "http://localhost:4321/faultySupermarket?wsdl";
	private static final String SM_WSDL_URI = "file://" + System.getProperty("user.dir") + "/resource/sm_plus.wsdl";
	private WSMock mock;
	
	@After
	public void turnOffMock(){
		mock.stop();
	}
	
	@Test
	public void shouldDoNotRespondForTheOperation() throws Exception {
		mock = new WSMock("faultySupermarket", "4321", SM_WSDL_URI);
		mock.doNotRespond("getPrice");
		mock.start();
		
		WSClient client = new WSClient(MOCK_WSDL_URI);
		client.setResponseTimeout(1000);

		// A java.net.SocketTimeoutException should also be thrown but I can not capture this exception in my test case
		assertNull(client.request("getPrice", "milk")); 
	}
	
	@Test
	public void shouldDoNotRespondForAllOperations() throws Exception {
		mock = new WSMock("faultySupermarket", "4321", SM_WSDL_URI);
		mock.doNotRespondAll();
		mock.start();
		
		WSClient client = new WSClient(MOCK_WSDL_URI);
		client.setResponseTimeout(1000);
		
		// A java.net.SocketTimeoutException should also be thrown but I can not capture this exception in my test case
		assertNull(client.request("purchase", "milk", "2")); 
	}
	
	@Test
	public void shouldSimulateAnOperationCrash() throws Exception {
		mock = new WSMock("faltySupermarket", "4321", SM_WSDL_URI);
		mock.crash("getPrice");
		mock.start();
	
		WSClient client = new WSClient(MOCK_WSDL_URI);
		
		// A com.eviware.soapui.impl.wsdl.mock.DispatchException is throw internally to jetty and it cannot be captured
		assertEquals("Fault", client.request("getPrice", "milk").getName()); 
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void shouldSimulateAllOperationsCrash() throws Exception {
		mock = new WSMock("faltySupermarket", "4321", SM_WSDL_URI);
		mock.crashAll();
		mock.start();
		
		// throw an ArrayIndexOutOfBoundsException cause WSClient does not find any operation to build the wsdl project
		WSClient client = new WSClient(MOCK_WSDL_URI); 
	
		
		assertNull(client.request("getPrice", "milk"));
		assertNull(client.request("purchase", "milk"));
	}
}
