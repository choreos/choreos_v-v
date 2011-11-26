package eu.choreos.vv.common;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockService;
import com.eviware.soapui.support.SoapUIException;

import eu.choreos.vv.exceptions.MockDeploymentException;
import eu.choreos.vv.exceptions.WSDLException;

public class MockProject {
	
		private String port;
		private String name;
		private String hostName;
		public WsdlInterface iface;
		public WsdlMockService service;

		public MockProject(String name, String wsdl) throws Exception {
			this.name = name;
			port = "8088";
			hostName = "localhost";

			buildWsdlPrject(name, wsdl);
		}

		private void buildWsdlPrject(String name, String wsdl) throws Exception {
			try {
				WsdlProject project = new WsdlProject();
				iface = WsdlInterfaceFactory.importWsdl(project, wsdl, true)[0];

				service = project.addNewMockService(name);
				service.setPort(Integer.parseInt(port));
			} catch (SoapUIException e) {
				throw new WSDLException(e);
			}
		}

		public String getPort() {
			return port;
		}

		public String getWSDL() {
			return "http://" + hostName + ":" + port + "/" + name + "?wsdl";
		}

		public String getHostName() {
			return hostName;
		}

		public void setPort(String port) {
			service.setPort(Integer.parseInt(port));
			this.port = port;
		}

		public void setHostName(String hostName) {
			this.hostName = hostName;
		}

		public void start() throws MockDeploymentException {
			iface.addEndpoint(service.getLocalEndpoint());

			try {
				if (HttpUtils.UriAreUsed("http://" + hostName + ":" + port))
					throw new MockDeploymentException("Address already in use");

				service.start();

			} catch (Exception e) {
				throw new MockDeploymentException(e);
			}
		}

		public void stop() {
			service.getMockRunner().stop();
		}


}
