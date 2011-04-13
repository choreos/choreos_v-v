package br.usp.ime.choreos.vv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.XmlException;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.support.SoapUIException;

public class WSClient {

	private final String wsdl;
	private WsdlInterface iface;
	private List<String> operations;

	public WSClient(String wsdl) throws XmlException, IOException,
			SoapUIException {
		WsdlProject project = new WsdlProject();
		iface = WsdlInterfaceFactory.importWsdl(project, wsdl, true)[0];
		parseWsdlOperations();

		this.wsdl = wsdl;
	}

	private void parseWsdlOperations() {
		this.operations = new ArrayList<String>();
		for (com.eviware.soapui.model.iface.Operation suop : this.iface
				.getAllOperations()) {

			String op = suop.getName();

			this.operations.add(op);
		}
	}

	List<String> getOperations() {
		return this.operations;
	}

	public String getWsdl() {
		return wsdl;
	}

	public String request(String operationName, String... parameters)
			throws Exception {
		if (!operations.contains(operationName))
			throw new InvalidOperationName();

		WsdlOperation operation = (WsdlOperation) iface
				.getOperationByName(operationName);

		String defaultRequestContent = operation.getRequestAt(0)
				.getRequestContent();

		String requestContent = SoapEnvelopeHelper.generate(
				defaultRequestContent, parameters);

		WsdlRequest request = operation.addNewRequest("myRequest");
		request.setRequestContent(requestContent);

		// submit the request
		WsdlSubmit<WsdlRequest> submit = request.submit(new WsdlSubmitContext(
				request), false);

		// wait for the response
		Response response = submit.getResponse();

		// print the response
		String responseContent = response.getContentAsString();

		responseContent = SoapEnvelopeHelper.getCleanResponse(responseContent);
		
		System.out.println(responseContent);

		return responseContent;
	}

}
