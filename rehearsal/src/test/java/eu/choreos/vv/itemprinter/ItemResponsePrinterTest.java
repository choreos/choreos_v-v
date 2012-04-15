package eu.choreos.vv.itemprinter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;

public class ItemResponsePrinterTest {
	

	private static final String SM_WSDL_URI = "file://" + System.getProperty("user.dir") + "/resource/sm_plus.wsdl";
	
	@Test
	public void responseItemShouldNotBeNull() throws Exception {
		WSClient client = new WSClient(SM_WSDL_URI);
		Item item = client.getItemResponseFor("getPrice");
		
		assertNotNull(item);
	}
	
	@Test
	public void shouldGetTheResponseAsAnItem() throws Exception {
		WSClient client = new WSClient(SM_WSDL_URI);
		Item item = client.getItemResponseFor("getPrice");
		Item child = item.getChild("return");
		
		assertEquals("getPriceResponse", item.getName());
		assertEquals("return", child.getName());
		assertEquals("?", child.getContent());
	}
	
	@Test
	public void shouldPrintTheParameterItem() throws Exception {
		WSClient client = new WSClient(SM_WSDL_URI);
		Item item = client.getItemResponseFor("getPrice");
 
		String expected = "Item getPriceResponse = new ItemImpl(\"getPriceResponse\");" + "\n" +
												 "String return = getPriceResponse.getContent(\"return\");";
		
		String actual = item.printAsResponse();
		
		assertEquals(expected, actual);
	}
	
}
