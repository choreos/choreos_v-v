package eu.choreos.vv.clientgenerator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.choreos.vv.common.ItemBuilder;
import eu.choreos.vv.exceptions.ParserException;


public class RequestBuilderTest {

	@Test
	public void shouldReturnSameSimpleXmlWithNoParameters() throws ParserException{
		String sampleXml = "<senv:Envelope>" 
			+ "<senv:Body>"
			+ "<tns:search_by_brandResponse>" 
			+ "</tns:search_by_brandResponse>"
			+ "</senv:Body>" 
			+ "</senv:Envelope>";

		String result = new ItemBuilder().buildItem(sampleXml, null);

		assertEquals(sampleXml, result);
	}

	@Test
	public void shouldReturnSameSlighltyMoreComplicatedXml() throws ParserException{
		String sampleXml = "<senv:Envelope " 
			+ "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
			+ "xmlns:tns=\"tns\">" 
			+ "<senv:Body>"
			+ "<tns:search_by_brandResponse xmln=\"schema\">" 
			+ "<tns:search_by_brandResult>" 
			+ "</tns:search_by_brandResult>" 
			+ "</tns:search_by_brandResponse>" 
			+ "</senv:Body>" 
			+ "</senv:Envelope>";

		String result = new ItemBuilder().buildItem(sampleXml, null);

		assertEquals(sampleXml, result);
	}
	
	@Test
	public void shouldReturnXmlWithOneParameterReplacedWithTheProperContent() throws ParserException{
		String sampleXml = "<senv:Envelope " 
			+ "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
			+ "                             xmlns:tns=\"tns\">" 
			+ "<senv:Body>"
			+ "    <tns:search_by_brand xmln=\"schema\">" 
			+ "            <s1:name>?</s1:name>" 
			+ "    </tns:search_by_brand>" 
			+ "</senv:Body>" 
			+ "</senv:Envelope>";
		
		Item root = new ItemImpl("search_by_brand");
		root.addChild("name").setContent("test");
		
		String result = new ItemBuilder().buildItem(sampleXml, root);
		
		String expectedXml = "<senv:Envelope " 
			+ "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
			+ "xmlns:tns=\"tns\">" 
			+ "<senv:Body>"
			+ "<tns:search_by_brand xmln=\"schema\">" 
			+ "<s1:name>test</s1:name>" 
			+ "</tns:search_by_brand>" 
			+ "</senv:Body>" 
			+ "</senv:Envelope>";
		
		assertEquals(expectedXml, result);
	}
	
	@Test(expected=ParserException.class)
	public void shouldRaiseExceptionWithWrongHierarchy() throws ParserException{
		String sampleXml = "<senv:Envelope " 
			+ "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
			+ "                             xmlns:tns=\"tns\">" 
			+ "<senv:Body>"
			+ "    <tns:search_by_brand xmln=\"schema\">" 
			+ "            <s1:name>?</s1:name>" 
			+ "    </tns:search_by_brand>" 
			+ "</senv:Body>" 
			+ "</senv:Envelope>";
		
		Item root = new ItemImpl("search_by_brand");
		
		String result = new ItemBuilder().buildItem(sampleXml, root);
		
		String expectedXml = "<senv:Envelope " 
			+ "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
			+ "xmlns:tns=\"tns\">" 
			+ "<senv:Body>"
			+ "<tns:search_by_brand xmln=\"schema\">" 
			+ "<s1:name>test</s1:name>" 
			+ "</tns:search_by_brand>" 
			+ "</senv:Body>" 
			+ "</senv:Envelope>";
		
		assertEquals(expectedXml, result);
	}
	
	@Test
	public void shouldReturnXmlWithSeveralParametersReplacedWithTheProperContent() throws ParserException{
		String sampleXml = "<senv:Envelope " + 
		"xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " + 
		"xmlns:tns=\"tns\">" + 
		"<senv:Body>" +
		"<tns:search_by_category>" +
		"<s1:Item>" +
		"<s1:category>?</s1:category>" +
		"<s1:price>?</s1:price>" +
		"<s1:model>?</s1:model>" +
		"<s1:brand>?</s1:brand>" +
		"</s1:Item>" +
		"<s1:Item>" +
		"<s1:category>?</s1:category>" +
		"<s1:price>?</s1:price>" +
		"<s1:model>?</s1:model>" +
		"<s1:brand>?</s1:brand>" +
		"</s1:Item>" +
		"<s1:Item>" +
		"<s1:category>?</s1:category>" +
		"<s1:price>?</s1:price>" +
		"<s1:model>?</s1:model>" +
		"<s1:brand>?</s1:brand>" +
		"</s1:Item>" +                                  
		"</tns:search_by_category>" +
		"</senv:Body>" +
		"</senv:Envelope>";
		
		Item root = new ItemImpl("search_by_category");

		Item item1 = root.addChild("Item");
		item1.addChild("category").setContent("mouse");
		item1.addChild("price").setContent("89.2");
		item1.addChild("model").setContent("RZG145");
		item1.addChild("brand").setContent("Razor");

		Item item2 = root.addChild("Item");
		item2.addChild("category").setContent("mouse");
		item2.addChild("price").setContent("61.0");
		item2.addChild("model").setContent("CCCC");
		item2.addChild("brand").setContent("Clone");

		Item item3 = root.addChild("Item");
		item3.addChild("category").setContent("mouse");
		item3.addChild("price").setContent("61.0");
		item3.addChild("model").setContent("MS23F");
		item3.addChild("brand").setContent("Microsoft");
		
		String result = new ItemBuilder().buildItem(sampleXml, root);
		
		String expectedXml = "<senv:Envelope " + 
		"xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " + 
		"xmlns:tns=\"tns\">" + 
		"<senv:Body>" +
		"<tns:search_by_category>" +
		"<s1:Item>" +
		"<s1:category>mouse</s1:category>" +
		"<s1:price>89.2</s1:price>" +
		"<s1:model>RZG145</s1:model>" +
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
		"<s1:price>61.0</s1:price>" +
		"<s1:model>MS23F</s1:model>" +
		"<s1:brand>Microsoft</s1:brand>" +
		"</s1:Item>" +                                  
		"</tns:search_by_category>" +
		"</senv:Body>" +
		"</senv:Envelope>";
		
		assertEquals(expectedXml, result);
	}
	
	@Test
	public void xmlWithComplexHierarchyAndSameNamesShouldReturnProperContent() throws ParserException{
		String sampleXml = "<senv:Envelope " 
			+ "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
			+ "                             xmlns:tns=\"tns\">" 
			+ "<senv:Body>"
			+ "    <tns:search_by_brand xmln=\"schema\">" 
			+ "            <s1:item>"
			+ "                <s1:item>?</s1:item>"
			+ "            </s1:item>"
			+ "            <s1:item>?</s1:item>"
			+ "    </tns:search_by_brand>" 
			+ "</senv:Body>" 
			+ "</senv:Envelope>";
		
		Item root = new ItemImpl("search_by_brand");
		Item child1 = root.addChild("item");
		root.addChild("item").setContent("test2");
		child1.addChild("item").setContent("test1");
		
		String result = new ItemBuilder().buildItem(sampleXml, root);
		
		String expectedXml = "<senv:Envelope " 
			+ "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
			+ "xmlns:tns=\"tns\">" 
			+ "<senv:Body>"
			+ "<tns:search_by_brand xmln=\"schema\">" 
			+ "<s1:item>"
			+ "<s1:item>test1</s1:item>"
			+ "</s1:item>"
			+ "<s1:item>test2</s1:item>"
			+ "</tns:search_by_brand>" 
			+ "</senv:Body>" 
			+ "</senv:Envelope>";
		
		assertEquals(expectedXml, result);
	}
	
	@Test(expected=ParserException.class)
	public void xmlWithComplexHierarchyAndSameNamesWithWrongHierarchyRequestItemShouldRaiseException() throws ParserException{
		
		/*
		 	Expected:
			|
			|--Item
			|----Item
			|--Item
			
			Passed:
			|
			|--Item
			|----Item
			|----Item
		*/
		String sampleXml = "<senv:Envelope " 
			+ "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
			+ "                             xmlns:tns=\"tns\">" 
			+ "<senv:Body>"
			+ "    <tns:search_by_brand xmln=\"schema\">" 
			+ "            <s1:item>"
			+ "                <s1:item>?</s1:item>"
			+ "            </s1:item>"
			+ "            <s1:item>?</s1:item>"
			+ "    </tns:search_by_brand>" 
			+ "</senv:Body>" 
			+ "</senv:Envelope>";
		
		Item root = new ItemImpl("search_by_brand");
		Item child1 = root.addChild("item");
		child1.addChild("item").setContent("test1");
		child1.addChild("item").setContent("test2");
		
		new ItemBuilder().buildItem(sampleXml, root);
	}
	
	@Test
	public void xmlWithNumberOfSameItemsDefinedInRuntimeMustBeSupported() throws Exception{
		String baseXml =      "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.choreos.eu/\">" +
							    "<soapenv:Header/>" +
							    "<soapenv:Body>" + 
							    "		<ser:setSupermarketsList>" +
							    "			<!--Zero or more repetitions:-->" +
							    "			 <endpoint>?</endpoint>" +
							    "		</ser:setSupermarketsList>" +
							    "</soapenv:Body>" +
							    "</soapenv:Envelope>";
		
		Item request = new ItemImpl("setSupermarketsList");
		request.addChild("endpoint").setContent("endpoint1");
		
		request.addChild("endpoint").setContent("endpoint2");

		String actualXml = new ItemBuilder().buildItem(baseXml, request);
		
		String expectedXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.choreos.eu/\">" +
		    "<soapenv:Header></soapenv:Header>" +
		    "<soapenv:Body>" + 
		    "<ser:setSupermarketsList>" +
		    "<endpoint>endpoint1</endpoint>" +
		    "<endpoint>endpoint2</endpoint>" +
		    "</ser:setSupermarketsList>" +
		    "</soapenv:Body>" +
		    "</soapenv:Envelope>";
		
	
		assertEquals(expectedXml, actualXml);
	}
	
	@Test
	public void xmlWithNumberOfSameItemsDefinedInRuntimeWithNamespacesMustBeSupported() throws Exception{
	
		String baseXml =  "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cus=\"http://petals.ow2.org/bpel/Customer/\">" +
								"<soapenv:Header/>" +
								"	<soapenv:Body>" +
								"		<cus:getPriceOfProductList>" +
								"			<!--Zero or more repetitions:-->" +
								"			<cus:item>?</cus:item>" +
								"			</cus:getPriceOfProductList>" +
								"	</soapenv:Body>" +
								"</soapenv:Envelope>";
		
		Item list = new ItemImpl("getPriceOfProductList");
		list.addChild("item").setContent("milk");
		list.addChild("item").setContent("cereal");
	
		String actualXml = new ItemBuilder().buildItem(baseXml, list);
		
		String expectedXml =  "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cus=\"http://petals.ow2.org/bpel/Customer/\">" +
						"<soapenv:Header></soapenv:Header>" +
						"<soapenv:Body>" +
						"<cus:getPriceOfProductList>" +
						"<cus:item>milk</cus:item>" +
						"<cus:item>cereal</cus:item>" +
						"</cus:getPriceOfProductList>" +
						"</soapenv:Body>" +
						"</soapenv:Envelope>";
		
		assertEquals(expectedXml, actualXml);
		
	}
}
