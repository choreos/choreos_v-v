package eu.choreos.vv.loadgenerator.executable;


/**
 * An Executable that collects initial and final system time. Hence, the result of call() is the latency of the execution. 
 *
 */
public abstract class LatencyMeasurementExecutable extends Executor {

	@Override
	public abstract void experiment() throws Exception;

	@Override
	public abstract void setUp() throws Exception;
	
	@Override
	protected double initialMeasurement() {
		return System.currentTimeMillis();
	}

	@Override
	protected double finalMeasurement() {
		return System.currentTimeMillis();
	}

}
