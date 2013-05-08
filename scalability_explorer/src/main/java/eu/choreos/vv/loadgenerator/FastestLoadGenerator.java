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
	
	protected int delay;
	
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
			Executor executor) throws Exception {
		final ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
		final List<Future<Double>> futureResults = new ArrayList<Future<Double>>();
		final List<Number> results = new ArrayList<Number>();
		try {
			for (int i = 0; i < numberOfCalls; i++) {
				performRequest(executor, executorService, futureResults); //TODO: exception handling. count failed requests?)
			}
			executorService.shutdown();
			while (!executorService
					.awaitTermination(timeout, TimeUnit.SECONDS))
				;
		} catch (InterruptedException e) {
			executorService.shutdownNow();
			throw e;
		}

		for (Future<Double> future : futureResults)
			results.add(future.get());

		return results;
	}

	protected void performRequest(Executor executor,
			final ExecutorService executorService,
			final List<Future<Double>> futureResults) throws Exception {
		Future<Double> result = executorService.submit(executor);
		futureResults.add(result);
	}

	@Override
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public void sleep(long delay) throws InterruptedException {
		long millis = delay / 1000000;
		int nanos = (int)(delay % 1000000);
		Thread.sleep(millis, nanos);
		
	}

}