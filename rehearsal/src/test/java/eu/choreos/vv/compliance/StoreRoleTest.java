package eu.choreos.vv.compliance;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.choreos.vv.abstractor.ComplianceTestCase;

public class StoreRoleTest extends ComplianceTestCase{
	
	 String endpoint;
	
	public  StoreRoleTest(String endpoint){
		super(endpoint);
		this.endpoint = endpoint;
	}
	
	// This Test fails purposely when runned by the Eclipse JUnit plugin
	
	@Test
	public void shouldReturnTheEndpoint(){
		assertEquals("http://localhost:1234/simpleStore?wsdl", endpoint);
	}
	
}
