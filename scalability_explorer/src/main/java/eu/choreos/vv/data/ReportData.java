package eu.choreos.vv.data;

import java.io.Serializable;
import java.util.List;

public class ReportData implements Serializable {
	List<Number> parameters;
	List<Number> measurements;
	public ReportData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ReportData(List<Number> parameters, List<Number> measurements) {
		super();
		this.parameters = parameters;
		this.measurements = measurements;
	}
	public List<Number> getParameters() {
		return parameters;
	}
	public void setParameters(List<Number> parameters) {
		this.parameters = parameters;
	}
	public List<Number> getMeasurements() {
		return measurements;
	}
	public void setMeasurements(List<Number> measurements) {
		this.measurements = measurements;
	}
}
