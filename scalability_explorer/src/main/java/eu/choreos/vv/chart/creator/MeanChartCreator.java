package eu.choreos.vv.chart.creator;

import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.data.xy.AbstractIntervalXYDataset;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;

import eu.choreos.vv.aggregations.Mean;
import eu.choreos.vv.aggregations.StandardDeviation;
import eu.choreos.vv.chart.LineChart;
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
					.getMeasurements("responseTime")));
			stat.setStandardDeviation((new StandardDeviation())
					.aggregate(report.get(index).getMeasurements("responseTime")));
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
		YIntervalSeriesCollection dataset = new YIntervalSeriesCollection();

        for (PlotData report : plotData) {
            createDataset(dataset, (StatisticalData)report);
		}
		return LineChart.createChart(title, plotData, xLabel, "mean of "
				+ yLabel, dataset, new DeviationRenderer(true, true));
	}
	
	private static void createDataset(YIntervalSeriesCollection dataset, StatisticalData report) {
    	YIntervalSeries series = new YIntervalSeries(report.getName());
//    	for (int i = 0; i < report.size(); i++) {
    	for (Double x: report.keySet()) {
    		Double y = report.get(x).getMean();
    		Double sd = report.get(x).getStandardDeviation();
			series.add(x, y, y-sd, y+sd);
		}
    	dataset.addSeries(series);
    }

}
