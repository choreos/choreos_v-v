package eu.choreos.vv.abstractor;


public class Choreography extends Service{

	@Override
	public void addService(Service internalService, String roleName) {
		internalServices.get(roleName).add(internalService);
        }

}
