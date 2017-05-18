package eu.choreos.vv.reportviewer.convert.xml;



public class LabelHandler extends ElementHandler {

	ElementHandler handler;
	
	public LabelHandler(ElementHandler currentHandler) {
		this.handler = currentHandler;
	}
	
	@Override
	public void setValue(String value) {
		handler.setValue(value);
	}

}
