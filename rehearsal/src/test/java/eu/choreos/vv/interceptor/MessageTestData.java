package eu.choreos.vv.interceptor;

public class MessageTestData {
	
	public static String getRequestContent() {
		String requestContent = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sim=\"http://simplestorews.vvws.choreos.eu/\">" + "\n" +
				"<soapenv:Header/>" + "\n" +
				"<soapenv:Body>" + "\n" +
				"<sim:searchByArtist>" + "\n" +
				"<arg0>Pink Floyd</arg0>" + "\n" +
				"</sim:searchByArtist>" + "\n" +
				"</soapenv:Body>" + "\n" +
				"</soapenv:Envelope>";
		return requestContent;
	}
	
	public static String getRequestContentWithComments() {
		String requestContent = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sim=\"http://simplestorews.vvws.choreos.eu/\">" + "\n" +
				"<soapenv:Header/>" + "\n" +
				"<soapenv:Body>" + "\n" +
				"<sim:searchByArtist>" + "\n" +
				"<!--Optional:-->" + "\n" +
				"<arg0>Pink Floyd</arg0>" + "\n" +
				"</sim:searchByArtist>" + "\n" +
				"</soapenv:Body>" + "\n" +
				"</soapenv:Envelope>";
		return requestContent;
	}
	
	public static String getResponseContent() {
		String responseContent = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" + "\n" +
				"<S:Body>" + "\n" +
				"<ns2:searchByArtistResponse xmlns:ns2=\"http://simplestorews.vvws.choreos.eu/\">" + "\n" +
				"<return>The dark side of the moon;</return>" + "\n" +
				"</ns2:searchByArtistResponse>" + "\n" +
				"</S:Body>" + "\n" +
				"</S:Envelope>";
		return responseContent;
	}

}
