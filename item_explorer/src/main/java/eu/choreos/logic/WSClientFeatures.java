package eu.choreos.logic;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;

import com.eviware.soapui.support.SoapUIException;

import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.EmptyRequetItemException;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.ParserException;
import eu.choreos.vv.exceptions.WSDLException;

public class WSClientFeatures {

	public static WSClient load(String wsdlURI) throws WSDLException, XmlException, IOException, FrameworkException {
		
			return new WSClient(wsdlURI);
	}
	
	public static String printRequestItem(WSClient client, String operationName){
		try {
			return client.getItemRequestFor(operationName).printAsRequest();
		} 
		catch (ParserException e) { e.printStackTrace(); } 
		catch (InvalidOperationNameException e) { e.printStackTrace(); } 
		catch (EmptyRequetItemException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static String printResponseItem(WSClient client, String operationName) throws XmlException, IOException, SoapUIException{
		try {
			return client.getItemResponseFor(operationName).printAsResponse();
		} 
		catch (ParserException e) { e.printStackTrace(); }
		
		return null;
	}

}
