package eu.choreos.vv;

import static org.junit.Assert.assertEquals;
import static eu.choreos.vv.ScalabilityTesting.run;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.vv.example.SumCount;

public class ScalabilityTestingTest {
	
	private final double EPSILON = 0.0001;
	private SumCount sumCount;
	
	@Before
	public void setUp() {
		sumCount = new SumCount();
	}

	@Test
	public void shouldIncreaseTheNumberParameterAndReturnReport() throws Exception {
		ScalabilityReport report = run(sumCount, "count", 1);
		double[] series = report.getSeries();
		for(int i=1; i<=5; i++) {
			assertEquals((double) i, series[i], EPSILON);
		}
		assertEquals("count", report.getName());
	}
	

	@Test
	public void shouldIncreaseTheNumberOfBothParameters() throws Exception {
		ScalabilityReport report = run(sumCount, "sumBoth", 1, 2);
		double[] series = report.getSeries();
		for(int i=1; i<=5; i++) {
			assertEquals(i*(1.0+2.0), series[i], EPSILON);
		}
		assertEquals("sumBoth", report.getName());
	}
	
	@Test
	public void shouldIncreaseTheNumberOfParametersWithAnnotationScale() throws Exception {
		ScalabilityReport report =  run(sumCount, "sumBothAndMultiple", 1, 2, 10);
		double[] series = report.getSeries();
		for(int i=1; i<=5; i++) {
			assertEquals(i*10*(1.0+2.0), series[i], EPSILON);
		}
		assertEquals("sumBothAndMultiple", report.getName());
	}
	
	
	@Test
	public void shouldReceiveAsReturnADoubleValue() throws Exception {		
		ScalabilityReport report = run(sumCount, "countReturningDouble", 1);
		double[] series = report.getSeries();
		for(int i=1; i<=5; i++) {
			assertEquals((double) i, series[i], EPSILON);
		}
		assertEquals("countReturningDouble", report.getName());
	}
	
	@Test
	public void shouldExecuteMethodIfHasNoParameterWithScaleAnnotation() throws Exception {
		ScalabilityReport report = run(sumCount, "withoutScaleParameter", 1);
		double[] series = report.getSeries();
		for(int i=1; i<=5; i++) {
			assertEquals(1.0, series[i], EPSILON);
		}
		assertEquals("withoutScaleParameter", report.getName());
	}
	
	
	@Test(expected=NoSuchMethodException.class)
	public void shouldNotExecuteMethodWithoutScalabilityTestAnnotation() throws Exception {
		run(sumCount, "withoutScalabilityTestAnnotation", 1);
	}
	
	@Test(expected=NoSuchMethodException.class)
	public void shouldThrowNoSuchMethodExceptionIfMethodDoesNotExists() throws Exception {
		run(sumCount, "methodDoesNotExists");
	}
	
	
	@Test
	public void shouldIncreaseExponentially() throws Exception {	
		ScalabilityReport report = run(sumCount, "countExponential", 1);
		double[] series = report.getSeries();
		int value = 1;
		for(int i=1; i<=5; i++) {
			assertEquals((double) value, series[i], EPSILON);
			value*=2;
		}
		assertEquals("countExponential", report.getName());
	}
	

	
	@Test
	public void shouldIncreaseQuadratically() throws Exception {
		ScalabilityReport report = run(sumCount, "countQuadratic", 1);
		double[] series = report.getSeries();
		for(int i=1; i<=5; i++) {
			assertEquals((double) (i*i), series[i], EPSILON);
		}
		assertEquals("countQuadratic", report.getName());
	}

}
