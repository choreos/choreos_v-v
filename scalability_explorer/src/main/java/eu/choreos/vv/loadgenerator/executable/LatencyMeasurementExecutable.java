package eu.choreos.vv.loadgenerator.executable;

public abstract class LatencyMeasurementExecutable extends Executable {

	@Override
	public abstract void run();
	
	@Override
	public abstract void setUp();
	
	@Override
	protected double initialMeasurement() {
		return System.currentTimeMillis();
	}

	@Override
	protected double finalMeasurement() {
		return System.currentTimeMillis();
	}

}
