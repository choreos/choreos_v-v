package eu.choreos.vv.servicesimulator;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import com.eviware.soapui.support.SoapUIException;

public class ScriptBuilderTest {
	
	@Test
	public void shouldScriptBeCreatedWithItsHeader() throws Exception {
		ScriptBuilder builder = new ScriptBuilder();
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
				"</soapenv:Envelope>'''" + "\n";
		
		assertEquals(expectedScript.replace(" ", ""), builder.getScript().replace(" ", ""));
	}
	
	
	private ScriptBuilder getBuilder() throws XmlException, IOException,
	SoapUIException {
		ScriptBuilder builder = new ScriptBuilder();
		
		String wsdl = "file://" + System.getProperty("user.dir") + "/resource/sm_plus.wsdl"; 
		builder.setDefaultRequest(MockUtils.getDefaultRequest(wsdl, "getPrice"));
		builder.setDefaultResponse(MockUtils.getDefaultResponse(wsdl, "getPrice"));
		return builder;
	}
}