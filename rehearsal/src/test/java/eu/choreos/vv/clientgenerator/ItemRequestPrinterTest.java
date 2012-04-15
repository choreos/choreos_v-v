package eu.choreos.vv.clientgenerator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.choreos.vv.exceptions.EmptyRequetItemException;

public class ItemRequestPrinterTest {
	

	private static final String SM_WSDL_URI = "file://" + System.getProperty("user.dir") + "/resource/sm_plus.wsdl";
	
	@Test
	public void shouldGetTheParameterAsAnItem() throws Exception {
		WSClient client = new WSClient(SM_WSDL_URI);
		Item item = client.getItemRequestFor("getPrice");
		Item child = item.getChild("name");
		
		assertEquals("getPrice", item.getName());
		assertEquals("name", child.getName());
		assertEquals("?", child.getContent());
	}
	
	@Test
	public void shouldPrintTheParameterItem() throws Exception {
		WSClient client = new WSClient(SM_WSDL_URI);
		Item item = client.getItemRequestFor("getPrice");
 
		String expected = "Item getPrice = new ItemImpl(\"getPrice\");" + "\n" +
												"getPrice.addChild(\"name\").setContent(\"?\");";
		
		String actual = item.printAsRequest();
		
		assertEquals(expected, actual);
	}
	
	@Test(expected=EmptyRequetItemException.class)
	public void shouldThrowAnExceptionWhenTheOperationHasNoParameter() throws Exception {
		WSClient client = new WSClient(SM_WSDL_URI);
		client.getItemRequestFor("getSpecialOffer");
	}
	

}
