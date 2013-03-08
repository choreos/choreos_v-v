package eu.choreos.vv.analysis;

import java.util.List;

import eu.choreos.vv.data.ScalabilityReport;

public class ComposedAnalysis extends Analyzer {

	private Analyzer[] analysers;
	
	public ComposedAnalysis(Analyzer... analysers) {
		this.analysers = analysers;
	}
	
	@Override
	public void analyse(List<ScalabilityReport> reports) throws Exception {
		for(Analyzer analyser: analysers) {
			analyser.analyse(reports);
		}
	}
	
	@Override
	public void analyse(ScalabilityReport report) throws Exception {
		for(Analyzer analyser: analysers) {
			analyser.analyse(report);
		}
	}

}
