package br.usp.ime.choreos.vv;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SoapEnvelopeHelperTest {

	private String testXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:trav=\"http://airline.ws.ime.usp.br/\">"
		+ "<soapenv:Header/>"
		+ "<soapenv:Body>"
		+ "<trav:getFlight>"
		+ "<arg0>?</arg0>"
		+ "<arg1>?</arg1>"
		+ "</trav:getFlight>"
		+ "</soapenv:Body>"
		+ "</soapenv:Envelope>";

	
	@Test(expected=Exception.class)
	public void shouldThrowExceptionForMissingArguments() throws Exception {
		
		List<String> parameters = new ArrayList<String>();
		parameters.add("Milan");
		
		SoapEnvelopeHelper.generate(testXml, parameters);
	}
	
	@Test
	public void shouldReplaceMarksInSoapWithRequiredArguments() throws Exception {
		
		List<String> parameters = new ArrayList<String>();
		parameters.add("Milan");
		parameters.add("12-21-2010");
		
		String result = SoapEnvelopeHelper.generate(testXml, parameters); 
		
		String expectedOutput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:trav=\"http://airline.ws.ime.usp.br/\">"
			+ "<soapenv:Header/>"
			+ "<soapenv:Body>"
			+ "<trav:getFlight>"
			+ "<arg0>Milan</arg0>"
			+ "<arg1>12-21-2010</arg1>"
			+ "</trav:getFlight>"
			+ "</soapenv:Body>"
			+ "</soapenv:Envelope>";
		
		assertEquals(expectedOutput, result);
	}
	
	@Test(expected=Exception.class)
	public void shouldThrowExceptionForExtraArgument() throws Exception {
		
		List<String> parameters = new ArrayList<String>();
		parameters.add("Milan");
		parameters.add("12-21-2010");
		parameters.add("Extra parameter");
		
		SoapEnvelopeHelper.generate(testXml, parameters);
	}
	
	@Test
	public void shouldReplaceMarksInSoapWithNonAsciiCharacters() throws Exception {

		List<String> parameters = new ArrayList<String>();
		parameters.add("Araçá do Ribeirão");
		parameters.add("12-21-2010");
		
		String result = SoapEnvelopeHelper.generate(testXml, parameters); 
		
		String expectedOutput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:trav=\"http://airline.ws.ime.usp.br/\">"
			+ "<soapenv:Header/>"
			+ "<soapenv:Body>"
			+ "<trav:getFlight>"
			+ "<arg0>Araçá do Ribeirão</arg0>"
			+ "<arg1>12-21-2010</arg1>"
			+ "</trav:getFlight>"
			+ "</soapenv:Body>"
			+ "</soapenv:Envelope>";
		
		assertEquals(expectedOutput, result);
	}

}
