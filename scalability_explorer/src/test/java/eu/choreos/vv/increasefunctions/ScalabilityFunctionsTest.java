package eu.choreos.vv.increasefunctions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ScalabilityFunctionsTest {

	@Test
	public void shouldIncreaseLinearly() {
		final double fixedParam = 2;
		ScalabilityFunction function = new LinearIncrease(fixedParam);
		for(double i = 1; i<=10; i+=fixedParam)
		assertEquals(i+fixedParam, function.increaseParams(i));
	}
	
	@Test
	public void shouldIncreaseExponentialy() {
		final double fixedParam = 2;
		ScalabilityFunction function = new ExponentialIncrease(fixedParam);
		for(double i = 1; i<=10; i*=fixedParam)
		assertEquals(i*fixedParam, function.increaseParams(i));
	}

}
