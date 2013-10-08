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
	private static final String START_TIME = "startTime";
	private static final String END_TIME = "endTime";	
	
	public ExperimentReportXML(PrintWriter writer) {
		super(writer);
	}
	
	@Override
	public void convert(ExperimentReport report) {
		header(report);
		writeReport(report);
		super.convert(report);
	}

	private void writeReport(ExperimentReport report) {
		openTag(REPORT);
		writeName(report);
		writeMeasurementUnit(report);
		writeParameterLabels(report);
		openTag(DATA);
		for(int iteration: report.keySet()) {
			writeIteration(iteration, report.get(iteration));
		}
		closeTag(DATA);
		closeTag(REPORT);
	}

	private void writeIteration(int iteration, ReportData reportData) {
		Map<String, String> param = new HashMap<String, String>();
		param.put(SEQUENCE, ""+iteration);
		param.put(START_TIME, reportData.getStartTime().toString());
		param.put(END_TIME, reportData.getEndTime().toString());
		openTag(ITERATION, param);
//		writeIterationOrder(iteration);
		writeReportData(reportData);
		closeTag(ITERATION);
		
	}

	private void writeReportData(ReportData reportData) {
		ReportDataXML reportDataXML = new ReportDataXML(this.getWriter());
		reportDataXML.convert(reportData);
	}

	private void writeParameterLabels(ExperimentReport report) {
		openTag(PARAMETERLABELS);
		writeList(LABEL, report.getParameterLabels());
		closeTag(PARAMETERLABELS);
	}

	private void writeMeasurementUnit(ExperimentReport report) {
		openTag(MEASUREMENTUNIT);
		value(report.getMeasurementUnit());
		closeTag(MEASUREMENTUNIT);
		
	}

	private void writeName(ExperimentReport report) {
		openTag(NAME);
		value(report.getName().toString());
		closeTag(NAME);
	}

	private void header(ExperimentReport report) {
		
	}

}
