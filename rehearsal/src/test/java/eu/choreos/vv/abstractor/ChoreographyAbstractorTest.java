package eu.choreos.vv.abstractor;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChoreographyAbstractorTest {

	private static Choreography futureMarket;
	private static Role supermarket;

	@BeforeClass
	public static void manualEnactment() throws Exception {
		futureMarket = new Choreography();

		// creating roles
		String smInterface = "http://localhost/supermarketRole.wsdl";
		String shipperInterface = "http://localhost/shipperRole.wsdl";

		supermarket = new Role("supermarket", smInterface);
		Role shipper = new Role("shipper", shipperInterface);

		futureMarket.addRole(supermarket);
		futureMarket.addRole(shipper);

		// creating services
		Service carrefutur = new Service();
		carrefutur.addRole(supermarket);
		carrefutur.setUri("http://localhost:8080/petals/services/carrefutur");
		carrefutur.addRole(supermarket);

		Service registry = new Service();
		registry.setUri("http://localhost:8080/petals/services/registry");
		carrefutur.addService(registry, "supermarket");

		Service carefuturWS = new Service();
		carefuturWS.setUri("http://localhost:8080/petals/services/carrefuturWS");
		carrefutur.addService(carefuturWS, "supermarket");

		futureMarket.addService(carrefutur, "supermarket");
	}

	@Test
	public void shouldGetAllServicesOfARole() throws Exception {
		Service futureMart = new Service();
		futureMart.setUri("http://localhost:8080/petals/services/futureMart");
		futureMart.addRole(supermarket);
		futureMarket.addService(futureMart, "supermarket");

		List<Service> services = futureMarket.getServicesForRole("supermarket");

		String carrefuturWSDL = services.get(0).getUri();
		String futureMartWSDL = services.get(1).getUri();

		assertEquals("http://localhost:8080/petals/services/carrefutur",
		                                carrefuturWSDL);
		assertEquals("http://localhost:8080/petals/services/futureMart",
		                                futureMartWSDL);
	}

	@Test
	public void shouldServicesKeepTheRoleName() throws Exception {
		List<Service> services = futureMarket.getServicesForRole("supermarket");
		String roleName = services.get(0).getRoles().get(0).getName();
		
		assertEquals("supermarket", roleName);
		
	}
	
	@Test
	public void shouldReturnAllServiceParticipantsIndependentOfTheirRoles() throws Exception {
		Service sm1 = futureMarket.getServicesForRole("supermarket").get(0);
			
		Role aRole = new Role("aRole", "http://localhost:1234/aRole");
		sm1.addRole(aRole);
			
		Service aService = new Service();
		aService.setUri("http://localhost:1234/aService");
		sm1.addService(aService, "aRole");
		List<Service> participants = sm1.getParticipants();
		
		assertEquals("http://localhost:8080/petals/services/registry", participants.get(0).getUri());
		assertEquals("http://localhost:1234/aService", participants.get(2).getUri());
	}
	
	@Test
	public void shouldReturnAllChoreographyParticipantsIndependentOfTheirRoles() throws Exception {
		Choreography choreography = Choreography.build("./resource/futureMarket.yml");
		List<Service> services = choreography.getParticipants();
		
		assertEquals("http://localhost:8084/petals/services/customer", services.get(4).getUri());
		assertEquals("http://localhost:8084/petals/services/shipper1", services.get(3).getUri());			
	}
	
}
