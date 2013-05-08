package eu.choreos.vv.loadgenerator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.choreos.vv.loadgenerator.executable.Executor;

public class LoadGeneratorTest {
	
	@Test
	public void shouldRunManyTimes() {
		final int DELAY = 500000;
		final int TIMES_TO_RUN = 1000;
		final List<Long> times = new ArrayList<Long>();
		LoadGenerator loadGen = new DegeneratedLoadGenerator();
		loadGen.setDelay(DELAY);
		try {
		loadGen.execute(TIMES_TO_RUN, new Executor() {

			@Override
			public void experiment() {
					times.add(System.currentTimeMillis());
			}

			@Override
			protected double initialMeasurement() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			protected double finalMeasurement() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void setUp() throws Exception {
				// TODO Auto-generated method stub
				
			}
		});
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(TIMES_TO_RUN, times.size());
	}
	
	@Test
	public void sholdRunForOneSecond() throws Exception {
		LoadGenerator loadGen = new DegeneratedLoadGenerator();
		loadGen.setDelay(1000000);
		long startTime = System.currentTimeMillis();
		loadGen.execute(100, new Executor() {

			@Override
			public void experiment() {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			protected double initialMeasurement() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			protected double finalMeasurement() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void setUp() throws Exception {
				// TODO Auto-generated method stub
				
			}
		});
		long endTime = System.currentTimeMillis();
		double duration = (endTime - startTime)/1000d;
		assertEquals(0.1, duration, 0.05);
	}

	@Test
	public void executionShouldTakeLongerThanDelay() throws Exception {
		LoadGenerator loadGen = new DegeneratedLoadGenerator();
		loadGen.setDelay(100000000);
		long startTime = System.currentTimeMillis();
		loadGen.execute(10, new Executor() {

			@Override
			public Double call() {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 1.0;
			}

			@Override
			public void experiment() {
				// TODO Auto-generated method stub

			}

			@Override
			protected double initialMeasurement() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			protected double finalMeasurement() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void setUp() throws Exception {
				// TODO Auto-generated method stub
				
			}
		});
		long endTime = System.currentTimeMillis();
		double duration = (endTime - startTime) / 1000d;
		assertEquals(1.1, duration, 0.01);
	}

}