package br.usp.ime.labxp.soapUIStudy;

import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.model.iface.Request.SubmitException;
import com.eviware.soapui.model.iface.Response;

public class Request  implements Runnable {
	WsdlInterface iface;
	
	public Request(WsdlInterface iface){
		this.iface = iface;
	}
	
	public void run(){
		// create new project
				
				// create a new empty request for that operation
				WsdlOperation operation = (WsdlOperation) iface.getOperationByName( "getFlight" );

				WsdlRequest request = operation.addNewRequest("myRequest");

				String requestContent = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:trav=\"http://airline.ws.ime.usp.br/\">" +
				"<soapenv:Header/>" +			
				"<soapenv:Body>" +
				"<trav:getFlight>" +
				"<arg0>Milan</arg0>" +
				"<arg1>12-21-2010</arg1>" +
				"</trav:getFlight>" +
				"</soapenv:Body>" +
				"</soapenv:Envelope>";
				
				// generate the request content from the schema
				request.setRequestContent( requestContent );
			
			
				// submit the request
				WsdlSubmit<WsdlRequest> submit;
				try {
					submit = request.submit( new WsdlSubmitContext(request), false);
					Response response = submit.getResponse();
					//		print the response
					String content = response.getContentAsString();

					System.out.println( content );	
					
				} catch (SubmitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
}