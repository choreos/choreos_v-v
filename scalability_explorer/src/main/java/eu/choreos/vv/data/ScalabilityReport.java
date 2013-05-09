package eu.choreos.vv.data;

import java.util.HashMap;
import java.util.List;

public class ScalabilityReport extends HashMap<Double, ReportData> {
	private static final long serialVersionUID = -2993273004741518768L;
	private String name;
	List<String> parameterLabels;
	String measurementUnit;

	public List<String> getParameterLabels() {
		return parameterLabels;
	}

	public void setParameterLabels(List<String> parameterLabels) {
		this.parameterLabels = parameterLabels;
	}

	
	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public ScalabilityReport() {
	}

	public ScalabilityReport(String methodName) {
		this.name = methodName;
	}

	public Comparable<String> getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
