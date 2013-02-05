package eu.choreos.vv.analysis;

import java.util.List;

import eu.choreos.vv.data.ScalabilityReport;

public abstract class Analyser {
	
	public void analyse(List<ScalabilityReport> reports) throws Exception {
		for(ScalabilityReport report: reports) {
			analyse(report);
		}
	}

	public abstract void analyse(ScalabilityReport report) throws Exception ;

}
