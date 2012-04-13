package eu.choreos.vv.compliance;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.choreos.vv.abstractor.ComplianceTestCase;

public class SimpleRoleComplianceTest extends ComplianceTestCase{
	
	 String endpoint;
	
	public  SimpleRoleComplianceTest(String endpoint){
		super(endpoint);
		this.endpoint = endpoint;
	}
	
	// This Test fails purposely when runned by the Eclipse JUnit plugin
	
	@Test
	public void shouldReturnTheEndpoint(){
		assertEquals("file://" + System.getProperty("user.dir") + "/resource/simpleStore.wsdl", endpoint);
	}
	
}
