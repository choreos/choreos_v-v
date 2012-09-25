package eu.choreos.vv.clientgenerator;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import eu.choreos.vv.common.ItemBuilder;
import eu.choreos.vv.exceptions.ParserException;

public class RequestBuilderTest {

	@Test
	public void shouldReturnSameSimpleXmlWithNoParameters() throws ParserException {
		String sampleXml = "<senv:Envelope>" + "<senv:Body>"
		                + "<tns:search_by_brandResponse>"
		                + "</tns:search_by_brandResponse>" + "</senv:Body>"
		                + "</senv:Envelope>";

		String result = new ItemBuilder().buildItem(sampleXml, null);

		assertEquals(sampleXml, result);
	}

	@Test
	public void shouldReturnSameSlighltyMoreComplicatedXml() throws ParserException {
		String sampleXml = "<senv:Envelope "
		                + "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" "
		                + "xmlns:tns=\"tns\">" + "<senv:Body>"
		                + "<tns:search_by_brandResponse xmln=\"schema\">"
		                + "<tns:search_by_brandResult>" + "</tns:search_by_brandResult>"
		                + "</tns:search_by_brandResponse>" + "</senv:Body>"
		                + "</senv:Envelope>";

		String result = new ItemBuilder().buildItem(sampleXml, null);

		assertEquals(sampleXml, result);
	}

	@Test
	public void shouldReturnXmlWithOneParameterReplacedWithTheProperContent()
	                throws ParserException {
		String sampleXml = "<senv:Envelope "
		                + "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" "
		                + "                             xmlns:tns=\"tns\">" + "<senv:Body>"
		                + "    <tns:search_by_brand xmln=\"schema\">"
		                + "            <s1:name>?</s1:name>" + "    </tns:search_by_brand>"
		                + "</senv:Body>" + "</senv:Envelope>";

		Item root = new ItemImpl("search_by_brand");
		root.addChild("name").setContent("test");

		String result = new ItemBuilder().buildItem(sampleXml, root);

		String expectedXml = "<senv:Envelope "
		                + "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" "
		                + "xmlns:tns=\"tns\">" + "<senv:Body>"
		                + "<tns:search_by_brand xmln=\"schema\">"
		                + "<s1:name>test</s1:name>" + "</tns:search_by_brand>"
		                + "</senv:Body>" + "</senv:Envelope>";

		assertEquals(expectedXml, result);
	}

	@Test(expected = ParserException.class)
	public void shouldRaiseExceptionWithWrongHierarchy() throws ParserException {
		String sampleXml = "<senv:Envelope "
		                + "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" "
		                + "                             xmlns:tns=\"tns\">" + "<senv:Body>"
		                + "    <tns:search_by_brand xmln=\"schema\">"
		                + "            <s1:name>?</s1:name>" + "    </tns:search_by_brand>"
		                + "</senv:Body>" + "</senv:Envelope>";

		Item root = new ItemImpl("search_by_brand");

		String result = new ItemBuilder().buildItem(sampleXml, root);

		String expectedXml = "<senv:Envelope "
		                + "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" "
		                + "xmlns:tns=\"tns\">" + "<senv:Body>"
		                + "<tns:search_by_brand xmln=\"schema\">"
		                + "<s1:name>test</s1:name>" + "</tns:search_by_brand>"
		                + "</senv:Body>" + "</senv:Envelope>";

		assertEquals(expectedXml, result);
	}

	@Test
	public void shouldReturnXmlWithSeveralParametersReplacedWithTheProperContent()
	                throws ParserException {
		String sampleXml = "<senv:Envelope "
		                + "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" "
		                + "xmlns:tns=\"tns\">" + "<senv:Body>" + "<tns:search_by_category>"
		                + "<s1:Item>" + "<s1:category>?</s1:category>"
		                + "<s1:price>?</s1:price>" + "<s1:model>?</s1:model>"
		                + "<s1:brand>?</s1:brand>" + "</s1:Item>" + "<s1:Item>"
		                + "<s1:category>?</s1:category>" + "<s1:price>?</s1:price>"
		                + "<s1:model>?</s1:model>" + "<s1:brand>?</s1:brand>"
		                + "</s1:Item>" + "<s1:Item>" + "<s1:category>?</s1:category>"
		                + "<s1:price>?</s1:price>" + "<s1:model>?</s1:model>"
		                + "<s1:brand>?</s1:brand>" + "</s1:Item>"
		                + "</tns:search_by_category>" + "</senv:Body>" + "</senv:Envelope>";

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

		String expectedXml = "<senv:Envelope "
		                + "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" "
		                + "xmlns:tns=\"tns\">" + "<senv:Body>" + "<tns:search_by_category>"
		                + "<s1:Item>" + "<s1:category>mouse</s1:category>"
		                + "<s1:price>89.2</s1:price>" + "<s1:model>RZG145</s1:model>"
		                + "<s1:brand>Razor</s1:brand>" + "</s1:Item>" + "<s1:Item>"
		                + "<s1:category>mouse</s1:category>" + "<s1:price>61.0</s1:price>"
		                + "<s1:model>CCCC</s1:model>" + "<s1:brand>Clone</s1:brand>"
		                + "</s1:Item>" + "<s1:Item>" + "<s1:category>mouse</s1:category>"
		                + "<s1:price>61.0</s1:price>" + "<s1:model>MS23F</s1:model>"
		                + "<s1:brand>Microsoft</s1:brand>" + "</s1:Item>"
		                + "</tns:search_by_category>" + "</senv:Body>" + "</senv:Envelope>";

		assertEquals(expectedXml, result);
	}

	@Test
	public void xmlWithComplexHierarchyAndSameNamesShouldReturnProperContent()
	                throws ParserException {
		String sampleXml = "<senv:Envelope "
		                + "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" "
		                + "                             xmlns:tns=\"tns\">" + "<senv:Body>"
		                + "    <tns:search_by_brand xmln=\"schema\">"
		                + "            <s1:item>" + "                <s1:item>?</s1:item>"
		                + "            </s1:item>" + "            <s1:item>?</s1:item>"
		                + "    </tns:search_by_brand>" + "</senv:Body>"
		                + "</senv:Envelope>";

		Item root = new ItemImpl("search_by_brand");
		Item child1 = root.addChild("item");
		root.addChild("item").setContent("test2");
		child1.addChild("item").setContent("test1");

		String result = new ItemBuilder().buildItem(sampleXml, root);

		String expectedXml = "<senv:Envelope "
		                + "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" "
		                + "xmlns:tns=\"tns\">" + "<senv:Body>"
		                + "<tns:search_by_brand xmln=\"schema\">" + "<s1:item>"
		                + "<s1:item>test1</s1:item>" + "</s1:item>"
		                + "<s1:item>test2</s1:item>" + "</tns:search_by_brand>"
		                + "</senv:Body>" + "</senv:Envelope>";

		assertEquals(expectedXml, result);
	}

	@Test(expected = ParserException.class)
	public void xmlWithComplexHierarchyAndSameNamesWithWrongHierarchyRequestItemShouldRaiseException()
	                throws ParserException {

		/*
		 * Expected: | |--Item |----Item |--Item
		 * 
		 * Passed: | |--Item |----Item |----Item
		 */
		String sampleXml = "<senv:Envelope "
		                + "                             xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" "
		                + "                             xmlns:tns=\"tns\">" + "<senv:Body>"
		                + "    <tns:search_by_brand xmln=\"schema\">"
		                + "            <s1:item>" + "                <s1:item>?</s1:item>"
		                + "            </s1:item>" + "            <s1:item>?</s1:item>"
		                + "    </tns:search_by_brand>" + "</senv:Body>"
		                + "</senv:Envelope>";

		Item root = new ItemImpl("search_by_brand");
		Item child1 = root.addChild("item");
		child1.addChild("item").setContent("test1");
		child1.addChild("item").setContent("test2");

		new ItemBuilder().buildItem(sampleXml, root);
	}

	@Test
	public void xmlWithNumberOfSameItemsDefinedInRuntimeMustBeSupported() throws Exception {
		String baseXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.choreos.eu/\">"
		                + "<soapenv:Header/>"
		                + "<soapenv:Body>"
		                + "		<ser:setSupermarketsList>"
		                + "			<!--Zero or more repetitions:-->"
		                + "			 <endpoint>?</endpoint>"
		                + "		</ser:setSupermarketsList>"
		                + "</soapenv:Body>" + "</soapenv:Envelope>";

		Item request = new ItemImpl("setSupermarketsList");
		request.addChild("endpoint").setContent("endpoint1");

		request.addChild("endpoint").setContent("endpoint2");

		String actualXml = new ItemBuilder().buildItem(baseXml, request);

		String expectedXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.choreos.eu/\">"
		                + "<soapenv:Header></soapenv:Header>"
		                + "<soapenv:Body>"
		                + "<ser:setSupermarketsList>"
		                + "<endpoint>endpoint1</endpoint>"
		                + "<endpoint>endpoint2</endpoint>"
		                + "</ser:setSupermarketsList>"
		                + "</soapenv:Body>" + "</soapenv:Envelope>";

		assertEquals(expectedXml, actualXml);
	}

	@Test
	public void xmlWithNumberOfSameItemsDefinedInRuntimeWithNamespacesMustBeSupported()
	                throws Exception {

		String baseXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cus=\"http://petals.ow2.org/bpel/Customer/\">"
		                + "<soapenv:Header/>"
		                + "	<soapenv:Body>"
		                + "		<cus:getPriceOfProductList>"
		                + "			<!--Zero or more repetitions:-->"
		                + "			<cus:item>?</cus:item>"
		                + "			</cus:getPriceOfProductList>"
		                + "	</soapenv:Body>" + "</soapenv:Envelope>";

		Item list = new ItemImpl("getPriceOfProductList");
		list.addChild("item").setContent("milk");
		list.addChild("item").setContent("cereal");

		String actualXml = new ItemBuilder().buildItem(baseXml, list);

		String expectedXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cus=\"http://petals.ow2.org/bpel/Customer/\">"
		                + "<soapenv:Header></soapenv:Header>"
		                + "<soapenv:Body>"
		                + "<cus:getPriceOfProductList>"
		                + "<cus:item>milk</cus:item>"
		                + "<cus:item>cereal</cus:item>"
		                + "</cus:getPriceOfProductList>"
		                + "</soapenv:Body>" + "</soapenv:Envelope>";

		assertEquals(expectedXml, actualXml);

	}

	@Test
	public void shouldConverttInternalRepeatedXmlTagsProperly() throws Exception {
		String baseXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreography.futuremarket.ime.usp.br/\">"
		                + "   <soapenv:Header/>                                "
		                + "   <soapenv:Body>                                   "
		                + "      <chor:purchase>                               "
		                + "         <!--Optional:-->                           "
		                + "         <arg0>                                     "
		                + "            <items>                                 "
		                + "			<!--Zero or more repetitions:--> "
		                + "               <entry>                              "
		                + "                  <!--Optional:-->                  "
		                + "                  <key>?</key>                      "
		                + "                  <!--Optional:-->                  "
		                + "                  <value>                           "
		                + "                     <!--Optional:-->               "
		                + "                     <product>                      "
		                + "                        <!--Optional:-->            "
		                + "                        <name>?</name>              "
		                + "                        <price>?</price>            "
		                + "                     </product>                     "
		                + "                     <quantity>?</quantity>         "
		                + "                     <!--Optional:-->               "
		                + "                     <seller>?</seller>             "
		                + "                  </value>                          "
		                + "               </entry>                             "
		                + "            </items>                                "
		                + "         </arg0>                                    "
		                + "         <!--Optional:-->                           "
		                + "         <arg1>                                     "
		                + "            <!--Optional:-->                        "
		                + "            <address>?</address>                    "
		                + "            <!--Optional:-->                        "
		                + "            <creditCard>?</creditCard>              "
		                + "            <!--Optional:-->                        "
		                + "            <name>?</name>                          "
		                + "         </arg1>                                    "
		                + "      </chor:purchase>                              "
		                + "   </soapenv:Body>                                  "
		                + "</soapenv:Envelope>                                 ";

		Item purchase = new ItemImpl("purchase");
		Item arg1 = purchase.addChild("arg1");
		arg1.addChild("creditCard").setContent("0000000000");
		arg1.addChild("address").setContent("my home");
		arg1.addChild("name").setContent("john");
		Item arg0 = purchase.addChild("arg0");
		
		Item items = arg0.addChild("items");
		
		Item entry = items.addChild("entry");
		Item value = entry.addChild("value");
		Item product = value.addChild("product");
		product.addChild("price").setContent("1.0");
		product.addChild("name").setContent("bread");
		value.addChild("quantity").setContent("10");
		value.addChild("seller").setContent("http://localhost:8080/supermarket1");
		entry.addChild("key").setContent("bread");

		entry = items.addChild("entry");
		value = entry.addChild("value");
		product = value.addChild("product");
		product.addChild("price").setContent("1.0");
		product.addChild("name").setContent("milk");
		value.addChild("quantity").setContent("10");
		value.addChild("seller").setContent("http://localhost:8080/supermarket2");
		entry.addChild("key").setContent("milk");
		
		String actualXml = new ItemBuilder().buildItem(baseXml, purchase);

		String expectedXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:chor=\"http://choreography.futuremarket.ime.usp.br/\">"
		                + "<soapenv:Header></soapenv:Header>"
		                + "<soapenv:Body>"
		                + "<chor:purchase>"
		                + "<arg0>"
		                + "<items>"
		                + "<entry>"
		                + "<key>bread</key>"
		                + "<value>"
		                + "<product>"
		                + "<name>bread</name>"
		                + "<price>1.0</price>"
		                + "</product>"
		                + "<quantity>10</quantity>"
		                + "<seller>http://localhost:8080/supermarket1</seller>"
		                + "</value>"
		                + "</entry>"
		                + "<entry>"
		                + "<key>milk</key>"
		                + "<value>"
		                + "<product>"
		                + "<name>milk</name>"
		                + "<price>1.0</price>"
		                + "</product>"
		                + "<quantity>10</quantity>"
		                + "<seller>http://localhost:8080/supermarket2</seller>"
		                + "</value>"
		                + "</entry>"
		                + "</items>"
		                + "</arg0>"
		                + "<arg1>"
		                + "<address>my home</address>"
		                + "<creditCard>0000000000</creditCard>"
		                + "<name>john</name>"
		                + "</arg1>"
		                + "</chor:purchase>"
		                + "</soapenv:Body>"
		                + "</soapenv:Envelope>";

		assertEquals(expectedXml, actualXml);

	}

}
