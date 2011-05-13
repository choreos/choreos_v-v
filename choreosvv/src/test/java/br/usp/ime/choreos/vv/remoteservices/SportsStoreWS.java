package br.usp.ime.choreos.vv.remoteservices;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.xmlbeans.XmlException;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.choreos.vv.ResponseItem;
import br.usp.ime.choreos.vv.WSClient;
import br.usp.ime.choreos.vv.exceptions.FrameworkException;
import br.usp.ime.choreos.vv.exceptions.InvalidOperationNameException;
import br.usp.ime.choreos.vv.exceptions.WSDLException;

public class SportsStoreWS {

        private static final String END_POINT = "http://choreos.ime.usp.br:53111/sports_store/services/SportsStoreWS?wsdl";
        private static WSClient service;
        
        @BeforeClass
        public static void setUp() throws WSDLException, XmlException, IOException, FrameworkException{
                service = new WSClient(END_POINT);
        }
        
        @Test
        public void shouldGetPriceByItemBarcode() throws InvalidOperationNameException, FrameworkException, NoSuchFieldException{
                ResponseItem response = service.request("getPriceByBarcode", "123");
                
                assertEquals(15.0, response.getChild("return").getContentAsDouble(), 0.001);
        }
        
        @Test
        public void shouldGetItemByName() throws InvalidOperationNameException, FrameworkException, NoSuchFieldException{
                ResponseItem response = service.request("getItemByName", "Soccer cleat");
                ResponseItem item = response.getChild("return");
                
                assertEquals("return", item.getName());
                assertEquals("http://rmi.java/xsd", item.getTagAttribute("xmlns:ax21"));
                assertEquals("153", item.getChild("barcode").getContent());
                assertEquals("adidas", item.getChild("brand").getContent());
                assertEquals("A Soccer cleat", item.getChild("description").getContent());
                assertEquals("Soccer cleat", item.getChild("name").getContent());
                assertEquals(90.0, item.getChild("price").getContentAsDouble(), 0.0001);
                assertEquals("soccer", item.getChild("sport").getContent());
        }
        
        @Test
        public void shouldGetAListOfItemsOfABrand() throws InvalidOperationNameException, FrameworkException, NoSuchFieldException{
        	ResponseItem response = service.request("getItemsByBrand", "nike");
        	List<ResponseItem> items = response.getChildAsList("return"); 
        	
        	assertEquals("123", items.get(0).getChild("barcode").getContent());
        	assertEquals("163", items.get(1).getChild("barcode").getContent());
        }
}
