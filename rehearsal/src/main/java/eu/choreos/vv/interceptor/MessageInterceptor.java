package eu.choreos.vv.interceptor;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemParser;
import eu.choreos.vv.common.WsdlUtils;
import eu.choreos.vv.exceptions.ParserException;



public class MessageInterceptor {

	private WSProxy proxy;
	private InterceptedMessagesRegistry registry; 
	private String port;
	
	public MessageInterceptor(String port){
		registry = InterceptedMessagesRegistry.getInstance();
		this.port = port;
	}
	
	public void interceptTo(String realWsdl) throws Exception {
		proxy = new WSProxy(WsdlUtils.getBaseName(realWsdl), realWsdl);
		proxy.setPort(port);
		proxy.start();
		
		registry.registerWsdl(realWsdl);
		
	}

	public List<Item> getMessages() {
		List<Item> itemMessages = new ArrayList<Item>();
		List<String> xmlMessages =  registry.getMessages(proxy.getRealWsdl());
		ItemParser parser = new ItemParser();

		try {
			for (String xmlMessage : xmlMessages)
					itemMessages.add(parser.parse(xmlMessage));
		} catch (ParserException e) {e.printStackTrace();}
		
		return itemMessages;
	}

	public String getRealWsdl() {
		return proxy.getRealWsdl();
	}

	public String getProxyWsdl() {
		return proxy.getProxyWsdl();
	}

	public String getPort() {
		return proxy.getPort();
	}
	
	public void stop(){
		proxy.stop();
	}

}
