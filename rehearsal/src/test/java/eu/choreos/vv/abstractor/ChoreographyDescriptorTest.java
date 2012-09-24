package eu.choreos.vv.abstractor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChoreographyDescriptorTest {

	private static Choreography choreography;
	
	@BeforeClass
	public static void setUp() throws Exception{
		 choreography = Choreography.build("./resource/futureMarket.yml");
	}
	
	@Test
	public void shouldReceiveAValidDeploymentDescriptorDuringTheCreation() throws Exception {
		assertTrue(choreography.getRoles().size() > 0 );
	}
	
	@Test
	public void shouldRetrieveTheFirstRole() throws Exception{
		Role role = choreography.getRoles().get(0);
		assertEquals("supermarket", role.getName());
		assertEquals("file:///home/besson/workspace/futureMarket/roles/supermarket.wsdl", role.getContractUri());
	}

	@Test
	public void shouldRetrieveTheSecondRole() throws Exception {
		Role role = choreography.getRoles().get(1);
		assertEquals("shipper", role.getName());
		assertEquals("file:///home/besson/workspace/futureMarket/roles/shipper.wsdl", role.getContractUri());
	}
	
	@Test
	public void shouldRetrieveTheFirstServicePlayingTheSupermarketRole() throws Exception {
		List<Service> services = choreography.getServicesForRole("supermarket");
		Service service = services.get(0);
		
		assertEquals("supermarket", service.getRoles().get(0).getName());
		assertEquals("http://localhost:4321/SM1", service.getServicesForRole("supermarket").get(0).getUri());
		assertEquals("http://localhost:1234/smregistry", service.getServicesForRole("supermarket").get(1).getUri());
	}

	@Test
	public void shouldRetrieveTheSecondServicePlayingTheSupermarketRole() throws Exception {
		List<Service> services = choreography.getServicesForRole("supermarket");
		Service service = services.get(1);
		
		assertEquals("supermarket", service.getRoles().get(0).getName());
		assertEquals("http://localhost:4321/SM2", service.getServicesForRole("supermarket").get(0).getUri());
		assertEquals("http://localhost:1234/smregistry", service.getServicesForRole("supermarket").get(1).getUri());
	}
	
	@Test
	public void shouldRetrieveThreeServicesPlayingTheSupermarketRole() throws Exception {
		List<Service> services = choreography.getServicesForRole("supermarket");
		
		assertEquals(3, services.size());
	}
	
	@Test
	public void theParticipantsPropertyShouldBeOptional() throws Exception {
		Choreography choreography = Choreography.build("./resource/futureMarketSimpler.yml");
		List<Service> sms = choreography.getServicesForRole("supermarket");
		
		assertEquals("http://localhost:8084/petals/services/supermarket1", sms.get(0).getUri());
		assertEquals("http://localhost:8084/petals/services/supermarket2", sms.get(1).getUri());
		assertEquals("http://localhost:8084/petals/services/supermarket3", sms.get(2).getUri());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void shouldThrowAnExceptionWhenGetInexistentParticipants() throws Exception {
		Choreography choreography = Choreography.build("./resource/futureMarketSimpler.yml");
		List<Service> participants = choreography.getServicesForRole("supermarket").get(0).getParticipants();
		participants.get(0);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void shouldThrowAnExceptionWhenGetInexistentParticipantsForARole() throws Exception {
		Choreography choreography = Choreography.build("./resource/futureMarketSimpler.yml");
		List<Service> participants = choreography.getServicesForRole("supermarket").get(0).getServicesForRole("supermarket");
		participants.get(0);
	}
}