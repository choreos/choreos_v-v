package eu.choreos.vv.clientgenerator;

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
import com.eviware.soapui.model.iface.Request.SubmitException;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.support.SoapUIException;

import eu.choreos.vv.common.HttpUtils;
import eu.choreos.vv.common.ItemBuilder;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.ParserException;
import eu.choreos.vv.exceptions.WSDLException;

/**
 * Proxy to invoke and treat web services operations
 *
 */
public class WSClient {

	private enum Strategy {STRING, ITEM}; // used on request

	private final String wsdl;
	private WsdlInterface iface;
	private List<String> operations;
	private String endpoint;
	private boolean remoteEndpoint = false;
	private int timeout;
	
	/**
	 * 
	 * @param wsdl the path or URL to the WSDL document
	 * @throws XmlException
	 * @throws IOException
	 * @throws FrameworkException
	 * @throws WSDLException
	 */
	public WSClient(String wsdl) throws XmlException, IOException, FrameworkException, WSDLException {
		
			if (!wsdl.startsWith("file") &&  !HttpUtils.verifyIfUriReturns0kforGET(wsdl))
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
		timeout = 30000;
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

	public List<String> getOperations() {
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
			requestContent = new ItemBuilder().buildItem(defaultRequestContent, requestRoot); 
		}
		
		WsdlRequest request = operation.addNewRequest("myRequest");
		request.setRequestContent(requestContent);
		request.setTimeout(((Integer)timeout).toString());
		
		if(remoteEndpoint)
			request.setEndpoint(endpoint);

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

		
		ItemParser parser = new ItemParser();
		
		System.out.println(responseContent);
		return parser.parse(responseContent) ;
	}

	public String getEndpoint() {
	        return endpoint;
        }
	
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	        remoteEndpoint = true;
        }

	public void setResponseTimeout(int timeout) {
		this.timeout = timeout;
	}
}
