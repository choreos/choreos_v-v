package br.usp.ime.choreos.vv;

import java.util.HashMap;
import java.util.List;

public interface ResponseInterface {
        
        public String getContent();
        public Integer  getContentAsInt();
        public Double  getContentAsDouble();
        public ResponseItem getAttr(String name) throws NoSuchFieldException;
        public List<ResponseItem> getAttrAsList(String name) throws NoSuchFieldException;
        public void addItem(ResponseItem item);
        public void setContent(String content);
        public HashMap<String, String> getTagParameters();

}
