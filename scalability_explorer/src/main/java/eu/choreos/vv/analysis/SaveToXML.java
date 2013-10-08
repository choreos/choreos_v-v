package eu.choreos.vv.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.data.convertion.xml.ExperimentReportXML;

public class SaveToXML extends Analyzer {
	
	
	private PrintWriter writer;

	public SaveToXML(File file) throws FileNotFoundException {
		this.writer = new PrintWriter(file);
		
	}

	@Override
	public void analyse(ExperimentReport report) throws Exception {
		ExperimentReportXML converter = new ExperimentReportXML(writer);
		converter.convert(report);
	}

}