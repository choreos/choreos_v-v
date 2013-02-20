package eu.choreos.vv.analysis;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.PlotData;

import eu.choreos.vv.aggregations.AggregationFunction;
import eu.choreos.vv.chart.ReportChart;
import eu.choreos.vv.data.ScalabilityReport;

public class AggregatePerformance extends Analyzer {

	private String title;
	private AggregationFunction function;
	private int paramIdx;
	
	private List<PlotData> plotData;

	public AggregatePerformance(String title, AggregationFunction function, int param) {
		this.title = title;
		this.function = function;
		this.paramIdx = param;
	}

	public AggregatePerformance(String title, AggregationFunction function) {
		this(title, function, 0);
		plotData = new ArrayList<PlotData>();
	}

	@Override
	public void analyse(ScalabilityReport report) {
		PlotData aggregation = new PlotData();
		aggregation.setName(report.getName().toString());
		for (Number index : report.keySet()) {
			aggregation.put(report.get(index).getParameters().get(paramIdx)
					.doubleValue(),// (Double) index,
					function.aggregate(report.get(index).getMeasurements()));
		}
		plotData.add(aggregation);


	}
	
	@Override
	public void analyse(List<ScalabilityReport> reports) throws Exception {
		ReportChart chart = new ReportChart(title, // "execution",
				reports.get(0).getParameterLabels().get(paramIdx), function.getLabel()
				+ " of " + reports.get(0).getMeasurementUnit());
		super.analyse(reports);
		chart.createChart(plotData);
	}

}
