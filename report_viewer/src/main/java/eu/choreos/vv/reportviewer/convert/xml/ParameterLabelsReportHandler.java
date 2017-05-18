package eu.choreos.vv.reportviewer.convert.xml;

import java.util.ArrayList;
import java.util.List;


public class ParameterLabelsReportHandler extends ElementHandler {
	
	private List<String> labels = new ArrayList<String>();
	
	@Override
	public void setValue(String value) {
		labels.add(value);
	}
	
	@Override
	public void finalize() {
		this.getHandler().getExperimentReport().setParameterLabels(labels);
	}

}
