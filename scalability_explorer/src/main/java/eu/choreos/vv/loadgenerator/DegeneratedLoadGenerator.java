package eu.choreos.vv.loadgenerator;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Load generator that keeps a constant delay between two consecutive
 * executions.
 * 
 */
public class DegeneratedLoadGenerator extends FastestLoadGenerator {

	@Override
	protected void performRequest(final ExecutorService executor,
			final List<Future<Double>> futureResults) throws Exception {
		long start = System.nanoTime();
		super.performRequest(executor, futureResults);
		long end = System.nanoTime();
		sleep(delay - end + start);
	}
}