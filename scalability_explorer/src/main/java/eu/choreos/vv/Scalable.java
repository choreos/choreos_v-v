package eu.choreos.vv;

import java.util.List;

/**
 * Interface to implement a scalability test item. Such an item is supposed to be invoked multiple times with it's parameters being increased by a ScalabilityFunction each time.
 * @author paulo
 *
 */
public interface Scalable {
	/**
	 * Performs one step in a scalabilty test, with the given parameters.
	 * @param params a number of numeric values
	 * @return a measurement of interest collected during the test.
	 * @throws Exception
	 */
	public List<Number> execute(ScaleCaster scaleCaster) throws Exception;
	
}
