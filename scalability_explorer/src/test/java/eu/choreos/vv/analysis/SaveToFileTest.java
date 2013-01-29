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

import eu.choreos.vv.ScalabilityReport;

public class SaveToFileTest {
	
	private static final String NAME = "test report";
	private File file;
	private List<ScalabilityReport> reports;
	
	private static List<Number> listFor(Number... values) {
		List<Number> list = new ArrayList<Number>();
		Collections.addAll(list, values);
		return list;
	}
	
	@Before
	public void setup() throws Exception {
		file = new File("/tmp/test.sex");
		reports = new ArrayList<ScalabilityReport>();
		ScalabilityReport report = new ScalabilityReport();
		report.setName(NAME);
		report.put(0, listFor(1, 2, 3, 4));
		report.put(1, listFor(2, 4, 6, 8));
		reports.add(report);
	}
	
	@Test
	public void shouldSaveOneReportToAFile() throws Exception {
		Analyser saver = new SaveToFile(file);
		saver.analyse(reports, "");
		
		InputStream is = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(is);
		List<ScalabilityReport> read = (List<ScalabilityReport>)ois.readObject();
		assertEquals(read.size(), 1);
		ScalabilityReport report = read.get(0);
		assertEquals(report.getName(), NAME);
		assertEquals(report.size(), 2);
		assertEquals(report.get(0), listFor(1, 2, 3, 4));
		assertEquals(report.get(1), listFor(2, 4, 6, 8));
	}

}
