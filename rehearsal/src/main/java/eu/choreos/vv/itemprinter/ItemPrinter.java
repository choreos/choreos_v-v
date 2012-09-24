package eu.choreos.vv.itemprinter;

import eu.choreos.vv.clientgenerator.Item;

public class ItemPrinter {
	
	/**
	 * Build a item representation for the request item
	 * 
	 * @param item
	 * @return  a item representation for the request item
	 */
	public static String printAsRequest(Item item) {
		ItemRepresentation itemBuilder = new ItemRequest(item);
		return buildItemRepresentation(itemBuilder);
	}
	
	/**
	 * Build a item representation for the response item
	 * 
	 * @param item
	 * @return  a item representation for the response item
	 */
	public static String printAsResponse(Item item) {
		ItemRepresentation itemBuilder = new ItemResponse(item);
		return buildItemRepresentation(itemBuilder);
	}
	
	private static String buildItemRepresentation(ItemRepresentation itemBuilder) {
		String printedItem = itemBuilder.buildHeader();
		printedItem += itemBuilder.buildBody();
		
		return printedItem;
	}

}

