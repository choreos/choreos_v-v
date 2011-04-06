package br.usp.ime.choreos.vv;

import static junit.framework.Assert.assertEquals;

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

	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowExceptionForMissingArguments() throws IllegalArgumentException {
		
		List<String> parameters = new ArrayList<String>();
		parameters.add("Milan");
		
		SoapEnvelopeHelper.generate(testXml, parameters);
	}
	
	@Test
	public void shouldReplaceMarksInSoapWithRequiredArguments() throws IllegalArgumentException {
		
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
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowExceptionForExtraArgument() throws IllegalArgumentException {
		
		List<String> parameters = new ArrayList<String>();
		parameters.add("Milan");
		parameters.add("12-21-2010");
		parameters.add("Extra parameter");
		
		SoapEnvelopeHelper.generate(testXml, parameters);
	}
	
	@Test
	public void shouldReplaceMarksInSoapWithNonAsciiCharacters() throws IllegalArgumentException{

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

	@Test
	public void testCleanResponse(){
		String xml = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"> "
				+ "<S:Body>"
				+ "<ns2:searchByArtistResponse xmlns:ns2=\"http://ws.vvws.choreos.ime.usp.br/\">"
				+ "<return>The dark side of the moon;</return>"
				+ "</ns2:searchByArtistResponse>"
				+ "</S:Body>"
				+ "</S:Envelope>";
		
		String expectedOutput = "<return>The dark side of the moon;</return>";
		
		assertEquals(expectedOutput, SoapEnvelopeHelper.getCleanResponse(xml));
		
	}

}
