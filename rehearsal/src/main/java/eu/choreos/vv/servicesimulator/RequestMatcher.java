package eu.choreos.vv.servicesimulator;

import java.util.HashMap;

public class RequestMatcher {
	
	private HashMap<String, String> responses;
	
	public RequestMatcher(HashMap<String, String> responses) {
		this.responses = responses;
	}
	
	public String get(String request){
		 String response=  responses.get(request);
		 
		 return (response!=null) ? response : responses.get("*");
		 
	}
	
	
	
	
	
	

}
