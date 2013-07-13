package eu.choreos.vv.example;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.choreos.vv.aggregations.Mean;
import eu.choreos.vv.analysis.ANOVATest;
import eu.choreos.vv.analysis.AggregatePerformance;
import eu.choreos.vv.analysis.ComposedAnalysis;
import eu.choreos.vv.experiments.ScalabilityExperiment;
import eu.choreos.vv.increasefunctions.LinearIncrease;
import eu.choreos.vv.loadgenerator.FastestLoadGenerator;

public class ScalabilityTesterExample extends ScalabilityExperiment {

	private static final int REQUESTS = 30;
	List<Long> resources;
	long sleepTime;
	int resourceIndex;
	
	long[] timestamps;
	int count;

	@Override
	public void beforeExperiment() {
		resources = new ArrayList<Long>();
		resourceIndex = 0;
	}
	
	@Override
	public void beforeIteration() {
		resources.add(5l);
		timestamps = new long[REQUESTS];
		count = 0;
//		resources.add(Math.round( Math.random() * 5l));
		System.out.println("######### new iteration ##############");
	}

	@Override
	public void beforeRequest() {
//		System.out.println("index " + resourceIndex);
//		System.out.println("resource size " + resources.size());
		sleepTime = resources.get(resourceIndex);
		resourceIndex = resourceIndex < resources.size() - 1 ? resourceIndex + 1
				: 0;
	}

	@Override
	public void request() {
		timestamps[count++] = System.nanoTime();
//		System.out.println("request time " + System.currentTimeMillis());
//		System.out.println("sleep " + sleepTime);
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void afterIteration() {
		for(int i = 1; i < REQUESTS; i++)
			System.out.println(timestamps[i]-timestamps[i-1]);
	}

	public static void execute() throws Exception {
		ScalabilityTesterExample example = new ScalabilityTesterExample();
//		example.setLoadGenerator(new MultiThreadLoadGenerator());
//		example.setLoadGenerator(new DegeneratedLoadGenerator());
		example.setLoadGenerator(new FastestLoadGenerator());
		example.setScalabilityFunctions(new LinearIncrease(100000));
//		example.setScalabilityFunction(new LinearIncrease(1000));
		example.setNumberOfRequestsPerStep(REQUESTS);
		example.setNumberOfSteps(5);
		example.setInitialRequestsPerMinute(100000);
//		example.setInitialRequestsPerMinute(3000);
//		example.setAnalyser(new ANOVATest());
		 example.setAnalyser(new ComposedAnalysis(new ANOVATest(), new AggregatePerformance("Matrix multiplication", new Mean())));

//		 example.run("test1", false);
		example.run("test1");

//		 example.setNumberOfRequestsPerStep(10);
//		 example.setInitialRequestsPerMinute(600);
//		 example.run("test2");
	}

	public static void main(String[] args) throws Exception {
		execute();

	}

	@Test
	public void shouldExecuteWithoutError() throws Exception {
		execute();
	}

}
