package eu.choreos.vv.experiments;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.Scalable;
import eu.choreos.vv.ScalingCaster;
import eu.choreos.vv.analysis.Analyser;
import eu.choreos.vv.data.ScalabilityReport;
import eu.choreos.vv.deployment.Deployer;
import eu.choreos.vv.increasefunctions.LinearIncrease;
import eu.choreos.vv.increasefunctions.ScalabilityFunction;
import eu.choreos.vv.loadgenerator.LoadGenerator;
import eu.choreos.vv.loadgenerator.UniformLoadGenerator;
import eu.choreos.vv.loadgenerator.executable.LatencyMeasurementExecutable;

/**
 * This class implements a skeleton of a scalability experiment consisted on
 * many steps. In each test battery, the frequency of requests and the quantity
 * of resources will be increased according to a ScalabilityFunction. A request
 * is executed a number of times, in each step, and some metrics are collected
 * for analysis. The steps will be executed up to a determined number of
 * executions (or until one's aggregated return value surpasses a defined
 * limit).
 * 
 */
public abstract class Experiment implements Scalable {

	private int numberOfRequestsPerStep;
	private int numberOfRequestsPerMinute;
	private int scaleSize;
	private Integer numberOfSteps;
	private Double measurementLimit;
//	private Number initialRequestsPerMinute;
//	private Number inititalResoucesQuantity;

	private LoadGenerator loadGen;
	private ScalabilityFunction scalabilityFunction;
	private Deployer deployer;
	private Analyser analyser; // TODO: multiple analysers

	private List<ScalabilityReport> reports;

	/**
	 * This method can be overridden to be executed before the experiment begin.
	 * 
	 * @throws Exception
	 */
	public void beforeExperiment() throws Exception {
	}

	/**
	 * This method can be overridden to execute before each step
	 * 
	 * @param resourceQuantity
	 *            current resource quantity
	 * @throws Exception
	 */
	public void beforeStep() throws Exception {
	}

	/**
	 * This method can be overridden to execute before each request
	 * 
	 * @throws Exception
	 */
	public void beforeRequest() throws Exception {
	}

	/**
	 * This method must be overridden in order to execute the proper request
	 * 
	 * @throws Exception
	 */
	public void request() throws Exception {
	}

	/**
	 * This method can be overriden to execute after each request
	 * 
	 * @throws Exception
	 */
	public void afterRequest() throws Exception {
	}

	/**
	 * This method can be overriden to execute after each step
	 * 
	 * @throws Expeption
	 */
	public void afterStep() throws Exception {

	}

	/**
	 * This method can be overriden to execute after the experiment
	 * 
	 * @throws Exception
	 */
	public void afterExperiment() throws Exception {
	}

	/**
	 * Creates a new ScalabilityTester that uses UniformLoadGenarator, Mean and
	 * LinearIncrease
	 */
	public Experiment() {
		this(new UniformLoadGenerator(), new LinearIncrease());
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
	public Experiment(LoadGenerator loadGenerator, ScalabilityFunction function) {
		this.loadGen = loadGenerator;
		this.scalabilityFunction = function;
		this.numberOfSteps = 1;
		this.numberOfRequestsPerStep = 1;
		this.measurementLimit = Double.MAX_VALUE;
//		this.initialRequestsPerMinute = 60;
//		this.inititalResoucesQuantity = 1;
		reports = new ArrayList<ScalabilityReport>();
	}

	public LoadGenerator getLoadGenerator() {
		return loadGen;
	}

	public void setLoadGenerator(LoadGenerator loadGen) {
		this.loadGen = loadGen;
	}

	public ScalabilityFunction getScalabilityFunction() {
		return scalabilityFunction;
	}

	public void setScalabilityFunction(ScalabilityFunction function) {
		this.scalabilityFunction = function;
	}

	public Deployer getDeployer() {
		return deployer;
	}

	public void setDeployer(Deployer enacter) {
		this.deployer = enacter;
	}

	public int getNumberOfRequestsPerStep() {
		return numberOfRequestsPerStep;
	}

	public void setNumberOfRequestsPerStep(int number) {
		this.numberOfRequestsPerStep = number;
	}

	public int getNumberOfRequestsPerMinute() {
		return numberOfRequestsPerMinute;
	}
	
	public void setNumberOfRequestsPerMinute(int number) {
		this.numberOfRequestsPerMinute = number;
	}
	
	public int getScaleSize() {
		return scaleSize;
	}

	public void setScaleSize(int scaleSize) {
		this.scaleSize = scaleSize;
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

//	public Number getInitialRequestsPerMinute() {
//		return initialRequestsPerMinute;
//	}
//
//	public void setInitialRequestsPerMinute(Number initialRequestsPerMinute) {
//		this.initialRequestsPerMinute = initialRequestsPerMinute;
//	}
//
//	public Number getInititalResoucesQuantity() {
//		return inititalResoucesQuantity;
//	}
//
//	public void setInititalResoucesQuantity(Number inititalResoucesQuantity) {
//		this.inititalResoucesQuantity = inititalResoucesQuantity;
//	}

	public Analyser getAnalyser() {
		return analyser;
	}

	public void setAnalyser(Analyser analyser) {
		this.analyser = analyser;
	}
	
	protected abstract Number[] setInitialParameterValues();
	
	protected abstract void getParameterValues(Number... values);
	
	protected abstract List<String> getParameterLabels();

	@Override
	public List<Number> execute(Number... params) throws Exception {
//		int requestsPerMinute = params[0].intValue();
//		int resourceQuantity = params[1].intValue();
		getParameterValues(params);
		
		List<Number> results = new ArrayList<Number>();

		if (deployer != null)
			deployer.scale(getScaleSize());
		beforeStep();

		results = loadGen.execute(numberOfRequestsPerStep, numberOfRequestsPerMinute,
				new LatencyMeasurementExecutable() {
					@Override
					public void setUp() throws Exception {
						beforeRequest();
					}

					@Override
					public void experiment() throws Exception {
						request();
						afterRequest();
					}
				});
		
		afterStep();

		// return aggregator.aggregate(results);
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
//	public void run(String name, int timesToRun, double latencyLimit,
//			int numberOfExecutionsPerTest, int initialRequestsPerMinute,
//			int inititalResoucesQuantity) throws Exception {
//		this.numberOfSteps = timesToRun;
//		this.measurementLimit = latencyLimit;
//		this.initialRequestsPerMinute = initialRequestsPerMinute;
//		this.inititalResoucesQuantity = inititalResoucesQuantity;
//
//		run(name);
//	}
//
//	public void run(String name, int timesToRun, int numberOfExecutionsPerTest,
//			int initialRequestsPerMinute, int inititalResoucesQuantity)
//			throws Exception {
//		run(name, timesToRun, Double.MAX_VALUE, numberOfExecutionsPerTest,
//				initialRequestsPerMinute, inititalResoucesQuantity);
//	}
	
	public void run(String name) throws Exception {
		run(name, true);
	}

	/**
	 * Runs a test battery according to current attributes of the
	 * ScalabilityTester
	 * 
	 * @param name
	 *            label to be used in a ScalabilityReportChart
	 */
	public void run(String name, boolean analyse) throws Exception {
		ScalingCaster scalingCaster = new ScalingCaster(this, name,
				numberOfSteps, measurementLimit, scalabilityFunction);
		
//		scalingCaster.setInitialParametersValues(initialRequestsPerMinute, inititalResoucesQuantity);
		scalingCaster.setInitialParametersValues(setInitialParameterValues());

		ScalabilityReport report;
		if (deployer != null)
			deployer.enactChoreography();
		beforeExperiment();
		report = scalingCaster.executeIncreasingParams();
		report.setParameterLabels(getParameterLabels());
		report.setMeasurementUnit(loadGen.getLabel());
		afterExperiment();
		reports.add(report);
		if (analyse)
			analyser.analyse(reports);
	}

}
