package eu.choreos.vv.chart.creator;

import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import eu.choreos.vv.aggregations.Percentile;
import eu.choreos.vv.chart.LineChart;
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
			aggregation.put(report.get(index).getParameters().get(paramIdx).doubleValue(), // (Double)
																							// index,
					function.aggregate(report.get(index).getMeasurements("responseTime")));
		}
		return aggregation;
	}

	@Override
	public ChartPanel createChart(List<PlotData> plotData, String title, String xLabel, String yLabel) {
		XYSeriesCollection dataset = new XYSeriesCollection();

		for (PlotData report : plotData) {
			createDataset(dataset, (LineData) report);
		}
		return LineChart.createChart(title, plotData, xLabel, yLabel, dataset, new XYLineAndShapeRenderer(true, true));
	}

	private static void createDataset(XYSeriesCollection dataset, LineData report) {
		XYSeries series = new XYSeries(report.getName());
		for (Double x : report.keySet()) {
			series.add(x, report.get(x));
		}
		dataset.addSeries(series);
	}
}
