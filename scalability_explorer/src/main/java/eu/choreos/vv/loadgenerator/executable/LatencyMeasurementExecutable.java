package eu.choreos.vv.loadgenerator.executable;

public abstract class LatencyMeasurementExecutable extends Executable {

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
