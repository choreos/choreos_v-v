package eu.choreos.vv.analysis;

import java.util.List;

import eu.choreos.vv.data.ExperimentReport;

public abstract class Analyzer {
	
	public void analyse(List<ExperimentReport> reports) throws Exception {
		for(ExperimentReport report: reports) {
			analyse(report);
		}
	}

	public abstract void analyse(ExperimentReport report) throws Exception ;

}
