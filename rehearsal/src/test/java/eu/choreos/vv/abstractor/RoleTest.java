package eu.choreos.vv.abstractor;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.vv.exceptions.NonJUnitTestCasesException;

public class RoleTest {

	private final String STORE_WSDL="http://localhost:1234/simpleStore?wsdl";
	
	Role store;
	
	@Before
	public void setUp(){
		store = new Role("store", STORE_WSDL);
	}

	@Test
	public void shouldOnlyAcceptJUnitTestCasesAsConformanceTests() throws Exception {
		assertTrue(Role.isCompatibleWithTests(StoreRoleTest.class));
	}
	
	@Test (expected=NonJUnitTestCasesException.class)
	public void shouldNotAcceptNonJUnitTestCases() throws Exception {
		store.runTests(NonTestCases.class);
	}
	
	@Test
	public void shouldExecuteAConformanceTestCase() throws Exception{
		assertTrue(store.runTests(StoreRoleTest.class));
	}

}
