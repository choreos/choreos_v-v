package eu.choreos.vv.loadgenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.experiments.Experiment;
import eu.choreos.vv.loadgenerator.strategy.LoadGenerationStrategy;

/**
 * Load generator that trigger the requests as fast as possible
 * 
 */
public class SequentialLoadGenerator <K, T> implements LoadGenerator<K, T> {

	static final String LABEL = "response time (msec)";

	private Experiment<K, T> experiment;
	private LoadGenerationStrategy strategy;

	protected long delay;

	@Override
	public String getLabel() {
		return LABEL;
	}

	@Override
	public ReportData execute(int numberOfCalls, Experiment<K, T> experiment)
			throws Exception {
		final List<Number> measurements = new ArrayList<Number>();
		Date start, end;
		this.experiment = experiment;
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
		report.setMeasurements(measurements);
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
		K valueBefore = experiment.beforeRequest();
		double start = System.currentTimeMillis();
		T valueRequest = experiment.request(valueBefore);
		double end = System.currentTimeMillis();
		experiment.afterRequest(valueRequest);
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