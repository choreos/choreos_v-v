package eu.choreos.vv.analysis;

import java.util.List;

import eu.choreos.vv.data.ScalabilityReport;

public interface Analyser {
	
	public void analyse(List<ScalabilityReport> reports) throws Exception;

}
