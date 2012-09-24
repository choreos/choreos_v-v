package eu.choreos.vv.clientgenerator;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;
import eu.choreos.vv.util.WebServiceController;


public class WSClientTest {

	private static final String SIMPLE_STORE_WSDL = "http://localhost:1234/SimpleStore?wsdl";
	private static final String STORE_WSDL = "http://localhost:1234/Store?wsdl";

	private static final int NUMBER_OF_OPERATIONS = 6;

	private static WSClient wsSimpleStoreClient;
	private static WSClient wsStoreClient;
	
	@BeforeClass
	public static void setUp() throws Exception {
		WebServiceController.deployService();
		wsSimpleStoreClient = new WSClient(SIMPLE_STORE_WSDL);
		wsStoreClient = new WSClient(STORE_WSDL);
	}
	
	@AfterClass
	public static void tearDown(){
		WebServiceController.undeployService();
	}
	
	@Test
	public void checkValidWsdlUri() {
		
		assertEquals(SIMPLE_STORE_WSDL, wsSimpleStoreClient.getWsdl());
	}

	@Test(expected=WSDLException.class)
	public void checkInvalidUri() throws WSDLException, XmlException, IOException, FrameworkException  {

		new WSClient("http://localhost:1234/mess?wsdl"); // invalid uri
	}
	
	@Test(expected=WSDLException.class)
	public void checkInvalidWsdlUri() throws WSDLException, XmlException, IOException, FrameworkException  {

		new WSClient("http://localhost:1234/SimpleStore?wsdl_invalid"); // invalid wsdl uri
	}

	@Test
	public void checkServiceOperationsListIsNotEmpty() {
		
		assertTrue(!wsSimpleStoreClient.getOperations().isEmpty());
	}
	
	@Test
	public void checkNumberOfOperations() {
		assertEquals(NUMBER_OF_OPERATIONS, wsSimpleStoreClient.getOperations().size());
	}

	@Test
	public void shouldMakeValidRequestWithOneParameter() throws Exception {	
		Item cd = wsSimpleStoreClient.request("searchByArtist", "Floyd");
		assertEquals("The dark side of the moon;", cd.getContent("return"));	
	}
	
	@Test
	public void shouldMakeValidRequestWithTwoParameters() throws Exception {
		Item status = wsSimpleStoreClient.request("purchase", "Album Name", "Client Name");
		assertEquals("true", status.getChild("return").getContent());	
	}
	
	@Test(expected=InvalidOperationNameException.class)
	public void shouldComplainAboutInvalidOperationName() throws InvalidOperationNameException, FrameworkException {
		wsSimpleStoreClient.request("Invalid Operation", "");
	}
	
	@Test
	public void shouldReceiveVoidReturn() throws InvalidOperationNameException, FrameworkException {

		// just testing if no Exception is thrown because the void return

		try {
			wsSimpleStoreClient.request("cancelPurchase", "cd name", "customer name");
		}
		catch (Exception e) {
			assertTrue(false); // if exceptions is thrown, test fail
		}
	}
	
	@Test
	public void requestToVoidWebServiceShouldHaveNullContent() throws InvalidOperationNameException, FrameworkException {

		Item item = wsSimpleStoreClient.request("cancelPurchase", "cd name", "customer name");
		
		assertNull(item.getContent());
		assertEquals((Integer) 0, item.getChildrenCount());
	}
	
	@Test
	public void oneWayMethodsShouldHaveNullItem() throws InvalidOperationNameException, FrameworkException {
		//Should not throw a null pointer exception
		wsSimpleStoreClient.request("sendPurchaseFeedback", "Great Store!");
	}
	
	@Test
	public void complexTypeRequestWithItemAsRoot() throws InvalidOperationNameException, FrameworkException, NoSuchFieldException {
		
		Item root = new ItemImpl("purchase");
		
		Item cd = root.addChild("arg0");
		cd.addChild("title").setContent("Pulse");
		cd.addChild("artist").setContent("Pink Floyd");
		cd.addChild("genre").setContent("Alternative Rock");
		cd.addChild("numberOfTracks").setContent("13");
		
		Item customer = root.addChild("arg1");
		customer.addChild("name").setContent("Piva");
		customer.addChild("address").setContent("IME");
		customer.addChild("creditCard").setContent("Visa");
		
		Item item = wsStoreClient.request("purchase", root);
		
		assertEquals("false", item.getChild("return").getContent());
		
	}
	
	@Test
	public void shouldUpdateTheEndpointCorrectly() throws Exception{
		WSClient ws = new WSClient("file://" + System.getProperty("user.dir") + "/resource/store_with_wrong_endpoint.wsdl");
		
		String expectedEndpoint = "http://localhost:1234/SimpleStore";
	
		ws.setEndpoint("http://localhost:1234/SimpleStore");
		assertEquals(expectedEndpoint, ws.getEndpoint());
	}
	
	@Test
	public void shouldUpdateTheEndpointAndTheOperationsMustKeepWorkingCorrectly() throws Exception{
		WSClient ws = new WSClient("file://" + System.getProperty("user.dir") + "/resource/store_with_wrong_endpoint.wsdl");
		
		ws.setEndpoint("http://localhost:1234/SimpleStore");
		Item cd = ws.request("searchByArtist", "Floyd");
		assertEquals("The dark side of the moon;", cd.getChild("return").getContent());	

		Item status = ws.request("purchase", "Album Name", "Client Name");
		assertEquals("true", status.getChild("return").getContent());	
	}
}
