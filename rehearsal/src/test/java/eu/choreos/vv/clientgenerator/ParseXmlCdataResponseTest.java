package eu.choreos.vv.clientgenerator;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

public class ParseXmlCdataResponseTest {

	@Test
	public void shouldReadSuccessFromResponse() throws WSDLException, XmlException, IOException, FrameworkException, InvalidOperationNameException, XPathExpressionException, NoSuchFieldException {
		String wsdl = "http://www.webservicex.net/globalweather.asmx?WSDL";
		WSClient wsClient = new WSClient(wsdl);

		Item request = new ItemImpl("GetWeather");
		request.addChild("CountryName").setContent("United States");
		request.addChild("CityName").setContent("New York");

		Item response = wsClient.request("GetWeather", request);
		String content = response.getContent("GetWeatherResult");

		assertTrue(content.contains("<Status>Success</Status>"));
	}
}
