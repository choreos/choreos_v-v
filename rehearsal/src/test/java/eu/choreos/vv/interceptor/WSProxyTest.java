package eu.choreos.vv.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.util.WebServiceController;

public class WSProxyTest {
	
	private final static String  SM_WSDL_URI = "file://" + System.getProperty("user.dir") + "/resource/sm_plus.wsdl";
	private final static String SIMPLE_STORE_WSDL = "http://localhost:1234/SimpleStore?wsdl";

	@BeforeClass
	public static void setUp(){
		WebServiceController.deployService();

	}
	
	@AfterClass
	public static void tearDown(){
		WebServiceController.undeployService();
	}

	
	@Test
	public void shouldCreateTheProxyByAddingTheSuffixProxyInTheInterceptedService() throws Exception {
		WSProxy proxy = new WSProxy("ws/sm_plus", SM_WSDL_URI);
		
		assertEquals("http://localhost:8088/ws/sm_plusProxy?wsdl", proxy.getWSDL());
	}
	
	@Test
	public void shouldBePossibeToDefineAPortForTheProxy() throws Exception {
		WSProxy proxy = new WSProxy("ws/sm_plus", SM_WSDL_URI);
		proxy.setPort("1234");
		
		assertEquals("http://localhost:1234/ws/sm_plusProxy?wsdl", proxy.getWSDL());
	}
	
	@Test
	public void shouldBePossibleToDefineAHostNameForTheProxy() throws Exception {
		WSProxy proxy = new WSProxy("ws/sm_plus", SM_WSDL_URI);
		proxy.setPort("1234");
		proxy.setHostName("myHome");
		
		assertEquals("http://myHome:1234/ws/sm_plusProxy?wsdl", proxy.getWSDL());
	}
	
	@Test
	public void theProxShouldContainAllRealServiceMessages() throws Exception {
		WSProxy proxy = new WSProxy("ws/sm_plus", SM_WSDL_URI);
		List<String> operations = proxy.getOperationNames();
		
		assertTrue(operations.contains("getPrice"));
		assertTrue(operations.contains("purchase"));
		assertTrue(operations.contains("getProductStatus"));
	}
	
	@Test
	public void shouldInterceptTheRequestMessage() throws Exception{
		InterceptedMessagesRegistry registry = InterceptedMessagesRegistry.getInstance();
		registry.registerWsdl(SIMPLE_STORE_WSDL);
		
		WSProxy proxy = new WSProxy("SimpleStore", SIMPLE_STORE_WSDL);
		proxy.setPort("9999");
		proxy.start();
		
		WSClient client = new WSClient(proxy.getWSDL());
		client.request("searchByArtist", "Pink Floyd");
		
		String expectedMessage = MessageTestData.getRequestContentWithComments();
		assertEquals(expectedMessage.replace(" ", ""), registry.getMessages(SIMPLE_STORE_WSDL).get(0).replace(" ", ""));
		
		proxy.stop();
	}
	
	@Test
	public void shouldReturnTheRealServiceResponse() throws Exception {
		InterceptedMessagesRegistry.getInstance().registerWsdl(SIMPLE_STORE_WSDL);
		
		WSProxy proxy = new WSProxy("SimpleStore", SIMPLE_STORE_WSDL);
		proxy.setPort("9999");
		proxy.start();
	
		WSClient client = new WSClient(proxy.getWSDL());
		Item response = client.request("searchByArtist", "Pink Floyd");
	
		assertEquals("The dark side of the moon;", response.getChild("return").getContent());
	}

}
