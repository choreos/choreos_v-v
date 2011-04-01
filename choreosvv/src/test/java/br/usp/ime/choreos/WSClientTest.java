package br.usp.ime.choreos;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.choreos.vv.Operation;
import br.usp.ime.choreos.vv.WSClient;
import br.usp.ime.choreos.vv.util.Bash;

import com.eviware.soapui.support.SoapUIException;

public class WSClientTest {

	private static final String WSDL = "http://localhost:1234/store?wsdl";
	private static final int NUMBER_OF_OPERATIONS = 5;
	private static final int NUMBER_OF_BUY_PARAMETERS = 2;
	
	private static WSClient wsClient;
	
	@BeforeClass
	public static void setUp() throws SoapUIException, XmlException, IOException {
		Bash.deployService();
		wsClient = new WSClient(WSDL);
	}
	
	@AfterClass
	public static void tearDown(){
		Bash.undeployService();
	}
	
	@Test
	public void checkValidWsdlUri() {
		
		assertEquals(WSDL, wsClient.getWsdl());
	}

	// TODO: should be WSDLException ???
	@Test(expected=SoapUIException.class)
	public void checkInvalidWsdlUri() throws SoapUIException, XmlException, IOException {

		new WSClient("http://localhost:1234/store?wsdl_invalid"); // invalid wsdl uri
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
	public void checkNumberOfParametersOfBuy() {
		
		Operation buy = wsClient.getOperationByName("buy");
		assertEquals(NUMBER_OF_BUY_PARAMETERS, buy.getNumberOfParameters());
	}

}
