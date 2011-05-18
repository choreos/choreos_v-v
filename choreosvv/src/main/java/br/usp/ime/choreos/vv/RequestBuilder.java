package br.usp.ime.choreos.vv;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import br.usp.ime.choreos.vv.exceptions.ParserException;

class RequestBuilder {
	private static SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	private SAXParser parser;
	private StringBuilder outputBuilder = new StringBuilder();

	private class RequestParserHandler extends DefaultHandler {
		private Item item;
		private Stack<HashMap<String, List<Item>>> itemStack = new Stack<HashMap<String, List<Item>>>();

		public RequestParserHandler(Item item){
			super();
			this.item = item;
		}
		
		/**
		 * @param ch     - The characters.
		 * @param start  - The start position in the character array.
		 * @param length - The number of characters to use from the character array.
		 */
		@Override
		public void characters(char[] ch, int start, int lenght) 
		throws SAXException {

			String trimmed = new String(ch, start, lenght).trim();

			if (!trimmed.isEmpty() && !itemStack.empty()){
				//TODO
			}
		}

		/**
		 * @param uri       - The Namespace URI.
		 * @param localName - The local name (without prefix).
		 * @param qName     - The qualified name (with prefix).
		 */
		@Override
		public void endElement(String uri, String localName, String qName)
		throws SAXException {
			outputBuilder.append("</" + qName + ">");
		}

		/**
		 *	@param uri        - The Namespace URI.
		 *	@param localName  - The local name (without prefix).
		 *	@param qName      - The qualified name (with prefix).
		 *	@param attributes - The attributes attached to the element.
		 */
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) 
		throws SAXException {

			String name = getNameWithoutNamespace(qName);
			
			if(!itemStack.empty() && itemStack.peek().containsKey(name)){
				// inserts all children in the stack as a "bag"
				
				HashMap<String, List<Item>> bag = new HashMap<String, List<Item>>();
				
				for(Item child : item.getChildren()){
					List<Item> children = bag.get(child.getName());
					
					if(children == null){
						children = new LinkedList<Item>();
						bag.put(child.getName(), children);
					}
					
					children.add(child);
				}
			} 

			outputBuilder.append("<" + qName);
			
			int numAttributes = attributes.getLength();
			for(int i = 0; i < numAttributes; i++){
				String attrName = attributes.getQName(i);
				String value = attributes.getValue(i);
				
				outputBuilder.append(" " + attrName + "=\"" + value + "\"");
			}
			
			outputBuilder.append(">");
		}

		private String getNameWithoutNamespace(String qName) {
			String[] names = qName.split(":");                        

			return names[names.length  - 1];
		}
	}

	public String buildRequest(String baseXml, Item root) throws ParserException {
		
		if(root == null){
			// FIXME: What if there should be parameters?
			return baseXml;
		}
		
		try {
			InputStream is = new ByteArrayInputStream(baseXml.getBytes("UTF-8"));
			parser = parserFactory.newSAXParser();
			parser.parse(is, new RequestParserHandler(root));
		}  
		catch (Exception e) {
			throw new ParserException(e);
		}

		return outputBuilder.toString();
	}

}
