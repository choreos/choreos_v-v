package br.usp.ime.labxp.soapUIStudy;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlProject;

public class Launcher  {

	public static void main (String args[]){
		try {
			
			WsdlProject project = new WsdlProject();
			WsdlInterface iface = WsdlInterfaceFactory.importWsdl( project, "http://localhost:9882/airline?wsdl", true )[0];
			Request t1= new Request(iface);
	
			t1.run();
			t1.run();
			t1.run();
			
			SoapUI.shutdown();	
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
