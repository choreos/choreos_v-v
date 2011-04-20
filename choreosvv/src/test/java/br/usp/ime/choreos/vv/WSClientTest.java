package br.usp.ime.choreos.vv;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.choreos.vv.exceptions.FrameworkException;
import br.usp.ime.choreos.vv.exceptions.InvalidOperationName;
import br.usp.ime.choreos.vv.exceptions.WSDLException;
import br.usp.ime.choreos.vv.util.WebServiceController;

public class WSClientTest {

	private static final String WSDL = "http://localhost:1234/SimpleStore?wsdl";

	private static final int NUMBER_OF_OPERATIONS = 4;
	
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
		
		String cd = wsClient.request("searchByArtist", "Floyd");
		assertEquals("<return>The dark side of the moon;</return>", cd);	
	}
	
	public void shouldMakeValidRequestWithTwoParameters() throws Exception {
		
		String status = wsClient.request("purchase", "Album Name", "Client Name");
		assertEquals("true", status);	
	}
	
	@Test(expected=InvalidOperationName.class)
	public void shouldComplainAboutInvalidOperationName() throws InvalidOperationName, FrameworkException  {
		wsClient.request("Invalid Operation", "");
	}


}
