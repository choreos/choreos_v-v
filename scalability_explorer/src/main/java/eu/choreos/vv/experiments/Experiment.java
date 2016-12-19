package eu.choreos.vv.experiments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.choreos.vv.Scalable;
import eu.choreos.vv.ScaleCaster;
import eu.choreos.vv.analysis.Analyzer;
import eu.choreos.vv.client.Client;
import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.deployment.Deployer;
import eu.choreos.vv.experiments.strategy.ExperimentStrategy;
import eu.choreos.vv.stop.IterationsStop;
import eu.choreos.vv.stop.StopCriterion;

/**
 * This class implements a skeleton of a scalability experiment consisted on
 * many Iterations. In each test battery, the frequency of requests and the quantity
 * of resources will be increased according to a ScalabilityFunction. A request
 * is executed a number of times, in each Iteration, and some metrics are collected
 * for analysis. The Iterations will be executed up to a determined number of
 * executions (or until one's aggregated return value surpasses a defined
 * limit).
 * 
 */
public abstract class Experiment implements Scalable {

	private int numberOfRequestsPerIteration;
	private int numberOfRequestsPerMinute;
	private Map<String, Object> parameters;
	private StopCriterion criteria;
	//private LoadGenerator<K, T> loadGen;
	private Client client;
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
		this.numberOfRequestsPerIteration = 1;
		this.criteria = new IterationsStop(1);
		reports = new ArrayList<ExperimentReport>();
		parameters = new HashMap<String, Object>();
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
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client; 
	}

	public int getNumberOfRequestsPerIteration() {
		return numberOfRequestsPerIteration;
	}

	public void setNumberOfRequestsPerIteration(int number) {
		this.numberOfRequestsPerIteration = number;
	}

	public int getNumberOfRequestsPerMinute() {
		return numberOfRequestsPerMinute;
	}

	public void setNumberOfRequestsPerMinute(int number) {
		this.numberOfRequestsPerMinute = number;
	}

	public Analyzer getAnalyser() {
		return analyzer;
	}

	public void setAnalyser(Analyzer analyser) {
		this.analyzer = analyser;
	}

	public StopCriterion getStoppingCriteria() {
		return criteria;
	}

	public void setStoppingCriteria(StopCriterion criteria) {
		this.criteria = criteria;
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
		strategy.onUpdateParameterValue(scaleCaster);

		ReportData report;

		if (deployer != null)
			deployer.scale(parameters);
		//beforeIteration();

		//newLoadGenerator();
		//loadGen.setDelay(60000000000l / numberOfRequestsPerMinute);
		report = client.execute(numberOfRequestsPerIteration, 60000000000l / numberOfRequestsPerMinute, parameters);

		//afterIteration();

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
		beforeExperiment();

		ScaleCaster scaleCaster = new ScaleCaster(this, name, criteria);

		strategy.putInitialParameterValues(scaleCaster);

		ExperimentReport report;
		if (deployer != null)
			deployer.deploy();
		report = scaleCaster.execute();
		report.setParameterLabels(getParameterLabels());
		report.setMeasurementUnit(client.getLabel());
		afterExperiment();
		if (store)
			reports.add(report);
		if (analyse)
			analyzer.analyse(reports);
	}

}
