package eu.choreos.vv.reportviewer.convert.xml;

import org.xml.sax.Attributes;

public abstract class ElementHandler {
	
	private ExperimentReportParserHandler handler;

	public ExperimentReportParserHandler getHandler() {
		return handler;
	}

	public void setHandler(ExperimentReportParserHandler handler) {
		this.handler = handler;
	}

	public void fillAttributes(Attributes attributes) throws Exception {}

	public void setValue(String value) {}
	
	public void initialize() {}
	
	public void finalize() {}

}
