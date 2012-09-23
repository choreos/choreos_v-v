package eu.choreos.vv;

import java.util.HashMap;

public class ScalabilityReport extends HashMap<Double, Double> {

	private static final long serialVersionUID = -2993273004741518768L;
	private String methodName;

	public ScalabilityReport(String methodName) {
		this.methodName = methodName;
	}

	public Comparable<String> getName() {
		return methodName;
	}

}
