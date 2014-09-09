package eu.choreos.vv.chart.creator;

import java.util.List;

import org.jfree.chart.ChartPanel;

import eu.choreos.vv.aggregations.Mean;
import eu.choreos.vv.aggregations.StandardDeviation;
import eu.choreos.vv.chart.YIntervalChart;
import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.data.PlotData;
import eu.choreos.vv.data.StatisticalData;
import eu.choreos.vv.data.Statistics;

public class MeanChartCreator implements ChartCreator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.choreos.vv.chart.creator.ChartCreator#createPlotData(eu.choreos.vv
	 * .data.ExperimentReport, int)
	 */
	@Override
	public PlotData createPlotData(ExperimentReport report, int paramIdx) {
		StatisticalData data = new StatisticalData();
		data.setName(report.getName().toString());
		for (Number index : report.keySet()) {
			Statistics stat = new Statistics();
			stat.setMean((new Mean()).aggregate(report.get(index)
					.getMeasurements("latency")));
			stat.setStandardDeviation((new StandardDeviation())
					.aggregate(report.get(index).getMeasurements("latency")));
			data.put(report.get(index).getParameters().get(paramIdx)
					.doubleValue(), stat);
		}

		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.choreos.vv.chart.creator.ChartCreator#createChart(java.util.List,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ChartPanel createChart(List<PlotData> plotData, String title,
			String xLabel, String yLabel) {
		// TODO Auto-generated method stub
		return YIntervalChart.createChart(title, plotData, xLabel, "mean of "
				+ yLabel);
	}

}
