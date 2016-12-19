package eu.choreos.vv.data;

import java.io.Serializable;
import java.util.ArrayList;
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
	
	public void addMeasurements(String label, List<Number> measurements) {
		List<Number> current = this.getMeasurements(label);
		if (current == null)
			this.setMeasurements(label, measurements);
		else
			current.addAll(measurements);
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
	
	public ReportData merge(ReportData that) {
		ReportData merged = new ReportData();
		
		//TODO: label the parameters and only merge reports with similiar parameters and values
		merged.setParameters(this.getParameters());
		
		merged.setStartTime(this.getStartTime().before(that.getStartTime())? this.getStartTime() : that.getStartTime());
		merged.setEndTime(this.getEndTime().after(that.getEndTime())? this.getEndTime() : that.getEndTime());
		
		//TODO add timestamp to the measurements and merge them ordered
		for(String key: this.measurements.keySet()) {
			merged.addMeasurements(key, new ArrayList<Number>(this.getMeasurements(key)));
		}
		for(String key: that.measurements.keySet()) {
			merged.addMeasurements(key, new ArrayList<Number>(that.getMeasurements(key)));
		}
		
		
		return merged;
	}

}
