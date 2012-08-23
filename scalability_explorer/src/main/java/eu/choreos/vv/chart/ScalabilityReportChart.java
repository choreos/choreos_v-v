package eu.choreos.vv.chart;

import java.awt.EventQueue;
import java.util.List;

import org.jfree.chart.XYChart;

import eu.choreos.vv.ScalabilityReport;

public class ScalabilityReportChart {
	
	private String applicationTitle;
	private String chartTitle;
	private String xLabel, yLabel;
	
	public ScalabilityReportChart(String applicationTitle, String chartTitle, String xLabel, String yLabel) {
		this.applicationTitle = applicationTitle;
		this.chartTitle = chartTitle;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
	}
	
    public void createChart(final List<ScalabilityReport> reports) {
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
