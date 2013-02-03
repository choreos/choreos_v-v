package eu.choreos.vv.example;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.choreos.vv.aggregations.Percentile;
import eu.choreos.vv.analysis.SimpleChart;
import eu.choreos.vv.experiments.ScalabilityExperiment;

public class ScalabilityTesterExample extends ScalabilityExperiment {

	List<Integer> resources;
	long sleepTime;
	int resourceIndex;

	@Override
	public void beforeExperiment() {
		resources = new ArrayList<Integer>();
		resourceIndex = 0;
	}
	
	@Override
	public void beforeStep() {
		resources.add(300);
	}

	@Override
	public void beforeRequest() {
		sleepTime = resources.get(resourceIndex);
		resourceIndex = resourceIndex < resources.size() - 1 ? resourceIndex + 1
				: 0;
	}

	@Override
	public void request() {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void afterExperiment() {
	}

	public static void execute() throws Exception {
		ScalabilityTesterExample example = new ScalabilityTesterExample();
		example.setNumberOfRequestsPerStep(10);
		example.setNumberOfSteps(10);
		example.setInitialRequestsPerMinute(600);
		 example.setAnalyser(new SimpleChart("simple test", new
		 Percentile(75)));
//		example.setAnalyser(new ANOVATest());

//		 example.run("test1", false);
		example.run("test1");

//		 example.run("test2", 10, 10, 60, 3);
	}

	public static void main(String[] args) throws Exception {
		execute();

	}

	@Test
	public void shouldExecuteWithoutError() throws Exception {
		execute();
	}

}
