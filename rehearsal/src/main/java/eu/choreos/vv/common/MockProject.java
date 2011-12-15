package eu.choreos.vv.common;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockService;
import com.eviware.soapui.support.SoapUIException;

import eu.choreos.vv.exceptions.MockDeploymentException;
import eu.choreos.vv.exceptions.WSDLException;

/**
 * This class provides the common properties of WSMock and WSProxy
 * 
 * @author Felipe Besson
 *
 */
public class MockProject {
	
		private String port;
		private String name;
		private String hostName;
		public WsdlInterface iface;
		public WsdlMockService service;

		/**
		 * Creates a Mock object that can be a WSMock or a WSProxy in our domain
		 * 
		 * @param name (address) in which the mock will be published
		 * @param wsdl of the mocked (real) service
		 * @throws Exception
		 */
		public MockProject(String name, String wsdl) throws Exception {
			this.name = name;
			port = "8088";
			hostName = "localhost";

			buildWsdlPrject(name, wsdl);
		}

		/**
		 * Calls the SoapUI features for creating the mock object
		 * 
		 * @param name (address) in which the mock will be published
		 * @param wsdl of the mocked (real) service
		 * @throws Exception
		 */
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

		/**
		 * Retrieves the port where the mocked service will be published
		 * 
		 * @return the port number
		 */
		public String getPort() {
			return port;
		}

		/**
		 * Retrieves the WSDL URI where the mocked service will be published
		 * 
		 * @return the WSDL URI
		 */
		public String getWsdl() {
			return "http://" + hostName + ":" + port + "/" + name + "?wsdl";
		}

		/**
		 * Retrieves the host name where themocked service will be published
		 * 
		 * @return the host name
		 */
		public String getHostName() {
			return hostName;
		}

		/**
		 * Sets the port number where the mocked service will be published
		 * 
		 * @param port
		 */
		public void setPort(String port) {
			service.setPort(Integer.parseInt(port));
			this.port = port;
		}

		/**
		 * Sets the host name where the mocked service will be published
		 * 
		 * @param hostName
		 */
		public void setHostName(String hostName) {
			this.hostName = hostName;
		}

		/**
		 * Deploy and publish the mocked service
		 * 
		 * @throws MockDeploymentException
		 */
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

		/**
		 * Undeploy the mocked service
		 * 
		 */
		public void stop() {
			service.getMockRunner().stop();
		}


}
