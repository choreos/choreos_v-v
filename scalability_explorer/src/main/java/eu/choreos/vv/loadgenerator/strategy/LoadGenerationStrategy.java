package eu.choreos.vv.loadgenerator.strategy;

public abstract class LoadGenerationStrategy {
	
	protected long start, end, delay;
	
	public void setup() throws Exception {}
	
	public void beforeRequest() throws Exception {
		start = System.nanoTime();
	}
	
	public void afterRequest() throws Exception {
		end = System.nanoTime();
	}
	
	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public static void sleep(long delay) throws InterruptedException {
		long millis = delay / 1000000;
		int nanos = (int) (delay % 1000000);
		Thread.sleep(millis, nanos);

	}
}
