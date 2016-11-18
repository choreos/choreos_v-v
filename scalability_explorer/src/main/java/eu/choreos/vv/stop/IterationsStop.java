package eu.choreos.vv.stop;

import eu.choreos.vv.data.ExperimentReport;

public class IterationsStop implements StopCriterion {

	private Integer iterations;

	public IterationsStop(Integer iterations) {
		this.iterations = iterations;
	}
	
	@Override
	public boolean stop(ExperimentReport report) {
		return report.keySet().size() >= iterations;
		
	}

}
