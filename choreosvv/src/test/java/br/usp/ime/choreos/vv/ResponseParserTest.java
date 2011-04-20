package br.usp.ime.choreos.vv;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ResponseParserTest {
	
	@Test
	public void simpleTest() {
		String sampleXml = "<senv:Envelope " 
				+ "				xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " 
				+ "				xmlns:tns=\"tns\">" 
				+ "  <senv:Body>"
				+ "   <tns:search_by_brandResponse>" 
				+ "<tns:search_by_brandResult>" 
				+ "    <s1:name>mouse</s1:name>" 
				+ "</tns:search_by_brandResult>" 
				+ "</tns:search_by_brandResponse>" 
				+ "</senv:Body>" 
				+ "</senv:Envelope>";
		
		String result = "create: ResponseItem(search_by_brandResult)\n" +
						"stack.push(search_by_brandResult)\n" +
						"create: ResponseItem(name)\n" +
						"stack.push(name)\n" +
						"name.setContent()\n" +
						"search_by_brandResult.add(name)\n" +
						"stack.pop()\n" +
						"stack.pop()\n";
		
		ResponseParser parser = new ResponseParser();
		assertEquals(result, parser.parse(sampleXml));
	}
	
	@Test
	public void complexTypeTest(){
	
		String sampleXml = "<senv:Envelope " + 
			"xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " + 
			"xmlns:tns=\"tns\"> " + 
			   "<senv:Body>" +
			      "<tns:search_by_categoryResponse>" +
			         "<tns:search_by_categoryResult>" +
			            "<s1:Item>" +
			               "<s1:category>mouse</s1:category>" +
			               "<s1:price>89.2</s1:price>" +
			               "<s1:model>RZG145</s1:model>" +
			               "<s1:brand>Razor</s1:brand>" +
			            "</s1:Item>" +
			         "</tns:search_by_categoryResult>" +
			      "</tns:search_by_categoryResponse>" +
			   "</senv:Body>" +
			"</senv:Envelope>";
		
		String result = "create: ResponseItem(search_by_categoryResult)\n" +
						"stack.push(search_by_categoryResult)\n" +
						"create: ResponseItem(Item)\n" +
						"stack.push(Item)\n" +
						"create: ResponseItem(category)\n" +
						"stack.push(category)\n" +
						"category.setContent()\n" +
						"Item.add(category)\n" +
						"stack.pop()\n" +
						"create: ResponseItem(price)\n" +
						"stack.push(price)\n" +
						"price.setContent()\n" +
						"Item.add(price)\n" +
						"stack.pop()\n" +
						"create: ResponseItem(model)\n" +
						"stack.push(model)\n" +
						"model.setContent()\n" +
						"Item.add(model)\n" +
						"stack.pop()\n" +
						"create: ResponseItem(brand)\n" +
						"stack.push(brand)\n" +
						"brand.setContent()\n" +
						"Item.add(brand)\n" +
						"stack.pop()\n" +
						"search_by_categoryResult.add(Item)\n" +
						"stack.pop()\n" + 
						"stack.pop()\n";

		ResponseParser parser = new ResponseParser();
		assertEquals(result, parser.parse(sampleXml));		
	}
	
	@Test
	public void complexTypeListTest(){
	
		String sampleXml = "<senv:Envelope " + 
			"xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2003/03/addressing\" " + 
			"xmlns:tns=\"tns\"> " + 
			   "<senv:Body>" +
			      "<tns:search_by_categoryResponse    >  " +
			         "<tns:search_by_categoryResult    >" +
			            "  <s1:Item>  " +
			               "<s1:category>mouse  </s1:category>  " +
			               "<s1:price>89.2</s1:price>  " +
			               "  <s1:model>RZG145</s1:model>  " +
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
			               "<s1:price   >  61.0</s1:price>  " +
			               "   <s1:model>MS23F</s1:model>" +
			               "<s1:brand>Microsoft</s1:brand>" +
			            "</s1:Item>" +			            
			         "</tns:search_by_categoryResult>" +
			      "</tns:search_by_categoryResponse>" +
			   "</senv:Body>" +
			"</senv:Envelope>";
		
		String result = "create: ResponseItem(search_by_categoryResult)\n" +
						"stack.push(search_by_categoryResult)\n" +
						"create: ResponseItem(Item)\n" +
						"stack.push(Item)\n" +
						"create: ResponseItem(category)\n" +
						"stack.push(category)\n" +
						"category.setContent()\n" +
						"Item.add(category)\n" +
						"stack.pop()\n" +
						"create: ResponseItem(price)\n" +
						"stack.push(price)\n" +
						"price.setContent()\n" +
						"Item.add(price)\n" +
						"stack.pop()\n" +
						"create: ResponseItem(model)\n" +
						"stack.push(model)\n" +
						"model.setContent()\n" +
						"Item.add(model)\n" +
						"stack.pop()\n" +
						"create: ResponseItem(brand)\n" +
						"stack.push(brand)\n" +
						"brand.setContent()\n" +
						"Item.add(brand)\n" +
						"stack.pop()\n" +
						"search_by_categoryResult.add(Item)\n" +
						"stack.pop()\n" + 
						"create: ResponseItem(Item)\n" +
						"stack.push(Item)\n" +
						"create: ResponseItem(category)\n" +
						"stack.push(category)\n" +
						"category.setContent()\n" +
						"Item.add(category)\n" +
						"stack.pop()\n" +
						"create: ResponseItem(price)\n" +
						"stack.push(price)\n" +
						"price.setContent()\n" +
						"Item.add(price)\n" +
						"stack.pop()\n" +
						"create: ResponseItem(model)\n" +
						"stack.push(model)\n" +
						"model.setContent()\n" +
						"Item.add(model)\n" +
						"stack.pop()\n" +
						"create: ResponseItem(brand)\n" +
						"stack.push(brand)\n" +
						"brand.setContent()\n" +
						"Item.add(brand)\n" +
						"stack.pop()\n" +
						"search_by_categoryResult.add(Item)\n" +
						"stack.pop()\n" + 
						"create: ResponseItem(Item)\n" +
						"stack.push(Item)\n" +
						"create: ResponseItem(category)\n" +
						"stack.push(category)\n" +
						"category.setContent()\n" +
						"Item.add(category)\n" +
						"stack.pop()\n" +
						"create: ResponseItem(price)\n" +
						"stack.push(price)\n" +
						"price.setContent()\n" +
						"Item.add(price)\n" +
						"stack.pop()\n" +
						"create: ResponseItem(model)\n" +
						"stack.push(model)\n" +
						"model.setContent()\n" +
						"Item.add(model)\n" +
						"stack.pop()\n" +
						"create: ResponseItem(brand)\n" +
						"stack.push(brand)\n" +
						"brand.setContent()\n" +
						"Item.add(brand)\n" +
						"stack.pop()\n" +
						"search_by_categoryResult.add(Item)\n" +
						"stack.pop()\n" + 
						"stack.pop()\n";

		ResponseParser parser = new ResponseParser();
		assertEquals(result, parser.parse(sampleXml));		
	}
	
}