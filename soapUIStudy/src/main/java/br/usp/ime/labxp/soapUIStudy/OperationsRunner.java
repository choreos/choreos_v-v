package br.usp.ime.labxp.soapUIStudy;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.model.iface.Operation;
import com.eviware.soapui.model.iface.Response;

public class OperationsRunner {

	public static void main(String args[]) throws Exception {
		// create new project

		WsdlProject project = new WsdlProject();

		// import a wsdl
		WsdlInterface iface = WsdlInterfaceFactory.importWsdl(project, "http://localhost:9882/airline?wsdl", true)[0];

		// getting all ws operations
		Operation[] operations = iface.getAllOperations();
		System.out.println("Operations -----------------------------------------");
		for (Operation operation : operations)
			System.out.println(operation.getName());
		System.out.println("----------------------------------------------------");

		// get desired operation
		WsdlOperation operation = (WsdlOperation) iface
				.getOperationByName("getFlight");

		// create a new empty request for that operation
		WsdlRequest request = operation.addNewRequest("myRequest");

		// create request content
		String requestContent = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:trav=\"http://airline.ws.ime.usp.br/\">"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ "<trav:getFlight>"
				+ "<arg0>Milan</arg0>"
				+ "<arg1>12-21-2010</arg1>"
				+ "</trav:getFlight>"
				+ "</soapenv:Body>"
				+ "</soapenv:Envelope>";

		request.setRequestContent(requestContent);

		// submit the request
		WsdlSubmit<WsdlRequest> submit = request.submit(new WsdlSubmitContext(
				request), false);

		// wait for the response
		Response response = submit.getResponse();

		// print the response
		String content = response.getContentAsString();

		System.out.println(content);

		// turn off SoapUI
		SoapUI.shutdown();

	}
}