package eu.choreos.vv.loadgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import eu.choreos.vv.loadgenerator.executable.Executor;

/**
 * Load generator that keeps a constant delay between two consecutive
 * executions.
 * 
 */
public class DegeneratedLoadGenerator extends FastestLoadGenerator {

	private int delay;

	//TODO switch requestsPerMin parameter for delay (check usage to update)
	public DegeneratedLoadGenerator(int poolSize, int timeout, int requestsPerMin) {
		super(poolSize, timeout);
		init(requestsPerMin);
	}

	private void init(int requestsPerMin) {
		this.delay = 60000 / requestsPerMin;
	}

	public DegeneratedLoadGenerator(int requestsPerMin) {
		super();
		init(requestsPerMin);
	}

	@Override
	protected void performRequest(Executor executable,
			final ExecutorService executor,
			final List<Future<Double>> futureResults) throws Exception {
		long start = System.currentTimeMillis();
		super.performRequest(executable, executor, futureResults);
		long end = System.currentTimeMillis();
		Thread.sleep(delay - end + start);
	}
}