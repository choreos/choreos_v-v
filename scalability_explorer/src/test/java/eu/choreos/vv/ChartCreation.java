package eu.choreos.vv;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.chart.ScalabilityReportChart;



public class ChartCreation {
	public static ScalabilityReport countChart() throws Exception {
		SumCount sumCount = new SumCount();
		return ScalabilityTesting.run(sumCount, "count", 1);
	}
	
	public static ScalabilityReport sumBothChart() throws Exception {
		SumCount sumCount = new SumCount();
		return ScalabilityTesting.run(sumCount, "sumBoth", 1, 2);	
	}
	
	
	public static ScalabilityReport withoutScaleParameterChart() throws Exception {
		SumCount sumCount = new SumCount();
		return ScalabilityTesting.run(sumCount, "withoutScaleParameter", 2);
	}
	
	
	public static void main(String[] args) throws Exception {
		List<ScalabilityReport> reports = new ArrayList<ScalabilityReport>();
		reports.add(countChart());
		reports.add(sumBothChart());
		reports.add(withoutScaleParameterChart());
		ScalabilityReportChart chart = new ScalabilityReportChart();
		chart.createChart(reports);
	}
}
