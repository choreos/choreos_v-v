package br.usp.ime.choreos.vv;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import br.usp.ime.choreos.vv.exceptions.MissingResponseTagException;
import br.usp.ime.choreos.vv.exceptions.ParserException;

import static junit.framework.Assert.assertEquals;

public class ResponseParserTest {
        
        @Test
        public void shouldParseASimpleXml() throws MissingResponseTagException, ParserException {
                String sampleXml = "<senv:Envelope " 
                                + "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
                                + "                             xmlns:tns=\"tns\">" 
                                + "  <senv:Body>"
                                + "   <tns:search_by_brandResponse xmln=\"schema\">" 
                                + "<tns:search_by_brandResult>" 
                                + "    <s1:name>mouse</s1:name>" 
                                + "</tns:search_by_brandResult>" 
                                + "</tns:search_by_brandResponse>" 
                                + "</senv:Body>" 
                                + "</senv:Envelope>";
                
                ResponseParser parser = new ResponseParser();
                ResponseItemImpl actual = parser.parse(sampleXml);
                
                ResponseItemImpl expected = new ResponseItemImpl("search_by_brandResult");
                ResponseItemImpl child = new ResponseItemImpl("name");
                child.setContent("mouse");
                expected.addChild(child);
                
                assertEquals(expected, actual); 
        }
        
        @Test
        public void shouldParseATagWithoutNamespace() throws  MissingResponseTagException, ParserException {
                String sampleXml = "<senv:Envelope " 
                                + "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
                                + "                             xmlns:tns=\"tns\">" 
                                + "  <senv:Body>"
                                + "   <search_by_brandResponse xmln=\"schema\">" 
                                + "   <brand>Nike</brand>"
                                + "  </search_by_brandResponse>" 
                                + "</senv:Body>" 
                                + "</senv:Envelope>";
                
                ResponseParser parser = new ResponseParser();
                ResponseItemImpl actual = parser.parse(sampleXml);
                                                
                assertEquals( "Nike", actual.getContent()); 
        }
        
        @Test (expected=MissingResponseTagException.class)
        public void shouldThrowsAnExceptioNWhenThereIsNoResponseTag() throws MissingResponseTagException, ParserException {
                String sampleXml = "<senv:Envelope " 
                                + "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
                                + "                             xmlns:tns=\"tns\">" 
                                + "  <senv:Body>"
                                + "   <brand>Nike</brand>"
                                + "</senv:Body>"
                                + "</senv:Envelope>";
                
                ResponseParser parser = new ResponseParser();
                parser.parse(sampleXml);
        }
        
        @Test (expected=ParserException.class)
        public void shouldThrowsParse() throws MissingResponseTagException, ParserException {
                String sampleXml = "<senv:Envelope " 
                                + "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
                                + "                             xmlns:tns=\"tns\">" 
                                + "  <senv:Body>"
                                + "   <brand>Nike</brand>"
                                + "</senv:Body>";
                
                ResponseParser parser = new ResponseParser();
                parser.parse(sampleXml);
        }
        
        
        @Test
        public void shouldParseASimpleXmlWithLineBreaks() throws MissingResponseTagException, ParserException {
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
                
                ResponseParser parser = new ResponseParser();
                ResponseItemImpl actual = parser.parse(sampleXml);
                
                ResponseItemImpl expected = new ResponseItemImpl("search_by_brandResult");
                ResponseItemImpl child = new ResponseItemImpl("name");
                child.setContent("mouse");
                expected.addChild(child);
                
                assertEquals(expected, actual);
        }
        
        
        @Test
        public void shouldParseAnItemWithParameters() throws NoSuchFieldException, MissingResponseTagException, ParserException{
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
                ResponseItemImpl item = parser.parse(sampleXml);

                ResponseItem actual = item.getChild("name"); 
                assertEquals("5", actual.getTagAttributes().get("length"));
        }
        
        
        @Test
        public void shouldParseAComplexTypeTest() throws MissingResponseTagException, ParserException{
        
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
                ResponseItemImpl actual = parser.parse(sampleXml);
                
                ResponseItemImpl expected = new ResponseItemImpl("search_by_categoryResult");
                
                ResponseItemImpl item = new ResponseItemImpl("Item");
                
                ResponseItemImpl childA = new ResponseItemImpl("category");
                childA.setContent("mouse");
                item.addChild(childA);
                
                ResponseItemImpl childB = new ResponseItemImpl("price");
                childB.setContent("89.2");
                item.addChild(childB);
                
                ResponseItemImpl childC = new ResponseItemImpl("model");
                childC.setContent("RZG145");
                item.addChild(childC);
                
                ResponseItemImpl childD = new ResponseItemImpl("brand");
                childD.setContent("Razor");
                item.addChild(childD);                
                
                expected.addChild(item);
                
                assertEquals(expected, actual);         
        }
        
        
        @Test
        public void shouldPaseComplexTypeListWithEmptySpaces() throws MissingResponseTagException, ParserException{
        
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
                
                ResponseItemImpl expected = new ResponseItemImpl("search_by_categoryResult");
                        
                ResponseItemImpl item1 = new ResponseItemImpl("Item");
                ResponseItemImpl childA = new ResponseItemImpl("category");
                childA.setContent("mouse");
                item1.addChild(childA);                  
                ResponseItemImpl childB = new ResponseItemImpl("price");
                childB.setContent("89.2");
                item1.addChild(childB);                  
                ResponseItemImpl childC = new ResponseItemImpl("model");
                childC.setContent("RZG145");
                item1.addChild(childC);                  
                ResponseItemImpl childD = new ResponseItemImpl("brand");
                childD.setContent("Razor");
                item1.addChild(childD);          
                expected.addChild(item1);
        
                ResponseItemImpl item2 = new ResponseItemImpl("Item");
                childA = new ResponseItemImpl("category");
                childA.setContent("mouse");
                item2.addChild(childA);                  
                childB = new ResponseItemImpl("price");
                childB.setContent("61.0");
                item2.addChild(childB);                  
                childC = new ResponseItemImpl("model");
                childC.setContent("CCCC");
                item2.addChild(childC);                  
                childD = new ResponseItemImpl("brand");
                childD.setContent("Clone");
                item2.addChild(childD);          
                expected.addChild(item2);
        
                ResponseItemImpl item3 = new ResponseItemImpl("Item");
                childA = new ResponseItemImpl("category");
                childA.setContent("mouse");
                item3.addChild(childA);                  
                childB = new ResponseItemImpl("price");
                childB.setContent("61.0");
                item3.addChild(childB);                  
                childC = new ResponseItemImpl("model");
                childC.setContent("MS23F");
                item3.addChild(childC);                  
                childD = new ResponseItemImpl("brand");
                childD.setContent("Microsoft");
                item3.addChild(childD);          
                expected.addChild(item3);
                
                ResponseParser parser = new ResponseParser();
                ResponseItemImpl actual = parser.parse(sampleXml);
                
                assertEquals(expected, actual);
        }
        
}