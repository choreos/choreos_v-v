package eu.choreos.vv.loadgenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.choreos.vv.client.Client;
import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.loadgenerator.strategy.LoadGenerationStrategy;

/**
 * Load generator that trigger the requests as fast as possible
 * 
 */
public class SequentialLoadGenerator <K, T> implements LoadGenerator<K, T> {

	static final String LABEL = "response time (msec)";

	private Client<K, T> client;
	private LoadGenerationStrategy strategy;

	protected long delay;

	@Override
	public String getLabel() {
		return LABEL;
	}

	@Override
	public ReportData execute(int numberOfCalls, Client<K, T> client)
			throws Exception {
		final List<Number> measurements = new ArrayList<Number>();
		Date start, end;
		this.client = client;
		strategy.setMeanDelay(delay);
		strategy.setup();
			start = new Date();
			for (int i = 0; i < numberOfCalls; i++) {
				strategy.beforeRequest();
				measurements.add(call());
				strategy.afterRequest();
			}
			end = new Date();

		ReportData report = new ReportData();
		report.setMeasurements("latency", measurements);
		report.setStartTime(start);
		report.setEndTime(end);
		return report;
	}
	
	public LoadGenerationStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(LoadGenerationStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	public long getDelay() {
		return delay;
	}

	public Double call() throws Exception {
		K valueBefore = client.beforeRequest();
		double start = System.currentTimeMillis();
		T valueRequest = client.request(valueBefore);
		double end = System.currentTimeMillis();
		client.afterRequest(valueRequest);
		return (end - start);
	}

	@Override
	public void setTimeout(int timeout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPoolSize(int poolsize) {
		// TODO Auto-generated method stub
		
	}

}