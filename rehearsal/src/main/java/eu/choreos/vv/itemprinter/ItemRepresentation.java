package eu.choreos.vv.itemprinter;

import eu.choreos.vv.clientgenerator.Item;

/**
 * This class is the String representation of an item Object
 * 
 * @author Felipe Besson
 *
 */
public interface ItemRepresentation {
	
	/**
	 * This methodo builds an item root representation
	 * 
	 * @return a String that represents the item root
	 */
	public  String buildHeader();
	
	/**
	 * This methodo builds an item body content representation
	 * 
	 * @return a String that represents the item body representation
	 */
	public  String buildBody();
	
	/**
	 * This methodo builds the children content of a parent item
	 * 
	 * @param item
	 * @param parentName
	 * 
	 * @return a String that represents the children content
	 */
	public String getLeafContent(Item item, String parentName);
	
	/**
	 * This method returns the access method for a item child which is 
	 * addChild for a request item and getChild or getContent for a response
	 * item
	 * 
	 * @param item
	 * @param parentName
	 * 
	 * @return a String that represents the child access method
	 */
	public String getChildAccessMethod(Item item, String parentName);

}
