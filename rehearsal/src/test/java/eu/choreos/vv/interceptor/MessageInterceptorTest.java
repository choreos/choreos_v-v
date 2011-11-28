package eu.choreos.vv.interceptor;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.util.WebServiceController;

public class MessageInterceptorTest {
	
	private final static String SIMPLE_STORE_WSDL = "http://localhost:1234/SimpleStore?wsdl";
	private final static String PROXY_SIMPLE_STORE_WSDL = "http://localhost:4321/SimpleStoreProxy?wsdl";
	private MessageInterceptor interceptor;
	
	@BeforeClass
	public static void setUp(){
		WebServiceController.deployService();
	}
	
	@AfterClass
	public static void tearDown(){
		WebServiceController.undeployService();
	}
	
	@Before
	public void startInterceptor() throws Exception{
		interceptor = new MessageInterceptor("4321");
	}
	
	@After
	public void stopInterceptor(){
		interceptor.stop();
	}
	
	@Test
	public void shouldBePossibleToRetrieveTheProxyForTheWsdlIntercepted() throws Exception {
		interceptor.interceptTo(SIMPLE_STORE_WSDL);
		
		assertEquals("http://localhost:1234/SimpleStore?wsdl", interceptor.getRealWsdl());
		assertEquals("http://localhost:4321/SimpleStoreProxy?wsdl", interceptor.getProxyWsdl());
		assertEquals("4321", interceptor.getPort());
	}
	
	@Test
	public void shouldBePossibleToInterceptAndRetrieveAMessage() throws Exception {
		interceptor.interceptTo(SIMPLE_STORE_WSDL);
		
		WSClient client = new WSClient(PROXY_SIMPLE_STORE_WSDL);
		client.request("searchByArtist", "Pink Floyd");
		
		Item message = interceptor.getMessages().get(0);
		
		assertEquals("searchByArtist", message.getName());
		assertEquals("Pink Floyd", message.getChild("arg0").getContent());
	}
	
	@Test
	public void messagesShouldBeRetrievedInTheOrderTheyWereCollected() throws Exception {
		interceptor.interceptTo(SIMPLE_STORE_WSDL);
		
		WSClient client = new WSClient(PROXY_SIMPLE_STORE_WSDL);
		client.request("searchByArtist", "Pink Floyd");
		client.request("searchByArtist", "Metallica");
		client.request("searchByGenre", "Rock" );
		
		List<Item> messages = interceptor.getMessages();
		
		assertEquals("Pink Floyd", messages.get(0).getChild("arg0").getContent());
		assertEquals("Metallica", messages.get(1).getChild("arg0").getContent());
		assertEquals("Rock", messages.get(2).getChild("arg0").getContent());
	}
	
	@Test
	public void shouldCleanMessageListEachTimeInterceptToIsCalled() throws Exception {
		interceptor.interceptTo(SIMPLE_STORE_WSDL);
		WSClient client = new WSClient(PROXY_SIMPLE_STORE_WSDL);
		client.request("searchByArtist", "Pink Floyd");

		List<Item> messages = interceptor.getMessages();
		assertEquals(1, messages.size());
		assertEquals("Pink Floyd", messages.get(0).getChild("arg0").getContent());
		
		interceptor.stop();
		
		interceptor.interceptTo(SIMPLE_STORE_WSDL);
		client.request("searchByArtist", "Bon Jovi");
		messages = interceptor.getMessages();
		
		assertEquals(1, messages.size());
		assertEquals("Bon Jovi", messages.get(0).getChild("arg0").getContent());
		
	}

}
