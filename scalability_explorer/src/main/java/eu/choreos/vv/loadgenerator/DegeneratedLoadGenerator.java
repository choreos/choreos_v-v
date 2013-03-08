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
	
	@Override
	public void setDelay(int delay) {
		this.delay = delay;
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