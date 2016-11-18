package eu.choreos.vv.stop;

import eu.choreos.vv.data.ExperimentReport;

public interface StopCriterion {
	
	public boolean stop(ExperimentReport report);

}
