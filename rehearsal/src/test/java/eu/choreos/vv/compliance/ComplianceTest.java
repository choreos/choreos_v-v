package eu.choreos.vv.compliance;

import org.junit.Before;

import eu.choreos.vv.abstractor.Role;
import eu.choreos.vv.abstractor.Service;

public class ComplianceTest {

	private final String STORE_WSDL="http://localhost:1234/simpleStore?wsdl";
	private final String ABC_STORE_WSDL="file:// " + System.getProperty("user.dir") + "/resource/simpleStore.wsdl";

	Service abcStore;
	Role store;
	
	@Before
	public void setUp(){
		store = new Role("store", STORE_WSDL);
		
		abcStore = new Service();
		abcStore.setWSDL(ABC_STORE_WSDL);
		abcStore.addRole(store);
	}
	
	

	

}
