package br.usp.ime.choreos.vv;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import br.usp.ime.choreos.vv.util.WebServiceController;

import com.eviware.soapui.support.SoapUIException;

public class WSClientTest {

	private static final String WSDL = "http://localhost:1234/SimpleStore?wsdl";

	private static final int NUMBER_OF_OPERATIONS = 4;
	
	private static WSClient wsClient;
	
	@BeforeClass
	public static void setUp() throws SoapUIException, XmlException, IOException {
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

	// TODO: should be WSDLException ???
	@Test(expected=SoapUIException.class)
	public void checkInvalidWsdlUri() throws SoapUIException, XmlException, IOException {

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
		assertEquals("The dark side of the moon;", cd);	
	}
	
	public void shouldMakeValidRequestWithTwoParameters() throws Exception {
		
		String status = wsClient.request("purchase", "Album Name", "Client Name");
		assertEquals("true", status);	
	}
	
	@Test(expected=InvalidOperationName.class)
	public void shouldComplainAboutInvalidOperationName() throws Exception{
		wsClient.request("Invalid Operation", "");
	}


}
