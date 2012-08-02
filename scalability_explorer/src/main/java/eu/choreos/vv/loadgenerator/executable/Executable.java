package eu.choreos.vv.loadgenerator.executable;

public abstract class Executable {
	
	public abstract void run() throws Exception;
	
	public abstract void setUp() throws Exception;
	
	public Double execute() throws Exception {
		setUp();
		double start = initialMeasurement();
		run();
		double end = finalMeasurement();
		return end - start;
	}
	
	protected abstract double initialMeasurement();

	protected abstract double finalMeasurement();
	
}
