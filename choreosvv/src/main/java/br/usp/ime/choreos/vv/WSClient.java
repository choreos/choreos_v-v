package br.usp.ime.choreos.vv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.xmlbeans.XmlException;

import br.usp.ime.choreos.vv.exceptions.FrameworkException;
import br.usp.ime.choreos.vv.exceptions.InvalidOperationNameException;
import br.usp.ime.choreos.vv.exceptions.ParserException;
import br.usp.ime.choreos.vv.exceptions.WSDLException;

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
 * Proxy to invoke and treat web services operations
 *
 */
public class WSClient {

	private enum Strategy {STRING, ITEM}; // used on request

	private final String wsdl;
	private WsdlInterface iface;
	private List<String> operations;

	/**
	 * 
	 * @param wsdl the path or URL to the WSDL document
	 * @throws XmlException
	 * @throws IOException
	 * @throws FrameworkException
	 * @throws WSDLException
	 */
	public WSClient(String wsdl) throws XmlException, IOException, FrameworkException, WSDLException {
		
			if (!verifyDomainAvailability(wsdl))
				throw new WSDLException("The url " + wsdl + " is not accessible");
		
		
		WsdlProject project;
		try {
			project = new WsdlProject();
		} catch (SoapUIException e) {
			throw new FrameworkException(e);
		}
		
		try {
			iface = WsdlInterfaceFactory.importWsdl(project, wsdl, true)[0];
		} catch (SoapUIException e) {
			throw new WSDLException(e);
		}
		
		parseWsdlOperations();

		this.wsdl = wsdl;
	}
	
	/**
	 * Retrieves the operations available in the service, using the SoapUI library
	 */
	private void parseWsdlOperations() {

		this.operations = new ArrayList<String>();

		for (com.eviware.soapui.model.iface.Operation suop : this.iface.getAllOperations()) {
			String op = suop.getName();
			this.operations.add(op);
		}
	}

	List<String> getOperations() {
		return this.operations;
	}

	/**
	 * 
	 * @return the WSDL URL
	 */
	public String getWsdl() {
		return wsdl;
	}

	/**
	 * Makes the request using String parameters
	 * 
	 * @param operationName Name of the operation to invoke
	 * @param arguments Variable number of string arguments to use in the request.
	 * The arguments must be ordered according to the request wsdl. For complex types with
	 * lots of arguments, it's better to use {@link #request(String, Item)}
	 * @return An <code>Item</code> representing the request response.
	 * @throws InvalidOperationNameException
	 * @throws FrameworkException
	 */
	public Item request(String operationName, String... arguments) throws InvalidOperationNameException, FrameworkException  {
		
		return makeRequest(operationName, Strategy.STRING, null, arguments);
	}

	/**
	 * Makes the request using an Item as argument
	 * 
	 * @param operationName Name of the operation to invoke
	 * @param requestRoot An Item representing the root of a tree
	 * that represents the structure of the complex type arguments.
	 * @return An <code>Item</code> representing the request response.
	 * @throws InvalidOperationNameException
	 * @throws FrameworkException
	 */
	public Item request(String operationName, Item requestRoot) throws InvalidOperationNameException, FrameworkException  {
		
		return makeRequest(operationName, Strategy.ITEM, requestRoot);
	}	

	/**
	 * Makes the actual request to the web service. It uses a strategy passed as a parameter
	 * to determine which type of arguments to use to generate the final SOAP request envelope.
	 * 
	 * @param operationName
	 * @param strategy  
	 * @param requestRoot
	 * @param parameters
	 * @return
	 * @throws FrameworkException
	 * @throws ParserException
	 * @throws InvalidOperationNameException
	 */
	private Item makeRequest(String operationName, Strategy strategy, Item requestRoot, String... parameters)
			throws FrameworkException, ParserException, InvalidOperationNameException {
		
		if (!operations.contains(operationName))
			throw new InvalidOperationNameException();

		WsdlOperation operation = (WsdlOperation) iface.getOperationByName(operationName);
		String defaultRequestContent = operation.getRequestAt(0).getRequestContent();
		
		
		String requestContent = null;
		if (strategy == Strategy.STRING) {
			requestContent = SoapEnvelopeHelper.generate(defaultRequestContent, parameters);
		} else if (strategy == Strategy.ITEM) {
			requestContent = new RequestBuilder().buildRequest(defaultRequestContent, requestRoot); 
		}
		
		WsdlRequest request = operation.addNewRequest("myRequest");
		request.setRequestContent(requestContent);

		// submit the request
		WsdlSubmit<WsdlRequest> submit = null;
		try {
			submit = request.submit(new WsdlSubmitContext(request), false);
		} catch (SubmitException e) {
			throw new FrameworkException(e);
		}

		// wait for the response
		Response response = submit.getResponse();

		// print the response
		String responseContent = response.getContentAsString();

		
		ResponseParser parser = new ResponseParser();
		
		System.out.println(responseContent);
		return parser.parse(responseContent) ;
	}
	
	
	private boolean verifyDomainAvailability(String url) throws HttpException, IOException{
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
				    		new DefaultHttpMethodRetryHandler(3, false));
		
		int  statusCode = client.executeMethod(method); 
				
                method.releaseConnection();
                 
		return (statusCode == HttpStatus.SC_OK);
	}
	

}
