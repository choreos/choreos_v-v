package eu.choreos.vv.experiments;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.ScaleCaster;
import eu.choreos.vv.analysis.Analyzer;
import eu.choreos.vv.data.ScalabilityReport;
import eu.choreos.vv.deployment.Deployer;
import eu.choreos.vv.increasefunctions.LinearIncrease;
import eu.choreos.vv.increasefunctions.ScalabilityFunction;
import eu.choreos.vv.loadgenerator.LoadGenerator;
import eu.choreos.vv.loadgenerator.DegeneratedLoadGenerator;
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
public abstract class ScalabilityExperiment extends Experiment {

	private Number initialRequestsPerMinute;
	private Number inititalResoucesQuantity;


	/**
	 * Creates a new ScalabilityTester that uses UniformLoadGenarator, Mean and
	 * LinearIncrease
	 */
	public ScalabilityExperiment() {
		super();
		init();
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
	public ScalabilityExperiment(LoadGenerator loadGenerator, ScalabilityFunction function) {
		super(loadGenerator, function);
		init();
	}
	
	private void init() {
		this.initialRequestsPerMinute = 60;
		this.inititalResoucesQuantity = 1;
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
	protected Number[] setInitialParameterValues() {
		Number[] values = new Number[2];
		values[0] = initialRequestsPerMinute;
		values[1] = inititalResoucesQuantity;
		return values;
	}
	
	@Override
	protected void getParameterValues(Number... values) {
		super.setNumberOfRequestsPerMinute(values[0].intValue());
		super.setScaleSize(values[1].intValue());
	}
	
	@Override
	protected List<String> getParameterLabels() {
		List<String> labels = new ArrayList<String>();
		labels.add("load");
		labels.add("size");
		return labels;
	}
	
}
