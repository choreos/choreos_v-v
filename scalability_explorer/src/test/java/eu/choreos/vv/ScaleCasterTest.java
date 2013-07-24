package eu.choreos.vv;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.vv.increasefunctions.ExponentialIncrease;
import eu.choreos.vv.increasefunctions.LinearIncrease;

public class ScaleCasterTest {

	ScaleCaster caster;
	@Before
	public void setUp() {
		caster = new ScaleCaster(new Scalable() {
			@Override
			public List<Number> execute(Number... params) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		},"name");
	}
	
	@Test
	public void oneParameter() {
	
		caster.setInitialParametersValues(0);
		caster.setTimesToExecute(5);
		caster.setFunctions(new LinearIncrease(10));
		caster.execute();
		int value = caster.getCurrentParametersValues()[0].intValue();
		assertEquals(50, value);
	}
	
	@Test
	public void twoParametersOneFunction() {
	
		caster.setInitialParametersValues(1, 2);
		caster.setTimesToExecute(5);
		caster.setFunctions(new ExponentialIncrease(2));
		caster.execute();
		int value1 = caster.getCurrentParametersValues()[0].intValue();
		int value2 = caster.getCurrentParametersValues()[1].intValue();
		assertEquals(32, value1);
		assertEquals(64, value2);
	}
	
	@Test
	public void twoParametersTwoFunctions() {
	
		caster.setInitialParametersValues(1, 2);
		caster.setTimesToExecute(5);
		caster.setFunctions(new ExponentialIncrease(2), new LinearIncrease(2));
		caster.execute();
		int value1 = caster.getCurrentParametersValues()[0].intValue();
		int value2 = caster.getCurrentParametersValues()[1].intValue();
		assertEquals(32, value1);
		assertEquals(12, value2);
	}
}
