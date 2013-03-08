package eu.choreos.vv.loadgenerator;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;

import eu.choreos.vv.loadgenerator.executable.Executor;

public class PoissonLoadGenerator extends FastestLoadGenerator {
	
	private RandomData random;
	private int delay;
	
	public PoissonLoadGenerator() {
		super();
		random = new RandomDataImpl();
	}
	
	@Override
	protected void performRequest(Executor executable,
			final ExecutorService executor,
			final List<Future<Double>> futureResults) throws Exception {
		long delay = nextPoisson();
		System.out.println("delay: " + delay);
		long start = System.currentTimeMillis();
		super.performRequest(executable, executor, futureResults);
		long end = System.currentTimeMillis();
		Thread.sleep(delay - end + start);
	}
	
	@Override
	public void setDelay(int delay) { 
		this.delay = delay;
	}

	private long nextPoisson() {
		return random.nextPoisson(delay);
	}

}
