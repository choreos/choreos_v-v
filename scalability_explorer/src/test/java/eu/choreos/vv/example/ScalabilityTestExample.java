package eu.choreos.vv.example;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.choreos.vv.analysis.AggregatePerformance;
import eu.choreos.vv.chart.creator.PercentileChartCreator;
import eu.choreos.vv.client.Client;
import eu.choreos.vv.experiments.Experiment;
import eu.choreos.vv.experiments.strategy.ComposedStrategy;
import eu.choreos.vv.experiments.strategy.ExperimentStrategy;
import eu.choreos.vv.experiments.strategy.ParameterScaling;
import eu.choreos.vv.experiments.strategy.WorkloadScaling;
import eu.choreos.vv.increasefunctions.ExponentialIncrease;
import eu.choreos.vv.increasefunctions.LinearIncrease;
import eu.choreos.vv.stop.IterationsStop;

public class ScalabilityTestExample extends Experiment {
	public static final int REQUESTS = 10;
	
	List<Long> resources = new ArrayList<Long>();
	
	
	@Override
	public void beforeExperiment() {
		ExperimentStrategy estrategy = new WorkloadScaling();
		estrategy.setFunction(new LinearIncrease(100));
		estrategy.setParameterInitialValue(100);

		ExperimentStrategy cstrategy = new ParameterScaling("a");
		cstrategy.setFunction(new ExponentialIncrease(2));
		cstrategy.setParameterInitialValue(1);

		ExperimentStrategy strategy = new ComposedStrategy(estrategy, cstrategy);

		this.setClient(new TheClient(resources));
		this.setStrategy(strategy);
		this.setNumberOfRequestsPerIteration(REQUESTS);
//		this.setStoppingCriteria(new MeasurementStop(100.0, new Mean()));
		this.setStoppingCriteria(new IterationsStop(3));
//		this.setAnalyser(new AggregatePerformance("Scalability Chart", new MeanChartCreator(), 1));
		this.setAnalyser(new AggregatePerformance("Scalability Chart", new PercentileChartCreator(90), 1));

	}

	public static void execute() throws Exception {
		ScalabilityTestExample example = new ScalabilityTestExample();
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

class TheClient extends Client<Long, Long> {
	List<Long> resources;
	int resourceIndex = 0;

	long[] timestamps;
	int count;

	public TheClient(List<Long> rs) {
		resources = rs;
	}

	@Override
	public Long beforeRequest() {
		// System.out.println("index " + resourceIndex);
		// System.out.println("resource size " + resources.size());
		Long sleepTime = resources.get(resourceIndex);
		resourceIndex = resourceIndex < resources.size() - 1 ? resourceIndex + 1
				: 0;
		return sleepTime;
	}

	@Override
	public Long request(Long param) {
		timestamps[count++] = System.nanoTime();
		// System.out.println("request time " + System.currentTimeMillis());
		// System.out.println("sleep " + sleepTime);
		try {
			Thread.sleep(param);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setUp() {
		// resources.add(50l);
		timestamps = new long[ScalabilityTestExample.REQUESTS];
		count = 0;
		resources.add(Math.round(Math.random() * 100l
				* (Integer) (params.get("a"))));
		System.out.println("######### new iteration ##############");
	}

	@Override
	public void tearDown() {
		for (int i = 1; i < ScalabilityTestExample.REQUESTS; i++)
			System.out.println(timestamps[i] - timestamps[i - 1]);
	}

}
