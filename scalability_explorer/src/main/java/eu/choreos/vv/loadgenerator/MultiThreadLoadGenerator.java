package eu.choreos.vv.loadgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import eu.choreos.vv.experiments.Experiment;
import eu.choreos.vv.loadgenerator.strategy.LoadGenerationStrategy;

public class MultiThreadLoadGenerator implements LoadGenerator {

	private long requestInterval;
	private long delay;
	private int poolSize;
	private long threadInteval;
	private List<LoadGeneratorThread> threads;
	private List<Number> results;
	private ExecutorService loadGeneratorService;
	
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Number> execute(int times, Experiment exec) throws Exception {
		// TODO Auto-generated method stub
		requestInterval = 100000000l;
		poolSize = (int)(requestInterval / delay);
		threadInteval = requestInterval / poolSize;
		if (poolSize > times) {
			poolSize = times;
			threadInteval = delay;
		}
		
		
		results = new ArrayList<Number>();
		
		createThreads(times/poolSize, exec);
		
		startThreads();
		
		
		loadGeneratorService.shutdown();
		loadGeneratorService.awaitTermination(60, TimeUnit.SECONDS);
		
		
		return results;
	}

	private void createThreads(int requests, Experiment experiment) {
		threads = new ArrayList<LoadGeneratorThread>();
		for(int i = 0; i < poolSize; i++) {
			LoadGenerator loadGen = new DegeneratedLoadGenerator();
			loadGen.setDelay(requestInterval);
			LoadGeneratorThread thread = new LoadGeneratorThread(loadGen, requests, experiment, results);
			threads.add(thread);
		}
		
	}

	private void startThreads() throws InterruptedException {
		loadGeneratorService = Executors
				.newFixedThreadPool(poolSize);
		for(LoadGeneratorThread thread: threads) {
			long s = System.nanoTime();
			loadGeneratorService.submit(thread);
			long d = System.nanoTime() - s;
			LoadGenerationStrategy.sleep(threadInteval - d);
		}
	}

	@Override
	public void setDelay(long delay) {
		this.delay = delay;

	}

	@Override
	public void setTimeout(int timeout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPoolSize(int poolsize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStrategy(LoadGenerationStrategy strategy) {
		// TODO Auto-generated method stub
		
	}

}
