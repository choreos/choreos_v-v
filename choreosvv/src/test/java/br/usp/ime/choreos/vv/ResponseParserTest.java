package br.usp.ime.choreos.vv;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import static junit.framework.Assert.assertEquals;

public class ResponseParserTest {
	
        @Test
        public void shouldParseASimpleXml() throws ParserConfigurationException, SAXException {
                String sampleXml = "<senv:Envelope " 
                                + "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
                                + "                             xmlns:tns=\"tns\">" 
                                + "  <senv:Body>"
                                + "   <tns:search_by_brandResponse>" 
                                + "<tns:search_by_brandResult>" 
                                + "    <s1:name>mouse</s1:name>" 
                                + "</tns:search_by_brandResult>" 
                                + "</tns:search_by_brandResponse>" 
                                + "</senv:Body>" 
                                + "</senv:Envelope>";
                
                ResponseParser parser = new ResponseParser();
                ResponseItem actual = parser.parse(sampleXml);
                
                ResponseItem expected = new ResponseItem("search_by_brandResult");
                ResponseItem child = new ResponseItem("name");
                child.setContent("mouse");
                expected.addChild(child);
                
                assertEquals(expected, actual); 
        }
        
        
        @Test
	public void shouldParseASimpleXmlWithLineBreaks() throws ParserConfigurationException, SAXException {
		String sampleXml = "<senv:Envelope " 
				+ "				xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
				+ "				xmlns:tns=\"tns\">\n" 
				+ "  <senv:Body>\n"
				+ "   <tns:search_by_brandResponse>\n" 
				+ "<tns:search_by_brandResult>\n" 
				+ "    <s1:name>mouse</s1:name>\n" 
				+ "</tns:search_by_brandResult>\n" 
				+ "</tns:search_by_brandResponse>\n" 
				+ "</senv:Body>\n" 
				+ "</senv:Envelope>";
		
		ResponseParser parser = new ResponseParser();
                ResponseItem actual = parser.parse(sampleXml);
                
                ResponseItem expected = new ResponseItem("search_by_brandResult");
                ResponseItem child = new ResponseItem("name");
                child.setContent("mouse");
                expected.addChild(child);
                
                assertEquals(expected, actual);
	}
        
        
        @Test
        public void shouldParseAnItemWithParameters() throws ParserConfigurationException, SAXException, NoSuchFieldException{
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
                
                ResponseParser parser = new ResponseParser();
                ResponseItem item = parser.parse(sampleXml);

                ResponseItem actual = item.getChild("name"); 
                assertEquals("5", actual.getTagAttributes().get("length"));
        }
	
        
	@Test
	public void shouldParseAComplexTypeTest() throws ParserConfigurationException, SAXException{
	
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

		ResponseParser parser = new ResponseParser();
                ResponseItem actual = parser.parse(sampleXml);
                
                ResponseItem expected = new ResponseItem("search_by_categoryResult");
                
                ResponseItem item = new ResponseItem("Item");
                
                ResponseItem childA = new ResponseItem("category");
                childA.setContent("mouse");
                item.addChild(childA);
                
                ResponseItem childB = new ResponseItem("price");
                childB.setContent("89.2");
                item.addChild(childB);
                
                ResponseItem childC = new ResponseItem("model");
                childC.setContent("RZG145");
                item.addChild(childC);
                
                ResponseItem childD = new ResponseItem("brand");
                childD.setContent("Razor");
                item.addChild(childD);                
                
                expected.addChild(item);
                
                assertEquals(expected, actual);		
	}
	
	
	@Test
	public void shouldPaseComplexTypeListWithEmptySpaces() throws ParserConfigurationException, SAXException{
	
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
		
		ResponseItem expected = new ResponseItem("search_by_categoryResult");
	                
	        ResponseItem item1 = new ResponseItem("Item");
                ResponseItem childA = new ResponseItem("category");
                childA.setContent("mouse");
                item1.addChild(childA);	                
                ResponseItem childB = new ResponseItem("price");
                childB.setContent("89.2");
                item1.addChild(childB);	                
                ResponseItem childC = new ResponseItem("model");
                childC.setContent("RZG145");
                item1.addChild(childC);	                
                ResponseItem childD = new ResponseItem("brand");
                childD.setContent("Razor");
                item1.addChild(childD);          
                expected.addChild(item1);
	
                ResponseItem item2 = new ResponseItem("Item");
                childA = new ResponseItem("category");
                childA.setContent("mouse");
                item2.addChild(childA);                  
                childB = new ResponseItem("price");
                childB.setContent("61.0");
                item2.addChild(childB);                  
                childC = new ResponseItem("model");
                childC.setContent("CCCC");
                item2.addChild(childC);                  
                childD = new ResponseItem("brand");
                childD.setContent("Clone");
                item2.addChild(childD);          
                expected.addChild(item2);
        
                ResponseItem item3 = new ResponseItem("Item");
                childA = new ResponseItem("category");
                childA.setContent("mouse");
                item3.addChild(childA);                  
                childB = new ResponseItem("price");
                childB.setContent("61.0");
                item3.addChild(childB);                  
                childC = new ResponseItem("model");
                childC.setContent("MS23F");
                item3.addChild(childC);                  
                childD = new ResponseItem("brand");
                childD.setContent("Microsoft");
                item3.addChild(childD);          
                expected.addChild(item3);
                
		ResponseParser parser = new ResponseParser();
		ResponseItem actual = parser.parse(sampleXml);
		
		assertEquals(expected, actual);
	}
	
}