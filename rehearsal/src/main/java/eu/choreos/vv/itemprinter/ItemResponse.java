package eu.choreos.vv.itemprinter;

import eu.choreos.vv.clientgenerator.Item;

public class ItemResponse implements ItemRepresentation{

	private String name;
	private Item item;
	
	public ItemResponse(Item item){
		this.item = item;
		name = PrinterUtils.toCamelCase(item.getName());
	}
	
	@Override
	public String buildHeader() {
		return getContentRoot(item, "Item " + name + " = new ItemImpl(\"" + name + "\");");
	}

	@Override
	public String buildBody() {
		String printedItem = "";
		
		for(Item entry : item.getChildren())
			printedItem +=  PrinterUtils.printChildren(entry, item.getName(), this);
				
		return printedItem;
	}
	
	
	private static String getContentRoot(Item item, String itemPrint){
		if (item.getContent() != null)
			itemPrint += "\nString " + item.getName() + "Root = " + item.getName() + ".getContent()";
		
		return itemPrint;
	}
	
	public   String getLeafContent(Item item, String parentName){
	
		if (item.getContent() != null)
			return "\nString " + item.getName() + " = " + parentName + ".getContent(\"" + PrinterUtils.toCamelCase(item.getName()) + "\");";
			
		return "\nItem " + item.getName() + " = " + parentName + ".getChild();";
	}

	@Override
	public String getChildAccessMethod(Item item, String parentName) {
		return "\nItem " + PrinterUtils.toCamelCase(item.getName()) + " = "  + parentName + ".getChild();";
	}

	
	


}
