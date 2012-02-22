package eu.choreos.vv.clientgenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.xmlbeans.XmlException;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockResponse;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockService;
import com.eviware.soapui.model.iface.Request.SubmitException;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.support.SoapUIException;

import eu.choreos.vv.common.HttpUtils;
import eu.choreos.vv.common.ItemBuilder;
import eu.choreos.vv.exceptions.EmptyRequetItemException;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.ParserException;
import eu.choreos.vv.exceptions.WSDLException;

/**
 * Dynamic client  to invoke and treat web service requests
 *
 * Felipe Besson, Pedro Leal, Leonardo Leite, Lucas Piva, Guilherme Nogueira
 */
public class WSClient {

	private enum Strategy {STRING, ITEM}; // used on request

	private final String wsdl;
	private WsdlInterface iface;
	private List<String> operations;
	private String endpoint;
	private boolean remoteEndpoint = false;
	private int timeout;
	private WsdlOperation operation;
	
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
	
	
	private String getDefaultRequestContent(String operationName) throws InvalidOperationNameException{
		if (!operations.contains(operationName))
			throw new InvalidOperationNameException();

		operation = (WsdlOperation) iface.getOperationByName(operationName);
		return  operation.getRequestAt(0).getRequestContent();
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
		
		String defaultRequestContent = getDefaultRequestContent(operationName);
		
		
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

	public Item getItemRequestFor(String operationName) throws InvalidOperationNameException, ParserException, EmptyRequetItemException {
		String defaultRequestContent = getDefaultRequestContent(operationName);
		Item item=  new ItemParser().parse(defaultRequestContent);
		
		if(item.getChildren().size() == 0)
			throw new EmptyRequetItemException("This operation has no parameter");
		
		return item;
	}

	public Item getItemResponseFor(String operationName) throws ParserException, XmlException, IOException, SoapUIException {
		WsdlProject project = new WsdlProject();
		iface = WsdlInterfaceFactory.importWsdl(project, wsdl, true)[0];

		WsdlMockService service = project.addNewMockService("name");
		WsdlMockOperation mockOperation = service.addNewMockOperation(iface.getOperationByName(operationName));
		WsdlMockResponse response = mockOperation.addNewMockResponse( "Response 1", true );
		
		String responseContent = response.getResponseContent();
		
		return new ItemParser().parse(responseContent);
	}
}
