package eu.choreos.vv.reportviewer.convert.xml;



public class MeasurementsHandler extends ElementHandler {

	@Override
	public void initialize() {
		this.getHandler().newValuesList();
	}
	
	@Override
	public void finalize() {
		//TODO generalize to all measurements
		this.getHandler().getReportData().setMeasurements("responseTime", this.getHandler().getValues());
	}
}
