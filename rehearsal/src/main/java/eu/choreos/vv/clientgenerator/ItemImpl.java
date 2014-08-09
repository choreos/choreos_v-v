package eu.choreos.vv.clientgenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import eu.choreos.vv.itemprinter.ItemPrinter;

public class ItemImpl implements Item{

	private static AtomicInteger ai = new AtomicInteger();
	private String content;
	private boolean contentCDATA;
	private String name;
	private Map<String, String> tagAttributes;
	private Map<String, LinkedList<Item>>items;

	// The id is only used for hashCode generation
	// It is not used on equals
	private int id;

	public ItemImpl(String tagName, HashMap<String, String> tagAttributes) {
		this.id = ai.getAndIncrement();
		this.name = tagName;
		this.tagAttributes = tagAttributes;
		items = new HashMap<String, LinkedList<Item>>();
		if (tagAttributes == null)
			tagAttributes = new HashMap<String, String>();
	}

	public ItemImpl(String tagName) {
		this.id = ai.getAndIncrement();
		this.name = tagName;
		items = new HashMap<String, LinkedList<Item>>();
		this.tagAttributes = new HashMap<String, String>();
	}

	public String getContent() {
		return content;
	}

	public Integer getContentAsInt() {
		return Integer.parseInt(content);
	}

	public Double getContentAsDouble() {
		return Double.parseDouble(content);
	}

	public Item getChild(String name) throws NoSuchFieldException {
		if(!items.containsKey(name))
			throw new NoSuchFieldException();

		return items.get(name).getFirst();
	}

	public List<Item> getChildAsList(String name) throws NoSuchFieldException {
		if(!items.containsKey(name))
			throw new NoSuchFieldException();
		return new LinkedList<Item>(items.get(name)); // return copy to protect encapsulation
	}

	public Item addChild(Item item) {
		LinkedList<Item> currentItems = items.get(item.getName());

		if(currentItems == null)
			currentItems = new LinkedList<Item>();

		currentItems.addLast(item);
		items.put(item.getName(), currentItems);
		
		return this;
	}

	public Item setContent(String content) {
		this.content = content;
		
		return this;

	}

	public Item appendContent(String content) {
		StringBuilder builder = null;
		if (this.content == null) {
			builder = new StringBuilder();
		} else {
			builder = new StringBuilder(this.content);
		}
		builder.append(content);
		this.content = builder.toString();
		return this;
	}

	public Map<String, String> getTagAttributes() {
		return new HashMap<String, String>(tagAttributes);
	}

	@Override
	public String getTagAttribute(String key) throws NoSuchFieldException {
		if (!tagAttributes.containsKey(key))
			throw new NoSuchFieldException("tagAttribute doesn't exist: " + key);
		return tagAttributes.get(key);
	}        

	public String getName(){
		return name;
	}        

	@Override
	public String toString() {
		return "ResponseItem [content=" + content + ", name=" + name + ", tagParameters="
		                      + tagAttributes + ", items=" + items + "]";
	}

	@Override
	public List<Item> getChildren() {
		List<Item> childrenList = new ArrayList<Item>();
		for (String s : this.items.keySet() ){
			childrenList.addAll(this.items.get(s));
		}

		return childrenList;
	}

	@Override
	public Integer getChildrenCount() {
		Integer childrenCount = 0;
		for (String s : this.items.keySet() ){
			childrenCount += this.items.get(s).size();
		}

		return childrenCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + id;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((tagAttributes == null) ? 0 : tagAttributes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ItemImpl other = (ItemImpl) obj;
		if (content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!content.equals(other.content)) {
			return false;
		}
		if (items == null) {
			if (other.items != null) {
				return false;
			}
		} else if (!items.equals(other.items)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (tagAttributes == null) {
			if (other.tagAttributes != null) {
				return false;
			}
		} else if (!tagAttributes.equals(other.tagAttributes)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int getListSizeFromItem(String tagName) {
		int count = 0;
		for (Item child : this.getChildren()) {
	                if(child.getName().equals(tagName))
	                	count++;
                }
		if(count == 0) {
			for (Item child : this.getChildren()) {
	                        count = child.getListSizeFromItem(tagName);
	                        if(count > 0)
	                        	return count;
                        }
		}
		else {
			return count;
		}
	        return 0;
        }

	@Override
	public String getElementAsString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<" + this.name);
		
		for (Map.Entry<String, String> entry : tagAttributes.entrySet()){
			strBuilder.append(" " + entry.getKey() + "=\"" + entry.getValue() + "\"");
		}
		
		strBuilder.append(">");
		
		for (LinkedList<Item> list : items.values()){
			for (Item item : list){
				strBuilder.append(item.getElementAsString());
			}
		}

		if (this.content != null) {
			if (this.contentCDATA) {
				strBuilder.append("<![CDATA[").append(this.content).append("]]>");
			} else {
				strBuilder.append(this.content);
			}
		}
		
		strBuilder.append("</" + this.name + ">");
		
		return strBuilder.toString();
	}

	public Item addChild(String name) {
		Item child = new ItemImpl(name);
		addChild(child);
		
		return child;
	}

	public String  getContent(String name) throws NoSuchFieldException {
		return getChild(name).getContent();
	}

	public Integer getContentAsInt(String name) throws NoSuchFieldException {
		return getChild(name).getContentAsInt();
	}

	@Override
	public Double getContentAsDouble(String name) throws NoSuchFieldException {
		return getChild(name).getContentAsDouble();
	}

	@Override
	public String printAsRequest() {
		return ItemPrinter.printAsRequest(this);
	}

	@Override
	public String printAsResponse() {
		return ItemPrinter.printAsResponse(this);
	}

	public void setContentCDATA(boolean isContentCDATA) {
		this.contentCDATA = isContentCDATA;
	}

	public boolean isContentCDATA() {
		return contentCDATA;
	}

}