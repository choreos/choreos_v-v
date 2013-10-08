package eu.choreos.vv.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReportData implements Serializable {
	List<Number> parameters;
	List<Number> measurements;
	Date startTime, endTime;

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
