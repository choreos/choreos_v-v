package eu.choreos.vv.abstractor;

import java.util.ArrayList;
import java.util.List;

public class Service {

	private String WSDL;
	private List<Role> roles;
	
	public Service(){
		roles = new ArrayList<Role>();
	}

	public String getWSDL() {
	        return WSDL;
        }

	public void setWSDL(String wSDL) {
	        WSDL = wSDL;
        } 
	
	public void addRole(Role role){
		roles.add(role);
	}
	
	public void removeRole(Role role){
		roles.remove(role);
	}
	
	public List<Role> getRoles(){
		return roles;
	}

	


}
