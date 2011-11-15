package eu.choreos.vv.servicesimulator;

import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Test;

import eu.choreos.vv.clientgenerator.WSClient;

public class FaultScenarioOperationsTest {

	private static final String MOCK_WSDL_URI = "http://localhost:4321/faltySupermarket?wsdl";
	private static final String SM_WSDL_URI = "file://" + System.getProperty("user.dir") + "/resource/sm_plus.wsdl";
	private Mock mock;
	
	@After
	public void turnOffMock(){
		mock.stop();
	}
	
	@Test
	public void shouldDoNotRespondForTheOperation() throws Exception {
		mock = new Mock("faltySupermarket", SM_WSDL_URI);
		mock.doNotRespond("getPrice");
		mock.setPort("4321");
		mock.start();
		
		WSClient client = new WSClient(MOCK_WSDL_URI);
		
		// A java.net.SocketTimeoutException should also be thrown but I can not capture this exception in my test case
		assertNull(client.request("getPrice", "milk")); 
	}
	
	@Test
	public void shouldDoNotRespondForAllOperations() throws Exception {
		mock = new Mock("faltySupermarket", SM_WSDL_URI);
		mock.doNotRespondAll();
		mock.setPort("4321");
		mock.start();
		
		WSClient client = new WSClient(MOCK_WSDL_URI);
		
		// A java.net.SocketTimeoutException should also be thrown but I can not capture this exception in my test case
		assertNull(client.request("getPrice", "milk")); 
		assertNull(client.request("purchase", "milk", "2")); 
	}
}
