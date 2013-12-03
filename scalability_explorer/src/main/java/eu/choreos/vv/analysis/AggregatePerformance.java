package eu.choreos.vv.analysis;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.chart.ReportChart;
import eu.choreos.vv.chart.creator.ChartCreator;
import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.data.PlotData;

public class AggregatePerformance extends Analyzer {

	private String title;
	private int paramIdx;
	ChartCreator creator;

	// private List<PlotData> plotData;

	public AggregatePerformance(String title, ChartCreator creator, int param) {
		this.title = title;
		this.creator = creator;
		this.paramIdx = param;
		// plotData = new ArrayList<PlotData>();
	}

	public AggregatePerformance(String title, ChartCreator creator) {
		this(title, creator, 0);
	}

	@Override
	public void analyse(ExperimentReport report) {
		List<PlotData> plotData = new ArrayList<PlotData>();
		PlotData aggregation = creator.createPlotData(report, paramIdx);
		plotData.add(aggregation);

		createChart(plotData, report.getParameterLabels().get(paramIdx),
				report.getMeasurementUnit());

	}

	@Override
	public void analyse(List<ExperimentReport> reports) throws Exception {

		List<PlotData> plotData = new ArrayList<PlotData>();
		for (ExperimentReport report : reports)
			plotData.add(creator.createPlotData(report, paramIdx));

		createChart(plotData,
				reports.get(0).getParameterLabels().get(paramIdx),
				reports.get(0).getMeasurementUnit());

	}

	private void createChart(List<PlotData> data, String xLabel, String yLabel) {
		ReportChart chart = new ReportChart();

		chart.createChart(creator.createChart(data, title, xLabel, yLabel));
	}

}
