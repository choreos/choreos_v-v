package eu.choreos.vv.example;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.ScalabilityTester;

public class ScalabilityTesterExample extends ScalabilityTester {

	List<Integer> resources;
	long sleepTime;
	int resourceIndex;
	
	@Override
	public void resourceScaling(int resourceQuantity) {
		resources.add(resourceQuantity * 5);
	}

	@Override
	public void setUp() {
		resources = new ArrayList<Integer>();
	}

	@Override
	public void beforeTest() {
		sleepTime = resources.get(resourceIndex);
		resourceIndex = resourceIndex < resources.size()-1? resourceIndex+1 : 0; 
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
	
	public void tearDown() {}

	public static void main(String[] args) {
		ScalabilityTesterExample example = new ScalabilityTesterExample();
		if (example.run("test", 5, 1000, 10, 60, 1))
			example.showChart("simple test");
		else
			example.getLastException().printStackTrace();
	}

}
