package eu.choreos.vv.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InterceptedMessagesRegistry {
	
	private static InterceptedMessagesRegistry instance = new InterceptedMessagesRegistry();
	
	private HashMap<String, List<String>> interceptedMessages;

	private InterceptedMessagesRegistry(){
		interceptedMessages = new HashMap<String, List<String>>();
	}
	
	public static InterceptedMessagesRegistry getInstance() {
		return instance;
	}

	public void registerWsdl(String wsdl) {
		interceptedMessages.put(wsdl, new ArrayList<String>());
	}

	public List<String> getMessages(String wsdl) {
		return interceptedMessages.get(wsdl);
	}

	public void removeWsdl(String wsdl) {
		interceptedMessages.remove(wsdl);			
	}

	public void addMessage(String wsdl, String xmlMessage) {
		interceptedMessages.get(wsdl).add(xmlMessage);
	}

}
