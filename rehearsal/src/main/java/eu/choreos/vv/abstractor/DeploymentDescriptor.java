package eu.choreos.vv.abstractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class DeploymentDescriptor {

	private List<RoleEntry> roles;
	private List<ServiceEntry> services;

	public List<RoleEntry> getRoles() {
		return roles;
	}
	
	public void setRoles(List<RoleEntry> roles) {
		this.roles = roles;
	}
	
	public static Choreography buildChoreography(String path) throws FileNotFoundException{
		Yaml yaml = new Yaml(new Constructor(DeploymentDescriptor.class));
		InputStream input = new FileInputStream(new File(path));
		DeploymentDescriptor descriptor = (DeploymentDescriptor) yaml.load(input);
		
		Choreography choreography = new Choreography();
		
		buildChoreographyRoles(choreography, descriptor);
		buildChoreographyServices(choreography, descriptor);
		
		return choreography;
	}
	
	private static void buildChoreographyServices(Choreography choreography, DeploymentDescriptor descriptor) {
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

	private static void buildChoreographyRoles(Choreography choreography, DeploymentDescriptor descriptor){
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
