package eu.choreos.vv.chart.creator;

import java.util.List;

import org.jfree.chart.ChartPanel;

import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.data.PlotData;

public interface ChartCreator {

	public abstract PlotData createPlotData(ExperimentReport report,
			int paramIdx);

	public abstract ChartPanel createChart(List<PlotData> plotData,
			String title, String xLabel, String yLabel);

}