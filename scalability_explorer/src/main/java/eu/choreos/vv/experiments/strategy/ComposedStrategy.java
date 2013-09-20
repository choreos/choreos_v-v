package eu.choreos.vv.experiments.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.choreos.vv.ScaleCaster;
import eu.choreos.vv.experiments.Experiment;

public class ComposedStrategy extends ExperimentStrategy {

	private List<ExperimentStrategy> strategies;
	
	public ComposedStrategy(ExperimentStrategy... strategies) {
		this.strategies = new ArrayList<ExperimentStrategy>();
		Collections.addAll(this.strategies, strategies);
	}

	@Override
	public void updateParameterValues(ScaleCaster scaleCaster) {
		for (ExperimentStrategy strategy : strategies) {
			strategy.updateParameterValues(scaleCaster);
		}
	}

	@Override
	public void putInitialParameterValues(ScaleCaster scaleCaster) {
		for (ExperimentStrategy strategy : strategies) {
			strategy.putInitialParameterValues(scaleCaster);
		}
	}

	@Override
	public void setExperiment(Experiment experiment) {
		super.setExperiment(experiment);
		for (ExperimentStrategy strategy : strategies) {
			strategy.setExperiment(experiment);
		}
	}
	
	@Override
	public List<String> getLabels() {
		labels.clear();
		for (ExperimentStrategy strategy : strategies) {
			labels.addAll(strategy.getLabels());
		}
		return labels;
	}

}
