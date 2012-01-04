package eu.choreos.vv.abstractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * This Java Bean class represents the YAML constructor model 
 * 
 * @author Felipe Besson
 *
 */
public class ChoreographyDescriptor {

	private List<RoleEntry> roles;
	private List<ServiceEntry> services;

	public List<RoleEntry> getRoles() {
		return roles;
	}
	
	public void setRoles(List<RoleEntry> roles) {
		this.roles = roles;
	}
	
	/**
	 * Parses the yaml received and map it to a choreography object
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Choreography buildChoreography(String path) throws FileNotFoundException{
		Yaml yaml = new Yaml(new Constructor(ChoreographyDescriptor.class));
		InputStream input = new FileInputStream(new File(path));
		ChoreographyDescriptor descriptor = (ChoreographyDescriptor) yaml.load(input);
		
		Choreography choreography = new Choreography();
		
		buildChoreographyRoles(choreography, descriptor);
		buildChoreographyServices(choreography, descriptor);
		
		return choreography;
	}
	
	/**
	 * Adds the choreography services into a choreography object
	 * 
	 * @param choreography
	 * @param descriptor
	 */
	private static void buildChoreographyServices(Choreography choreography, ChoreographyDescriptor descriptor) {
		List<ServiceEntry> serviceEntries = descriptor.getServices();
		 
		for (ServiceEntry serviceEntry : serviceEntries) {
			Service service = new Service();
			service.setUri(serviceEntry.getUri());
			service.addRole(serviceEntry.getRole());
			
			String roleName = serviceEntry.getRole().getName();
					
			for(ServiceEntry internalEntry : serviceEntry.getParticipants()){
				Service internalService = new Service();
				internalService.setUri(internalEntry.getUri());
				service.addService(internalService, roleName);
			}
			
			choreography.addService(service, roleName);			
		}
		 
	}

	/**
	 * Adds the choreography roles into a choreography object
	 * 
	 * @param choreography
	 * @param descriptor
	 */
	private static void buildChoreographyRoles(Choreography choreography, ChoreographyDescriptor descriptor){
		List<RoleEntry> roleEntries = descriptor.getRoles();
		
		for (RoleEntry entry : roleEntries) 
			choreography.addRole(entry.getRole());
	}

	public List<ServiceEntry> getServices() {
		return services;
	}

	public void setServices(List<ServiceEntry> services) {
		this.services = services;
	}
}
