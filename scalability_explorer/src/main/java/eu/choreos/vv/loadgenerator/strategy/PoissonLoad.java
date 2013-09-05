package eu.choreos.vv.loadgenerator.strategy;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;

import eu.choreos.vv.loadgenerator.BasicLoadGenerator;

public class PoissonLoad extends LoadGenerationStrategy {

	private RandomData random;
	private long nextPoisson;
	
	@Override
	public void setup() throws Exception {
		random = new RandomDataImpl();
	}

	@Override
	public void beforeRequest() throws Exception {
		nextPoisson = nextPoisson();
		super.beforeRequest();
		
	}

	@Override
	public void afterRequest() throws Exception {
		super.afterRequest();
		sleep(nextPoisson - end + start);
		
	}

	private long nextPoisson() {
		return random.nextPoisson(delay);
	}
}







