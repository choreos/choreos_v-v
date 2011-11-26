package eu.choreos.vv.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class WSProxyTest {
	
	private static String  SM_WSDL_URI = "file://" + System.getProperty("user.dir") + "/resource/sm_plus.wsdl";
	
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

}
