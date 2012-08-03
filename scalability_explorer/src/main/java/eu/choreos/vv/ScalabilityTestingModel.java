package eu.choreos.vv;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.aggregations.Aggregator;
import eu.choreos.vv.aggregations.Percentile;
import eu.choreos.vv.annotations.Scale;
import eu.choreos.vv.chart.ScalabilityReportChart;
import eu.choreos.vv.increasefunctions.LinearIncrease;
import eu.choreos.vv.loadgenerator.LoadGenerator;
import eu.choreos.vv.loadgenerator.UniformLoadGenerator;
import eu.choreos.vv.loadgenerator.executable.LatencyMeasurementExecutable;

public abstract class ScalabilityTestingModel {
	
	private List<ScalabilityReport> reports;
	
	public void setUp() throws Exception {}

	public void resourceScaling(int resourceQuantity) throws Exception {}
	
	public void beforeTest() throws Exception {}
	
	public void test() throws Exception {}
	
	public void tearDown() throws Exception {}
	
	public ScalabilityTestingModel() {
		reports = new ArrayList<ScalabilityReport>();
	}
	
	public double scalabilityTest(@Scale int requestsPerMinute, @Scale(chartDomain=true) int resourceQuantity, int numberOfExecutions) throws Exception {
		LoadGenerator loadGen = new UniformLoadGenerator();
		List<Double> results = new ArrayList<Double>();

		resourceScaling(resourceQuantity);
		
		try {
			results = loadGen.execute(numberOfExecutions, requestsPerMinute, new LatencyMeasurementExecutable() {
				@Override
				public void setUp() throws Exception {
					beforeTest();
				}
				
				@Override
				public void run() throws Exception {
					test();
				}
			});
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Aggregator percentile = new Percentile(95);
		return percentile.aggregate(results);
	}
	
	
	public void run(int timesToRun, double latencyLimit, int numberOfExecutionsPerTest, int initialRequestsPerMinute, int inititalResoucesQuantity) {
		ScalabilityReport report;
		try {
			setUp();
			report = ScalabilityTesting.run(new LinearIncrease(), timesToRun, latencyLimit, this, "scalabilityTest", initialRequestsPerMinute, inititalResoucesQuantity, numberOfExecutionsPerTest);
			tearDown();
			reports.add(report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showChart() {
		ScalabilityReportChart chart = new ScalabilityReportChart();
		chart.createChart(reports);		
	}

}
