package eu.choreos.vv;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.choreos.vv.Item;
import eu.choreos.vv.ItemImpl;
import eu.choreos.vv.RequestBuilder;
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

		String result = new RequestBuilder().buildRequest(sampleXml, null);

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

		String result = new RequestBuilder().buildRequest(sampleXml, null);

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
		Item child = new ItemImpl("name");
		child.setContent("test");
		root.addChild(child);
		
		String result = new RequestBuilder().buildRequest(sampleXml, root);
		
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
		
		String result = new RequestBuilder().buildRequest(sampleXml, root);
		
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

		Item item1 = new ItemImpl("Item");
		Item childA = new ItemImpl("category");
		childA.setContent("mouse");
		item1.addChild(childA);                  
		Item childB = new ItemImpl("price");
		childB.setContent("89.2");
		item1.addChild(childB);                  
		Item childC = new ItemImpl("model");
		childC.setContent("RZG145");
		item1.addChild(childC);                  
		Item childD = new ItemImpl("brand");
		childD.setContent("Razor");
		item1.addChild(childD);          
		root.addChild(item1);

		Item item2 = new ItemImpl("Item");
		Item childE = new ItemImpl("category");
		childE.setContent("mouse");
		item2.addChild(childE);                  
		Item childF = new ItemImpl("price");
		childF.setContent("61.0");
		item2.addChild(childF);                  
		Item childG = new ItemImpl("model");
		childG.setContent("CCCC");
		item2.addChild(childG);                  
		Item childH = new ItemImpl("brand");
		childH.setContent("Clone");
		item2.addChild(childH);          
		root.addChild(item2);

		Item item3 = new ItemImpl("Item");
		Item childI = new ItemImpl("category");
		childI.setContent("mouse");
		item3.addChild(childI);                  
		Item childJ = new ItemImpl("price");
		childJ.setContent("61.0");
		item3.addChild(childJ);                  
		Item childK = new ItemImpl("model");
		childK.setContent("MS23F");
		item3.addChild(childK);                  
		Item childL = new ItemImpl("brand");
		childL.setContent("Microsoft");
		item3.addChild(childL);          
		root.addChild(item3);
		
		String result = new RequestBuilder().buildRequest(sampleXml, root);
		
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
		Item child1 = new ItemImpl("item");
		Item child2 = new ItemImpl("item");
		Item grandChild = new ItemImpl("item");
		grandChild.setContent("test1");
		child2.setContent("test2");
		root.addChild(child1);
		root.addChild(child2);
		child1.addChild(grandChild);
		
		String result = new RequestBuilder().buildRequest(sampleXml, root);
		
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
		Item child1 = new ItemImpl("item");
		Item grandChild1 = new ItemImpl("item");
		Item grandChild2 = new ItemImpl("item");
		grandChild1.setContent("test1");
		grandChild2.setContent("test2");
		root.addChild(child1);
		child1.addChild(grandChild1);
		child1.addChild(grandChild2);
		
		new RequestBuilder().buildRequest(sampleXml, root);
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
		Item endpoint1 = new ItemImpl("endpoint");
		endpoint1.setContent("endpoint1");
		request.addChild(endpoint1);
		
		Item endpoint2 = new ItemImpl("endpoint");
		endpoint2.setContent("endpoint2");
		request.addChild(endpoint2);

		String actualXml = new RequestBuilder().buildRequest(baseXml, request);
		
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
		
		Item item1 = new ItemImpl("item");
		item1.setContent("milk");
		list.addChild(item1);
		
		Item item2 = new ItemImpl("item");
		item2.setContent("cereal");
		list.addChild(item2);
	
		String actualXml = new RequestBuilder().buildRequest(baseXml, list);
		
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
