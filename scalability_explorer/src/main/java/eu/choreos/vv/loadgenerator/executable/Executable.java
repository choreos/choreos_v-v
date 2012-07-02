package eu.choreos.vv.loadgenerator.executable;

public abstract class Executable {
	
	public abstract void run();
	
	public abstract void setUp();
	
	public Double execute() {
		setUp();
		double start = initialMeasurement();
		run();
		double end = finalMeasurement();
		return end - start;
	}
	
	protected abstract double initialMeasurement();

	protected abstract double finalMeasurement();
	
}
