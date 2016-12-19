package eu.choreos.vv.loadgenerator;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.choreos.vv.client.Client;
import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.loadgenerator.strategy.LoadGenerationStrategy;
import eu.choreos.vv.loadgenerator.strategy.TraceDrivenLoad;

public class TraceDrivenTest {

	@Test
	public void test() throws Exception {
		String trace = "100000000\n100000000\n20000000\n459300000\n3405000000\n394239823";
		Reader reader = new StringReader(trace);
		BufferedReader in = new BufferedReader(reader);
		LoadGenerationStrategy strategy = new TraceDrivenLoad(in);
		LoadGeneratorFactory.getInstance().setStrategy(strategy);
		LoadGenerator<Object, Object> loadGen = LoadGeneratorFactory.getInstance().create();
		final List<Long> times = new ArrayList<Long>();

		ReportData report = loadGen.execute(6,
				new Client<Object, Object>() {

					long ant, now = System.nanoTime();

					
					@Override
					public Object request(Object param) {
						ant = now;
						now = System.nanoTime();
						times.add(now - ant);
						System.out.format("request %d\n", (now - ant)/1000000);
						return times;
					}


				});

		assertEquals(times.get(1)/10000000f, 10f, 0.2);
		assertEquals(times.get(2)/10000000f, 10f, 0.1);
		assertEquals(times.get(3)/10000000f, 2f, 0.1);
		assertEquals(times.get(4)/10000000f, 45.9f, 0.1);
		assertEquals(times.get(5)/10000000f, 340.5f, 0.1);
	}

}
