package eu.choreos.vv.chart;

import java.awt.EventQueue;
import java.util.List;

import org.jfree.chart.PlotData;
import org.jfree.chart.XYChart;

import eu.choreos.vv.aggregations.AggregationFunction;
import eu.choreos.vv.data.ScalabilityReport;

/**
 * Creates charts to present the results obtained by batteries of scalability tests.
 * @author paulo
 *
 */
public class ReportChart {
	
	final static private String applicationTitle  = "Scalability explorer";;
	private String chartTitle;
	private String xLabel, yLabel;
	
	/**
	 * Creates a new ScalabilityReportChart with titles and labels
	 * @param chartTitle chart title
	 * @param xLabel x-axis label
	 * @param yLabel y-axis label
	 */
	public ReportChart(String chartTitle, String xLabel, String yLabel) {
		this.chartTitle = chartTitle;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
	}
	
	public ReportChart(String chartTitle, ScalabilityReport report, int paramIdx, AggregationFunction function) {
		this(chartTitle, report.getParameterLabels().get(paramIdx), function.getLabel() + " of " + report.getMeasurementUnit());
	}
	
	/**
	 * Shows the chart with the given reports plotted.
	 * @param reports a list of ScalabilityReport
	 */
    public void createChart(final List<PlotData> reports) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                XYChart chart = new XYChart(applicationTitle, chartTitle, reports, xLabel, yLabel);
                chart.pack();
                chart.setLocationRelativeTo(null);
                chart.setVisible(true);
            }
         });
    }
}
