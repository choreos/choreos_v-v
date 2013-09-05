package eu.choreos.vv.loadgenerator;

import java.util.List;

import eu.choreos.vv.experiments.Experiment;

public class LoadGeneratorThread implements Runnable {

	
	
	private LoadGenerator loadGenerator;
	private int requests;
	private Experiment experiment;
	private List<Number> results;

	public LoadGeneratorThread(LoadGenerator loadGenerator, int requests, Experiment experiment, List<Number> results) {
		this.loadGenerator = loadGenerator;
		this.requests = requests;
		this.experiment = experiment;
		this.results = results;
	}
	
	@Override
	public void run() {
		try {
			List<Number> execute = loadGenerator.execute(requests, experiment);
			results.addAll(execute);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
