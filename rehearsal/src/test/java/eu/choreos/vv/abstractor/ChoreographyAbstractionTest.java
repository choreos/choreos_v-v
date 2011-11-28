package eu.choreos.vv.abstractor;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChoreographyAbstractionTest {

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
		carrefutur.setWSDL("http://localhost:8080/petals/services/carrefutur?wsdl");
		carrefutur.addRole(supermarket);

		Service registry = new Service();
		registry.setWSDL("http://localhost:8080/petals/services/registry?wsdl");
		carrefutur.addService(registry, "supermarket");

		Service carefuturWS = new Service();
		carefuturWS.setWSDL("http://localhost:8080/petals/services/carrefuturWS?wsdl");
		carrefutur.addService(carefuturWS, "supermarket");

		futureMarket.addService(carrefutur, "supermarket");
	}

	@Test
	public void shouldGetAllServicesOfARole() throws Exception {
		Service futureMart = new Service();
		futureMart.setWSDL("http://localhost:8080/petals/services/futureMart?wsdl");
		futureMart.addRole(supermarket);
		futureMarket.addService(futureMart, "supermarket");

		List<Service> services = futureMarket.getServicesForRole("supermarket");

		String carrefuturWSDL = services.get(0).getWSDL();
		String futureMartWSDL = services.get(1).getWSDL();

		assertEquals("http://localhost:8080/petals/services/carrefutur?wsdl",
		                                carrefuturWSDL);
		assertEquals("http://localhost:8080/petals/services/futureMart?wsdl",
		                                futureMartWSDL);
	}

	@Test
	public void shouldServicesKeepTheRoleName() throws Exception {
		List<Service> services = futureMarket.getServicesForRole("supermarket");
		String roleName = services.get(0).getRoles().get(0).getName();
		
		assertEquals("supermarket", roleName);
		
	}
}
