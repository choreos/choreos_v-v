package eu.choreos.vv.loadgenerator.executable;

import java.util.concurrent.Callable;

/**
 * An Executable is an object that can be called multiple times by a LoadGenerator 
 *
 */
public abstract class Executor implements Callable<Double> {

	/**
	 * This method must be overridden to implement the proper experiment to be executed
	 * @throws Exception
	 */
	public abstract void experiment() throws Exception;
	
	/**
	 * This method must be overrided to execute any required preparation which is not intended to influence the measurement.
	 * @throws Exception
	 */
	public abstract void setUp() throws Exception;

	/**
	 * Calls the other methods in a proper sequence. It can be used by an ExecutorService.
	 * @return finalMeasurement() - initialMeasurement()
	 */
	@Override
	public Double call() throws Exception {
		setUp();
		double start = initialMeasurement();
		experiment();
		double end = finalMeasurement();
		return (end - start);
	}

	/**
	 * This method must be overridden to implement a measurement to be performed before the experiment
	 * @return the measured value
	 */
	protected abstract double initialMeasurement();

	/**
	 * This method must be overridden to implement a measurement to be performed alter the experiment
	 * @return the measured value
	 */
	protected abstract double finalMeasurement();

}
