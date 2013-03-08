package eu.choreos.vv.loadgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import eu.choreos.vv.loadgenerator.executable.Executor;

/**
 * Load generator that trigger the requests as fast as possible 
 *
 */
public class FastestLoadGenerator implements LoadGenerator {

	static final int DEFAULT_THREADS_TIMEOUT = 60;
	static final int DEFAULT_POOL_SIZE = 50;
	static final String LABEL = "response time (msec)";
	
	private int poolSize;
	private int timeout;
	
	public FastestLoadGenerator(int poolSize, int timeout) {
		this.poolSize = poolSize;
		this.timeout = timeout;
	}
	
	public FastestLoadGenerator() {
		this(DEFAULT_POOL_SIZE, DEFAULT_THREADS_TIMEOUT);
	}

	@Override
	public String getLabel() {
		return LABEL;
	}

	@Override
	public List<Number> execute(int numberOfCalls,
			Executor executable) throws Exception {
		final ExecutorService executor = Executors.newFixedThreadPool(poolSize);
		final List<Future<Double>> futureResults = new ArrayList<Future<Double>>();
		final List<Number> results = new ArrayList<Number>();
		try {
			for (int i = 0; i < numberOfCalls; i++) {
				performRequest(executable, executor, futureResults); //TODO: exception handling. count failed requests?)
			}
			executor.shutdown();
			while (!executor
					.awaitTermination(timeout, TimeUnit.SECONDS))
				;
		} catch (InterruptedException e) {
			executor.shutdownNow();
			throw e;
		}

		for (Future<Double> future : futureResults)
			results.add(future.get());

		return results;
	}

	protected void performRequest(Executor executable,
			final ExecutorService executor,
			final List<Future<Double>> futureResults) throws Exception {
		Future<Double> result = executor.submit(executable);
		futureResults.add(result);
	}

	@Override
	public void setDelay(int delay) {

	}

}