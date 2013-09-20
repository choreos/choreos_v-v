package eu.choreos.vv.data.convertion.xml;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.data.ReportData;

public class ExperimentReportXML extends XMLConverter<ExperimentReport> {
	
	private static final String REPORT = "report";
	private static final String MEASUREMENTUNIT = "measurementUnit";
	private static final String NAME = "name";
	private static final String PARAMETERLABELS = "parameterLabels";
	private static final String LABEL = "label";
	private static final String DATA = "data";
	private static final String ITERATION = "iteration";
	private static final String SEQUENCE = "sequence";	
	
	public static void convert(PrintWriter writer, ExperimentReport report) {
		header(writer, report);
		writeReport(writer, report);
	}

	private static void writeReport(PrintWriter writer, ExperimentReport report) {
		openTag(writer, REPORT);
		writeName(writer, report);
		writeMeasurementUnit(writer, report);
		writeParameterLabels(writer, report);
		openTag(writer, DATA);
		for(int iteration: report.keySet()) {
			writeIteration(writer, iteration, report.get(iteration));
		}
		closeTag(writer, DATA);
		closeTag(writer, REPORT);
	}

	private static void writeIteration(PrintWriter writer, int iteration, ReportData reportData) {
		Map<String, String> param = new HashMap<String, String>();
		param.put(SEQUENCE, ""+iteration);
		openTag(writer, ITERATION, param);
//		writeIterationOrder(writer, iteration);
		writeReportData(writer, reportData);
		closeTag(writer, ITERATION);
		
	}

	private static void writeReportData(PrintWriter writer, ReportData reportData) {
		ReportDataXML.convert(writer, reportData);
	}

	private static void writeParameterLabels(PrintWriter writer, ExperimentReport report) {
		openTag(writer, PARAMETERLABELS);
		writeList(writer, LABEL, report.getParameterLabels());
		closeTag(writer, PARAMETERLABELS);
	}

	private static void writeMeasurementUnit(PrintWriter writer, ExperimentReport report) {
		openTag(writer, MEASUREMENTUNIT);
		value(writer, report.getMeasurementUnit());
		closeTag(writer, MEASUREMENTUNIT);
		
	}

	private static void writeName(PrintWriter writer, ExperimentReport report) {
		openTag(writer, NAME);
		value(writer, report.getName().toString());
		closeTag(writer, NAME);
	}

	private static void header(PrintWriter writer, ExperimentReport report) {
		
	}
}
