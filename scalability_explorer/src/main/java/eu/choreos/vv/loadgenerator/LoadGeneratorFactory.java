package eu.choreos.vv.loadgenerator;

import eu.choreos.vv.loadgenerator.strategy.DegeneratedLoad;
import eu.choreos.vv.loadgenerator.strategy.LoadGenerationStrategy;

public class LoadGeneratorFactory {
	
	private long delay;
	private int poolSize;
	private int timeout;
	private LoadGenerationStrategy strategy;
	
	private static LoadGeneratorFactory factory;
	
	private LoadGeneratorFactory() {
		this.delay = 1000000000;
		this.poolSize = 50;
		this.timeout = 60;
		this.strategy = new DegeneratedLoad();
		
	}
	
	public static LoadGeneratorFactory getInstance() {
		if (factory == null)
			factory = new LoadGeneratorFactory();
		return factory;
	}
	
	public <T, K> LoadGenerator<T, K> degeneratedLoad() {
		LoadGenerator<T, K> instance = new BasicLoadGenerator<T, K>();
		instance.setDelay(delay);
		instance.setPoolSize(poolSize);
		instance.setTimeout(timeout);
		instance.setStrategy(strategy);
		return instance;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public LoadGenerationStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(LoadGenerationStrategy strategy) {
		this.strategy = strategy;
	}
	
	
	

}
