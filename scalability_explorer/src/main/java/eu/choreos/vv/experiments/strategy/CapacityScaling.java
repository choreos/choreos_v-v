package eu.choreos.vv.experiments.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.choreos.vv.ScaleCaster;

public class CapacityScaling extends ExperimentStrategy {

	public CapacityScaling() {
		labels.add("capacity");
	}
	
	@Override
	public void updateParameterValues(ScaleCaster scaleCaster) {
		getExperiment().setScaleSize(this.getCurrentParameterValue(scaleCaster).intValue());

	}

}
