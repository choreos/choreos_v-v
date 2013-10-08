package eu.choreos.vv.analysis;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.data.ExperimentReport;

public class SaveToFileTest {
	
	private static final String NAME = "test report";
	private File file;
	private List<ExperimentReport> reports;
	
	private static List<Number> listFor(Number... values) {
		List<Number> list = new ArrayList<Number>();
		Collections.addAll(list, values);
		return list;
	}
	
	@Before
	public void setup() throws Exception {
		file = new File("/tmp/test.sex");
		reports = new ArrayList<ExperimentReport>();
		ExperimentReport report = new ExperimentReport();
		report.setName(NAME);
		ReportData data = new ReportData();
		data.setMeasurements(listFor(1, 2, 3, 4));
		report.put(0, data);
		data = new ReportData();
		data.setMeasurements(listFor(2, 4, 6, 8));
		report.put(1, data);
		reports.add(report);
	}
	
	@Test
	public void shouldSaveOneReportToAFile() throws Exception {
		Analyzer saver = new SaveToFile(file);
		saver.analyse(reports);
		
		InputStream is = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(is);
		List<ExperimentReport> read = (List<ExperimentReport>)ois.readObject();
		assertEquals(read.size(), 1);
		ExperimentReport report = read.get(0);
		assertEquals(report.getName(), NAME);
		assertEquals(report.size(), 2);
		assertEquals(report.get(0).getMeasurements(), listFor(1, 2, 3, 4));
		assertEquals(report.get(1).getMeasurements(), listFor(2, 4, 6, 8));
		ois.close();
	}

}
