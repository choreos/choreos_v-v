package eu.choreos.vv.loadgenerator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.choreos.vv.experiments.Experiment;

public class LoadGeneratorTest {
	
	@Test
	public void shouldRunManyTimes() {
		final int DELAY = 1000000;
		final int TIMES_TO_RUN = 1000;
		final List<Long> times = new ArrayList<Long>();
		LoadGenerator loadGen = LoadGeneratorFactory.getInstance().degeneratedLoad();
		loadGen.setDelay(DELAY);
		try {
		loadGen.execute(TIMES_TO_RUN, new Experiment<Object, Object>() {

			@Override
			public Object request(Object param) {
					times.add(System.currentTimeMillis());
					return null;
			}

			@Override
			protected Number[] getInitialParameterValues() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected void updateParameterValues(Number... values) {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected List<String> getParameterLabels() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(TIMES_TO_RUN, times.size());
	}
	
	@Test
	public void sholdRunForOneSecond() throws Exception {
		LoadGenerator loadGen = LoadGeneratorFactory.getInstance().degeneratedLoad();
		loadGen.setDelay(1000000);
		long startTime = System.currentTimeMillis();
		loadGen.execute(100, new Experiment<Object, Object>() {

			@Override
			public Object request(Object param) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected Number[] getInitialParameterValues() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected void updateParameterValues(Number... values) {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected List<String> getParameterLabels() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		long endTime = System.currentTimeMillis();
		double duration = (endTime - startTime)/1000d;
		assertEquals(0.1, duration, 0.05);
	}

	@Test
	public void executionShouldTakeLongerThanDelay() throws Exception {
		LoadGenerator loadGen = LoadGeneratorFactory.getInstance().degeneratedLoad();
		loadGen.setDelay(100000000);
		long startTime = System.currentTimeMillis();
		loadGen.execute(10, new Experiment<Object, Object>() {

			@Override
			public Object request(Object param) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected Number[] getInitialParameterValues() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected void updateParameterValues(Number... values) {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected List<String> getParameterLabels() {
				// TODO Auto-generated method stub
				return null;
			}


		});
		long endTime = System.currentTimeMillis();
		double duration = (endTime - startTime) / 1000d;
		assertEquals(1.1, duration, 0.007);
	}

}