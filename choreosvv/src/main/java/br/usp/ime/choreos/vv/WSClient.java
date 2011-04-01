package br.usp.ime.choreos.vv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.XmlException;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.support.SoapUIException;

public class WSClient {

	private final String wsdl;
	private WsdlInterface iface;
	private List<Operation> operations;
	
	public WSClient(String wsdl) throws XmlException, IOException, SoapUIException  {
		WsdlProject project = new WsdlProject();
		iface = WsdlInterfaceFactory.importWsdl(project, wsdl, true)[0];
		parseWsdlOperations();
		
		this.wsdl = wsdl;
	}

	private void parseWsdlOperations() {
		this.operations = new ArrayList<Operation>();
		for (com.eviware.soapui.model.iface.Operation suop: this.iface.getAllOperations()) {
			Operation op = new Operation(suop.getName(), 1);
			this.operations.add(op);
		}
	}

	public List<Operation> getOperations() {
		return this.operations;
	}
	

	public String getWsdl() {
		return wsdl;
	}

	public Operation getOperationByName(String name) {
		for (Operation op: this.operations){
			if (op.getName().equals(name)){
				return op;
			}	
		}
		return null;
	}

}
