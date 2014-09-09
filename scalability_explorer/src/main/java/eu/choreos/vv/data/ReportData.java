package eu.choreos.vv.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportData implements Serializable {
	List<Number> parameters;
	Map<String, List<Number>> measurements;
	Date startTime, endTime;

	public ReportData() {
		super();
		measurements = new HashMap<String, List<Number>>();
	}

//	public ReportData(List<Number> parameters, List<Number> measurements) {
//		super();
//		this.parameters = parameters;
//		this.measurements = measurements;
//	}

	public List<Number> getParameters() {
		return parameters;
	}

	public void setParameters(List<Number> parameters) {
		this.parameters = parameters;
	}

	public List<Number> getMeasurements(String label) {
		return measurements.get(label);
	}

	public void setMeasurements(String label, List<Number> measurements) {
		this.measurements.put(label, measurements);
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
