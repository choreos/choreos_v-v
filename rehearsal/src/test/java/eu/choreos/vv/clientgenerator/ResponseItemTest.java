package eu.choreos.vv.clientgenerator;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;



public class ResponseItemTest {

	private ItemImpl item;

	@Before
	public void setUp(){
		item = new ItemImpl("author");
	}

	@Test
	public void shouldAddStringContent(){
		Item itemName = new ItemImpl("name").setContent("Fernando Pessoa");

		assertEquals("Fernando Pessoa", itemName.getContent());
	}

	@Test
	public void shouldAddIntegerContent(){
		Item itemName = new ItemImpl("year");
		itemName.setContent("1930");

		assertEquals((Integer)1930, itemName.getContentAsInt());
	}

	@Test (expected=NumberFormatException.class)
	public void shouldThrowAnExceptionWhenTheContentCannotBeAnInteger(){
		Item itemName = new ItemImpl("year");
		itemName.setContent("1930 A.C");

		itemName.getContentAsInt();
	}

	@Test
	public void shouldAddDoubleContent(){
		Item itemName = new ItemImpl("price");
		itemName.setContent("12.0");

		assertEquals(12.0, itemName.getContentAsDouble(), 1e-9);
	}

	@Test (expected=NumberFormatException.class)
	public void shouldThrowAnExceptionWhenTheContentCannotBeADouble(){
		Item itemName = new ItemImpl("price");
		itemName.setContent("R$ 12.0");

		itemName.getContentAsInt();
	}

	@Test
	public void shouldAddAnItemWithParameters(){
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("xsd", "www.br.usp.ime.choreos.vv");
		Item childItem = new ItemImpl("name", parameters );

		assertEquals("www.br.usp.ime.choreos.vv", childItem.getTagAttributes().get("xsd"));
	}

	@Test
	public void shouldAddAnSimpleItem() throws NoSuchFieldException{
		item.addChild("name").setContent("Fernando Pessoa");
				
		assertEquals("Fernando Pessoa", item.getContent("name"));
	}
	
	@Test
	public void shouldAddAnSimpleItemAnGetItsContentAsInt() throws NoSuchFieldException{
		item.addChild("year").setContent("1930");
				
		assertEquals((Integer)1930, item.getContentAsInt("year"));
	}
	
	@Test
	public void shouldAddAnSimpleItemAnGetItsContentAsDouble() throws NoSuchFieldException{
		item.addChild("price").setContent("12.0");
				
		assertEquals(12.0, item.getContentAsDouble("price"), 1e-9);
	}
	
	@Test
	public void shouldAddTwoSimpleItemWithTheSameName() throws NoSuchFieldException{
		item.addChild("name").setContent("Fernando Pessoa");
		item.addChild("name").setContent("Machado de Assis");
		
		List<Item> children = item.getChildAsList("name");

		assertEquals("Fernando Pessoa", children.get(0).getContent());
		assertEquals("Machado de Assis", children.get(1).getContent());
	}

	@Test
	public void shouldAddAComplexItem() throws NoSuchFieldException{
		Item coAuthorItem = item.addChild("co-author");  
		coAuthorItem.addChild("name").setContent("Eça de Queiroz");

		assertEquals("Eça de Queiroz", item.getChild("co-author").getContent("name"));
	}

	@Test
	public void shouldAddAComplexItemAnHaveContent() throws NoSuchFieldException{
		Item coAuthorItem = item.addChild("co-author");  
		coAuthorItem.addChild("name").setContent("Eça de Queiroz");
		item.setContent("1935");


		assertEquals("Eça de Queiroz", item.getChild("co-author").getContent("name"));
		assertEquals((Integer)1935, item.getContentAsInt());
	}

	@Test
	public void shouldAddAComplexWithTwoSimpleItems() throws NoSuchFieldException{
		Item coAuthorItem = item.addChild("co-author");  
		coAuthorItem.addChild("name").setContent("Eça de Queiroz");
		coAuthorItem.addChild("name").setContent("Olavo Bilac");

		List<Item> coAuthors = item.getChild("co-author").getChildAsList("name");

		assertEquals("Eça de Queiroz", coAuthors.get(0).getContent());
		assertEquals("Olavo Bilac", coAuthors.get(1).getContent());
	}

	@Test
	public void shouldGetAListWithOneElement() throws NoSuchFieldException {
		item.addChild("name").setContent("Fernando Pessoa");

		assertEquals(1, item.getChildAsList("name").size());
		assertEquals( "Fernando Pessoa", item.getContent("name"));
	}

	@Test (expected=NoSuchFieldException.class)
	public void shouldThrowsAnExceptionWhenTheElementNotExist() throws NoSuchFieldException {
		item.getChild("sirname");
	}
	
	@Test
        public void shouldReturnOneOccurrencesOfTagName() throws Exception {
		Item request = new ItemImpl("setSupermarketsList");
		request.addChild("endpoint").setContent("endpoint1");
		
		assertEquals(1, request.getListSizeFromItem("endpoint"));
        }
	
	@Test
        public void shouldReturnTwoOccurrencesOfTagName() throws Exception {
		Item request = new ItemImpl("setSupermarketsList");
		request.addChild("endpoint").setContent("endpoint1");
		request.addChild("endpoint").setContent("endpoint2");
		
		assertEquals(2, request.getListSizeFromItem("endpoint"));
        }	
	
	@Test
        public void shouldReturnZeroOccurencesOfTagName() throws Exception {
		Item request = new ItemImpl("setSupermarketsList");
		request.addChild("endpoint").setContent("endpoint1");
		request.addChild("endpoint").setContent("endpoint2");
		
		assertEquals(0, request.getListSizeFromItem("strangeTagName"));
	}
	
	@Test
        public void shouldReturnTheCorrectListSize() throws Exception {
		Item root = new ItemImpl("root");
		Item request = root.addChild("setSupermarketsList");
		request.addChild("endpoint").setContent("endpoint1");
		request.addChild("endpoint").setContent("endpoint2");
		
		assertEquals(2, request.getListSizeFromItem("endpoint"));	        
        }
	
	@Test
	public void shouldGetStringRepresentation() throws NoSuchFieldException {
		Item root = new ItemImpl("root");
		Item request = root.addChild("setSupermarketsList");
		request.addChild("endpoint").setContent("endpoint1");
		request.addChild("endpoint").setContent("endpoint2");
		
		assertEquals("<root><setSupermarketsList><endpoint>endpoint1</endpoint><endpoint>endpoint2</endpoint></setSupermarketsList></root>",root.getElementAsString());
	}
}
