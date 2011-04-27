package br.usp.ime.choreos.vv;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ResponseItem implements ResponseInterface{

        private String content;
        private String name;
        private HashMap<String, String> tagParameters;
        private HashMap<String, LinkedList<ResponseItem>>items;
        
        public ResponseItem(String tagName, HashMap<String, String> tagParameters) {
                this.name = tagName;
                this.tagParameters = tagParameters;
                items = new HashMap<String, LinkedList<ResponseItem>>();
        }

        public ResponseItem(String tagName) {
                this.name = tagName;
                items = new HashMap<String, LinkedList<ResponseItem>>();
                this.tagParameters = new HashMap<String, String>();
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
        
        public ResponseItem getAttr(String name) throws NoSuchFieldException {
                if(!items.containsKey(name))
                        throw new NoSuchFieldException();
                
                return items.get(name).getFirst();
        }

        public List<ResponseItem> getAttrAsList(String name) throws NoSuchFieldException {
                if(!items.containsKey(name))
                        throw new NoSuchFieldException();
                return items.get(name);
        }

        public void addItem(ResponseItem item) {
                LinkedList<ResponseItem> currentItems = items.get(item.getName());
                
                if(currentItems == null)
                        currentItems = new LinkedList<ResponseItem>();
                
                currentItems.addLast(item);
                items.put(item.getName(), currentItems);
        }

        public void setContent(String content) {
               this.content = content;
                
        }

        public HashMap<String, String> getTagParameters() {
                return tagParameters;
        }
        
        public String getName(){
                return name;
        }        
        
        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((content == null) ? 0 : content.hashCode());
                result = prime * result + ((items == null) ? 0 : items.hashCode());
                result = prime * result + ((name == null) ? 0 : name.hashCode());
                result = prime * result + ((tagParameters == null) ? 0 : tagParameters.hashCode());
                return result;
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                ResponseItem other = (ResponseItem) obj;
                if (content == null) {
                        if (other.content != null)
                                return false;
                } else if (!content.equals(other.content))
                        return false;
                if (items == null) {
                        if (other.items != null)
                                return false;
                } else if (!items.equals(other.items))
                        return false;
                if (name == null) {
                        if (other.name != null)
                                return false;
                } else if (!name.equals(other.name))
                        return false;
                if (tagParameters == null) {
                        if (other.tagParameters != null)
                                return false;
                } else if (!tagParameters.equals(other.tagParameters))
                        return false;
                return true;
        }

        @Override
        public String toString() {
                return "ResponseItem [content=" + content + ", name=" + name + ", tagParameters="
                + tagParameters + ", items=" + items + "]";
        }
}
