package eu.choreos.vv.servicesimulator;

import java.util.HashMap;
import java.util.Set;

import com.eviware.soapui.impl.wsdl.mock.WsdlMockResponse;

public class MockResponseMap extends HashMap<MockResponse, WsdlMockResponse>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean containsKey(Object key){
		MockResponse other  = (MockResponse)key;
		Set<MockResponse> keys = this.keySet();

		for (MockResponse entry : keys) {
			if(entry.equals(other))
				return true;
		}
		
		return false;
	}
	
	@Override
	public WsdlMockResponse get(Object key){
		MockResponse other  = (MockResponse)key;
		WsdlMockResponse found = null;
		
		Set<java.util.Map.Entry<MockResponse, WsdlMockResponse>> entries = this.entrySet();
		
		for (java.util.Map.Entry<MockResponse, WsdlMockResponse> entry : entries) {
			if(entry.getKey().equals(other))
				found = entry.getValue();
		}
		
		return found;
	}

}