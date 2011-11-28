package eu.choreos.vv.abstractor;

import static eu.choreos.vv.assertions.RehearsalAsserts.assertRole;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.vv.compliance.FullRoleComplianceTest;
import eu.choreos.vv.compliance.NonTestCases;
import eu.choreos.vv.compliance.RoleComplianceWithFailuresTest;
import eu.choreos.vv.compliance.SimpleRoleComplianceTest;
import eu.choreos.vv.exceptions.NonJUnitTestCasesException;



public class ComplianceTest {

	private final String STORE_WSDL = "http://localhost:1234/simpleStore?wsdl";
	private final String ABC_STORE_WSDL = "file://" + System.getProperty("user.dir") + "/resource/simpleStore.wsdl";

	private Service abcStore;
	private Role store;
	
	@Before
	public void setUp(){
		store = new Role("store", STORE_WSDL);
		
		abcStore = new Service();
		abcStore.setWSDL(ABC_STORE_WSDL);
		abcStore.addRole(store);
	}
	
	@Test(expected=junit.framework.AssertionFailedError.class)
	public void shouldAssertionFailWhenTheRolesAreDifferent() throws Exception {
		String smWSDL = "file://" + System.getProperty("user.dir") + "/resource/SM3.wsdl";
		
		Role supermarket = new Role("supermarket", smWSDL);
		assertRole(supermarket, abcStore, null);
		
	}
	
	@Test(expected=NonJUnitTestCasesException.class)
	public void shouldThrowsAnExceptionlWhenThereAreNoJunitTestCases() throws Exception {
		assertRole(store, abcStore, NonTestCases.class);
	}
	
	@Test
	public void assertRoleShouldProvideTheServiceEndpointOfTestCases() throws Exception{
		assertRole(store, abcStore, SimpleRoleComplianceTest.class);
	}
	
	@Test
	public void assertRoleShouldSucceedWhenAllTestCasesSucceed() throws Exception{
		assertRole(store, abcStore, FullRoleComplianceTest.class);
	}
	
	@Test(expected=junit.framework.AssertionFailedError.class)
	public void assertRoleShouldNotSucceedWhenAtLeastOneTestCaseFailed() throws Exception{
		assertRole(store, abcStore, RoleComplianceWithFailuresTest.class);
	}

}
