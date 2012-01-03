package eu.choreos.vv.abstractor;

import java.io.FileNotFoundException;

/**
 * This class represents the choreography object and belongs to the Abstraction Choreography feature
 * 
 * @author Felipe Besson
 *
 */
public class Choreography extends Service{
	

	/**
	 * Receive a choreography deployment descriptor
	 * 
	 * @param descriptorPath
	 * @throws FileNotFoundException 
	 */
	public static Choreography build(String descriptorPath) throws FileNotFoundException {
		return DeploymentDescriptor.buildChoreography(descriptorPath);
	}
	
	public Choreography( ){}

	@Override
	public void addService(Service internalService, String roleName) {
		participants.get(roleName).add(internalService);
        }
	
}
