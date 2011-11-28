package eu.choreos.vv.abstractor;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.util.WebServiceController;

public class ServiceAbstractionTest {
	
	private final static String STORE_WSDL = "http://localhost:1234/simpleStore?wsdl";
	private final static String GLOBAL_RECORDS_WSDL = "file://" + System.getProperty("user.dir") + "/resource/simpleStore.wsdl";

	private static Service globalRecords;
	private static Role store;
	
	@BeforeClass
	public static void deployService() throws Exception {
		WebServiceController.deployService();
	}
	
	@AfterClass
	public static void undeplyService(){
		WebServiceController.undeployService();
	}
	
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
		paymentWS.setWSDL("file://" + System.getProperty("user.dir") + "/resource/SM3.wsdl");
		
		globalRecords.addService(paymentWS, "store");
		
		List<Service> internalServices = globalRecords.getServicesForRole("store");
		
		String internalRoleName = internalServices.get(0).getRoles().get(0).getName();
		String internalRoleWSDL = internalServices.get(0).getRoles().get(0).getWsdl();
		
		assertEquals("store0", internalRoleName);
		assertEquals("", internalRoleWSDL);

	}
	
	@Test
	public void shouldAssignAnInternalRoleNameForAllInternalServices() throws Exception {
		String PAYMENT_WS_WSDL = "file://" + System.getProperty("user.dir") + "/resource/SM3.wsdl";
		String CUSTOMER_WS_WSDL = "file://" + System.getProperty("user.dir") + "/resource/SM4.wsdl";
		
		Service paymentWS = new Service();
		paymentWS.setWSDL(PAYMENT_WS_WSDL);
		globalRecords.addService(paymentWS, "store");
		
		
		Service customerSupport =new Service();
		customerSupport.setWSDL(CUSTOMER_WS_WSDL);
		globalRecords.addService(customerSupport, "store");
		
		List<Service> internalServices = globalRecords.getServicesForRole("store");
		
		String internalServiceWSDL = internalServices.get(0).getWSDL();
		String internalRoleName = internalServices.get(0).getRoles().get(0).getName();
		assertEquals(PAYMENT_WS_WSDL, internalServiceWSDL);
		assertEquals("store0", internalRoleName);
		
		internalServiceWSDL = internalServices.get(1).getWSDL();
		internalRoleName = internalServices.get(1).getRoles().get(0).getName();
		assertEquals(CUSTOMER_WS_WSDL, internalServiceWSDL);
		assertEquals("store1", internalRoleName);

	}
	
	@Test
	public void throughTheServiceClientShouldBePossibleToRetrieveTheOperationNames() throws Exception {
	        WSClient storeClient = globalRecords.getWSClient();
	        
	        List<String> operationNames = storeClient.getOperations();
	        
	        assertTrue(operationNames.contains("searchByTitle"));
	        
    }
	
	@Test
	public void throughTheServiceClientShouldBePossibleToInvokeOperations() throws Exception {
			WSClient storeClient = globalRecords.getWSClient();
			
			Item cd = storeClient.request("searchByArtist", "Floyd");
			assertEquals("The dark side of the moon;", cd.getChild("return").getContent());	
	}


}
