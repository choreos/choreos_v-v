package eu.choreos.vv.clientgenerator;

import java.util.List;


public class ItemPrinter {

	public static String print(Item item) {
		return printItem(item);
	}
	
	private static String formatRoot(Item item){
		String name = toCamelCase(item.getName());
		String printedItem =  "Item " + name + " = new ItemImpl(\"" + name + "\");" ;
		
		if (item.getContent()!= null)
			printedItem = printedItem + "\n" + name + ".setContent(\"" + item.getContent() + "\");";
		
		return printedItem;
	}
	
	private static String formatAddChildCommand(Item root, Item child){
		return  toCamelCase(root.getName()) + ".addChild(" + toCamelCase(child.getName()) + ");";
	}
	
	private static String printItem(Item item){
		String itemObject = formatRoot(item);
		
		List<Item> itemList = item.getChildren(); 
	
		if (itemList == null)
			return itemObject;
		
		for(Item entry : itemList){
			itemObject = itemObject + "\n" +
											printItem(entry) + "\n" +
											formatAddChildCommand(item, entry);			
		}
		return itemObject;
	}
	
	private static String toCamelCase(String input){
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

}
