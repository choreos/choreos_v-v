package eu.choreos.vv.itemprinter;

import eu.choreos.vv.clientgenerator.Item;

public class ItemRequest implements ItemRepresentation{

	private String name;
	private Item item;
	
	public ItemRequest(Item item){
		this.item = item;
		name = PrinterUtils.toCamelCase(item.getName());
	}
	
	@Override
	public String buildHeader() {
		return setContent(item, "Item " + name + " = new ItemImpl(\"" + name + "\");");
	}

	@Override
	public String buildBody() {
		String printedItem = "";
		
		for(Item entry : item.getChildren())
			printedItem += PrinterUtils.printChildren(entry, item.getName(), this);
				
		return printedItem;
	}
	
	private static String setContent(Item item, String itemPrint){
		if (item.getContent() != null)
			itemPrint = itemPrint.replaceAll(";", ".setContent(\"" + item.getContent() +  "\");");
		
		return itemPrint;
	}

	@Override
	public String getLeafContent(Item item, String parentName) {
		return setContent(item, "\n" + parentName + ".addChild(\""+PrinterUtils.toCamelCase(item.getName())+"\");");
	}

	@Override
	public String getChildAccessMethod(Item item, String parentName) {
		return setContent(item, "\nItem " + PrinterUtils.toCamelCase(item.getName()) + " = "  + parentName + ".addChild(\""+PrinterUtils.toCamelCase(item.getName())+"\");");
	}
	

}
