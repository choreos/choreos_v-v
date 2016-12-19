package eu.choreos.vv.data;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.vv.TestUtils;

public class ReportDataTest {

	ReportData report1 = new ReportData();
	ReportData report2 = new ReportData();
	Calendar calendar = Calendar.getInstance();
	
	@Before
	public void setup() {
		
				
		calendar.set(2016, 01, 01, 9, 30);
		report1.setStartTime(calendar.getTime());
		calendar.roll(Calendar.MINUTE, true);
		report2.setStartTime(calendar.getTime());
		
		calendar.add(Calendar.MINUTE, 10);
		report1.setEndTime(calendar.getTime());
		calendar.roll(Calendar.MINUTE, false);
		report2.setEndTime(calendar.getTime());
		
		report1.setMeasurements("responseTime", TestUtils.listFor(1, 2, 3));
		report2.setMeasurements("responseTime", TestUtils.listFor(1, 2, 2));
		
	}
	
	@Test
	public void testAddMeasurements() {
		report1.addMeasurements("responseTime", TestUtils.listFor(4, 5, 6));
		assertEquals(TestUtils.listFor(1, 2, 3, 4, 5, 6), report1.getMeasurements("responseTime"));
	}
	
	@Test
	public void testAddMeasurementsWithRepetition() {
		report1.addMeasurements("responseTime", TestUtils.listFor(3, 5, 6));
		assertEquals(TestUtils.listFor(1, 2, 3, 3, 5, 6), report1.getMeasurements("responseTime"));
	}
	
	@Test
	public void testAddMeasurementsInexistents() {
		report1.addMeasurements("throughput", TestUtils.listFor(4, 5, 6));
		assertEquals(TestUtils.listFor(4, 5, 6), report1.getMeasurements("throughput"));
		assertEquals(TestUtils.listFor(1, 2, 3), report1.getMeasurements("responseTime"));
	}

	@Test
	public void testMerge() {
		ReportData report = report2.merge(report1);
		
		assertEquals(report.getStartTime(), report1.getStartTime());
		assertEquals(report.getEndTime(), report1.getEndTime());
		
		assertEquals(TestUtils.listFor(1, 2, 2, 1, 2, 3), report.getMeasurements("responseTime"));
		assertEquals(TestUtils.listFor(1, 2, 2), report2.getMeasurements("responseTime"));
		assertEquals(TestUtils.listFor(1, 2, 3), report1.getMeasurements("responseTime"));
	}

}
