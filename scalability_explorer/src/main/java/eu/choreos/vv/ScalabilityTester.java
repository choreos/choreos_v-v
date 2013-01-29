package eu.choreos.vv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.PlotData;

import eu.choreos.vv.aggregations.AggregationFunction;
import eu.choreos.vv.aggregations.Mean;
import eu.choreos.vv.chart.ScalabilityReportChart;
import eu.choreos.vv.deployment.Deployer;
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

	private int numberOfExecutionsPerStep;
	private Integer numberOfSteps;
	private Double measurementLimit;
	private Number initialRequestsPerMinute;
	private Number inititalResoucesQuantity;

	private LoadGenerator loadGen;
	private AggregationFunction aggregator;
	private ScalabilityFunction scalabilityFunction;
	private Deployer enacter;

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
	 * This method can be overridden to execute before each test
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
	 * This method can be overriden to execute after each test
	 * @throws Exception
	 */
	public void afterTest() throws Exception {
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
		this.scalabilityFunction = function;
		this.numberOfSteps = 1;
		this.numberOfExecutionsPerStep = 1;
		this.measurementLimit = Double.MAX_VALUE;
		this.initialRequestsPerMinute = 60;
		this.inititalResoucesQuantity = 1;
		reports = new ArrayList<ScalabilityReport>();
	}

	public LoadGenerator getLoadGenerator() {
		return loadGen;
	}

	public void setLoadGenerator(LoadGenerator loadGen) {
		this.loadGen = loadGen;
	}

	public AggregationFunction getAggregator() {
		return aggregator;
	}

	public void setAggregator(AggregationFunction aggregator) {
		this.aggregator = aggregator;
	}

	public ScalabilityFunction getScalabilityFunction() {
		return scalabilityFunction;
	}

	public void setScalabilityFunction(ScalabilityFunction function) {
		this.scalabilityFunction = function;
	}

	public Deployer getEnacter() {
		return enacter;
	}

	public void setEnacter(Deployer enacter) {
		this.enacter = enacter;
	}

	public int getNumberOfExecutionsPerStep() {
		return numberOfExecutionsPerStep;
	}

	public void setNumberOfExecutionsPerStep(int numberOfExecutionsPerTest) {
		this.numberOfExecutionsPerStep = numberOfExecutionsPerTest;
	}

	public Integer getNumberOfSteps() {
		return numberOfSteps;
	}

	public void setNumberOfSteps(Integer numberOfTestsToRun) {
		this.numberOfSteps = numberOfTestsToRun;
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
	public List<Number> test(Number... params) throws Exception {
		int requestsPerMinute = params[0].intValue();
		int resourceQuantity = params[1].intValue();
		List<Number> results = new ArrayList<Number>();

		if (enacter != null)
			enacter.scale(resourceQuantity);
		resourceScaling(resourceQuantity);

		results = loadGen.execute(numberOfExecutionsPerStep, requestsPerMinute,
				new LatencyMeasurementExecutable() {
					@Override
					public void setUp() throws Exception {
						beforeTest();
					}

					@Override
					public void experiment() throws Exception {
						test();
						afterTest();
					}
				});

//		return aggregator.aggregate(results);
		return results;
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
		this.numberOfSteps = timesToRun;
		this.measurementLimit = latencyLimit;
		this.initialRequestsPerMinute = initialRequestsPerMinute;
		this.inititalResoucesQuantity = inititalResoucesQuantity;
		
		run(name);
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
				numberOfSteps, measurementLimit, scalabilityFunction);
		scalabilityTest.setInitialParametersValues(initialRequestsPerMinute,
				inititalResoucesQuantity);

		ScalabilityReport report;
		if (enacter != null)
			enacter.enactChoreography();
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
		List <PlotData> plotData = new ArrayList<PlotData>();
		ScalabilityReportChart chart = new ScalabilityReportChart(title,
				"execution", aggregator.getLabel() + " of "
						+ loadGen.getLabel());
		for(ScalabilityReport report: reports) {
			PlotData aggregation = new PlotData();
			aggregation.setName(report.getName().toString());
			for(Number index: report.keySet()) {
				aggregation.put((Double)index, aggregator.aggregate(report.get(index)));
			}
			plotData.add(aggregation);
		}
		
		chart.createChart(plotData);
	}

}
