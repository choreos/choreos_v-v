package eu.choreos.vv.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This singleton class stores the messages intercepted by Message Interceptor from a same host
 * 
 * @author besson
 *
 */
public class InterceptedMessagesRegistry {
	
	private static InterceptedMessagesRegistry instance = new InterceptedMessagesRegistry();
	
	private HashMap<String, List<String>> interceptedMessages;

	/** 
	 * Private constructor for applying the Singleton properties
	 * 
	 */
	private InterceptedMessagesRegistry(){
		interceptedMessages = new HashMap<String, List<String>>();
	}
	
	/**
	 * Returns the unique existent instance of this class
	 * 
	 * @return InterceptedMessagesRegistry instance
	 */
	public static InterceptedMessagesRegistry getInstance() {
		return instance;
	}

	/**
	 * Adds a new wsdl
	 * 
	 * @param wsdl
	 */
	public void registerWsdl(String wsdl) {
		interceptedMessages.put(wsdl, new ArrayList<String>());
	}

	/**
	 * Gets all messages stored for the provided wsdl
	 * 
	 * @param wsdl
	 * @return
	 */
	public List<String> getMessages(String wsdl) {
		return interceptedMessages.get(wsdl);
	}

	/**
	 * Removes all messages stored for the the provided wsdl
	 * 
	 * @param wsdl
	 */
	public void removeWsdl(String wsdl) {
		interceptedMessages.remove(wsdl);			
	}

	/**
	 * Adds a message for the provided wsdl
	 * 
	 * @param wsdl
	 * @param xmlMessage
	 */
	public void addMessage(String wsdl, String xmlMessage) {
		interceptedMessages.get(wsdl).add(xmlMessage);
	}

}
