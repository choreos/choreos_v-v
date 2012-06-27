package eu.choreos.vv;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.choreos.vv.loadgenerator.Executable;
import eu.choreos.vv.loadgenerator.LoadGenerator;
import eu.choreos.vv.loadgenerator.UniformLoadGenerator;

public class LoadGeneratorTest {

	@Test
	public void sholdRunForThreeSeconds() throws Exception {
		LoadGenerator loadGen = new UniformLoadGenerator();
		long startTime = System.currentTimeMillis();
		loadGen.execute(10, 180, new Executable() {
			
			@Override
			public Double execute() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		});
		long endTime = System.currentTimeMillis();
		double duration = (endTime - startTime)/1000;
		assertEquals(3.0, duration, 0.1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void executionShouldTakeLongerThanDelay() throws Exception {
		LoadGenerator loadGen = new UniformLoadGenerator();
		long startTime = System.currentTimeMillis();
		loadGen.execute(10, 60, new Executable() {
			
			@Override
			public Double execute() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		});
		long endTime = System.currentTimeMillis();
		double duration = (endTime - startTime)/1000;
		assertEquals(10.0, duration, 0.1);
	}

}