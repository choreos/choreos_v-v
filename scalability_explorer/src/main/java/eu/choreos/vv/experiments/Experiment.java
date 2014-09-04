package eu.choreos.vv.experiments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.choreos.vv.Scalable;
import eu.choreos.vv.ScaleCaster;
import eu.choreos.vv.analysis.Analyzer;
import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.deployment.Deployer;
import eu.choreos.vv.experiments.strategy.ExperimentStrategy;
import eu.choreos.vv.loadgenerator.LoadGenerator;
import eu.choreos.vv.loadgenerator.LoadGeneratorFactory;

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
public abstract class Experiment<K, T> implements Scalable {

	private int numberOfRequestsPerStep;
	private int numberOfRequestsPerMinute;
	private Map<String, Object> parameters;
	private Integer numberOfSteps;
	private Double measurementLimit;

	private LoadGenerator<K, T> loadGen;

	private Deployer deployer;
	private Analyzer analyzer;

	private List<ExperimentReport> reports;

	private ExperimentStrategy strategy;

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
	public void beforeIteration() throws Exception {
	}

	/**
	 * This method can be overridden to execute before each request
	 * 
	 * @throws Exception
	 */
	public K beforeRequest() throws Exception {
		return null;
	}

	/**
	 * This method must be overridden in order to execute the proper request
	 * 
	 * @throws Exception
	 */
	public T request(K param) throws Exception {
		return null;
	}

	/**
	 * This method can be overriden to execute after each request
	 * 
	 * @throws Exception
	 */
	public void afterRequest(T param) throws Exception {
	}

	/**
	 * This method can be overriden to execute after each step
	 * 
	 * @throws Expeption
	 */
	public void afterIteration() throws Exception {

	}

	/**
	 * This method can be overriden to execute after the experiment
	 * 
	 * @throws Exception
	 */
	public void afterExperiment() throws Exception {
	}


	/**
	 * Creates a new Experiment
	 * 
	 */
	public Experiment() {
		this.numberOfSteps = 1;
		this.numberOfRequestsPerStep = 1;
		this.measurementLimit = Double.MAX_VALUE;
		reports = new ArrayList<ExperimentReport>();
		parameters = new HashMap<String, Object>();
	}

	private void newLoadGenerator() {
		loadGen = LoadGeneratorFactory.getInstance().<K, T> create();
	}

	public ExperimentStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(ExperimentStrategy strategy) {
		this.strategy = strategy;
		strategy.setExperiment(this);
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

	public Analyzer getAnalyser() {
		return analyzer;
	}

	public void setAnalyser(Analyzer analyser) {
		this.analyzer = analyser;
	}

	protected List<String> getParameterLabels() {
		return strategy.getLabels();
	}
	
	public void setParam(String name, Object value) {
		parameters.put(name, value);
	}
	
	public Object getParam(String name) {
		return parameters.get(name);
	}
	
	@Override
	public ReportData execute(ScaleCaster scaleCaster) throws Exception {
		strategy.updateParameterValues(scaleCaster);

		ReportData report;

		if (deployer != null)
			deployer.scale(parameters);
		beforeIteration();

		newLoadGenerator();
		loadGen.setDelay(60000000000l / numberOfRequestsPerMinute);
		report = loadGen.execute(numberOfRequestsPerStep, this);

		afterIteration();

		return report;
	}

	/**
	 * same as run(name, true, true);
	 * 
	 * @param name
	 *            label to be used in a ScalabilityReportChart
	 * @throws Exception
	 */
	public void run(String name) throws Exception {
		run(name, true, true);
	}

	/**
	 * same as run(name, analyse, true);
	 * 
	 * @param name
	 *            label to be used in a ScalabilityReportChart
	 * @param analyse
	 *            true to perform analysis at the and of the experiment
	 * @throws Exception
	 */
	public void run(String name, boolean analyse) throws Exception {
		run(name, analyse, true);
	}

	/**
	 * Runs a test battery according to current attributes of the
	 * ScalabilityTester
	 * 
	 * @param name
	 *            label to be used in a ScalabilityReportChart
	 * @param analyse
	 *            true to perform analysis at the and of the experiment
	 * @param store
	 *            false discards the experiment report
	 */
	public void run(String name, boolean analyse, boolean store)
			throws Exception {
		ScaleCaster scaleCaster = new ScaleCaster(this, name, numberOfSteps,
				measurementLimit);

		strategy.putInitialParameterValues(scaleCaster);
//		scaleCaster.setInitialParametersValues(getInitialParameterValues());

		ExperimentReport report;
		if (deployer != null)
			deployer.deploy();
		beforeExperiment();
		report = scaleCaster.execute();
		report.setParameterLabels(getParameterLabels());
		report.setMeasurementUnit(loadGen.getLabel());
		afterExperiment();
		if (store)
			reports.add(report);
		if (analyse)
			analyzer.analyse(reports);
	}

}
