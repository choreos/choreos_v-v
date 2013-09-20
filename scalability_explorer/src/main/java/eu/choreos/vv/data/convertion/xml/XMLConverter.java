package eu.choreos.vv.data.convertion.xml;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public abstract class XMLConverter<T> {
	
	public static <T> void convert(PrintWriter writer, T item){}

	protected static void openTag(PrintWriter writer, String name) {
		openTag(writer, name, null);
	}
	
	protected static void openTag(PrintWriter writer, String name, Map<String, String> parameters) {
		writer.print("<"+name);
		if (parameters != null) {
			for(String param: parameters.keySet()) {
				writer.print(" " + param + "=\"" + parameters.get(param) + "\"");
			}
		}
		writer.println(">");
	}

	protected static void closeTag(PrintWriter writer, String name) {
		writer.println("</"+name+">");
	}

	protected static void value(PrintWriter writer, String value) {
		writer.println(value);
	}
	
	protected static <E> void writeList(PrintWriter writer, String tag, List<E> list) {
		for(E item: list) {
			openTag(writer, tag);
			value(writer, item.toString());
			closeTag(writer, tag);
		}
	}

}