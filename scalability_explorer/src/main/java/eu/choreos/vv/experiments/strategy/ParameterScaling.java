package eu.choreos.vv.experiments.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.choreos.vv.ScaleCaster;

public class ParameterScaling extends ExperimentStrategy {

	private String name;
	
	public ParameterScaling(String param) {
		name = param;
		labels.add(name);
	}
	
	@Override
	public void updateParameterValues(ScaleCaster scaleCaster) {
		getExperiment().setParam(name, this.getCurrentParameterValue(scaleCaster));

	}

}
