package eu.choreos.vv.analysis;

import java.util.List;

import eu.choreos.vv.data.ExperimentReport;

public class ComposedAnalysis extends Analyzer {

	private Analyzer[] analysers;
	
	public ComposedAnalysis(Analyzer... analysers) {
		this.analysers = analysers;
	}
	
	@Override
	public void analyse(List<ExperimentReport> reports) throws Exception {
		for(Analyzer analyser: analysers) {
			analyser.analyse(reports);
		}
	}
	
	@Override
	public void analyse(ExperimentReport report) throws Exception {
		for(Analyzer analyser: analysers) {
			analyser.analyse(report);
		}
	}

}
