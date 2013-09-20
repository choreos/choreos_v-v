package eu.choreos.vv.data.convertion.xml;

import java.io.PrintWriter;

import eu.choreos.vv.data.ReportData;

public class ReportDataXML extends XMLConverter<ReportData> {
	
	private static final String VALUE = "value";
	private static final String PARAMETERS = "parameters";
	private static final String MEASUREMENTS = "measurements";

	public ReportDataXML(PrintWriter writer) {
		super(writer);
	}

	public void convert(ReportData data) {
		writerParameters(data);
		writeMeasurements(data);
		super.convert(data);
	}

	private void writeMeasurements(ReportData data) {
		openTag(MEASUREMENTS);
		writeList(VALUE, data.getMeasurements());
		closeTag(MEASUREMENTS);
	}

	private void writerParameters(ReportData data) {
		openTag(PARAMETERS);
		writeList(VALUE, data.getParameters());
		closeTag(PARAMETERS);		
	}

}
