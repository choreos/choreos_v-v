package eu.choreos.vv.reportviewer.convert.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.data.convertion.xml.ExperimentReportXML;
import eu.choreos.vv.data.convertion.xml.ReportDataXML;

public class ExperimentReportParserHandler extends DefaultHandler {

	private List<ExperimentReport> reportsList;
	private ExperimentReport report;
	private ReportData data = null;
	private List<Number> values = null;
	private int sequence = 0;
	
	private Stack<ElementHandler> stack;
	private ElementHandler currentHandler;

	public ExperimentReportParserHandler(List<ExperimentReport> reports) {
		this.reportsList = reports;
		stack = new Stack<ElementHandler>();
	}
	
	
//	@Override
//	public void startDocument() throws SAXException {
//	}
	
	@Override
	public void characters(char[] ch, int start, int lenght) 
	throws SAXException {
		String value = new String(ch, start, lenght).trim();
		if (!value.isEmpty())
			currentHandler.setValue(value);
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) 
			throws SAXException {
			pushNewHandler(qName);
			try {
				currentHandler.fillAttributes(attributes);
			} catch (Exception e) {
				throw new SAXException(e);
			}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
	throws SAXException {
		popHandler();
	}
	
	private void popHandler() {
		currentHandler.finalize();
		stack.pop();
		if (!stack.isEmpty())
		currentHandler = stack.peek();
	}


	private void pushNewHandler(String type) {
		currentHandler = newHandler(type);
		currentHandler.initialize();
		stack.push(currentHandler);
	}
	
	public ReportData getReportData() {
		return this.data;
	}

	public void newIteration() {
		data = new ReportData();
	}
	
	public void closeIteration() {
		if (data != null)
			report.put(sequence, data);
	}
	
	public List<Number> getValues() {
		return this.values; 
	}
	
	public void newValuesList() {
		this.values = new ArrayList<Number>();
	}

	public void newReport() {
		report = new ExperimentReport();
	}
	
	public void closeReport() {
		if (report != null)
			reportsList.add(report);
	}

	private ElementHandler newHandler(String type) {
		ElementHandler handler = null;
		if (type.equals(ExperimentReportXML.DATA))
			handler = new DataHandler();
		else if (type.equals(ExperimentReportXML.MEASUREMENT_UNIT))
			handler = new MeasurementUnitHandler();
		else if (type.equals(ExperimentReportXML.NAME))
			handler = new NameHandler();
		else if (type.equals(ExperimentReportXML.PARAMETER_LABELS))
			handler = new ParameterLabelsReportHandler();
		else if (type.equals(ExperimentReportXML.REPORT))
			handler = new ReportHandler();
		else if (type.equals(ExperimentReportXML.ITERATION))
			handler = new IterationHandler();
		else if (type.equals(ExperimentReportXML.LABEL))
			handler = new LabelHandler(currentHandler);
		else if (type.equals(ReportDataXML.VALUE))
			handler = new ValueHandler();
		else if (type.equals(ReportDataXML.MEASUREMENTS))
			handler = new MeasurementsHandler();
		else if (type.equals(ReportDataXML.PARAMETERS))
			handler = new ParametersHandler();
		else if (type.equals(ExperimentReportXML.EXPERIMENT))
			handler = new ExperimentHandler();
		handler.setHandler(this);
		return handler;
	}


	public ExperimentReport getExperimentReport() {
		return report;
	}


	public void setSequence(int sequence) {
		this.sequence = sequence;
		
	}
	
}
