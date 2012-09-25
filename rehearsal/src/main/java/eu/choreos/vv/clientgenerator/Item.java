package eu.choreos.vv.clientgenerator;

import java.util.List;
import java.util.Map;

/**
 * This interface represents a complexType returned within the 
 * response SOAP envelope of a request operation on a Web Service.
 * It is a tree like structure that represents the XML.
 * 
 * The topmost Item of this structure is usually the operationResponse tag.
 * 
 * @author Leonardo Leite, Guilherme Nogueira, Felipe Besson, Lucas Piva
 *
 */
public interface Item {

	/**
	 * This method returns the content of the XML tag this item represents.
	 * The content is any text written between the opening and closing tag,
	 * that is not a child tag.
	 * 
	 * @return The content of the item.
	 */
	public String getContent();
	
	/**
	 * This method returns the content of the XML tag this item represents.
	 * The content is any text written between the opening and closing tag,
	 * that is not a child tag.
	 * 
	 * @param the name of the xml tag
	 * @return The content of the item.
	 */
	public String getContent(String name)  throws NoSuchFieldException;

	/**
	 * This method returns the content of the XML tag this item represents as an Integer.
	 * The content is any text written between the opening and closing tag,
	 * that is not a child tag.
	 * 
	 * @return The content of the item as an Integer.
	 */
	public Integer  getContentAsInt();
	
	/**
	 * This method returns the content of the XML tag this item represents as an Integer.
	 * The content is any text written between the opening and closing tag,
	 * that is not a child tag.
	 * 
	 * 
	 * @return The content of the item as an Integer.
	 */
	public Integer  getContentAsInt(String name) throws NoSuchFieldException;

	/**
	 * This method returns the content of the XML tag this item represents as a Double.
	 * The content is any text written between the opening and closing tag,
	 * that is not a child tag.
	 * 
	 * @return The content of the item as a Double.
	 */
	public Double  getContentAsDouble();
	
	/**
	 * This method returns the content of the XML tag this item represents as a Double.
	 * The content is any text written between the opening and closing tag,
	 * that is not a child tag.
	 * 
	 * @param the name of the xml tag
	 * @return The content of the item as a Double.
	 */
	public Double  getContentAsDouble(String name) throws NoSuchFieldException;;

	/**
	 * This method returns a list of children items of this item. 
	 * A child item represents a child tag of the XML file.
	 * 
	 * @return A List of children ResponseItems. If there are no children, returns an empty list.
	 */
	public List<Item> getChildren();

	/**
	 * This method returns the number of children items of this item. 
	 * A child item represents a child tag of the XML file.
	 * 
	 * @return The number of children ResponseItems.
	 */
	public Integer getChildrenCount();

	/**
	 * This method returns the first child item of this item that matches the given name. 
	 * The child item represents a child tag of the XML file. 
	 * If there are several children with the same name, one should use the <code>getChildAsList</code>
	 * method to get a List of items, as this method will return only the first one.
	 * 
	 * @param The name of the tag. E.g. "\<foobar\>"'s name is "foobar".
	 * @return The child Item that matches the given name.
	 * @throws NoSuchFieldException if there isn't a matching item
	 */
	public Item getChild(String name) throws NoSuchFieldException;


	/**
	 * This method returns a List of child items of this item that match the given name. 
	 * The child item represents a child tag of the XML file. 
	 * 
	 * @param name The name of the tags. E.g. "\<foobar\>"'s name is "foobar".
	 * @return The List of children ResponseItem that match the given name.
	 * @throws NoSuchFieldException if there isn't a matching item
	 */
	public List<Item> getChildAsList(String name) throws NoSuchFieldException;

	/**
	 * This method is used when building the structure to add a child by using its name to another item.
	 * The child represents a child tag in the XML file.
	 * 
	 * @param name the name of the xml tag
	 */
	public Item addChild(String name);
	
	/**
	 * This method is used when building the structure to add a child item to another item.
	 * The child represents a child tag in the XML file.
	 * 
	 * @param name the name of the xml tag
	 */
	public Item addChild(Item item);
	
	/**
	 * This method sets the content of the XML tag this item represents.
	 * The content is any text written between the opening and closing tag,
	 * that is not a child tag.
	 * 
	 * @param content The content of the item.
	 * @return 
	 */
	public Item setContent(String content);

	/**
	 * This method returns a Map containing the XML tag attributes of the tag
	 * the Item represents.
	 * 
	 * @return The Map of Attributes of the XML tag.
	 */
	public Map<String, String> getTagAttributes();

	/**
	 * This method returns the value of a XML tag attribute of the tag
	 * the Item represents.
	 * 
	 * @param key The name of the attribute. E.g. <tag name='value' attr2='...' ...>
	 * @return The value of the attribute
	 * @throws NoSuchFieldException
	 */
	public String getTagAttribute(String key) throws NoSuchFieldException;

	/**
	 * Returns the name of the XML tag this item represents.
	 * 
	 * @return The name of the tag.
	 */
	public String getName();

	/**
	 * Return the number of occurrences of a tagName
	 * 
	 * @param tagName
	 * @return
	 */
	public int getListSizeFromItem(String tagName);
	
	/**
	 * Returns the string representation of the XML this node represents, including its children.
	 * 
	 * @return the string representation of this XML DOM element
	 */
	public String getElementAsString();

	/**
	 * Return the entire structure of the Item object when it represents a request
	 * 
	 * @return
	 */
	public String printAsRequest();
	
	/**
	 * Return the entire structure of the Item object when it represents a response
	 * 
	 * @return
	 */
	public String printAsResponse();

}
