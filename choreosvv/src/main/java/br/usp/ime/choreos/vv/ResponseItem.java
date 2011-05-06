package br.usp.ime.choreos.vv;

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
public interface ResponseItem {
        
        /**
         * This method returns the content of the XML tag this item represents.
         * The content is any text written between the opening and closing tag,
         * that is not a child tag.
         * 
         * @return The content of the item.
         */
		public String getContent();
        
        /**
         * This method returns the content of the XML tag this item represents as an Integer.
         * The content is any text written between the opening and closing tag,
         * that is not a child tag.
         * 
         * @return The content of the item as an Integer.
         */
        public Integer  getContentAsInt();
        
        /**
         * This method returns the content of the XML tag this item represents as a Double.
         * The content is any text written between the opening and closing tag,
         * that is not a child tag.
         * 
         * @return The content of the item as a Double.
         */
        public Double  getContentAsDouble();

        /**
         * This method returns a list of children items of this item. 
         * A child item represents a child tag of the XML file.
         * 
         * @return A List of children ResponseItems. If there are no children, returns an empty list.
         */
        public List<ResponseItem> getChildren();
        
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
        public ResponseItem getChild(String name) throws NoSuchFieldException;
        
        
        /**
         * This method returns a List of child items of this item that match the given name. 
         * The child item represents a child tag of the XML file. 
         * 
         * @param name The name of the tags. E.g. "\<foobar\>"'s name is "foobar".
         * @return The List of children ResponseItem that match the given name.
         * @throws NoSuchFieldException if there isn't a matching item
         */
        public List<ResponseItem> getChildAsList(String name) throws NoSuchFieldException;
        
        /**
         * This method is used when building the structure to add a child item to another item.
         * The child represents a child tag in the XML file.
         * 
         * @param item An Item representing the child.
         */
        public void addChild(ResponseItem item);
        
        /**
         * This method sets the content of the XML tag this item represents.
         * The content is any text written between the opening and closing tag,
         * that is not a child tag.
         * 
         * @param content The content of the item.
         */
        public void setContent(String content);
        
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
}
