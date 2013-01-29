package eu.choreos.vv;

import java.util.HashMap;
import java.util.List;

public class ScalabilityReport extends HashMap<Number, List<Number>> {

	private static final long serialVersionUID = -2993273004741518768L;
	private String methodName;

	public ScalabilityReport(String methodName) {
		this.methodName = methodName;
	}

	public Comparable<String> getName() {
		return methodName;
	}

}
