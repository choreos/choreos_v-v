package eu.choreos.vv.loadgenerator;

import eu.choreos.vv.chart.Labeled;
import eu.choreos.vv.client.Client;
import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.loadgenerator.strategy.LoadGenerationStrategy;

/**
 *  A load generator is a component capable to repeatedly run an Executable according to the defined execution pattern.
 *
 */

public interface LoadGenerator <K, T> extends Labeled {
	/**
	 * Calls the Executable a number of times in a certain frequency
	 * @param times number of times to execute
	 * @param callsPerMin execution frequency
	 * @param exec object to be executed
	 * @return a list of the values returned by the executions
	 * @throws Exception
	 */
	public ReportData execute(int times, Client<K, T> exec) throws Exception;
	
	public void setDelay(long delay);
	
	public void setTimeout(int timeout);
	
	public void setPoolSize(int poolsize);
	
	public void setStrategy(LoadGenerationStrategy strategy);
	
}
