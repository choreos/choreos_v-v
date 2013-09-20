package eu.choreos.vv.data.convertion.xml;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public abstract class XMLConverter<T> {
	
	private PrintWriter writer;
	
	public XMLConverter(PrintWriter writer) {
		this.writer = writer;
	}
	
	public void convert(T item) {
		writer.flush();
	}

	protected void openTag(String name) {
		openTag(name, null);
	}
	
	protected void openTag(String name, Map<String, String> parameters) {
		writer.print("<"+name);
		if (parameters != null) {
			for(String param: parameters.keySet()) {
				writer.print(" " + param + "=\"" + parameters.get(param) + "\"");
			}
		}
		writer.println(">");
	}

	protected void closeTag(String name) {
		writer.println("</"+name+">");
	}

	protected void value(String value) {
		writer.println(value);
	}
	
	protected <E> void writeList(String tag, List<E> list) {
		for(E item: list) {
			openTag(tag);
			value(item.toString());
			closeTag(tag);
		}
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}
	
	

}