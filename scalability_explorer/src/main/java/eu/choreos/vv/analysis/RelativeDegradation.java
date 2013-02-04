package eu.choreos.vv.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jfree.chart.PlotData;

import eu.choreos.vv.aggregations.AggregationFunction;
import eu.choreos.vv.chart.ScalabilityReportChart;
import eu.choreos.vv.data.ScalabilityReport;

public class RelativeDegradation implements Analyser {

	private String title;
	private AggregationFunction function;

	public RelativeDegradation(String title, AggregationFunction function) {
		this.title = title;
		this.function = function;
	}
	
	@Override
	public void analyse(List<ScalabilityReport> reports) throws Exception {
		List<PlotData> plotData = new ArrayList<PlotData>();
		ScalabilityReportChart chart = new ScalabilityReportChart(
				title, "execution", "Relative degradation");
		for (ScalabilityReport report : reports) {
			PlotData aggregation = new PlotData();
			aggregation.setName(report.getName().toString());
			Number firstKey = Collections.min(report.keySet());//report.keySet().iterator().next();
			Double basePerformance = function.aggregate(report.get(firstKey).getMeasurements());
			for (Number index : report.keySet()) {
				aggregation
						.put((Double) index, function.aggregate(report.get(
								index).getMeasurements())/basePerformance);
			}
			plotData.add(aggregation);
		}

		chart.createChart(plotData);
	}

}
