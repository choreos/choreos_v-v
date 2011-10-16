package eu.choreos.vv.compliance;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.abstractor.ComplianceTestCase;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vvws.common.RunServer;

public class FullRoleComplianceTest extends ComplianceTestCase{
	
	 String endpoint;
	 private static WSClient service; 
	 private static List<String> operations;
	
	public  FullRoleComplianceTest(String endpoint){
		super(endpoint);
		this.endpoint = endpoint;
	}
	
	// This Test fails purposely when runned by the Eclipse JUnit plugin
	
	@BeforeClass
	public static void deployService(){
		RunServer.runServers();
	}
	
	@AfterClass
	public static void undeployService(){
		RunServer.killServers();
	}
	
	@Before
	public  void setUp() throws Exception {
		service = new WSClient(endpoint);
		operations = service.getOperations();
	}
	
	@Test
	public void shouldProvideSearchByArtistOperation(){
		assertTrue(operations.contains("searchByArtist"));
	}
	
	@Test
	public void shouldProvideSearchByTitleOperation(){
		assertTrue(operations.contains("searchByTitle"));
	}
	
	@Test
	public void shouldProvidePurchaseOperation(){
		assertTrue(operations.contains("purchase"));
	}
	
}
