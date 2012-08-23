package eu.choreos.vv.example;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.ScalabilityTester;
import eu.choreos.vv.aggregations.Percentile;

public class ScalabilityTesterExample extends ScalabilityTester {

	List<Integer> resources;
	long sleepTime;
	int resourceIndex;

	@Override
	public void resourceScaling(int resourceQuantity) {
		resources.add(resourceQuantity * 10);
	}

	@Override
	public void setUp() {
		resources = new ArrayList<Integer>();
		resourceIndex = 0;
	}

	@Override
	public void beforeTest() {
		sleepTime = resources.get(resourceIndex);
		resourceIndex = resourceIndex < resources.size() - 1 ? resourceIndex + 1
				: 0;
	}

	@Override
	public void test() {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tearDown() {
	}

	public static void main(String[] args) {
		ScalabilityTesterExample example = new ScalabilityTesterExample();
		example.setAggregator(new Percentile(75));
		example.setNumberOfExecutionsPerTest(10);
		example.setNumberOfTestsToRun(10);
		example.setInitialRequestsPerMinute(600);

		if (!example.run("test1"))
			example.getLastException().printStackTrace();

		if (!example.run("test2", 5, 1000, 10, 60, 1))
			example.getLastException().printStackTrace();
		example.showChart("simple test");
	}

}
