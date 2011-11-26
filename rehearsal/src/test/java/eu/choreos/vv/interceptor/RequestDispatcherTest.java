package eu.choreos.vv.interceptor;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.util.WebServiceController;

public class RequestDispatcherTest {

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
		String SIMPLE_STORE_WSDL = "http://localhost:1234/SimpleStore?wsdl";
		String operationName = "searchByArtist";
		String requestContent = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sim=\"http://simplestorews.vvws.choreos.eu/\">" +
																"<soapenv:Header/>" +
																"<soapenv:Body>" +
																"<sim:searchByArtist>" +
																"<arg0>Pink Floyd</arg0>" +
																"</sim:searchByArtist>" +
																"</soapenv:Body>" +
																"</soapenv:Envelope>";
		
		String expectedResponse = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" + "\n" +
																	"<S:Body>" + "\n" +
																	"<ns2:searchByArtistResponse xmlns:ns2=\"http://simplestorews.vvws.choreos.eu/\">" + "\n" +
																	"<return>The dark side of the moon;</return>" + "\n" +
																	"</ns2:searchByArtistResponse>" + "\n" +
																	"</S:Body>" + "\n" +
																	"</S:Envelope>";
		
		String actualResponse = RequestDispatcher.getResponse(SIMPLE_STORE_WSDL, operationName, requestContent);
		
		assertEquals(expectedResponse.replace(" ", ""), actualResponse.replace(" ", ""));
	}
}
