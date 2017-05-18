package eu.choreos.vv.reportviewer.convert.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.reportviewer.convert.exceptions.XMLParseException;

public class ExperimentReportBuilder {
	private static SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	private SAXParser parser;
	
	public List<ExperimentReport> parse(File file) throws XMLParseException {
		
		List<ExperimentReport> reports = new ArrayList<ExperimentReport>();
		
		try {
			parser = parserFactory.newSAXParser();
			parser.parse(file, new ExperimentReportParserHandler(reports));
		} catch (Exception e) {
			System.out.println("EXCEPTION: " + e.getMessage());
			XMLParseException pe = new XMLParseException();
			pe.initCause(e);
			throw new XMLParseException();
		}
		
		return reports;
	}

}
