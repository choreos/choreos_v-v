package eu.choreos.vv.clientgenerator;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import eu.choreos.vv.exceptions.ParserException;


/**
 * JAX-WS tests made with version  JAX-WS RI 2.1.2-b05-RC1
 * Ruby tests made with version... ??? // TODO
 *
 */
public class ResponseParserTest {

	@Test
	public void shouldParseASimpleXml() throws  ParserException {
		String sampleXml = "<senv:Envelope " 
			+ "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
			+ "                             xmlns:tns=\"tns\">" 
			+ "<senv:Body>"
			+ "    <tns:search_by_brandResponse xmln=\"schema\">" 
			+ "        <tns:search_by_brandResult>" 
			+ "            <s1:name>mouse</s1:name>" 
			+ "        </tns:search_by_brandResult>" 
			+ "    </tns:search_by_brandResponse>" 
			+ "</senv:Body>" 
			+ "</senv:Envelope>";

		ItemParser parser = new ItemParser();
		Item actual = parser.parse(sampleXml);

		HashMap<String, String> rootParameters = new HashMap<String, String>();
		rootParameters.put("xmln", "schema");
		ItemImpl root = new ItemImpl("search_by_brandResponse", rootParameters);
		ItemImpl child1 = new ItemImpl("search_by_brandResult");
		ItemImpl child2 = new ItemImpl("name");
		child2.setContent("mouse");
		child1.addChild(child2);
		root.addChild(child1);

		assertEquals(root, actual); 
	}

	@Test
	public void shouldParseATagWithoutNamespace() throws   ParserException, NoSuchFieldException {
		String sampleXml = "<senv:Envelope " 
			+ "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
			+ "                             xmlns:tns=\"tns\">" 
			+ "<senv:Body>"
			+ "    <search_by_brandResponse xmln=\"schema\">" 
			+ "        <brand>Nike</brand>"
			+ "    </search_by_brandResponse>" 
			+ "</senv:Body>" 
			+ "</senv:Envelope>";

		ItemParser parser = new ItemParser();
		Item actual = parser.parse(sampleXml);

		assertEquals("search_by_brandResponse", actual.getName());

		assertEquals( "Nike", actual.getChild("brand").getContent()); 
	}

	@Test (expected=ParserException.class)
	public void shouldThrowsParse() throws  ParserException {
		String sampleXml = "<senv:Envelope " 
			+ "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
			+ "                             xmlns:tns=\"tns\">" 
			+ "  <senv:Body>"
			+ "   <brand>Nike</brand>"
			+ "</senv:Body>";

		ItemParser parser = new ItemParser();
		parser.parse(sampleXml);
	}


	@Test
	public void shouldParseASimpleXmlWithLineBreaks() throws  ParserException {
		String sampleXml = "<senv:Envelope " 
			+ "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
			+ "                             xmlns:tns=\"tns\">\n" 
			+ "  <senv:Body>\n"
			+ "   <tns:search_by_brandResponse>\n" 
			+ "<tns:search_by_brandResult>\n" 
			+ "    <s1:name>mouse</s1:name>\n" 
			+ "</tns:search_by_brandResult>\n" 
			+ "</tns:search_by_brandResponse>\n" 
			+ "</senv:Body>\n" 
			+ "</senv:Envelope>";

		ItemParser parser = new ItemParser();
		Item actual = parser.parse(sampleXml);

		ItemImpl root = new ItemImpl("search_by_brandResponse");
		ItemImpl child1 = new ItemImpl("search_by_brandResult");
		ItemImpl child2 = new ItemImpl("name");
		child2.setContent("mouse");
		child1.addChild(child2);
		root.addChild(child1);

		assertEquals((Integer)1, actual.getChildrenCount());

		assertEquals(root, actual);
	}


	@Test
	public void shouldParseAnItemWithParameters() throws NoSuchFieldException,  ParserException{
		String sampleXml = "<senv:Envelope " 
			+ "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
			+ "                             xmlns:tns=\"tns\">" 
			+ "  <senv:Body>"
			+ "   <tns:search_by_brandResponse>" 
			+ "<tns:search_by_brandResult>" 
			+ "    <s1:name length=\"5\">mouse</s1:name>" 
			+ "</tns:search_by_brandResult>" 
			+ "</tns:search_by_brandResponse>" 
			+ "</senv:Body>" 
			+ "</senv:Envelope>"; 

		ItemParser parser = new ItemParser();
		Item actual = parser.parse(sampleXml);

		Item child1 = actual.getChild("search_by_brandResult").getChild("name"); 
		assertEquals("5", child1.getTagAttributes().get("length"));
	}

	@Test
	public void shouldParseAComplexTypeTest() throws  ParserException, NoSuchFieldException{

		String sampleXml = "<senv:Envelope " + 
		"xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " + 
		"xmlns:tns=\"tns\"> " + 
		"<senv:Body>" +
		"<tns:search_by_categoryResponse>" +
		"<tns:search_by_categoryResult>" +
		"<s1:Item>" +
		"<s1:category>mouse</s1:category>" +
		"<s1:price>89.2</s1:price>" +
		"<s1:model>RZG145</s1:model>" +
		"<s1:brand>Razor</s1:brand>" +
		"</s1:Item>" +
		"</tns:search_by_categoryResult>" +
		"</tns:search_by_categoryResponse>" +
		"</senv:Body>" +
		"</senv:Envelope>";

		ItemParser parser = new ItemParser();
		Item actual = parser.parse(sampleXml);

		assertEquals("search_by_categoryResponse", actual.getName());
		assertEquals((Integer)1, actual.getChildrenCount());

		actual = (ItemImpl) actual.getChild("search_by_categoryResult");

		ItemImpl expected = new ItemImpl("search_by_categoryResult");

		ItemImpl item = new ItemImpl("Item");

		ItemImpl childA = new ItemImpl("category");
		childA.setContent("mouse");
		item.addChild(childA);

		ItemImpl childB = new ItemImpl("price");
		childB.setContent("89.2");
		item.addChild(childB);

		ItemImpl childC = new ItemImpl("model");
		childC.setContent("RZG145");
		item.addChild(childC);

		ItemImpl childD = new ItemImpl("brand");
		childD.setContent("Razor");
		item.addChild(childD);                

		expected.addChild(item);

		assertEquals(expected, actual);         
	}


	@Test
	public void shouldPaseComplexTypeListWithEmptySpaces() throws  ParserException, NoSuchFieldException{

		String sampleXml = "<senv:Envelope " + 
		"xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " + 
		"xmlns:tns=\"tns\"> " + 
		"<senv:Body>" +
		"<tns:search_by_categoryResponse    >  " +
		"<tns:search_by_categoryResult    >" +
		"  <s1:Item>  " +
		"<s1:category>mouse  </s1:category>  " +
		"<s1:price>89.2</s1:price>  " +
		"  <s1:model>RZG145</s1:model>  " +
		"<s1:brand>Razor</s1:brand>" +
		"</s1:Item>" +
		"<s1:Item>" +
		"<s1:category>mouse</s1:category>" +
		"<s1:price>61.0</s1:price>" +
		"<s1:model>CCCC</s1:model>" +
		"<s1:brand>Clone</s1:brand>" +
		"</s1:Item>" +
		"<s1:Item>" +
		"<s1:category>mouse</s1:category>" +
		"<s1:price   >  61.0</s1:price>  " +
		"   <s1:model>MS23F</s1:model>" +
		"<s1:brand>Microsoft</s1:brand>" +
		"</s1:Item>" +                                  
		"</tns:search_by_categoryResult>" +
		"</tns:search_by_categoryResponse>" +
		"</senv:Body>" +
		"</senv:Envelope>";

		ItemImpl expected = new ItemImpl("search_by_categoryResult");

		ItemImpl item1 = new ItemImpl("Item");
		ItemImpl childA = new ItemImpl("category");
		childA.setContent("mouse");
		item1.addChild(childA);                  
		ItemImpl childB = new ItemImpl("price");
		childB.setContent("89.2");
		item1.addChild(childB);                  
		ItemImpl childC = new ItemImpl("model");
		childC.setContent("RZG145");
		item1.addChild(childC);                  
		ItemImpl childD = new ItemImpl("brand");
		childD.setContent("Razor");
		item1.addChild(childD);          
		expected.addChild(item1);

		ItemImpl item2 = new ItemImpl("Item");
		childA = new ItemImpl("category");
		childA.setContent("mouse");
		item2.addChild(childA);                  
		childB = new ItemImpl("price");
		childB.setContent("61.0");
		item2.addChild(childB);                  
		childC = new ItemImpl("model");
		childC.setContent("CCCC");
		item2.addChild(childC);                  
		childD = new ItemImpl("brand");
		childD.setContent("Clone");
		item2.addChild(childD);          
		expected.addChild(item2);

		ItemImpl item3 = new ItemImpl("Item");
		childA = new ItemImpl("category");
		childA.setContent("mouse");
		item3.addChild(childA);                  
		childB = new ItemImpl("price");
		childB.setContent("61.0");
		item3.addChild(childB);                  
		childC = new ItemImpl("model");
		childC.setContent("MS23F");
		item3.addChild(childC);                  
		childD = new ItemImpl("brand");
		childD.setContent("Microsoft");
		item3.addChild(childD);          
		expected.addChild(item3);

		ItemParser parser = new ItemParser();
		Item actual = parser.parse(sampleXml);

		assertEquals("search_by_categoryResponse", actual.getName());
		assertEquals((Integer)1, actual.getChildrenCount());

		actual = (ItemImpl) actual.getChild("search_by_categoryResult");

		assertEquals(expected, actual);
	}

	@Test
	public void testResponseWithNoRootTag() throws NoSuchFieldException, ParserException {
		String xml =  "<soapenv:Envelope xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\">"
			+ "  <soapenv:Body>"
			+ "      <ns:getItensByBrandResponse xmlns:ns=\"http://ws.vvws.choreos.ime.usp.br\" xmlns:ax21=\"http://rmi.java/xsd\" xmlns:ax22=\"http://io.java/xsd\" xmlns:ax26=\"http://model.vvws.choreos.ime.usp.br/xsd\">"
			+ "         <ns:return xsi:type=\"ax26:Item\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
			+ "           <ax26:barcode>133</ax26:barcode>"
			+ "           <ax26:brand>adidas</ax26:brand>"
			+ "            <ax26:description>A Soccershirt</ax26:description>"
			+ "            <ax26:name>Soccershirt</ax26:name>"
			+ "           <ax26:price>80.0</ax26:price>"
			+ "           <ax26:sport>soccer</ax26:sport>"
			+ "        </ns:return>"
			+ "         <ns:return xsi:type=\"ax26:Item\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
			+ "           <ax26:barcode>143</ax26:barcode>"
			+ "           <ax26:brand>adidas</ax26:brand>"
			+ "            <ax26:description>A Soccerball</ax26:description>"
			+ "           <ax26:name>Ball</ax26:name>"
			+ "           <ax26:price>20.0</ax26:price>"
			+ "           <ax26:sport>soccer</ax26:sport>"
			+ "        </ns:return>"
			+ "         <ns:return xsi:type=\"ax26:Item\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
			+ "           <ax26:barcode>153</ax26:barcode>"
			+ "           <ax26:brand>adidas</ax26:brand>"
			+ "            <ax26:description>A Soccercleat</ax26:description>"
			+ "            <ax26:name>Soccercleat</ax26:name>"
			+ "           <ax26:price>90.0</ax26:price>"
			+ "           <ax26:sport>soccer</ax26:sport>"
			+ "        </ns:return>"
			+ "     </ns:getItensByBrandResponse>"
			+ "  </soapenv:Body>"
			+ "</soapenv:Envelope>";

		ItemParser parser = new ItemParser();
		Item actual = parser.parse(xml);

		assertEquals("getItensByBrandResponse", actual.getName());

		List<Item> l = actual.getChildAsList("return"); 
		assertEquals(3, l.size());

		assertEquals(new Integer(133), l.get(0).getChild("barcode").getContentAsInt());

		assertEquals(new Integer(143), l.get(1).getChild("barcode").getContentAsInt());

		assertEquals(new Integer(153), l.get(2).getChild("barcode").getContentAsInt());

	}

	@Test
	public void shouldParseVoidReturn() throws ParserException {

		// just testing if no MissingResponseTagException is thrown

		// On Ruby web services, calling a "void" method returns this SOAP XML as response
		//
		// On JAX-WS if the return method signature has any type (not void), 
		// and the method execution returns null
		// the SOAP response XML will be also like this
		String sampleXml = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
		"<S:Body>" +
		" <ns2:cancelPurchaseResponse xmlns:ns2=\"http://ws.vvws.choreos.ime.usp.br/\"/>" +
		"</S:Body>" +
		"</S:Envelope>";

		ItemParser parser = new ItemParser();
		Item item = null;
		item = parser.parse(sampleXml);
		
		assertEquals(null, item.getContent());
	}

	@Test
	public void shouldReturnEmptyStringWithEmptyResponseOnRuby() throws ParserException {
		
		// On Ruby web services, returning an empty string is represented by
		// the following SOAP response XML 
		String sampleXml = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
		"<S:Body>" +
		" <ns2:cancelPurchaseResponse xmlns:ns2=\"http://ws.vvws.choreos.ime.usp.br/\">" +
		" </ns2:cancelPurchaseResponse>" +
		"</S:Body>" +
		"</S:Envelope>";
		
		ItemParser parser = new ItemParser();
		assertNull(parser.parse(sampleXml).getContent());
	}
	
	@Test
	public void shouldReturnStringWhenResponseContentIsOnlyString() throws ParserException {
		String sampleXml = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
		"<S:Body>" +
		" <ns2:cancelPurchaseResponse xmlns:ns2=\"http://ws.vvws.choreos.ime.usp.br/\">" +
		"    this is some content" +
		" </ns2:cancelPurchaseResponse>" +
		"</S:Body>" +
		"</S:Envelope>";
		
		ItemParser parser = new ItemParser();
		assertEquals("this is some content", parser.parse(sampleXml).getContent());
	}

	@Test
	public void shouldReturnEmptyStringWithEmptyResponseOnJaxws() throws ParserException,  NoSuchFieldException{
		
		// On JAX-WS, returning an empty string is represented by
		// the following SOAP response XML 
		String sampleXml = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
		"<S:Body>" +
		" <ns2:cancelPurchaseResponse xmlns:ns2=\"http://ws.vvws.choreos.ime.usp.br/\">" +
		"    <return/>" +
		" </ns2:cancelPurchaseResponse>" +
		"</S:Body>" +
		"</S:Envelope>";
		
		ItemParser parser = new ItemParser();
		assertNull(parser.parse(sampleXml).getChild("return").getContent());
	}
	
	@Test
	public void shouldReturnNullItemWithXmlWithNoResponseTag() throws ParserException,  NoSuchFieldException{
		
		// On JAX-WS
		String sampleXml = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
		"<S:Body>" +
		"</S:Body>" +
		"</S:Envelope>";
		
		ItemParser parser = new ItemParser();
		assertNull(parser.parse(sampleXml));
	}
	
	@Test
	public void shouldReturnTheCorrectItemObjectEvenWhenTheMessageContainsResponseTag() throws Exception{
	
		String sampleXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sim=\"http://simplestorews.vvws.choreos.eu/\">" +
													"<soapenv:Header/>" +
													"<soapenv:Body>" +
													"<sim:searchByArtist>" +
													"<!--Optional:-->" +
													"<arg0>Pink Floyd</arg0>" +
													"</sim:searchByArtist>" +
													"</soapenv:Body>" +
													"</soapenv:Envelope>";
		
		ItemParser parser = new ItemParser();
		Item message = parser.parse(sampleXml);
		
		assertEquals("Pink Floyd", message.getChild("arg0").getContent());
	}
	
}
