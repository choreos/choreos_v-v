package eu.choreos.vv.clientgenerator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ItemPrinterTest {
	
	@Test
	public  void shouldPrintASimpleItemDeclaration() throws Exception {
		Item item = new ItemImpl ("a");
		String actual = ItemPrinter.print(item);
		
		assertEquals("Item a = new ItemImpl(\"a\");", actual);
	}
	
	@Test
	public void shouldPrintAnItemWithAChild() throws Exception {
		Item item = new ItemImpl("a");
		item.addChild("b");
		
		String actual = ItemPrinter.print(item);
		String expected = "Item a = new ItemImpl(\"a\"); " + "\n" + 
												"a.addChild(\"b\");";
		
		assertEquals(expected.replace(" ", ""), actual.replace(" ", ""));
	}
	
	@Test
	public void shouldPrintAnItemWithNestedChildren() throws Exception {
		Item item = new ItemImpl("a");
		Item b = item.addChild("b");
		b.addChild("b1");
		
		String actual = ItemPrinter.print(item);
		String expected = "Item a = new ItemImpl(\"a\"); " + "\n" + 
												"Item b = a.addChild(\"b\"); " + "\n" +
												"b.addChild(\"b1\"); " ;
		
		assertEquals(expected.replace(" ", ""), actual.replace(" ", ""));
	}
	
	@Test
	public void shouldPrintAnItemWithNestedAndCommonChildren() throws Exception {
		Item item = new ItemImpl("a");
		Item b = item.addChild("b");
		b.addChild("b1");
		
		item.addChild("c");
		
		String actual = ItemPrinter.print(item);
		String expected = "Item a = new ItemImpl(\"a\"); " + "\n" + 
												"Item b = a.addChild(\"b\"); " + "\n" +
												"b.addChild(\"b1\"); " + "\n" +
												"a.addChild(\"c\");";
		
		assertEquals(expected.replace(" ", ""), actual.replace(" ", ""));
	}
	
	@Test
	public void shouldReturnTheItemContent() throws Exception {
		Item item = new ItemImpl("a").setContent("firt letter of the alphabet");
		
		String expected = "Item a = new ItemImpl(\"a\").setContent(\"firt letter of the alphabet\");";
		
		String actual = ItemPrinter.print(item);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldReturnTheItemContentOfNestedItems() throws Exception {
		Item a = new ItemImpl("a").setContent("firt letter of the alphabet");
		a.addChild("b").setContent("second letter of the alphabet");
		
		
		String expected = "Item a = new ItemImpl(\"a\").setContent(\"firt letter of the alphabet\");" + "\n" +
												"a.addChild(\"b\").setContent(\"second letter of the alphabet\");";
		
		String actual = ItemPrinter.print(a);
		
		assertEquals(expected, actual);
												
	}
	
	@Test
	public void whenItemNameHasSpacesShouldBeGenerateInCamelCase() throws Exception {
		Item item = new ItemImpl("to camel case");
		String actual = ItemPrinter.print(item);
		String expected = "Item toCamelCase = new ItemImpl(\"" + "toCamelCase" + "\");";
		
		assertEquals(expected, actual);
	}

}
