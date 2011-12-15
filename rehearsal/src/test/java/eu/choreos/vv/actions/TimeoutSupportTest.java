package eu.choreos.vv.actions;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.servicesimulator.WSMock;

public class TimeoutSupportTest {
	
	private static final String MOCK_WSDL_URI = "http://localhost:4321/faultySupermarket?wsdl";
	private static final String SM_WSDL_URI = "file://" + System.getProperty("user.dir") + "/resource/sm_plus.wsdl";
	private static WSMock mock;
	
	@BeforeClass
	public static void setUp() throws Exception{
		mock = new WSMock("faultySupermarket", SM_WSDL_URI);
		mock.doNotRespond("getPrice");
		mock.setPort("4321");
		mock.start();
	}
	
	@AfterClass
	public static void tearDown() throws Exception{
		mock.stop();
	}
	
	@Test(timeout=50000)
	public void  shouldTheRequestTakeFifthHundreadMilliseconds() throws Exception {
		WSClient client = new WSClient(MOCK_WSDL_URI); 
		client.setResponseTimeout(1000);
		client.request("getPrice", "milk");
	}

}
