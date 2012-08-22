package eu.choreos.vv.loadgenerator.executable;

import java.util.concurrent.Callable;

public abstract class Executable implements Callable<Double> {

	public abstract void experiment() throws Exception;

	public abstract void setUp() throws Exception;

	@Override
	public Double call() throws Exception {
		setUp();
		double start = initialMeasurement();
		experiment();
		double end = finalMeasurement();
		return (end - start);

	}

	protected abstract double initialMeasurement();

	protected abstract double finalMeasurement();

}
