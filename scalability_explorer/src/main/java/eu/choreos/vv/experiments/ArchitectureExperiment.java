package eu.choreos.vv.experiments;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.increasefunctions.ScalabilityFunction;
import eu.choreos.vv.loadgenerator.LoadGenerator;

/**
 * This class implements a skeleton of a infrastructure experiment consisted on
 * many steps. In each test battery, the frequency of requests and the quantity
 * of resources will be increased according to a ScalabilityFunction. A request
 * is executed a number of times, in each step, and some metrics are collected
 * for analysis. The steps will be executed up to a determined number of
 * executions (or until one's aggregated return value surpasses a defined
 * limit).
 * 
 */
public abstract class ArchitectureExperiment <K, T> extends Experiment <K, T> {

	private Number inititalResoucesQuantity;
	private int requestsPerMinute;


	/**
	 * Creates a new ScalabilityTester that uses UniformLoadGenarator, Mean and
	 * LinearIncrease
	 */
	public ArchitectureExperiment() {
		super();
		init();
	}

	private void init() {
		this.inititalResoucesQuantity = 1;
		List<String> labels = new ArrayList<String>();
		labels.add("size");
		super.setParameterLabels(labels);
	}

	public Number getInititalResoucesQuantity() {
		return inititalResoucesQuantity;
	}

	public void setInititalResoucesQuantity(Number inititalResoucesQuantity) {
		this.inititalResoucesQuantity = inititalResoucesQuantity;
	}
	
	public int getRequestsPerMinute() {
		return requestsPerMinute;
	}

	public void setRequestsPerMinute(int requestsPerMinute) {
		this.requestsPerMinute = requestsPerMinute;
	}

	@Override
	protected Number[] getInitialParameterValues() {
		Number[] values = new Number[1];
		values[0] = inititalResoucesQuantity;
		return values;
	}
	
	@Override
	protected void updateParameterValues(Number... values) {
		super.setNumberOfRequestsPerMinute(requestsPerMinute);
		super.setScaleSize(values[0].intValue());
	}
	
}