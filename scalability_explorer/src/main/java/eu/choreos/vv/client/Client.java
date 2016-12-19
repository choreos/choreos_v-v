package eu.choreos.vv.client;

import java.util.Map;

import eu.choreos.vv.chart.Labeled;
import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.loadgenerator.LoadGenerator;
import eu.choreos.vv.loadgenerator.LoadGeneratorFactory;

public abstract class Client<K, T> implements Labeled {

	protected LoadGenerator<K, T> loadGen;
	protected Map<String, Object> params;

	@Override
	public String getLabel() {
		return loadGen.getLabel();
	}

	public ReportData execute(int numberOfCalls, long delay, Map<String, Object> params) throws Exception {
		ReportData report;
		
		this.params = params;
		
		this.setUp();
		
		this.newLoadGenerator();
		loadGen.setDelay(delay);
		report = loadGen.execute(numberOfCalls, this);
		
		this.tearDown();
		return report;
	}
	
	/**
	 * This method can be overridden to execute before each Iteration
	 * 
	 * @throws Exception
	 */
	public void setUp() throws Exception {
	}

	/**
	 * This method can be overriden to execute after each Iteration
	 * 
	 * @throws Expeption
	 */
	public void tearDown() throws Exception {

	}


	/**
	 * This method can be overridden to execute before each request
	 * 
	 * @throws Exception
	 */
	public K beforeRequest() throws Exception {
		return null;
	}

	/**
	 * This method must be overridden in order to execute the proper request
	 * 
	 * @throws Exception
	 */
	public T request(K param) throws Exception {
		return null;
	}

	/**
	 * This method can be overriden to execute after each request
	 * 
	 * @throws Exception
	 */
	public void afterRequest(T param) throws Exception {
	}

	private void newLoadGenerator() {
		loadGen = LoadGeneratorFactory.getInstance().<K, T> create();
	}

}
