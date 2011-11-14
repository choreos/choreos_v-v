package eu.choreos.vv.servicesimulator;

import java.util.Arrays;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.SoapEnvelopeHelper;
import eu.choreos.vv.common.ItemBuilder;
import eu.choreos.vv.exceptions.ParserException;

public class MockResponse {
	
	private boolean primitiveResponse;
	private boolean primitiveRequest;

	private String[] responseParam;
	private String[] requestParam;
	private Item responseItem;
	private Item requestItem;
	

	public MockResponse whenReceive(String... requestParam) {
		this.primitiveRequest = true;
		this.requestParam = requestParam;
		return this;
	}
	
	public MockResponse whenReceive(Item requestItem) {
		this.requestItem= requestItem;
		return this;
	}

	public MockResponse replyWith(Item responseItem) {
		this.responseItem = responseItem;
		return this;
	}
	
	public MockResponse replyWith(String... responseParam) {
		primitiveResponse = true;
		this.responseParam = responseParam;
		return this;
	}

	public String[] getRequestParam() {
		return requestParam;
	}

	public String buildResponseContent(String baseXml) throws ParserException {
		String resultXml = "";
		
		if(primitiveResponse)
			resultXml = SoapEnvelopeHelper.generate(baseXml, responseParam);
		else
			resultXml = new ItemBuilder().buildItem(baseXml, responseItem);
			
		return resultXml;
	}
	
	public String buildRequestContent(String baseXml) throws ParserException {
		String resultXml = "";
		
		if(primitiveRequest)
			resultXml = SoapEnvelopeHelper.generate(baseXml, requestParam);
		else
			resultXml = new ItemBuilder().buildItem(baseXml, requestItem);
			
		return resultXml;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {

		MockResponse other = (MockResponse) obj;
		if (other.requestParam != null){
			if (Arrays.equals(requestParam, other.requestParam))
				return true;
		}
		
		if ( other.requestItem != null){
			if (requestItem == other.requestItem)
				return true;
		}
		
		return false;
	}
	

}
