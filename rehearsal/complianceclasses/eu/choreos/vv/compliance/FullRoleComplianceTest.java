package eu.choreos.vv.compliance;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.vv.abstractor.ComplianceTestCase;
import eu.choreos.vv.clientgenerator.WSClient;

public class FullRoleComplianceTest extends ComplianceTestCase{
	
	 String endpoint;
	 private static WSClient service; 
	 private static List<String> operations;
	
	public  FullRoleComplianceTest(String endpoint){
		super(endpoint);
		this.endpoint = endpoint;
	}
	
	// This Test fails purposely when runned by the Eclipse JUnit plugin
	
	@Before
	public  void setUp() throws Exception {
		service = new WSClient(endpoint);
		operations = service.getOperations();
	}
	
	@Test
	public void shouldProvideSearchByArtistOperation(){
		operations.contains("searchByArtist");
	}
	
}
