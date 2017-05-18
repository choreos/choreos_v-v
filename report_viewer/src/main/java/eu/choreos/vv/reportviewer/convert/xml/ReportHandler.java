package eu.choreos.vv.reportviewer.convert.xml;



public class ReportHandler extends ElementHandler {
	
	@Override
	public void initialize() {
		this.getHandler().newReport();
	}
	
	@Override
	public void finalize() {
		this.getHandler().closeReport();
	}

}
