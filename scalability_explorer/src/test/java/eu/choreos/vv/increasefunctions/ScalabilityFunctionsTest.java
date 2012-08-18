package eu.choreos.vv.increasefunctions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ScalabilityFunctionsTest {

	@Test
	public void shouldIncreaseLinearly() {
		ScalabilityFunction function = new LinearIncrease();
		final double fixedParam = 2;
		for(double i = 1; i<=10; i+=fixedParam)
		assertEquals(i+fixedParam, function.increaseParams(i, fixedParam));
	}
	
	@Test
	public void shouldIncreaseExponentialy() {
		ScalabilityFunction function = new ExponentialIncrease();
		final double fixedParam = 2;
		for(double i = 1; i<=10; i*=fixedParam)
		assertEquals(i*fixedParam, function.increaseParams(i, fixedParam));
	}

}
