package eu.choreos.vv.abstractor;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ConformanceTestCase {
	
	static String endpoint;
	
	public ConformanceTestCase(String entry){
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
