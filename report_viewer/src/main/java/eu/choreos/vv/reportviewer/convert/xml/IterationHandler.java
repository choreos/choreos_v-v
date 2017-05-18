package eu.choreos.vv.reportviewer.convert.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.xml.sax.Attributes;

import eu.choreos.vv.data.ReportData;
import eu.choreos.vv.data.convertion.xml.ExperimentReportXML;


public class IterationHandler extends ElementHandler {

	SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz YYYY");
	
	@Override
	public void fillAttributes(Attributes attributes) throws ParseException {
		ReportData rd = this.getHandler().getReportData();
		int numAttributes = attributes.getLength();
		for(int i = 0; i < numAttributes; i++){
			String attrName = attributes.getLocalName(i);
			String value = attributes.getValue(i);
			
			setAttribute(rd, attrName, value);
		}
	}
	
	private void setAttribute(ReportData data, String attribute, String value) throws ParseException {
		if (attribute.equals(ExperimentReportXML.START_TIME)) {
			data.setStartTime(dateFormat.parse(value));
		} else if (attribute.equals(ExperimentReportXML.END_TIME)) {
			data.setEndTime(dateFormat.parse(value));
		} else if (attribute.equals(ExperimentReportXML.SEQUENCE)) {
			this.getHandler().setSequence(Integer.parseInt(value));
		}
	}
	
	@Override
	public void initialize() {
		this.getHandler().newIteration();
	}
	
	@Override
	public void finalize() {
		this.getHandler().closeIteration();
	}

}
