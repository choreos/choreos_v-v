package eu.choreos.vv;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.aggregations.AggregationFunction;
import eu.choreos.vv.aggregations.Mean;
import eu.choreos.vv.chart.ScalabilityReportChart;
import eu.choreos.vv.increasefunctions.LinearIncrease;
import eu.choreos.vv.increasefunctions.ScalabilityFunction;
import eu.choreos.vv.loadgenerator.LoadGenerator;
import eu.choreos.vv.loadgenerator.UniformLoadGenerator;
import eu.choreos.vv.loadgenerator.executable.LatencyMeasurementExecutable;

/**
 * This class implements a skeleton of a scalability test in which multiple test
 * batteries will be executed. In each test battery, the frequency of requests
 * and the quantity of resources will be increased according to a
 * ScalabilityFunction. The test are executed a number of times, in each
 * battery, and the return values are aggregated accordingly. Test batteries
 * will be executed up to a determined number of executions or until one's
 * aggregated return value surpasses a defined limit.
 * 
 */
public abstract class ScalabilityTester implements ScalabilityTestItem {

	private int numberOfExecutionsPerTest;
	private Integer numberOfTestsToRun;
	private Double measurementLimit;
	private Number initialRequestsPerMinute;
	private Number inititalResoucesQuantity;

	private LoadGenerator loadGen;
	private AggregationFunction aggregator;
	private ScalabilityFunction function;

	private Exception lastException;

	private List<ScalabilityReport> reports;

	/**
	 * This method can be overridden to be executed before all the test
	 * batteries.
	 * 
	 * @throws Exception
	 */
	public void setUp() throws Exception {
	}

	/**
	 * This method can be overridden to define how to properly scale the
	 * resources. Is is executed before each test battery.
	 * 
	 * @param resourceQuantity
	 *            current resource quantity
	 * @throws Exception
	 */
	public void resourceScaling(int resourceQuantity) throws Exception {
	}

	/**
	 * This method can be overridden to execute before each experiment
	 * 
	 * @throws Exception
	 */
	public void beforeTest() throws Exception {
	}

	/**
	 * This method must be overridden in order to execute the proper test
	 * 
	 * @throws Exception
	 */
	public void test() throws Exception {
	}

	/**
	 * This method can be overriden to execute after all test batteries
	 * 
	 * @throws Exception
	 */
	public void tearDown() throws Exception {
	}

	/**
	 * Creates a new ScalabilityTester that uses UniformLoadGenarator, Mean and
	 * LinearIncrease
	 */
	public ScalabilityTester() {
		this(new UniformLoadGenerator(), new Mean(), new LinearIncrease());
	}

	/**
	 * Creates a new ScalabilityTester
	 * 
	 * @param loadGenerator
	 *            load generator to run the tests
	 * @param aggregator
	 *            aggregation function
	 * @param function
	 *            scalability function
	 */
	public ScalabilityTester(LoadGenerator loadGenerator,
			AggregationFunction aggregator, ScalabilityFunction function) {
		this.loadGen = loadGenerator;
		this.aggregator = aggregator;
		this.function = function;
		this.numberOfTestsToRun = 1;
		this.numberOfExecutionsPerTest = 1;
		this.measurementLimit = Double.MAX_VALUE;
		this.initialRequestsPerMinute = 60;
		this.inititalResoucesQuantity = 1;
		reports = new ArrayList<ScalabilityReport>();
	}

	public LoadGenerator getLoadGen() {
		return loadGen;
	}

	public void setLoadGen(LoadGenerator loadGen) {
		this.loadGen = loadGen;
	}

	public AggregationFunction getAggregator() {
		return aggregator;
	}

	public void setAggregator(AggregationFunction aggregator) {
		this.aggregator = aggregator;
	}

	public ScalabilityFunction getFunction() {
		return function;
	}

	public void setFunction(ScalabilityFunction function) {
		this.function = function;
	}

	public int getNumberOfExecutionsPerTest() {
		return numberOfExecutionsPerTest;
	}

	public void setNumberOfExecutionsPerTest(int numberOfExecutionsPerTest) {
		this.numberOfExecutionsPerTest = numberOfExecutionsPerTest;
	}

	public Integer getNumberOfTestsToRun() {
		return numberOfTestsToRun;
	}

	public void setNumberOfTestsToRun(Integer numberOfTestsToRun) {
		this.numberOfTestsToRun = numberOfTestsToRun;
	}

	public Double getMeasurementLimit() {
		return measurementLimit;
	}

	public void setMeasurementLimit(Double measurementLimit) {
		this.measurementLimit = measurementLimit;
	}

	public Number getInitialRequestsPerMinute() {
		return initialRequestsPerMinute;
	}

	public void setInitialRequestsPerMinute(Number initialRequestsPerMinute) {
		this.initialRequestsPerMinute = initialRequestsPerMinute;
	}

	public Number getInititalResoucesQuantity() {
		return inititalResoucesQuantity;
	}

	public void setInititalResoucesQuantity(Number inititalResoucesQuantity) {
		this.inititalResoucesQuantity = inititalResoucesQuantity;
	}

	@Override
	public double test(Number... params) throws Exception {
		int requestsPerMinute = params[0].intValue();
		int resourceQuantity = params[1].intValue();
		List<Number> results = new ArrayList<Number>();

		resourceScaling(resourceQuantity);

		results = loadGen.execute(numberOfExecutionsPerTest, requestsPerMinute,
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

	/**
	 * runs a test battery according to the parameters
	 * 
	 * @param name
	 *            name to identify the battery - used by ScalabilityReportChart
	 * @param timesToRun
	 *            maximum number of test batteries to be executed
	 * @param latencyLimit
	 *            maximum return value allowed for a test battery
	 * @param numberOfExecutionsPerTest
	 *            number of executions in each test battery
	 * @param initialRequestsPerMinute
	 *            initial frequency of requests
	 * @param inititalResoucesQuantity
	 *            initial amount of resources
	 */
	public void run(String name, int timesToRun, double latencyLimit,
			int numberOfExecutionsPerTest, int initialRequestsPerMinute,
			int inititalResoucesQuantity) throws Exception {
		this.numberOfExecutionsPerTest = numberOfExecutionsPerTest;
		ScalabilityTest scalabilityTest = new ScalabilityTest(this, name,
				timesToRun, latencyLimit, function);
		scalabilityTest.setInitialParametersValues(initialRequestsPerMinute,
				inititalResoucesQuantity);

		ScalabilityReport report;
		setUp();
		report = scalabilityTest.executeIncreasingParams();
		tearDown();
		reports.add(report);
	}

	public void run(String name, int timesToRun, int numberOfExecutionsPerTest,
			int initialRequestsPerMinute, int inititalResoucesQuantity)
			throws Exception {
		run(name, timesToRun, Double.MAX_VALUE, numberOfExecutionsPerTest,
				initialRequestsPerMinute, inititalResoucesQuantity);
	}

	/**
	 * Runs a test battery according to current attributes of the
	 * ScalabilityTester
	 * 
	 * @param name
	 *            label to be used in a ScalabilityReportChart
	 */
	public void run(String name) throws Exception {
		ScalabilityTest scalabilityTest = new ScalabilityTest(this, name,
				numberOfTestsToRun, measurementLimit, function);
		scalabilityTest.setInitialParametersValues(initialRequestsPerMinute,
				inititalResoucesQuantity);

		ScalabilityReport report;
		setUp();
		report = scalabilityTest.executeIncreasingParams();
		tearDown();
		reports.add(report);
	}

	/**
	 * Presents a chart with the results of all test batteries executed by this
	 * ScalabilityTester
	 * 
	 * @param title
	 *            chart title
	 */
	public void showChart(String title) {
		ScalabilityReportChart chart = new ScalabilityReportChart(title,
				"execution", aggregator.getLabel() + " of "
						+ loadGen.getLabel());
		chart.createChart(reports);
	}

	/**
	 * returns the last exception caught during tests
	 * 
	 * @return an Exception
	 */
	public Exception getLastException() {
		return lastException;
	}

}
