package eu.choreos.vv.itemprinter;

import eu.choreos.vv.clientgenerator.Item;

public class PrinterUtils {
	
	public static String toCamelCase(String input){
		if(!input.contains(" "))  
			return input;
		
		  String[] parts = input.split(" ");
		
		   String camelCaseString = parts[0];
		   
		   for (int i=1; i< parts.length; i++){
		      camelCaseString = camelCaseString + toProperCase(parts[i]);
		   }
		   
		   return camelCaseString;
	}
	
	static String toProperCase(String s) {
	    return s.substring(0, 1).toUpperCase() +
	               s.substring(1).toLowerCase();
	}
	
	public  static String printChildren(Item item, String parentName, ItemRepresentation representation){
		String printedItem = "";
		
		if (item.getChildrenCount() == 0)
			return  representation.getLeafContent(item, parentName);

		printedItem +=  representation.getChildAccessMethod(item, parentName);
		
		for (Item  entry : item.getChildren())
			printedItem += printChildren(entry, item.getName(), representation);
		
		return printedItem;
	}

}
