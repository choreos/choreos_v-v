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
import eu.choreos.vv.loadgenerator.LoadGeneratorFactory;
import eu.choreos.vv.loadgenerator.strategy.LoadGenerationStrategy;
import eu.choreos.vv.loadgenerator.strategy.NullStrategy;
import eu.choreos.vv.loadgenerator.strategy.TruncatedNormalLoad;

public class ScalabilityTestExample extends ScalabilityExperiment <Long, Long>{

	private static final int REQUESTS = 30;
	List<Long> resources;
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
	public Long beforeRequest() {
//		System.out.println("index " + resourceIndex);
//		System.out.println("resource size " + resources.size());
		Long sleepTime = resources.get(resourceIndex);
		resourceIndex = resourceIndex < resources.size() - 1 ? resourceIndex + 1
				: 0;
		return sleepTime;
	}

	@Override
	public Long request(Long param) {
		timestamps[count++] = System.nanoTime();
//		System.out.println("request time " + System.currentTimeMillis());
//		System.out.println("sleep " + sleepTime);
		try {
			Thread.sleep(param);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void afterIteration() {
		for(int i = 1; i < REQUESTS; i++)
			System.out.println(timestamps[i]-timestamps[i-1]);
	}

	public static void execute() throws Exception {
		ScalabilityTestExample example = new ScalabilityTestExample();
		TruncatedNormalLoad strategy = new TruncatedNormalLoad();
		strategy.setLowerBound(500);
		strategy.setUpperBound(500000000);
		strategy.setStandardDeviation(1000);
		LoadGeneratorFactory.getInstance().setStrategy(strategy);
		example.setScalabilityFunctions(new LinearIncrease(10000));
		example.setNumberOfRequestsPerStep(REQUESTS);
		example.setNumberOfSteps(5);
		example.setInitialRequestsPerMinute(100000);
		example.setAnalyser(new ComposedAnalysis(new ANOVATest(), new AggregatePerformance("Matrix multiplication", new Mean())));

		example.run("test1");

	}

	public static void main(String[] args) throws Exception {
		execute();

	}

	@Test
	public void shouldExecuteWithoutError() throws Exception {
		execute();
	}

}
