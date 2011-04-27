package br.usp.ime.choreos.vv;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Stack;

import javax.smartcardio.ATR;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Utility class to parse the Soap XML response of a Web Service operation
 * Obs: it's a package visibility class, since it's not used by the client
 * 
 * @author leonardo, guilherme
 *
 */
class ResponseParser {
	
	private static SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	private SAXParser parser;
	private ResponseItem result;

	
	private class ResponseParserHandler extends DefaultHandler {
		private String opName;
		private boolean processing;
		private Stack<ResponseItem> tagStack = new Stack<ResponseItem>();

		/**
		 * @param ch     - The characters.
		 * @param start  - The start position in the character array.
		 * @param length - The number of characters to use from the character array.
		 */
		@Override
		public void characters(char[] ch, int start, int lenght) 
			throws SAXException {
			
			String trimmed = new String(ch, start, lenght).trim();
			
			if (processing && !trimmed.isEmpty()){
			        tagStack.peek().setContent(trimmed);			}
		}

		/**
		 * @param uri       - The Namespace URI.
		 * @param localName - The local name (without prefix).
		 * @param qName     - The qualified name (with prefix).
		 */
		@Override
		public void endElement(String uri, String localName, String qName)
			throws SAXException {
			
			//deletes namespace
			String name = qName.split(":")[1];
			
			if (processing) {
				if (name.equals(opName)){
					processing = false;
				} else {
					ResponseItem poped = tagStack.pop();
					if (!tagStack.empty()){
						ResponseItem father = tagStack.peek();
						father.addItem(poped);
					}
					else {
					        result = poped;
					}
				}
			}
			
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
			
			//deletes namespace
			String name = qName.split(":")[1];
			
			if (name.endsWith("Response") && !processing) {
				opName = name;
				processing = true;
			} 
			else if (processing) {
			        HashMap<String, String> parameters = new HashMap<String, String>();
			        
			        for(int i=0; i< attributes.getLength(); i++)
			                parameters.put(attributes.getQName(i), attributes.getValue(i));
			        
	                        result = new ResponseItem(name, parameters);
				tagStack.push(result);
			}
		}
	}
	
	public ResponseItem parse(String xml) throws ParserConfigurationException, SAXException {
		try {
		        InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			parser = parserFactory.newSAXParser();
			parser.parse(is, new ResponseParserHandler());
		}  
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
