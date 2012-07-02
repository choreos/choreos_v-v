package eu.choreos.vv;

import static eu.choreos.vv.ScalabilityTesting.run;
import static org.junit.Assert.assertEquals;

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
		for(double i=1; i<=5; i++) {
			assertEquals((double) i, report.get(i-1), EPSILON);
		}
		assertEquals("count", report.getName());
	}
	

	@Test
	public void shouldIncreaseTheNumberOfBothParameters() throws Exception {
		ScalabilityReport report = run(sumCount, "sumBoth", 1, 2);
		for(double i=1; i<=5; i++) {
			assertEquals(i*(1.0+2.0), report.get(i), EPSILON);
		}
		assertEquals("sumBoth", report.getName());
	}
	
	@Test
	public void shouldIncreaseTheNumberOfParametersWithAnnotationScale() throws Exception {
		ScalabilityReport report =  run(sumCount, "sumBothAndMultiple", 1, 2, 10);
		for(double i=1; i<=5; i++) {
			assertEquals(i*10*(1.0+2.0), report.get(i-1), EPSILON);
		}
		assertEquals("sumBothAndMultiple", report.getName());
	}
	
	
	@Test
	public void shouldReceiveAsReturnADoubleValue() throws Exception {		
		ScalabilityReport report = run(sumCount, "countReturningDouble", 1);
		for(double i=1; i<=5; i++) {
			assertEquals((double) i, report.get(i-1), EPSILON);
		}
		assertEquals("countReturningDouble", report.getName());
	}
	
	@Test
	public void shouldExecuteMethodIfHasNoParameterWithScaleAnnotation() throws Exception {
		ScalabilityReport report = run(sumCount, "withoutScaleParameter", 1);
		for(double i=1; i<=5; i++) {
			assertEquals(1.0, report.get(i-1), EPSILON);
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
		int value = 1;
		for(double i=1; i<=5; i++) {
			assertEquals((double) value, report.get(i-1), EPSILON);
			value*=2;
		}
		assertEquals("countExponential", report.getName());
	}
	

	
	@Test
	public void shouldIncreaseQuadratically() throws Exception {
		ScalabilityReport report = run(sumCount, "countQuadratic", 1);
		for(double i=1; i<=5; i++) {
			assertEquals((double) (i*i), report.get(i-1), EPSILON);
		}
		assertEquals("countQuadratic", report.getName());
	}
	
	@Test
	public void shouldRunFiveTimes() throws Exception {
		ScalabilityReport report = run(sumCount, "goUpToTheLimit", 1);
		assertEquals(6, report.size());
	}

}
