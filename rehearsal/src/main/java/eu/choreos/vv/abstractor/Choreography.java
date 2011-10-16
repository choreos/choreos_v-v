package eu.choreos.vv.abstractor;


public class Choreography extends Service{

	@Override
	public void addService(Service internalService, Role role) {
		internalServices.get(role).add(internalService);
        }

}
