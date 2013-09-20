package eu.choreos.vv.analysis;

import java.io.File;
import java.io.PrintWriter;

import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.data.convertion.xml.ExperimentReportXML;

public class SaveToXML extends Analyzer {
	
	
	private File file;

	public SaveToXML(File file) {
		this.file = file;
		
	}

	@Override
	public void analyse(ExperimentReport report) throws Exception {
		PrintWriter writer = new PrintWriter(file);
		ExperimentReportXML.convert(writer, report);
		writer.flush();
		
	}

}
