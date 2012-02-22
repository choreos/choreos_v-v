package eu.choreos.vv.clientgenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

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
												"Item return = new ItemImpl(\"return\");" + "\n" +
												"return.setContent(\"?\");" + "\n" +
												"getPriceResponse.addChild(return);";
		
		String actual = item.print();
		
		assertEquals(expected, actual);
	}
	
}
