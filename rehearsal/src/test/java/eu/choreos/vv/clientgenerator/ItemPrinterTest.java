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
		Item b = new ItemImpl("b");
		item.addChild(b);
		
		String actual = ItemPrinter.print(item);
		String expected = "Item a = new ItemImpl(\"a\"); " + "\n" + 
												"Item b = new ItemImpl(\"b\"); " + "\n" +
												"a.addChild(b);";
		
		assertEquals(expected.replace(" ", ""), actual.replace(" ", ""));
	}
	
	@Test
	public void shouldPrintAnItemWithNestedChildren() throws Exception {
		Item item = new ItemImpl("a");
		Item b = new ItemImpl("b");
		Item b1 = new ItemImpl("b1");
		b.addChild(b1);
		item.addChild(b);
		
		String actual = ItemPrinter.print(item);
		String expected = "Item a = new ItemImpl(\"a\"); " + "\n" + 
												"Item b = new ItemImpl(\"b\"); " + "\n" +
												"Item b1 = new ItemImpl(\"b1\"); " + "\n" +
												"b.addChild(b1);" + "\n" +
												"a.addChild(b);";
		
		assertEquals(expected.replace(" ", ""), actual.replace(" ", ""));
	}
	
	@Test
	public void shouldPrintAnItemWithNestedAndCommonChildren() throws Exception {
		Item item = new ItemImpl("a");
		Item b = new ItemImpl("b");
		Item b1 = new ItemImpl("b1");
		b.addChild(b1);
		item.addChild(b);
		Item c = new ItemImpl("c");
		item.addChild(c);
		
		String actual = ItemPrinter.print(item);
		String expected = "Item a = new ItemImpl(\"a\"); " + "\n" + 
												"Item b = new ItemImpl(\"b\"); " + "\n" +
												"Item b1 = new ItemImpl(\"b1\"); " + "\n" +
												"b.addChild(b1);" + "\n" +
												"a.addChild(b);" + "\n" +
												"Item c = new ItemImpl(\"c\"); " + "\n" +
												"a.addChild(c);";
		
		assertEquals(expected.replace(" ", ""), actual.replace(" ", ""));
	}
	
	@Test
	public void shouldReturnTheItemContent() throws Exception {
		Item item = new ItemImpl("a");
		item.setContent("firt letter of the alphabet");
		
		String expected = "Item a = new ItemImpl(\"a\");" + "\n" +
												"a.setContent(\"firt letter of the alphabet\");";
		
		String actual = ItemPrinter.print(item);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldReturnTheItemContentOfNestedItems() throws Exception {
		Item a = new ItemImpl("a");
		Item b = new ItemImpl("b");
		b.setContent("second letter of the alphabet");
		a.addChild(b);
		a.setContent("firt letter of the alphabet");
		
		String expected = "Item a = new ItemImpl(\"a\");" + "\n" +
												"a.setContent(\"firt letter of the alphabet\");" + "\n" +
												"Item b = new ItemImpl(\"b\");" + "\n" +
												"b.setContent(\"second letter of the alphabet\");" + "\n" +
												"a.addChild(b);";
		
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
