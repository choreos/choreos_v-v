package eu.choreos.vv.abstractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.xmlbeans.XmlException;

import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.WSDLException;

/**
 *  * This class represents the service object and belongs to the Abstraction Choreography feature
 * 
 * @author besson
 *
 */
public class Service {

	private String WSDL;
	protected HashMap<String, Role> roles;
	protected HashMap<String, List<Service>> internalServices;
	private WSClient serviceClient;
	
	public Service(){
		roles = new HashMap<String, Role>();
		internalServices = new HashMap<String, List<Service>>();
	}

	public String getWSDL() {
	        return WSDL;
        }

	public void setWSDL(String wSDL) {
	        this.WSDL = wSDL;
        } 
	
	public void addRole(Role role){
		roles.put(role.getName(), role);
		internalServices.put(role.getName(), new ArrayList<Service>());
	}
	
	public void removeRole(String role){
		internalServices.remove(role);
	}
	
	public List<Role> getRoles(){
		ArrayList<Role> roleEntries = new ArrayList<Role>();
		
		for (Entry<String, Role> entry : roles.entrySet()) 
			roleEntries.add(entry.getValue());
			
		return roleEntries;
	}

	public void addService(Service service, String roleName) {
		int index = internalServices.get(roleName).size();
		Role internalRole = new Role(roleName + index, "");
		
		service.addRole(internalRole);
		internalServices.get(roleName).add(service);
        }

	public List<Service> getServicesForRole(String roleName) {
	        return internalServices.get(roleName);
        }

	public WSClient getWSClient() {
	        
	        try {
	    				serviceClient = new WSClient (WSDL);
	    			} catch (WSDLException e) {
	    				e.printStackTrace();
	    			} catch (XmlException e) {
	    				e.printStackTrace();
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			} catch (FrameworkException e) {
	    				e.printStackTrace();
	    			}
	
	return serviceClient;
	}
}
