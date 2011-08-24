package eu.choreos.vv.abstractor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StoreRoleTest extends ConformanceTestCase{
	
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
