package eu.choreos.vv.servicesimulator;

import java.util.Arrays;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.SoapEnvelopeHelper;
import eu.choreos.vv.common.ItemBuilder;
import eu.choreos.vv.exceptions.ParserException;

/**
 * This class represents the Mock Response returned by WSMock
 * @author besson
 *
 */
public class MockResponse {
	
	private boolean primitiveResponse;
	private boolean primitiveRequest;

	private String[] responseParam;
	private String[] requestParam;
	private Item responseItem;
	private Item requestItem;
	
	/**
	 * Defines conditional parameters
	 * 
	 * @param request paremeters (simple types) 
	 * @return the current MockResponse 
	 */
	public MockResponse whenReceive(String... requestParam) {
		this.primitiveRequest = true;
		this.requestParam = requestParam;
		return this;
	}
	
	/**
	 * Defines conditional parameters
	 * 
	 * @param request Item (complex type)
	 * @return the current MockResponse 
	 */
	public MockResponse whenReceive(Item requestItem) {
		this.requestItem= requestItem;
		return this;
	}

	/**
	 * Defines the response content for the provided conditional parameters
	 * 
	 * @param responseItem is a complex type message response
	 * @return
	 */
	public MockResponse replyWith(Item responseItem) {
		this.responseItem = responseItem;
		return this;
	}
	
	/**
	 * Defines the response content for the provided conditional parameters
	 * 
	 * @param responseParam is a simple type message response
	 * @return
	 */
	public MockResponse replyWith(String... responseParam) {
		primitiveResponse = true;
		this.responseParam = responseParam;
		return this;
	}

	/**
	 * Gets the request parameters (simple types) defined
	 * 
	 * @return an array with the parameters
	 */
	public String[] getRequestParam() {
		return requestParam;
	}

	/**
	 * Fills a generic Soap response envelope with the response content provided
	 * 
	 * @param baseXml is the generic Soap envelope
	 * @return a concrete Soap envelope
	 * @throws ParserException
	 */
	public String buildResponseContent(String baseXml) throws ParserException {
		String resultXml = "";
		
		if(primitiveResponse)
			resultXml = SoapEnvelopeHelper.generate(baseXml, responseParam);
		else
			resultXml = new ItemBuilder().buildItem(baseXml, responseItem);
			
		return resultXml;
	}

	/**
	 * Fills a generic Soap request envelope with the response content provided
	 * 
	 * @param baseXml is the generic Soap envelope
	 * @return a concrete Soap envelope
	 * @throws ParserException
	 */
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
