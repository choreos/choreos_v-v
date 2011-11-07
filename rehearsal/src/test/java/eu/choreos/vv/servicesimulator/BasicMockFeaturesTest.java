package eu.choreos.vv.servicesimulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.WSDLException;

public class BasicMockFeaturesTest {
	
	public static String REAL_WSDL_URI = "";
	private static Mock aMock;
	
	@BeforeClass
	public static void setUp() throws Exception {
		REAL_WSDL_URI = "file://" + System.getProperty("user.dir") + "/resource/simpleStore.wsdl";
		aMock = new Mock("myMock", REAL_WSDL_URI);

	}
	
	@Test
	public void shouldCreateAMockWithDefautValuesForAGivenWsdl() throws Exception {
		assertEquals("8088", aMock.getPort());
		assertEquals("localhost", aMock.getDomain());
		assertEquals("http://localhost:8088/myMock?wsdl", aMock.getWSDL());
	}
	
	@Test
	public void shouldCreateAMockWithCustomValuesForAGivenWsdl() throws Exception {
		Mock aMock = new Mock("myMock", REAL_WSDL_URI);
		aMock.setPort("9000");
		aMock.setDomain("143.107.58.12");
		
		assertEquals("9000", aMock.getPort());
		assertEquals("143.107.58.12", aMock.getDomain());
		assertEquals("http://143.107.58.12:9000/myMock?wsdl", aMock.getWSDL());
	}
	
	@Test(expected=WSDLException.class)
	public void shouldThrowsAnExceptionIfTheWSDLIsInvalid() throws Exception{
		new Mock("isNotMyMock", "http://localhost/aMess?wsdl");
	}
	
	@Test
	public void mockShouldBeCreatedWithAllOperationsFromTheWSDgetOperationsLMocked() throws Exception{
		List<MockOperation> mockOperations = aMock.getOperations();
		
		WSClient client = new WSClient(REAL_WSDL_URI);
		List<String> realOperationNames = client.getOperations();
		
		for (MockOperation operation : mockOperations)
			assertTrue(realOperationNames.contains(operation.getName()));
	}
	
	@Test
	public void whenCreatedAllMockResponsesDoNotHaveResponses() throws Exception{
		List<MockOperation> mockOperations = aMock.getOperations();
				
		for (MockOperation operation : mockOperations)
			assertNull(aMock.getResponsesFor(operation.getName()));
	}
	
	@Test
	public void afterCreatedProperlyTheMockShoulBeStarted() throws Exception{
		aMock.start();
		
		// Make a http request by accessing mock wsdl uri
		//--------------------------------------------------
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(aMock.getWSDL());
		
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
				    		new DefaultHttpMethodRetryHandler(3, false));
		//--------------------------------------------------
		
		assertEquals(HttpStatus.SC_OK, client.executeMethod(method));
		
		method.releaseConnection();
		
		aMock.stop();
	}
}
