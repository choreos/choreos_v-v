package eu.choreos.vv.chart.creator;

import java.util.List;

import org.jfree.chart.ChartPanel;

import eu.choreos.vv.aggregations.Percentile;
import eu.choreos.vv.chart.XYChart;
import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.data.LineData;
import eu.choreos.vv.data.PlotData;

public class PercentileChartCreator implements ChartCreator {
	
	Percentile function;
	
	public PercentileChartCreator(int percentile) {
		function = new Percentile(percentile);
	}

	@Override
	public PlotData createPlotData(ExperimentReport report, int paramIdx) {
		LineData aggregation = new LineData();
		aggregation.setName(report.getName().toString());
		for (Number index : report.keySet()) {
			aggregation.put(report.get(index).getParameters().get(paramIdx)
					.doubleValue(),// (Double) index,
					function.aggregate(report.get(index).getMeasurements("latency")));
		}
		return aggregation;
	}

	@Override
	public ChartPanel createChart(List<PlotData> plotData, String title,
			String xLabel, String yLabel) {
		return XYChart.createChart(title, plotData, xLabel, yLabel);
	}

}
