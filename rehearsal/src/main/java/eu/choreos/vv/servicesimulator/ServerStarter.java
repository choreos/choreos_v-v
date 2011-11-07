package eu.choreos.vv.servicesimulator;

import com.eviware.soapui.impl.wsdl.mock.WsdlMockService;

public class ServerStarter implements Runnable{

	private WsdlMockService service;
	
	public ServerStarter(WsdlMockService service){
		this.service = service;
	}
	
	@Override
	public void run() {
		try {
			service.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
