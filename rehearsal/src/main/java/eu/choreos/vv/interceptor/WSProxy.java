package eu.choreos.vv.interceptor;

import java.util.ArrayList;
import java.util.List;

import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockResponse;

import eu.choreos.vv.common.MockProject;

public class WSProxy extends MockProject {
	
	private String realWsdl;
	
	public WSProxy(String uri, String realWsdl) throws Exception {
		super(uri+ "Proxy", realWsdl);
		this.realWsdl = realWsdl;
		addOperationsToProxy();
	}
	
	private void addOperationsToProxy(){
		for (int i=0; i < iface.getOperationCount(); i++){
			WsdlMockOperation mockOperation = service.addNewMockOperation(iface.getOperationAt(i));
			mockOperation.setDispatchStyle("SCRIPT");
			
			String responseContent = "${message}";
			String script = getScript(mockOperation.getName());
			
			WsdlMockResponse response = mockOperation.addNewMockResponse( "Response 1", true );
			response.setScript(script);
			response.setResponseContent(responseContent);
		}
	}
	
	public List<String> getOperationNames() {
		List<String> operationNames =new ArrayList<String>();
		
		for (WsdlOperation operation : service.getMockedOperations()) 
			operationNames.add(operation.getName());
		
		return operationNames;
	}

	private String getScript(String operationName) {
		String script = "context.message =  eu.choreos.vv.interceptor.RequestDispatcher.getResponse ('" + realWsdl + "','" +
										operationName + "', mockRequest.requestContent)";
		
		return script;
	}

	public String getRealWsdl() {
		return realWsdl;
	}
	
	public String getProxyWsdl(){
		return super.getWSDL();
	}
	
}
