package eu.choreos.vv.data.convertion.xml;

import java.io.PrintWriter;

import eu.choreos.vv.data.ReportData;

public class ReportDataXML extends XMLConverter<ReportData> {

	private static final String VALUE = "value";
	private static final String PARAMETERS = "parameters";
	private static final String MEASUREMENTS = "measurements";

	public static void convert(PrintWriter writer, ReportData data) {
		writerParameters(writer, data);
		writeMeasurements(writer, data);
		
	}

	private static void writeMeasurements(PrintWriter writer, ReportData data) {
		openTag(writer, MEASUREMENTS);
		writeList(writer, VALUE, data.getMeasurements());
		closeTag(writer, MEASUREMENTS);
	}

	private static void writerParameters(PrintWriter writer, ReportData data) {
		openTag(writer, PARAMETERS);
		writeList(writer, VALUE, data.getParameters());
		closeTag(writer, PARAMETERS);		
	}

}
