package eu.choreos.vv.clientgenerator;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;



public class ResponseItemTest {

	private ItemImpl item;

	@Before
	public void setUp(){
		item = new ItemImpl("author");
	}

	@Test
	public void shouldAddStringContent(){
		Item itemName = new ItemImpl("name");
		itemName.setContent("Fernando Pessoa");

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
		Item childItem = new ItemImpl("name");
		childItem.setContent("Fernando Pessoa");
		item.addChild(childItem);

		assertEquals("Fernando Pessoa", item.getChild("name").getContent());
	}

	@Test
	public void shouldAddTwoSimpleItemWithTheSameName() throws NoSuchFieldException{
		Item childItemA = new ItemImpl("name");
		childItemA.setContent("Fernando Pessoa");
		item.addChild(childItemA);

		Item childItemB = new ItemImpl("name");
		childItemB.setContent("Machado de Assis");
		item.addChild(childItemB);

		List<Item> children = item.getChildAsList("name");

		assertEquals("Fernando Pessoa", children.get(0).getContent());
		assertEquals("Machado de Assis", children.get(1).getContent());
	}

	@Test
	public void shouldAddAComplexItem() throws NoSuchFieldException{
		Item coAuthorItem = new ItemImpl("co-author");  
		Item nameItem = new ItemImpl("name");
		nameItem.setContent("Eça de Queiroz");

		coAuthorItem.addChild(nameItem);
		item.addChild(coAuthorItem);

		assertEquals("Eça de Queiroz", item.getChild("co-author").getChild("name").getContent());
	}

	@Test
	public void shouldAddAComplexItemAnHaveContent() throws NoSuchFieldException{
		Item coAuthorItem = new ItemImpl("co-author");  
		Item nameItem = new ItemImpl("name");
		nameItem.setContent("Eça de Queiroz");

		coAuthorItem.addChild(nameItem);
		item.addChild(coAuthorItem);
		item.setContent("1935");


		assertEquals("Eça de Queiroz", item.getChild("co-author").getChild("name").getContent());
		assertEquals((Integer)1935, item.getContentAsInt());
	}

	@Test
	public void shouldAddAComplexWithTwoSimpleItems() throws NoSuchFieldException{
		Item coAuthorItem = new ItemImpl("co-author");  
		Item nameItemA = new ItemImpl("name");
		nameItemA.setContent("Eça de Queiroz");

		Item nameItemB = new ItemImpl("name");
		nameItemB.setContent("Olavo Bilac");

		coAuthorItem.addChild(nameItemA);
		coAuthorItem.addChild(nameItemB);
		item.addChild(coAuthorItem);

		List<Item> coAuthors = item.getChild("co-author").getChildAsList("name");

		assertEquals("Eça de Queiroz", coAuthors.get(0).getContent());
		assertEquals("Olavo Bilac", coAuthors.get(1).getContent());
	}

	@Test
	public void shouldGetAListWithOneElement() throws NoSuchFieldException {
		Item childItem = new ItemImpl("name");
		childItem.setContent("Fernando Pessoa");
		item.addChild(childItem);

		assertEquals(1, item.getChildAsList("name").size());
		assertEquals( "Fernando Pessoa", item.getChild("name").getContent());
	}

	@Test (expected=NoSuchFieldException.class)
	public void shouldThrowsAnExceptionWhenTheElementNotExist() throws NoSuchFieldException {
		item.getChild("sirname");
	}
	
	@Test
        public void shouldReturnOneOccurrencesOfTagName() throws Exception {
		Item request = new ItemImpl("setSupermarketsList");
		Item endpoint1 = new ItemImpl("endpoint");
		endpoint1.setContent("endpoint1");
		request.addChild(endpoint1);
		
		assertEquals(1, request.getListSizeFromItem("endpoint"));
        }
	
	@Test
        public void shouldReturnTwoOccurrencesOfTagName() throws Exception {
		Item request = new ItemImpl("setSupermarketsList");
		Item endpoint1 = new ItemImpl("endpoint");
		endpoint1.setContent("endpoint1");
		request.addChild(endpoint1);
		
		Item endpoint2 = new ItemImpl("endpoint");
		endpoint2.setContent("endpoint2");
		request.addChild(endpoint2);
		
		assertEquals(2, request.getListSizeFromItem("endpoint"));
        }	
	
	@Test
        public void shouldReturnZeroOccurencesOfTagName() throws Exception {
		Item request = new ItemImpl("setSupermarketsList");
		Item endpoint1 = new ItemImpl("endpoint");
		endpoint1.setContent("endpoint1");
		request.addChild(endpoint1);
		
		Item endpoint2 = new ItemImpl("endpoint");
		endpoint2.setContent("endpoint2");
		request.addChild(endpoint2);
		
		assertEquals(0, request.getListSizeFromItem("strangeTagName"));
	}
	
	@Test
        public void testName() throws Exception {
		Item root = new ItemImpl("root");
		Item request = new ItemImpl("setSupermarketsList");
		Item endpoint1 = new ItemImpl("endpoint");
		endpoint1.setContent("endpoint1");
		request.addChild(endpoint1);
		
		Item endpoint2 = new ItemImpl("endpoint");
		endpoint2.setContent("endpoint2");
		request.addChild(endpoint2);
		root.addChild(request);
		
		assertEquals(2, request.getListSizeFromItem("endpoint"));	        
        }
}
