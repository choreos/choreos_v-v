package eu.choreos.vv.analysis;

import java.util.List;

import eu.choreos.vv.ScalabilityReport;

public interface Analyser {
	
	public void analyse(List<ScalabilityReport> reports, String unitLabel) throws Exception;

}
