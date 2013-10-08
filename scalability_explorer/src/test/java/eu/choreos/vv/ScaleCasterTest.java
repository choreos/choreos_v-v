package eu.choreos.vv;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.increasefunctions.ExponentialIncrease;
import eu.choreos.vv.increasefunctions.LinearIncrease;
import eu.choreos.vv.increasefunctions.ScalabilityFunction;

public class ScaleCasterTest {

	ScaleCaster caster;
	@Before
	public void setUp() {
		caster = new ScaleCaster(new Scalable() {
			@Override
			public ReportData execute(ScaleCaster scaleCaster) throws Exception {
				// TODO Auto-generated method stub
				return new ReportData();
			}
		},"name",5, 0.0);
	}
	
	@Test
	public void oneParameter() {
		ScalabilityFunction function = new LinearIncrease(10);
		String key = caster.addInitialParameterValue(0, function);
		caster.execute();
		int value = caster.getCurrentParameterValue(key).intValue();
		assertEquals(50, value);
	}
	
	@Test
	public void twoParametersOneFunction() {
		ScalabilityFunction function = new ExponentialIncrease(2); 
		String key1 = caster.addInitialParameterValue(1, function);
		String key2 = caster.addInitialParameterValue(2, function);
		caster.execute();
		int value1 = caster.getCurrentParameterValue(key1).intValue();
		int value2 = caster.getCurrentParameterValue(key2).intValue();
		assertEquals(32, value1);
		assertEquals(64, value2);
	}
	
	@Test
	public void twoParametersTwoFunctions() {
	
		String key1 = caster.addInitialParameterValue(1, new ExponentialIncrease(2));
		String key2 = caster.addInitialParameterValue(2, new LinearIncrease(2));
		caster.execute();
		int value1 = caster.getCurrentParameterValue(key1).intValue();
		int value2 = caster.getCurrentParameterValue(key2).intValue();
		assertEquals(32, value1);
		assertEquals(12, value2);
	}
}
