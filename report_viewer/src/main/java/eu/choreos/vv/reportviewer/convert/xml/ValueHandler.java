package eu.choreos.vv.reportviewer.convert.xml;



public class ValueHandler extends ElementHandler {
	
	@Override
	public void setValue(String value) {
		Double number = Double.parseDouble(value);
		this.getHandler().getValues().add(number);
	}

}
