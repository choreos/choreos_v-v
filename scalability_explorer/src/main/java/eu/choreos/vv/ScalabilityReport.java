package eu.choreos.vv;

public class ScalabilityReport {


	private String methodName;
	private double[] series;

	public ScalabilityReport(String methodName, int times) {
		this.methodName = methodName;
		series = new double[times+1];
	}

	public double[] getSeries() {
		return series;
	}

	@SuppressWarnings("rawtypes")
	public Comparable getName() {
		return methodName;
	}

	public void add(int i, double value) {
		series[i] = value;
	}

}
