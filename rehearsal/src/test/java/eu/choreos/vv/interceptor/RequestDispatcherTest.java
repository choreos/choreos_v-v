package eu.choreos.vv.interceptor;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.util.WebServiceController;

public class RequestDispatcherTest {

	private final String SIMPLE_STORE_WSDL = "http://localhost:1234/SimpleStore?wsdl";

	@BeforeClass
	public static void setUp(){
		WebServiceController.deployService();

	}
	
	@AfterClass
	public static void tearDown(){
		WebServiceController.undeployService();
	}

	@Test
	public void shouldInvokeTheWSCorrectly() throws Exception {
		InterceptedMessagesRegistry.getInstance().registerWsdl(SIMPLE_STORE_WSDL);
		
		String operationName = "searchByArtist";
		String requestContent = MessageTestData.getRequestContent();
		
		String expectedResponse = MessageTestData.getResponseContent();
		
		String actualResponse = RequestDispatcher.getResponse(SIMPLE_STORE_WSDL, operationName, requestContent);
		
		assertEquals(expectedResponse.replace(" ", ""), actualResponse.replace(" ", ""));
	}

	
	@Test
	public void shouldRegisterMessagesIntoTheRegistry() throws Exception {
		InterceptedMessagesRegistry registry = InterceptedMessagesRegistry.getInstance();
		registry.registerWsdl(SIMPLE_STORE_WSDL);
		
		String operationName = "searchByArtist";
		String requestContent = MessageTestData.getRequestContent();
		
		RequestDispatcher.getResponse(SIMPLE_STORE_WSDL, operationName, requestContent);
	
		
		assertEquals(requestContent.replace(" ", ""), registry.getMessages(SIMPLE_STORE_WSDL).get(0).replace(" ", ""));
	}
	
	
	
}
