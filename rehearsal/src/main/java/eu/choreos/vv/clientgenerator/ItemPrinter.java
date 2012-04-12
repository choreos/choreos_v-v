package eu.choreos.vv.clientgenerator;





public class ItemPrinter {

	public static String print(Item item) {
		String name = toCamelCase(item.getName());
		String printedItem = setContent(item, "Item " + name + " = new ItemImpl(\"" + name + "\");");
		
		for(Item entry : item.getChildren())
			printedItem += printChildren(entry, item.getName());
				
		return printedItem;
	}
	
	private static String printChildren(Item item, String parentName){
		String printedItem = "";
		
		if (item.getChildrenCount() == 0)
			return  setContent(item, "\n" + parentName + ".addChild(\""+toCamelCase(item.getName())+"\");");

		printedItem += setContent(item, "\nItem " + toCamelCase(item.getName()) + " = "  + parentName + ".addChild(\""+toCamelCase(item.getName())+"\");");
		
		for (Item  entry : item.getChildren())
			printedItem += printChildren(entry, item.getName());
		
		return printedItem;
	}
	
	private static String setContent(Item item, String itemPrint){
		if (item.getContent() != null)
			itemPrint = itemPrint.replaceAll(";", ".setContent(\"" + item.getContent() +  "\");");
		
		return itemPrint;
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

