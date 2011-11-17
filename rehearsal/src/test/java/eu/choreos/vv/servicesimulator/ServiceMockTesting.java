package eu.choreos.vv.servicesimulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.NoMockResponseException;

public class ServiceMockTesting {

	private static final String MOCK_WSDL_URI = "http://localhost:4321/supermarketMock?wsdl";
	private static final String SM_WSDL_URI = "file://" + System.getProperty("user.dir") + "/resource/sm_plus.wsdl";
	private WSMock smMock;
	
	@Before
	public void startMock() throws Exception{
		 smMock = getMock();
	}

	
	@After
	public void stopMock() throws Exception{
		smMock.stop();
	}
	
	
	@Test
	public void shouldDefineAResponseWithASimpleTypeBeforeMockIsStarted() throws Exception {
		MockResponse response = new MockResponse().whenReceive("*").replyWith("90");
		smMock.returnFor("getPrice", response);		
		smMock.start();
		
		WSClient smClient = new WSClient(MOCK_WSDL_URI);
		Item result = smClient.request("getPrice", "milk");
		
		assertEquals((Double)90.0, result.getChild("return").getContentAsDouble());
	}
	
	@Test(expected=InvalidOperationNameException.class)
	public void shouldThrowAnExceptionWhenTheOperationDoesNotExist() throws Exception{
		MockResponse response = new MockResponse().whenReceive("*").replyWith("90");
		smMock.start();

		smMock.returnFor("getFreeProducts", response);
	}
	
	@Test
	public void shouldDefineAResponseWithSimpleTypesBeforeMockIsStarted() throws Exception {
		
		Item list = new ItemImpl("getSpecialOfferResponse");
		Item item1 = new ItemImpl("return");
		item1.setContent("nachos");
		list.addChild(item1);
		Item item2= new ItemImpl("return");
		item2.setContent("beer");
		list.addChild(item2);
		
		MockResponse mockReponse = new MockResponse().whenReceive("*").replyWith(list);
		smMock.returnFor("getSpecialOffer", mockReponse);		
		smMock.start();
		
		WSClient smClient = new WSClient(MOCK_WSDL_URI);
		List<Item> response = smClient.request("getSpecialOffer").getChildAsList("return");
		
		assertEquals("nachos", response.get(0).getContent());
		assertEquals("beer", response.get(1).getContent());
	}
	
	@Test
	public void shouldAssignMoreThanOneOperationForAMockInstance() throws Exception {
		MockResponse response = new MockResponse().whenReceive("*").replyWith("90");
		smMock.returnFor("getPrice", response);	
		
		Item list = new ItemImpl("getSpecialOfferResponse");
		Item item1 = new ItemImpl("return");
		item1.setContent("nachos");
		list.addChild(item1);
		Item item2= new ItemImpl("return");
		item2.setContent("beer");
		list.addChild(item2);
		
		MockResponse mockReponse = new MockResponse().whenReceive("*").replyWith(list);
		smMock.returnFor("getSpecialOffer", mockReponse);		
		
		smMock.start();
		
		WSClient smClient = new WSClient(MOCK_WSDL_URI);
		Item result = smClient.request("getPrice", "milk");
		
		assertEquals((Double)90.0, result.getChild("return").getContentAsDouble());
		
		List<Item>results = smClient.request("getSpecialOffer").getChildAsList("return");
		
		assertEquals("nachos", results.get(0).getContent());
		assertEquals("beer", results.get(1).getContent());
	
	}
	
	@Test
	public void shouldDefineAResponseWithComplexTypeBeforeMockIsStarted() throws Exception {
		
		Item result = new ItemImpl("return");
		Item name  = new ItemImpl("name");
		name.setContent("milk");
		Item status = new ItemImpl("status");
		status.setContent("zero units");
		result.addChild(name);
		result.addChild(status);
	
		MockResponse mockReponse = new MockResponse().whenReceive("*").replyWith(result);

		smMock.returnFor("getProductStatus", mockReponse);		
		smMock.start();
		
		WSClient smClient = new WSClient(MOCK_WSDL_URI);
		Item response = smClient.request("getProductStatus", "milk").getChild("return");
		
		assertEquals("milk", response.getChild("name").getContent());
		assertEquals("zero units", response.getChild("status").getContent());
	}

	
	@Test
	public void shouldDefineAResponseWithASimpleTypeAfterMockIsStarted() throws Exception {
		smMock.start();
		
		MockResponse response = new MockResponse().whenReceive("*").replyWith("190");
		smMock.returnFor("getPrice", response);		
		
		WSClient smClient = new WSClient(MOCK_WSDL_URI);
		Item result = smClient.request("getPrice", "milk");
		
		assertEquals((Double)190.0, result.getChild("return").getContentAsDouble());
	}

	
	@Test
	public void shouldDefineAResponseWithComplexTypeAfterMockIsStarted() throws Exception {
		smMock.start();
		
		Item result = new ItemImpl("return");
		Item name  = new ItemImpl("name");
		name.setContent("milk");
		Item status = new ItemImpl("status");
		status.setContent("zero units");
		result.addChild(name);
		result.addChild(status);
		
		MockResponse mockResponse = new MockResponse().whenReceive("*").replyWith(result);
		smMock.returnFor("getProductStatus", mockResponse);		
		
		WSClient smClient = new WSClient(MOCK_WSDL_URI);
		Item response = smClient.request("getProductStatus", "milk").getChild("return");
		
		assertEquals("milk", response.getChild("name").getContent());
		assertEquals("zero units", response.getChild("status").getContent());
	}
	

	@Test
	public void shouldTwoMockRunningInDifferentPortWorkProperly() throws Exception {
		MockResponse response = new MockResponse().whenReceive("*").replyWith("90");
		smMock.returnFor("getPrice", response);		
		smMock.start();
		
		WSClient sm1Client = new WSClient(MOCK_WSDL_URI);
		Item response1 = sm1Client.request("getPrice", "milk");
		
		WSMock sm2Mock = getMock();
		sm2Mock.setPort("1357");
		 response = new MockResponse().whenReceive("*").replyWith("190");
		sm2Mock.returnFor("getPrice", response);		
		sm2Mock.start();
		
		WSClient sm2Client = new WSClient("http://localhost:1357/supermarketMock?wsdl");
		Item response2 = sm2Client.request("getPrice", "milk");
		
		assertEquals((Double)90.0, response1.getChild("return").getContentAsDouble());
		assertEquals((Double)190.0, response2.getChild("return").getContentAsDouble());

		sm2Mock.stop();
	}

	
	@Test
	public void shouldBePossibleUpdateAnResponseDefinedPreviously() throws Exception{
		MockResponse response = new MockResponse().whenReceive("*").replyWith("90");
		smMock.returnFor("getPrice", response);		
		smMock.start();
		
		WSClient smClient = new WSClient(MOCK_WSDL_URI);
		Item result = smClient.request("getPrice", "milk");
		assertEquals((Double)90.0, result.getChild("return").getContentAsDouble());
		
		 response = new MockResponse().whenReceive("*").replyWith("42");
		smMock.returnFor("getPrice", response);

		result = smClient.request("getPrice", "milk");
		assertEquals((Double)42.0, result.getChild("return").getContentAsDouble());
	}

	
	@Test
	public void duringTheRedefinitionNewResponseShouldReplaceTheOldOneWhenBothAreEqual() throws Exception{
		MockResponse response = new MockResponse().whenReceive("*").replyWith("81");
		smMock.returnFor("getPrice", response);		
		
		response = new MockResponse().whenReceive("*").replyWith("82");
		smMock.returnFor("getPrice", response);
		smMock.start();
		
		WSClient smClient = new WSClient(MOCK_WSDL_URI);
		Item result = smClient.request("getPrice", "milk");
		assertEquals((Double)82.0, result.getChild("return").getContentAsDouble());
	
		result = smClient.request("getPrice", "bread");
		assertFalse(result.getChild("return").getContentAsDouble() == 81.0);
		
		result = smClient.request("getPrice", "milk");
		assertEquals((Double)82.0, result.getChild("return").getContentAsDouble());
	}
	
	
	@Test
	public void shouldReturnTenWhenAskMilkOtherwiseShouldReturnOneHundread() throws Exception{
		MockResponse response1 = new MockResponse().whenReceive("*").replyWith("100");
		MockResponse response2 = new MockResponse().whenReceive("milk").replyWith("10");

		smMock.returnFor("getPrice", response1, response2);		
		smMock.start();
		
		WSClient smClient = new WSClient(MOCK_WSDL_URI);
		
		Item response = smClient.request("getPrice", "milk");
		assertEquals((Double)10.0, response.getChild("return").getContentAsDouble());
		
		response = smClient.request("getPrice", "bread");
		assertEquals((Double)100.0, response.getChild("return").getContentAsDouble());
		
		response = smClient.request("getPrice", "orange juice");
		assertEquals((Double)100.0, response.getChild("return").getContentAsDouble());
	}
	
	@Test
	public void shouldReturnTheCorrectResponsesWhenThereAreThreeConditions() throws Exception{
		MockResponse response1 = new MockResponse().whenReceive("coke").replyWith("3.50");
		MockResponse response2 = new MockResponse().whenReceive("water").replyWith("4.00");
		MockResponse response3 = new MockResponse().whenReceive("beer").replyWith("5.00");
		
		smMock.returnFor("getPrice", response1, response2, response3);
		smMock.start();

		WSClient smClient = new WSClient(MOCK_WSDL_URI);
		
		 Item response = smClient.request("getPrice", "beer");
		assertEquals((Double)5.00, response.getChild("return").getContentAsDouble());

		response = smClient.request("getPrice", "coke");
		assertEquals((Double)3.50, response.getChild("return").getContentAsDouble());
		
		 response = smClient.request("getPrice", "water");
		assertEquals((Double)4.00, response.getChild("return").getContentAsDouble());
	}
	
	@Test
	public void shouldBePossibleDefineTwoParameterInTheConditionInputParameters() throws Exception{
		MockResponse response1 = new MockResponse().whenReceive("beer", "2").replyWith("Be hurry, we only have 2 units");

		smMock.returnFor("purchase", response1);		
		smMock.start();
		
		WSClient smClient = new WSClient(MOCK_WSDL_URI);
		
		Item response = smClient.request("purchase", "beer", "2");
		assertEquals("Be hurry, we only have 2 units", response.getChild("return").getContent());
	}
	
	@Test
	public void shouldBePossibleDefineParametersAsItemObjects() throws Exception{
		Item request = getRequest("milk");
		Item response = getResponse("milk", "empty");
		
		MockResponse mockResponse = new MockResponse().whenReceive(request).replyWith(response);
		smMock.returnFor("getProductStatus", mockResponse);
		smMock.start();
		
		WSClient smClient = new WSClient(MOCK_WSDL_URI);
		Item clientResponse = smClient.request("getProductStatus", request).getChild("return");

		assertEquals("milk", clientResponse.getChild("name").getContent());
		assertEquals("empty", clientResponse.getChild("status").getContent());
	}
	
	@Test
	public void shouldBePossibleUseSimpleAndComplexTypeTogetherInTheMockResponses() throws Exception{
		Item request1 = getRequest("milk");
		Item request2 = getRequest("salt");
		Item response1 = getResponse("milk", "infinity");
		Item response2 = getResponse("*", "totally available");
		
		MockResponse mockResponse1 = new MockResponse().whenReceive(request1).replyWith(response1);
		MockResponse mockResponse2 = new MockResponse().whenReceive("*").replyWith(response2);
		
		smMock.returnFor("getProductStatus", mockResponse1, mockResponse2);
		smMock.start();
		
		WSClient smClient = new WSClient(MOCK_WSDL_URI);
		Item clientResponse = smClient.request("getProductStatus", request1).getChild("return");

		assertEquals("milk", clientResponse.getChild("name").getContent());
		assertEquals("infinity", clientResponse.getChild("status").getContent());
	
		smClient = new WSClient(MOCK_WSDL_URI);
		clientResponse = smClient.request("getProductStatus", request2).getChild("return");

		assertEquals("*", clientResponse.getChild("name").getContent());
		assertEquals("totally available", clientResponse.getChild("status").getContent());
	}
	
	@Test(expected=NoMockResponseException.class)
	public void shouldThrowAnExceptionWhenNoResponseIsDefinedForAnOperation() throws Exception {
		smMock.start();

		smMock.returnFor("getProductStatus");
	}

	
	private WSMock getMock() throws Exception {
		WSMock smMock = new WSMock("supermarketMock", SM_WSDL_URI);
		smMock.setPort("4321");
		return smMock;
	}
	
	private Item getResponse(String name, String status) {
		Item response = new ItemImpl("getProductStatusResponse");
		Item responseContent = new ItemImpl("return");
		Item nameResponse = new ItemImpl("name");
		nameResponse.setContent(name);
		responseContent.addChild(nameResponse);
		
		Item statusResponse = new ItemImpl("status");
		statusResponse.setContent(status);
		responseContent.addChild(statusResponse);
		
		response.addChild(responseContent);
		return response;
	}
	
	
	private Item getRequest(String name) {
		Item request = new ItemImpl("getProductStatus");
		Item nameRequest = new ItemImpl("name");
		nameRequest.setContent(name);
		request.addChild(nameRequest);
		return request;
	}
	
}
