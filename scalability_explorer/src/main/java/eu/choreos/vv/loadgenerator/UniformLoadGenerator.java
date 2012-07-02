package eu.choreos.vv.loadgenerator;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.loadgenerator.executable.Executable;

public class UniformLoadGenerator implements LoadGenerator {

	@Override
	public List<Double> execute(int numberOfCalls, int callsPerMin, Executable e) throws InterruptedException {
		long delay = 60000 / callsPerMin;
		List<Double> results = new ArrayList<Double>();
		for(int i = 0; i < numberOfCalls; i++) {
			long start = System.currentTimeMillis();
			Double result = e.execute();
			long end = System.currentTimeMillis();
			results.add(result);
			Thread.sleep(delay - end + start);
		}
		return results;
	}
	

}
