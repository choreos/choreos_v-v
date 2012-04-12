package eu.choreos.vv.servicesimulator;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import com.eviware.soapui.support.SoapUIException;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;

public class ScriptBuilderTest {
	
	@Test
	public void shouldScriptBeCreatedWithItsHeader() throws Exception {
		ScriptBuilder builder = new ScriptBuilder(null, false);
		String header = builder.getScript();
		
		String expectedScript = "def request = new XmlSlurper().parseText(mockRequest.requestContent)" + "\n";
		
		assertEquals(expectedScript, header);		
	}
	
	@Test
	public void shouldAddAResponseInAnIfStatement() throws Exception {
		ScriptBuilder builder = getBuilder();
		MockResponse response = new MockResponse().whenReceive("milk").replyWith("90");
		builder.addConditionFor(response);
		
		String expectedScript = "def request = new XmlSlurper().parseText(mockRequest.requestContent)" + "\n" +
				"if ( request == new XmlSlurper().parseText('''<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreos.eu/\">" + "\n" +
				"   <soapenv:Header/>" + "\n" +
				"   <soapenv:Body>" +	"\n" +
				"      <chor:getPrice>" + "\n" +
				"          <!--Optional:-->" + "\n" +
				"         <name>milk</name>" + "\n" +
				"      </chor:getPrice>" +	"\n" +
				"  </soapenv:Body>" +	 "\n" +
				"</soapenv:Envelope>'''))" + "\n" +
				"context.message = '''<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreos.eu/\">" + "\n" +
				" <soapenv:Header/>" + "\n" +
				" <soapenv:Body>"+ "\n" +
				" <chor:getPriceResponse>" + "\n"+
				"   <!--Optional:-->" + "\n"+
					"    <return>90</return>"+ "\n"+
				"   </chor:getPriceResponse>" + "\n"+
						"  </soapenv:Body>" + "\n"+
				"</soapenv:Envelope>'''" + "\n";
		
		assertEquals(expectedScript.replace(" ", ""), builder.getScript().replace(" ", ""));
	}

	
	@Test
	public void shouldReplaceAnExistingResponse() throws Exception {
		ScriptBuilder builder = getBuilder();
		MockResponse response = new MockResponse().whenReceive("milk").replyWith("90");
		builder.addConditionFor(response);
		MockResponse response1 = new MockResponse().whenReceive("milk").replyWith("100");
		builder.addConditionFor(response1);
		
		String expectedScript = "def request = new XmlSlurper().parseText(mockRequest.requestContent)" + "\n" +
				"if ( request == new XmlSlurper().parseText('''<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreos.eu/\">" + "\n" +
				"   <soapenv:Header/>" + "\n" +
				"   <soapenv:Body>" +	"\n" +
				"  <chor:getPrice>" + "\n" +
				"     <!--Optional:-->" + "\n" +
				"   <name>milk</name>" + "\n" +
				" </chor:getPrice>" +	"\n" +
				"   </soapenv:Body>" +	 "\n" +
				"</soapenv:Envelope>'''))" + "\n" +
				"context.message = '''<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreos.eu/\">" + "\n" +
				" <soapenv:Header/>" + "\n" +
				" <soapenv:Body>"+ "\n" +
				" <chor:getPriceResponse>" + "\n"+
				"   <!--Optional:-->" + "\n"+
					"    <return>100</return>"+ "\n"+
				"   </chor:getPriceResponse>" + "\n"+
						"  </soapenv:Body>" + "\n"+
				"</soapenv:Envelope>'''" + "\n";
		
		assertEquals(expectedScript.replace(" ", ""), builder.getScript().replace(" ", ""));
}

	@Test
	public void shouldAddTwoIfStatements() throws Exception {
		ScriptBuilder builder = getBuilder();
		MockResponse response = new MockResponse().whenReceive("milk").replyWith("90");
		builder.addConditionFor(response);
		MockResponse response1 = new MockResponse().whenReceive("bread").replyWith("100");
		builder.addConditionFor(response1);
		
		String expectedScript = "def request = new XmlSlurper().parseText(mockRequest.requestContent)" + "\n" +
				"if ( request == new XmlSlurper().parseText('''<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreos.eu/\">" + "\n" +
				"   <soapenv:Header/>" + "\n" +
				"   <soapenv:Body>" +	"\n" +
				"  <chor:getPrice>" + "\n" +
				"     <!--Optional:-->" + "\n" +
				"   <name>milk</name>" + "\n" +
				" </chor:getPrice>" +	"\n" +
				"   </soapenv:Body>" +	 "\n" +
				"</soapenv:Envelope>'''))" + "\n" +
				"context.message = '''<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreos.eu/\">" + "\n" +
				" <soapenv:Header/>" + "\n" +
				" <soapenv:Body>"+ "\n" +
				" <chor:getPriceResponse>" + "\n"+
				"   <!--Optional:-->" + "\n"+
					"    <return>90</return>"+ "\n"+
				"   </chor:getPriceResponse>" + "\n"+
						"  </soapenv:Body>" + "\n"+
				"</soapenv:Envelope>'''" + "\n" +
				"if ( request == new XmlSlurper().parseText('''<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreos.eu/\">" + "\n" +
				"   <soapenv:Header/>" + "\n" +
				"   <soapenv:Body>" +	"\n" +
				"  <chor:getPrice>" + "\n" +
				"     <!--Optional:-->" + "\n" +
				"   <name>bread</name>" + "\n" +
				" </chor:getPrice>" +	"\n" +
				"   </soapenv:Body>" +	 "\n" +
				"</soapenv:Envelope>'''))" + "\n" +
				"context.message = '''<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreos.eu/\">" + "\n" +
				" <soapenv:Header/>" + "\n" +
				" <soapenv:Body>"+ "\n" +
				" <chor:getPriceResponse>" + "\n"+
				"   <!--Optional:-->" + "\n"+
					"    <return>100</return>"+ "\n"+
				"   </chor:getPriceResponse>" + "\n"+
						"  </soapenv:Body>" + "\n"+
				"</soapenv:Envelope>'''" + "\n";
		
		assertEquals(expectedScript.replace(" ", ""), builder.getScript().replace(" ", ""));
	}
	
	@Test
	public void shouldAddResponseWithWildCardInAnElseStatement() throws Exception {
		ScriptBuilder builder = getBuilder();
		MockResponse response = new MockResponse().whenReceive("milk").replyWith("90");
		builder.addConditionFor(response);
		MockResponse response1 = new MockResponse().whenReceive("*").replyWith("100");
		builder.addConditionFor(response1);	
		
		String expectedScript = "def request = new XmlSlurper().parseText(mockRequest.requestContent)" + "\n" +
				"if ( request == new XmlSlurper().parseText('''<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreos.eu/\">" + "\n" +
				"   <soapenv:Header/>" + "\n" +
				"   <soapenv:Body>" +	"\n" +
				"  <chor:getPrice>" + "\n" +
				"     <!--Optional:-->" + "\n" +
				"   <name>milk</name>" + "\n" +
				" </chor:getPrice>" +	"\n" +
				"   </soapenv:Body>" +	 "\n" +
				"</soapenv:Envelope>'''))" + "\n" +
				"context.message = '''<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreos.eu/\">" + "\n" +
				" <soapenv:Header/>" + "\n" +
				" <soapenv:Body>"+ "\n" +
				" <chor:getPriceResponse>" + "\n"+
				"   <!--Optional:-->" + "\n"+
					"    <return>90</return>"+ "\n"+
				"   </chor:getPriceResponse>" + "\n"+
						"  </soapenv:Body>" + "\n"+
				"</soapenv:Envelope>'''" + "\n" +
				"else" + "\n" +
				"context.message = '''<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreos.eu/\">" + "\n" +
				" <soapenv:Header/>" + "\n" +
				" <soapenv:Body>"+ "\n" +
				" <chor:getPriceResponse>" + "\n"+
				"   <!--Optional:-->" + "\n"+
					"    <return>100</return>"+ "\n"+
				"   </chor:getPriceResponse>" + "\n"+
						"  </soapenv:Body>" + "\n"+
				"</soapenv:Envelope>'''";
		
		assertEquals(expectedScript.replace(" ", ""), builder.getScript().replace(" ", ""));
	}
	
	@Test
	public void shouldNotAddElseStatementWhenOnlyTheWildCardParameterIsReceived() throws Exception {
		ScriptBuilder builder = getBuilder();
		MockResponse response = new MockResponse().whenReceive("*").replyWith("10000");
		builder.addConditionFor(response);

		String expectedScript = "def request = new XmlSlurper().parseText(mockRequest.requestContent)" + "\n" +
				"context.message = '''<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreos.eu/\">" + "\n" +
				" <soapenv:Header/>" + "\n" +
				" <soapenv:Body>"+ "\n" +
				" <chor:getPriceResponse>" + "\n"+
				"   <!--Optional:-->" + "\n"+
					"    <return>10000</return>"+ "\n"+
				"   </chor:getPriceResponse>" + "\n"+
						"  </soapenv:Body>" + "\n"+
				"</soapenv:Envelope>'''";

		assertEquals(expectedScript.replace(" ", ""), builder.getScript().replace(" ", ""));
	}
	
	@Test
	public void shouldAddAnIfStatementWhenReceiveAComplexItem() throws Exception {
		Item request = new ItemImpl("getProductStatus");
		request.addChild("name").setContent("milk");
		
		Item response = new ItemImpl("getProductStatusResponse");
		Item responseContent = response.addChild("return");
		responseContent.addChild("name").setContent("milk");

		responseContent.addChild("status").setContent("empty");
		
		ScriptBuilder builder = new ScriptBuilder(null, false);
		
		String wsdl = "file://" + System.getProperty("user.dir") + "/resource/sm_plus.wsdl"; 
		builder.setDefaultRequest(MockUtils.getDefaultRequest(wsdl, "getProductStatus"));
		builder.setDefaultResponse(MockUtils.getDefaultResponse(wsdl, "getProductStatus"));
		
		MockResponse mockResponse = new MockResponse().whenReceive(request).replyWith(response);
		builder.addConditionFor(mockResponse);

		String expectedScript = "def request = new XmlSlurper().parseText(mockRequest.requestContent)" + "\n" +
				"if ( request == new XmlSlurper().parseText('''<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreos.eu/\">" + 
					"<soapenv:Header></soapenv:Header>" + 
					"<soapenv:Body>" + 
					"<chor:getProductStatus>" + 
					"<name>milk</name>" + 
					"</chor:getProductStatus>" + 
					"</soapenv:Body>" + 
					"</soapenv:Envelope>'''))" + "\n" +
					"context.message = '''<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreos.eu/\">" + 
					"<soapenv:Header></soapenv:Header>" + 
					"<soapenv:Body>" + 
					"<chor:getProductStatusResponse>" + 
					"<return>" + 
					"<name>milk</name>" + 
					"<status>empty</status>" + 
					"</return>" +
					"</chor:getProductStatusResponse>" + 
					"</soapenv:Body>" + 
					"</soapenv:Envelope>'''" + "\n";

		assertEquals(expectedScript.replace(" ", ""), builder.getScript().replace(" ", ""));
	}
	
	private ScriptBuilder getBuilder() throws XmlException, IOException,
	SoapUIException {
		ScriptBuilder builder = new ScriptBuilder(null, false);
		
		String wsdl = "file://" + System.getProperty("user.dir") + "/resource/sm_plus.wsdl"; 
		builder.setDefaultRequest(MockUtils.getDefaultRequest(wsdl, "getPrice"));
		builder.setDefaultResponse(MockUtils.getDefaultResponse(wsdl, "getPrice"));
		return builder;
	}
}