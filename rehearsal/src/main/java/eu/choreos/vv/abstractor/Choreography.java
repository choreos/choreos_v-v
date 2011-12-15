package eu.choreos.vv.abstractor;

/**
 * This class represents the choreography object and belongs to the Abstraction Choreography feature
 * 
 * @author Felipe Besson
 *
 */
public class Choreography extends Service{

	@Override
	public void addService(Service internalService, String roleName) {
		internalServices.get(roleName).add(internalService);
        }

}
