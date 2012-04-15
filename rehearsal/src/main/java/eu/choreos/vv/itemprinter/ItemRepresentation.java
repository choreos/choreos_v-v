package eu.choreos.vv.itemprinter;

import eu.choreos.vv.clientgenerator.Item;

public interface ItemRepresentation {
	
	public  String buildHeader();
	public  String buildBody();
	public String getLeafContent(Item item, String parentName);
	public String getChildAccessMethod(Item item, String parentName);

}
