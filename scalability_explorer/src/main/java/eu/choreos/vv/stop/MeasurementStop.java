package eu.choreos.vv.stop;

import java.util.Collections;
import java.util.List;

import eu.choreos.vv.aggregations.AggregationFunction;
import eu.choreos.vv.data.ExperimentReport;

public class MeasurementStop implements StopCriterion {

	private Double limit;
	private AggregationFunction function;

	public MeasurementStop(Double limit, AggregationFunction function) {
		this.limit = limit;
		this.function = function;
	}
	
	@Override
	public boolean stop(ExperimentReport report) {
		if (report.isEmpty()) return false;
		Object key = Collections.max(report.keySet());
		List<Number> data = report.get(key).getMeasurements("latency");
		return limit < function.aggregate(data);
	}

}
