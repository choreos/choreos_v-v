package eu.choreos.vv.abstractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Service {

	private String WSDL;
	protected HashMap<String, Role> roles;
	protected HashMap<String, List<Service>> internalServices;
	
	public Service(){
		roles = new HashMap<String, Role>();
		internalServices = new HashMap<String, List<Service>>();
	}

	public String getWSDL() {
	        return WSDL;
        }

	public void setWSDL(String wSDL) {
	        WSDL = wSDL;
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

}
