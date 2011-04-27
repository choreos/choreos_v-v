package br.usp.ime.choreos.vv;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class ResponseItemTest {
        
        private ResponseItem item;
        
        @Before
        public void setUp(){
                item = new ResponseItem("author");
        }
        
        @Test
        public void shouldAddStringContent(){
                ResponseItem itemName = new ResponseItem("name");
                itemName.addContent("Fernando Pessoa");
                
                assertEquals("Fernando Pessoa", itemName.getContent());
        }
        
        @Test
        public void shouldAddIntegerContent(){
                ResponseItem itemName = new ResponseItem("year");
                itemName.addContent("1930");
                
                assertEquals((Integer)1930, itemName.getContentAsInt());
        }
        
        @Test (expected=NumberFormatException.class)
        public void shouldThrowAnExceptionWhenTheContentCannotBeAnInteger(){
                ResponseItem itemName = new ResponseItem("year");
                itemName.addContent("1930 A.C");
                
                itemName.getContentAsInt();
        }
        
        @Test
        public void shouldAddDoubleContent(){
                ResponseItem itemName = new ResponseItem("price");
                itemName.addContent("12.0");
                
                assertEquals(12.0, itemName.getContentAsDouble(), 1e-9);
        }
        
        @Test (expected=NumberFormatException.class)
        public void shouldThrowAnExceptionWhenTheContentCannotBeADouble(){
                ResponseItem itemName = new ResponseItem("price");
                itemName.addContent("R$ 12.0");
                
                itemName.getContentAsInt();
        }
        
        @Test
        public void shouldAddAnItemWithParameters(){
                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("xsd", "www.br.usp.ime.choreos.vv");
                ResponseItem childItem = new ResponseItem("name", parameters );
                
                assertEquals("www.br.usp.ime.choreos.vv", childItem.getTagParameters().get("xsd"));
        }
        
        @Test
        public void shouldAddAnSimpleItem(){
                ResponseItem childItem = new ResponseItem("name");
                childItem.addContent("Fernando Pessoa");
                item.addItem(childItem);
               
               assertEquals("Fernando Pessoa", item.getAttr("name").getContent());
        }
        
        @Test
        public void shouldAddTwoSimpleItemWithTheSameName() throws NoSuchFieldException{
                ResponseItem childItemA = new ResponseItem("name");
                childItemA.addContent("Fernando Pessoa");
                item.addItem(childItemA);
                
                ResponseItem childItemB = new ResponseItem("name");
                childItemB.addContent("Machado de Assis");
                item.addItem(childItemB);
                
                List<ResponseItem> children = item.getAttrAsList("name");
               
               assertEquals("Fernando Pessoa", children.get(0).getContent());
               assertEquals("Machado de Assis", children.get(1).getContent());
        }
        
        @Test
        public void shouldAddAComplexItem(){
                ResponseItem coAuthorItem = new ResponseItem("co-author");  
                ResponseItem nameItem = new ResponseItem("name");
                nameItem.addContent("Eça de Queiroz");
                
                coAuthorItem.addItem(nameItem);
                item.addItem(coAuthorItem);
                
                assertEquals("Eça de Queiroz", item.getAttr("co-author").getAttr("name").getContent());
        }
        
        @Test
        public void shouldAddAComplexItemAnHaveContent(){
                ResponseItem coAuthorItem = new ResponseItem("co-author");  
                ResponseItem nameItem = new ResponseItem("name");
                nameItem.addContent("Eça de Queiroz");
                
                coAuthorItem.addItem(nameItem);
                item.addItem(coAuthorItem);
                item.addContent("1935");
                
                
                assertEquals("Eça de Queiroz", item.getAttr("co-author").getAttr("name").getContent());
                assertEquals((Integer)1935, item.getContentAsInt());
        }
        
        @Test
        public void shouldAddAComplexWithTwoSimpleItems() throws NoSuchFieldException{
                ResponseItem coAuthorItem = new ResponseItem("co-author");  
                ResponseItem nameItemA = new ResponseItem("name");
                nameItemA.addContent("Eça de Queiroz");
                
                ResponseItem nameItemB = new ResponseItem("name");
                nameItemB.addContent("Olavo Bilac");
                
                coAuthorItem.addItem(nameItemA);
                coAuthorItem.addItem(nameItemB);
                item.addItem(coAuthorItem);
                
                List<ResponseItem> coAuthors = item.getAttr("co-author").getAttrAsList("name");
                
                assertEquals("Eça de Queiroz", coAuthors.get(0).getContent());
                assertEquals("Olavo Bilac", coAuthors.get(1).getContent());
        }
        
        @Test (expected=NoSuchFieldException.class)
        public void shouldThrowAnExceptionWhenTheTagIsTakenAsAList() throws NoSuchFieldException{
                ResponseItem childItem = new ResponseItem("name");
                childItem.addContent("Fernando Pessoa");
                item.addItem(childItem);
                
                item.getAttrAsList("name");
        }

}
