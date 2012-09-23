package eu.choreos.vv.interceptor;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.model.iface.Request.SubmitException;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.support.SoapUIException;

/**
 * This class provides features for routing the messages in the proxies
 * 
 * @author Felipe Besson
 *
 */
public class RequestDispatcher {
	
	public static String getResponse(String wsdl, String operationName, String requestContent) throws XmlException, IOException, SoapUIException, SubmitException{
		addResponse(wsdl, requestContent);
		
		WsdlProject project = new WsdlProject();
		WsdlInterface iface = WsdlInterfaceFactory.importWsdl(project, wsdl, true)[0];
		
		WsdlOperation operation = (WsdlOperation) iface.getOperationByName(operationName);
		WsdlRequest request = operation.addNewRequest("myRequest");
		request.setRequestContent(requestContent);

		// submit the request
		WsdlSubmit<WsdlRequest> submit = request.submit(new WsdlSubmitContext(
						request), false);

		// wait for the response
		Response response = submit.getResponse();

		// print the response
		String content = response.getContentAsString();

		return content;
	}

	private static void addResponse(String wsdl, String requestContent) {
		InterceptedMessagesRegistry.getInstance().addMessage(wsdl, requestContent);
	}
	

}
