package eu.choreos.vv.abstractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Service {

	private String WSDL;
	private HashMap<Role, List<Service>> internalServices;
	
	public Service(){
		internalServices = new HashMap<Role, List<Service>>();
	}

	public String getWSDL() {
	        return WSDL;
        }

	public void setWSDL(String wSDL) {
	        WSDL = wSDL;
        } 
	
	public void addRole(Role role){
		internalServices.put(role, new ArrayList<Service>());
	}
	
	public void removeRole(Role role){
		internalServices.remove(role);
	}
	
	public List<Role> getRoles(){
		Set<Role> roleKeys =  internalServices.keySet();
		ArrayList<Role> roles = new ArrayList<Role>();
		
		for (Role role : roleKeys) 
	                roles.add(role);
                
		return roles;
	}

	public void addService(Service internalService, Role role) {
		int index = internalServices.get(role).size();
		Role internalRole = new Role(role.getName() + index, "");
		
		internalService.addRole(internalRole);
		internalServices.get(role).add(internalService);
        }

	public List<Service> getInternalServicesForRole(Role role) {
	        return internalServices.get(role);
        }

}
