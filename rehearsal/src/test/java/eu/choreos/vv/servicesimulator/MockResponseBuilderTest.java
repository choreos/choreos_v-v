package eu.choreos.vv.servicesimulator;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.SoapEnvelopeHelper;
import eu.choreos.vv.common.ItemBuilder;

public class MockResponseBuilderTest {
	private static String defaultResponse;

	@BeforeClass
	public static void setUp() throws Exception{
		String wsdl = "file://" + System.getProperty("user.dir") + "/resource/simpleStore.wsdl";
		defaultResponse = MockUtils.getDefaultResponse(wsdl, "searchByArtist");
	}
	
	@Test
	public void shouldGeneratedTheXmlResponseUsingSoapHelper() throws Exception {
		
		String expectedXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sim=\"http://simplestorews.vvws.choreos.eu/\">" + "\n" + 
		"   <soapenv:Header/>" + "\n" +
		"   <soapenv:Body>" + "\n" +
		"      <sim:searchByArtistResponse>" + "\n" +
		"         <!--Optional:-->" + "\n" +
		"         <return>mocked response</return>" + "\n" +
		"      </sim:searchByArtistResponse>" + "\n" +
		"   </soapenv:Body>" + "\n" +
		"</soapenv:Envelope>";

		String actualXml = SoapEnvelopeHelper.generate(defaultResponse, "mocked response");
		assertEquals(expectedXml, actualXml.trim());

	}
	
	
	@Test
	public void shouldGenerateTheXmlResponseUsingItemBuilder() throws Exception {
		String expectedXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sim=\"http://simplestorews.vvws.choreos.eu/\">" +
				"<soapenv:Header></soapenv:Header>" +
				"<soapenv:Body>" + 
				"<sim:searchByArtistResponse>" +
				"<return>mocked response</return>" +
				"</sim:searchByArtistResponse>" +
				"</soapenv:Body>" +
				"</soapenv:Envelope>";
		
		Item root = new ItemImpl("searchByArtistResponse");
		root.addChild("return").setContent("mocked response");
		
		String actualXml = new ItemBuilder().buildItem(defaultResponse, root);
		
		assertEquals(expectedXml, actualXml);
	}
	

}
