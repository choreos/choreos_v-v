package eu.choreos.vv.experiments.strategy;

import eu.choreos.vv.ScaleCaster;

public class WorkloadScaling extends ExperimentStrategy {
	
	
	public WorkloadScaling() {
		labels.add("load");
	}

	@Override
	public void onUpdateParameterValue(ScaleCaster scaleCaster) {
		getExperiment().setNumberOfRequestsPerMinute(getCurrentParameterValue(scaleCaster).intValue());
	}

}
