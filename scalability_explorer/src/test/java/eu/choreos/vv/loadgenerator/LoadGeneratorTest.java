package eu.choreos.vv.loadgenerator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.choreos.vv.loadgenerator.LoadGenerator;
import eu.choreos.vv.loadgenerator.UniformLoadGenerator;
import eu.choreos.vv.loadgenerator.executable.Executer;

public class LoadGeneratorTest {
	
	@Test
	public void shouldRunManyTimes() {
		final int CALLS_PER_MIN = 10000;
		final int TIMES_TO_RUN = 1000;
		final List<Long> times = new ArrayList<Long>();
		LoadGenerator loadGen = new UniformLoadGenerator();
		try {
		loadGen.execute(TIMES_TO_RUN, CALLS_PER_MIN, new Executer() {

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
	public void sholdRunForThreeSeconds() throws Exception {
		LoadGenerator loadGen = new UniformLoadGenerator();
		long startTime = System.currentTimeMillis();
		loadGen.execute(10, 180, new Executer() {

			@Override
			public void experiment() {
				try {
					Thread.sleep(100);
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
		double duration = (endTime - startTime) / 1000;
		assertEquals(3.0, duration, 0.1);
	}

	@Test
	// (expected=IllegalArgumentException.class)
	public void executionShouldTakeLongerThanDelay() throws Exception {
		LoadGenerator loadGen = new UniformLoadGenerator();
		long startTime = System.currentTimeMillis();
		loadGen.execute(10, 60, new Executer() {

			@Override
			public Double call() {
				try {
					Thread.sleep(2000);
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
		double duration = (endTime - startTime) / 1000;
		assertEquals(11.0, duration, 0.1);
	}

}