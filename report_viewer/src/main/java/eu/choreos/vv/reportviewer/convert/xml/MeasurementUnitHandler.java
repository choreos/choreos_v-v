package eu.choreos.vv.reportviewer.convert.xml;


public class MeasurementUnitHandler extends ElementHandler {
	
	@Override
	public void setValue(String value) {
		this.getHandler().getExperimentReport().setMeasurementUnit(value);
	}

}
