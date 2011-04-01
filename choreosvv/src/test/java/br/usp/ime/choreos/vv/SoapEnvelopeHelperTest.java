package br.usp.ime.choreos.vv;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.usp.ime.choreos.vv.SoapEnvelopeHelper;
import static org.junit.Assert.*;

public class SoapEnvelopeHelperTest {
	
	@Test
	public void testGenerator() throws Exception {
		String testXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:trav=\"http://airline.ws.ime.usp.br/\">"
			+ "<soapenv:Header/>"
			+ "<soapenv:Body>"
			+ "<trav:getFlight>"
			+ "<arg0>?</arg0>"
			+ "<arg1>?</arg1>"
			+ "</trav:getFlight>"
			+ "</soapenv:Body>"
			+ "</soapenv:Envelope>";
		
		
		List<String> parameters = new ArrayList<String>();
		parameters.add("Milan");
		
		String result;
		
		try {
			result = SoapEnvelopeHelper.generate(testXml, parameters);
			assertTrue(false);
		} catch(Exception e){
			// Test passed
		}
		
		parameters.add("12-21-2010");
		
		result = SoapEnvelopeHelper.generate(testXml, parameters); 
		
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
		
		parameters.add("Extra parameter");
		
		try {
			result = SoapEnvelopeHelper.generate(testXml, parameters);
			assertTrue(false);
		} catch(Exception e){
			// Test passed
		}
	}
	
	@Test
	public void testGeneratorWithNonAsciiCharacters() throws Exception {
		String testXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:trav=\"http://airline.ws.ime.usp.br/\">"
			+ "<soapenv:Header/>"
			+ "<soapenv:Body>"
			+ "<trav:getFlight>"
			+ "<arg0>?</arg0>"
			+ "<arg1>?</arg1>"
			+ "</trav:getFlight>"
			+ "</soapenv:Body>"
			+ "</soapenv:Envelope>";
		
		
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
