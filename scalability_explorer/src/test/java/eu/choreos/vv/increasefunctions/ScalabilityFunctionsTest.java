package eu.choreos.vv.increasefunctions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ScalabilityFunctionsTest {

	@Test
	public void shouldIncreaseLinearly() {
		final int fixedParam = 2;
		ScalabilityFunction function = new LinearIncrease(fixedParam);
		for(int i = 1; i<=10; i+=fixedParam)
		assertEquals(i+fixedParam, function.increaseParams(i).intValue());
	}
	
	@Test
	public void shouldIncreaseExponentialy() {
		final int fixedParam = 2;
		ScalabilityFunction function = new ExponentialIncrease(fixedParam);
		for(int i = 1; i<=10; i*=fixedParam)
		assertEquals(i*fixedParam, function.increaseParams(i).intValue());
	}

}
