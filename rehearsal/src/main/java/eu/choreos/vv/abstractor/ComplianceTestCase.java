package eu.choreos.vv.abstractor;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class represents the generic test case that must be extended in the compliance test creation
 * 
 * @author Felipe Besson
 *
 */
@RunWith(value = Parameterized.class)
public class ComplianceTestCase {
	
	static String endpoint;
	
	public ComplianceTestCase(String entry){
		endpoint = entry;
	}	

	@Parameters
	public static Collection<Object[]> data(){
		   Object[][] data = new Object[][] { { endpoint } };

		return Arrays.asList(data);
	}
	
	public  static void setName(String name){
		endpoint = name;
	}
	
}
