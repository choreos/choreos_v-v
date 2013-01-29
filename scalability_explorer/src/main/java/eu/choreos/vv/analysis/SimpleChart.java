package eu.choreos.vv.analysis;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.PlotData;

import eu.choreos.vv.ScalabilityReport;
import eu.choreos.vv.aggregations.AggregationFunction;
import eu.choreos.vv.chart.ScalabilityReportChart;

public class SimpleChart implements Analyser {

	private String title;
	private AggregationFunction function;

	public SimpleChart(String title, AggregationFunction function) {
		this.title = title;
		this.function = function;
	}

	@Override
	public void analyse(List<ScalabilityReport> reports, String unitLabel) {
		List<PlotData> plotData = new ArrayList<PlotData>();
		ScalabilityReportChart chart = new ScalabilityReportChart(title,
				"execution", function.getLabel() + " of " + unitLabel);
		for (ScalabilityReport report : reports) {
			PlotData aggregation = new PlotData();
			aggregation.setName(report.getName().toString());
			for (Number index : report.keySet()) {
				aggregation.put((Double) index,
						function.aggregate(report.get(index)));
			}
			plotData.add(aggregation);
		}

		chart.createChart(plotData);

	}

}
