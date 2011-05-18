package br.usp.ime.choreos.vv;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import br.usp.ime.choreos.vv.exceptions.FrameworkException;
import br.usp.ime.choreos.vv.exceptions.InvalidOperationNameException;
import br.usp.ime.choreos.vv.exceptions.WSDLException;
import br.usp.ime.choreos.vv.util.WebServiceController;

public class WSClientTest {

	private static final String WSDL = "http://localhost:1234/SimpleStore?wsdl";

	private static final int NUMBER_OF_OPERATIONS = 6;

	private static WSClient wsClient;
	
	@BeforeClass
	public static void setUp() throws Exception {
		WebServiceController.deployService();
		wsClient = new WSClient(WSDL);
	}
	
	@AfterClass
	public static void tearDown(){
		WebServiceController.undeployService();
	}
	
	@Test
	public void checkValidWsdlUri() {
		
		assertEquals(WSDL, wsClient.getWsdl());
	}

	@Test(expected=WSDLException.class)
	public void checkInvalidWsdlUri() throws WSDLException, XmlException, IOException, FrameworkException  {

		new WSClient("http://localhost:1234/SimpleStore?wsdl_invalid"); // invalid wsdl uri
	}

	@Test
	public void checkServiceOperationsListIsNotEmpty() {
		
		assertTrue(!wsClient.getOperations().isEmpty());
	}
	
	@Test
	public void checkNumberOfOperations() {
		assertEquals(NUMBER_OF_OPERATIONS, wsClient.getOperations().size());
	}

	@Test
	public void shouldMakeValidRequestWithOneParameter() throws Exception {	
		Item cd = wsClient.request("searchByArtist", "Floyd");
		assertEquals("The dark side of the moon;", cd.getChild("return").getContent());	
	}
	
	public void shouldMakeValidRequestWithTwoParameters() throws Exception {
		Item status = wsClient.request("purchase", "Album Name", "Client Name");
		assertEquals("true", status.getContent());	
	}
	
	@Test(expected=InvalidOperationNameException.class)
	public void shouldComplainAboutInvalidOperationName() throws InvalidOperationNameException, FrameworkException {
		wsClient.request("Invalid Operation", "");
	}
	
	@Test
	public void shouldReceiveVoidReturn() throws InvalidOperationNameException, FrameworkException {

		// just testing if no Exception is thrown because the void return

		try {
			wsClient.request("cancelPurchase", "cd name", "customer name");
		}
		catch (Exception e) {
			assertTrue(false); // if exceptions is thrown, test fail
		}
	}
	
	@Test
	public void requestToVoidWebServiceShouldHaveNullContent() throws InvalidOperationNameException, FrameworkException {

		Item item = wsClient.request("cancelPurchase", "cd name", "customer name");
		
		assertNull(item.getContent());
		assertEquals((Integer) 0, item.getChildrenCount());
	}
	
	@Test
	@Ignore
	public void oneWayMethodsShouldHaveNullItem() throws InvalidOperationNameException, FrameworkException {

		Item item = wsClient.request("sendPurchaseFeedback", "Great Store!");
		
		assertNull(item.getContent());
		assertEquals((Integer) 0, item.getChildrenCount());
	}

}
