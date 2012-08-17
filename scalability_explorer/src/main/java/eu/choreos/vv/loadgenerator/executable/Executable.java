package eu.choreos.vv.loadgenerator.executable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Executable implements Runnable {
	
	private final List<Number> results = new ArrayList<Number>();
	
	public abstract void experiment() throws Exception;
	
	public abstract void setUp() throws Exception;
	
	public List<Number> getResults() {
		return results;
	}

	public void clearResults() {
		results.clear();
	}
	
	@Override
	public void run() {
		try {
			setUp();
			double start = initialMeasurement();
			experiment();
			double end = finalMeasurement();
			results.add(end - start);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			

	}
	
	protected abstract double initialMeasurement();

	protected abstract double finalMeasurement();
	
}
