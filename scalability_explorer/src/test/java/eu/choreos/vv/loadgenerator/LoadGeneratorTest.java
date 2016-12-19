package eu.choreos.vv.loadgenerator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.choreos.vv.client.Client;

public class LoadGeneratorTest {

	@Test
	public void shouldRunManyTimes() {
		final int DELAY = 10000000;
		final int TIMES_TO_RUN = 1000;
		final List<Long> times = new ArrayList<Long>();
		LoadGenerator<Object, Object> loadGen = LoadGeneratorFactory
				.getInstance().create();
		loadGen.setDelay(DELAY);
		try {
			loadGen.execute(TIMES_TO_RUN, new Client<Object, Object>() {

				@Override
				public Object request(Object param) {
					times.add(System.currentTimeMillis());
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
		LoadGenerator<Object, Object> loadGen = LoadGeneratorFactory
				.getInstance().create();
		loadGen.setDelay(10000000);
		long startTime = System.currentTimeMillis();
		loadGen.execute(100, new Client<Object, Object>() {

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

		});
		long endTime = System.currentTimeMillis();
		double duration = (endTime - startTime) / 1000d;
		assertEquals(1, duration, 0.1);
	}

	@Test
	public void executionShouldTakeLongerThanDelay() throws Exception {
		LoadGenerator<Object, Object> loadGen = LoadGeneratorFactory
				.getInstance().create();
		loadGen.setDelay(100000000);
		long startTime = System.currentTimeMillis();
		loadGen.execute(10, new Client<Object, Object>() {

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

		});
		long endTime = System.currentTimeMillis();
		double duration = (endTime - startTime) / 1000d;
		assertEquals(1.2, duration, 0.1);
	}

}