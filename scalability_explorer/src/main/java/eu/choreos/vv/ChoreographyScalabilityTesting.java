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

public abstract class ChoreographyScalabilityTesting {
	
	public abstract void setUp();

	public abstract void resourceScaling(int resourceQuantity);
	
	public abstract void beforeTest();
	
	public abstract void test();
	
	public abstract void tearDown();
	
	public double scalabilityTest(@Scale int requestsPerMinute, @Scale(chartDomain=true) int resourceQuantity, int numberOfExecutions) {
		LoadGenerator loadGen = new UniformLoadGenerator();
		List<Double> results = new ArrayList<Double>();

		resourceScaling(resourceQuantity);
		
		try {
			results = loadGen.execute(numberOfExecutions, requestsPerMinute, new LatencyMeasurementExecutable() {
				@Override
				public void setUp() {
					beforeTest();
				}
				
				@Override
				public void run() {
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
			ScalabilityReportChart chart = new ScalabilityReportChart();
			ArrayList<ScalabilityReport> list = new ArrayList<ScalabilityReport>();
			list.add(report);
			chart.createChart(list);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
