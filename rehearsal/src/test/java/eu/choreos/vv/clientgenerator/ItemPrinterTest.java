package eu.choreos.vv.clientgenerator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ItemPrinterTest {
	
	@Test
	public  void shouldPrintASimpleItemDeclarationAsRequest() throws Exception {
		Item item = new ItemImpl ("a");
		String actual = ItemPrinter.printAsRequest(item);
		
		assertEquals("Item a = new ItemImpl(\"a\");", actual);
	}
	
	@Test
	public  void shouldPrintASimpleItemDeclarationAsResponse() throws Exception {
		Item item = new ItemImpl ("a");
		
		String actual = ItemPrinter.printAsResponse(item);
		String expected = "Item a = new ItemImpl(\"a\");";		
		
		assertEquals(expected, actual);
	}
	
	@Test
	public  void shouldPrintASimpleItemDeclarationWithContentAsResponse() throws Exception {
		Item item = new ItemImpl ("a");
		item.setContent("content");
		
		String actual = ItemPrinter.printAsResponse(item);
		String expected = "Item a = new ItemImpl(\"a\");" + "\n" +
												"String aRoot = a.getContent()";
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldPrintAnItemWithAChildAsRequest() throws Exception {
		Item item = new ItemImpl("a");
		item.addChild("b");
		
		String actual = ItemPrinter.printAsRequest(item);
		String expected = "Item a = new ItemImpl(\"a\"); " + "\n" + 
												"a.addChild(\"b\");";
		
		assertEquals(expected.replace(" ", ""), actual.replace(" ", ""));
	}
	
	@Test
	public void shouldPrintAnItemWithAChildAsResponse() throws Exception {
		Item item = new ItemImpl("a");
		item.addChild("b");
		
		String actual = ItemPrinter.printAsResponse(item);
		String expected = "Item a = new ItemImpl(\"a\"); " + "\n" + 
												"Item b = a.getChild();";		
		assertEquals(expected.replace(" ", ""), actual.replace(" ", ""));
	}
	
	@Test
	public void shouldPrintAnItemWithAChildWithContentAsResponse() throws Exception {
		Item item = new ItemImpl("a");
		item.addChild("b").setContent("bla");
		
		
		String actual = ItemPrinter.printAsResponse(item);
		String expected = "Item a = new ItemImpl(\"a\"); " + "\n" + 
												"String b = a.getContent(\"b\");";		
		assertEquals(expected.replace(" ", ""), actual.replace(" ", ""));
	}
	
	@Test
	public void shouldPrintAnItemWithNestedChildrenAsRequest() throws Exception {
		Item item = new ItemImpl("a");
		Item b = item.addChild("b");
		b.addChild("b1");
		
		String actual = ItemPrinter.printAsRequest(item);
		String expected = "Item a = new ItemImpl(\"a\"); " + "\n" + 
												"Item b = a.addChild(\"b\"); " + "\n" +
												"b.addChild(\"b1\"); " ;
		
		assertEquals(expected.replace(" ", ""), actual.replace(" ", ""));
	}
	
	@Test
	public void shouldPrintAnItemWithNestedChildrenAsResponse() throws Exception {
		Item item = new ItemImpl("a");
		Item b = item.addChild("b");
		b.addChild("b1");
		
		String actual = ItemPrinter.printAsResponse(item);
		String expected = "Item a = new ItemImpl(\"a\"); " + "\n" + 
												"Item b = a.getChild(); " + "\n" +
												"Item b1 = b.getChild(); " ;
		
		assertEquals(expected.replace(" ", ""), actual.replace(" ", ""));
	}
	
	@Test
	public void shouldPrintAnItemWithNestedChildrenAsResWithContentponse() throws Exception {
		Item item = new ItemImpl("a");
		Item b = item.addChild("b");
		b.addChild("b1").setContent("uhuu");
		
		String actual = ItemPrinter.printAsResponse(item);
		String expected = "Item a = new ItemImpl(\"a\"); " + "\n" + 
												"Item b = a.getChild(); " + "\n" +
												"String b1 = b.getContent(\"b1\"); " ;
		
		assertEquals(expected.replace(" ", ""), actual.replace(" ", ""));
	}
	
	@Test
	public void shouldPrintAnItemWithNestedAndCommonChildrenAsRequest() throws Exception {
		Item item = new ItemImpl("a");
		Item b = item.addChild("b");
		b.addChild("b1");
		
		item.addChild("c");
		
		String actual = ItemPrinter.printAsRequest(item);
		String expected = "Item a = new ItemImpl(\"a\"); " + "\n" + 
												"Item b = a.addChild(\"b\"); " + "\n" +
												"b.addChild(\"b1\"); " + "\n" +
												"a.addChild(\"c\");";
		
		assertEquals(expected.replace(" ", ""), actual.replace(" ", ""));
	}
	
	@Test
	public void shouldPrintAnItemWithNestedAndCommonChildrenAsResponse() throws Exception {
		Item item = new ItemImpl("a");
		Item b = item.addChild("b");
		b.addChild("b1");
		
		item.addChild("c");
		
		String actual = ItemPrinter.printAsResponse(item);
		String expected = "Item a = new ItemImpl(\"a\"); " + "\n" + 
												"Item b = a.getChild(); " + "\n" +
												"Item b1 = b.getChild(); " + "\n" +
												"Item c = a.getChild();";
		
		assertEquals(expected.replace(" ", ""), actual.replace(" ", ""));
	}
	
	@Test
	public void shouldReturnTheItemContent() throws Exception {
		Item item = new ItemImpl("a").setContent("firt letter of the alphabet");
		
		String expected = "Item a = new ItemImpl(\"a\").setContent(\"firt letter of the alphabet\");";
		
		String actual = ItemPrinter.printAsRequest(item);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldReturnTheItemContentOfNestedItems() throws Exception {
		Item a = new ItemImpl("a").setContent("firt letter of the alphabet");
		a.addChild("b").setContent("second letter of the alphabet");
		
		
		String expected = "Item a = new ItemImpl(\"a\").setContent(\"firt letter of the alphabet\");" + "\n" +
												"a.addChild(\"b\").setContent(\"second letter of the alphabet\");";
		
		String actual = ItemPrinter.printAsRequest(a);
		
		assertEquals(expected, actual);
												
	}
	
	@Test
	public void whenItemNameHasSpacesShouldBeGenerateInCamelCase() throws Exception {
		Item item = new ItemImpl("to camel case");
		String actual = ItemPrinter.printAsRequest(item);
		String expected = "Item toCamelCase = new ItemImpl(\"" + "toCamelCase" + "\");";
		
		assertEquals(expected, actual);
	}

}
