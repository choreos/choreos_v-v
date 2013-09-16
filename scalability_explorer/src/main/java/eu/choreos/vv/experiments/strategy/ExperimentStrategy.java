package eu.choreos.vv.experiments.strategy;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.ScaleCaster;
import eu.choreos.vv.experiments.Experiment;
import eu.choreos.vv.increasefunctions.ScalabilityFunction;

public abstract class ExperimentStrategy {

	private Experiment experiment;
	private String parameterKey;
	private Number parameterInitialValue;
	private ScalabilityFunction function;
	protected List<String> labels = new ArrayList<String>();

	
	public Experiment getExperiment() {
		return experiment;
	}

	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}
	
	public Number getParameterInitialValue() {
		return parameterInitialValue;
	}

	public void setParameterInitialValue(Number parameterInitialValue) {
		this.parameterInitialValue = parameterInitialValue;
	}

	public ScalabilityFunction getFunction() {
		return function;
	}

	public void setFunction(ScalabilityFunction function) {
		this.function = function;
	}

	public void putInitialParameterValues(ScaleCaster scaleCaster) {
		parameterKey = scaleCaster.addInitialParameterValue(parameterInitialValue, function);
	}
	
	protected Number getCurrentParameterValue(ScaleCaster scaleCaster) {
		return scaleCaster.getCurrentParameterValue(parameterKey);
	}

	public abstract void updateParameterValues(ScaleCaster scaleCaster);
	
	public List<String> getLabels() {
		return labels;
	}
	
}
