package eu.choreos.vv;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.aggregations.Aggregator;
import eu.choreos.vv.aggregations.Mean;
import eu.choreos.vv.chart.ScalabilityReportChart;
import eu.choreos.vv.increasefunctions.LinearIncrease;
import eu.choreos.vv.increasefunctions.ScalabilityFunction;
import eu.choreos.vv.loadgenerator.LoadGenerator;
import eu.choreos.vv.loadgenerator.UniformLoadGenerator;
import eu.choreos.vv.loadgenerator.executable.LatencyMeasurementExecutable;

public abstract class ScalabilityTester implements ScalabilityTestItem {

	private int numberOfExecutions;
	private LoadGenerator loadGen;
	private Aggregator aggregator;
	private ScalabilityFunction function;

	private Exception lastException;

	private List<ScalabilityReport> reports;

	public void setUp() throws Exception {
	}

	public void resourceScaling(int resourceQuantity) throws Exception {
	}

	public void beforeTest() throws Exception {
	}

	public void test() throws Exception {
	}

	public void tearDown() throws Exception {
	}

	public ScalabilityTester() {
		this(new UniformLoadGenerator(), new Mean(), new LinearIncrease());
	}

	public ScalabilityTester(LoadGenerator loadGenerator,
			Aggregator aggregator, ScalabilityFunction function) {
		this.loadGen = loadGenerator;
		this.aggregator = aggregator;
		this.function = function;
		reports = new ArrayList<ScalabilityReport>();
	}

	public LoadGenerator getLoadGen() {
		return loadGen;
	}

	public void setLoadGen(LoadGenerator loadGen) {
		this.loadGen = loadGen;
	}

	public Aggregator getAggregator() {
		return aggregator;
	}

	public void setAggregator(Aggregator aggregator) {
		this.aggregator = aggregator;
	}

	public ScalabilityFunction getFunction() {
		return function;
	}

	public void setFunction(ScalabilityFunction function) {
		this.function = function;
	}

	@Override
	public double test(Number... params) throws Exception {
		int requestsPerMinute = params[0].intValue();
		int resourceQuantity = params[1].intValue();
		List<Number> results = new ArrayList<Number>();

		resourceScaling(resourceQuantity);

		results = loadGen.execute(numberOfExecutions, requestsPerMinute,
				new LatencyMeasurementExecutable() {
					@Override
					public void setUp() throws Exception {
						beforeTest();
					}

					@Override
					public void experiment() throws Exception {
						test();
					}
				});

		return aggregator.aggregate(results);
	}

	public boolean run(String name, int timesToRun, double latencyLimit,
			int numberOfExecutionsPerTest, int initialRequestsPerMinute,
			int inititalResoucesQuantity) {
		numberOfExecutions = numberOfExecutionsPerTest;
		ScalabilityTest scalabilityTest = new ScalabilityTest(this, name,
				timesToRun, latencyLimit, function);
		scalabilityTest.setInitialParametersValues(initialRequestsPerMinute,
				inititalResoucesQuantity);

		ScalabilityReport report;
		try {
			setUp();
			report = scalabilityTest.executeIncreasingParams();
			tearDown();
			reports.add(report);
		} catch (Exception e) {
			lastException = e;
			return false;
		}
		return true;
	}

	public boolean run(String name, int timesToRun,
			int numberOfExecutionsPerTest, int initialRequestsPerMinute,
			int inititalResoucesQuantity) {
		return run(name, timesToRun, Double.MAX_VALUE,
				numberOfExecutionsPerTest, initialRequestsPerMinute,
				inititalResoucesQuantity);
	}

	public void showChart(String title) {
		ScalabilityReportChart chart = new ScalabilityReportChart("Scalability explorer", title, "execution", aggregator.getLabel() + " of " + loadGen.getLabel());
		chart.createChart(reports);
	}

	public Exception getLastException() {
		return lastException;
	}

}
