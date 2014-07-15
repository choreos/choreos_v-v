package eu.choreos.vv.clientgenerator;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.NoMockResponseException;
import eu.choreos.vv.exceptions.NoReplyWithStatementException;
import eu.choreos.vv.exceptions.WSDLException;
import eu.choreos.vv.servicesimulator.MockResponse;
import eu.choreos.vv.servicesimulator.WSMock;

public class ParseXmlCdataResponseTest {

	@Test
	public void shouldReadSuccessFromResponse() throws WSDLException, XmlException, IOException, FrameworkException, InvalidOperationNameException, NoSuchFieldException, NoMockResponseException,
			NoReplyWithStatementException {
		String wsdl = "file://" + System.getProperty("user.dir") + "/resource/globalweather.wsdl";

		WSMock wsMock = new WSMock("globalweather", "4321", wsdl);
		MockResponse mockResponse = new MockResponse();
		String path = System.getProperty("user.dir") + "/resource/getWeatherResponse.xml";
		mockResponse.whenReceive(request()).replyWith(fileContent(path));
		wsMock.returnFor("GetWeather", mockResponse);
		wsMock.start();

		WSClient wsClient = new WSClient("http://localhost:4321/globalweather?wsdl");
		Item response = wsClient.request("GetWeather", request());
		String content = response.getContent("GetWeatherResult");
		assertTrue(content.contains("<Status>Success</Status>"));
	}

	private Item request() {
		Item request = new ItemImpl("GetWeather");
		request.addChild("CountryName").setContent("United States");
		request.addChild("CityName").setContent("New York");
		return request;
	}

	public String fileContent(String filePath) throws FileNotFoundException {
		InputStream stream = new FileInputStream(new File(filePath));
		return readAsString(stream);
	}

	public String readAsString(InputStream inputStream) {
		return readAsString(inputStream, "UTF-8");
	}

	public String readAsString(InputStream inputStream, String encoding) {
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(inputStream, writer, encoding);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return writer.toString();
	}
}
