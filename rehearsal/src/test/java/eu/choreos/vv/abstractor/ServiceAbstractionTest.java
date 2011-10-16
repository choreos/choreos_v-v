package eu.choreos.vv.abstractor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ServiceAbstractionTest {
	
	private final static String STORE_WSDL = "http://localhost:1234/simpleStore?wsdl";
	private final static String GLOBAL_RECORDS_WSDL = "file://" + System.getProperty("user.dir") + "/resource/simpleStore.wsdl";

	private static Service globalRecords;
	private static Role store;
	
	@Before
	public  void setUp() throws Exception{
		store = new Role("store", STORE_WSDL);
		
		globalRecords = new Service();
		globalRecords.setWSDL(GLOBAL_RECORDS_WSDL);
		globalRecords.addRole(store);
	}
		
	@Test
	public void serviceShouldReturnAllRolesPlayed() throws Exception {
		Role producer = new Role("producer", "a service interface");
		globalRecords.addRole(producer);
		
		assertTrue(globalRecords.getRoles().contains(store));
		assertTrue(globalRecords.getRoles().contains(producer));
	}
	
	@Test
	public void shouldAssignAnInternalRoleNameForAnInternalService() throws Exception {
		Service paymentWS = new Service();
		paymentWS.setWSDL("payment service wsdl");
		
		globalRecords.addService(paymentWS, store);
		
		List<Service> internalServices = globalRecords.getInternalServicesForRole(store);
		
		String internalRoleName = internalServices.get(0).getRoles().get(0).getName();
		String internalRoleWSDL = internalServices.get(0).getRoles().get(0).getWsdl();
		
		assertEquals("store0", internalRoleName);
		assertEquals("", internalRoleWSDL);

	}
	
	@Test
	public void shouldAssignAnInternalRoleNameForAllInternalServices() throws Exception {
		Service paymentWS = new Service();
		paymentWS.setWSDL("payment service wsdl");
		globalRecords.addService(paymentWS, store);
		
		
		Service customerSupport =new Service();
		customerSupport.setWSDL("customer support wsdl");
		globalRecords.addService(customerSupport, store);
		
		List<Service> internalServices = globalRecords.getInternalServicesForRole(store);
		
		String internalServiceWSDL = internalServices.get(0).getWSDL();
		String internalRoleName = internalServices.get(0).getRoles().get(0).getName();
		assertEquals("payment service wsdl", internalServiceWSDL);
		assertEquals("store0", internalRoleName);
		
		internalServiceWSDL = internalServices.get(1).getWSDL();
		internalRoleName = internalServices.get(1).getRoles().get(0).getName();
		assertEquals("customer support wsdl", internalServiceWSDL);
		assertEquals("store1", internalRoleName);
		

	}


}
