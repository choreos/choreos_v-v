package eu.choreos.vv.reportviewer.convert.xml;



public class ParametersHandler extends ElementHandler {
	
	@Override
	public void initialize() {
		this.getHandler().newValuesList();
	}
	
	@Override
	public void finalize() {
		this.getHandler().getReportData().setParameters(this.getHandler().getValues());
	}

}
