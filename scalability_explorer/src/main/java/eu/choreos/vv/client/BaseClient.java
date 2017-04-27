package eu.choreos.vv.client;

import java.util.Map;

import eu.choreos.vv.chart.Labeled;
import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.loadgenerator.LoadGenerator;
import eu.choreos.vv.loadgenerator.LoadGeneratorFactory;

public abstract class BaseClient<K, T> implements Client<K, T> {

	protected LoadGenerator<K, T> loadGen;
	protected Map<String, Object> params;

	@Override
	public String getLabel() {
		return loadGen.getLabel();
	}

	/* (non-Javadoc)
	 * @see eu.choreos.vv.client.Client#execute(int, long, java.util.Map)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see eu.choreos.vv.client.Client#setUp()
	 */
	@Override
	public void setUp() throws Exception {
	}

	/* (non-Javadoc)
	 * @see eu.choreos.vv.client.Client#tearDown()
	 */
	@Override
	public void tearDown() throws Exception {

	}


	/* (non-Javadoc)
	 * @see eu.choreos.vv.client.Client#beforeRequest()
	 */
	@Override
	public K beforeRequest() throws Exception {
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.choreos.vv.client.Client#request(K)
	 */
	@Override
	public T request(K param) throws Exception {
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.choreos.vv.client.Client#afterRequest(T)
	 */
	@Override
	public void afterRequest(T param) throws Exception {
	}

	private void newLoadGenerator() {
		loadGen = LoadGeneratorFactory.getInstance().<K, T> create();
	}

}
