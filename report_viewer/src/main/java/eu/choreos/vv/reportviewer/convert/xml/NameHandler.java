package eu.choreos.vv.reportviewer.convert.xml;


public class NameHandler extends ElementHandler {
	
	@Override
	public void setValue(String value) {
		this.getHandler().getExperimentReport().setName(value);
	}

}
