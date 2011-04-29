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
                itemName.setContent("Fernando Pessoa");
                
                assertEquals("Fernando Pessoa", itemName.getContent());
        }
        
        @Test
        public void shouldAddIntegerContent(){
                ResponseItem itemName = new ResponseItem("year");
                itemName.setContent("1930");
                
                assertEquals((Integer)1930, itemName.getContentAsInt());
        }
        
        @Test (expected=NumberFormatException.class)
        public void shouldThrowAnExceptionWhenTheContentCannotBeAnInteger(){
                ResponseItem itemName = new ResponseItem("year");
                itemName.setContent("1930 A.C");
                
                itemName.getContentAsInt();
        }
        
        @Test
        public void shouldAddDoubleContent(){
                ResponseItem itemName = new ResponseItem("price");
                itemName.setContent("12.0");
                
                assertEquals(12.0, itemName.getContentAsDouble(), 1e-9);
        }
        
        @Test (expected=NumberFormatException.class)
        public void shouldThrowAnExceptionWhenTheContentCannotBeADouble(){
                ResponseItem itemName = new ResponseItem("price");
                itemName.setContent("R$ 12.0");
                
                itemName.getContentAsInt();
        }
        
        @Test
        public void shouldAddAnItemWithParameters(){
                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("xsd", "www.br.usp.ime.choreos.vv");
                ResponseItem childItem = new ResponseItem("name", parameters );
                
                assertEquals("www.br.usp.ime.choreos.vv", childItem.getTagAttributes().get("xsd"));
        }
        
        @Test
        public void shouldAddAnSimpleItem() throws NoSuchFieldException{
                ResponseItem childItem = new ResponseItem("name");
                childItem.setContent("Fernando Pessoa");
                item.addChild(childItem);
               
               assertEquals("Fernando Pessoa", item.getChild("name").getContent());
        }
        
        @Test
        public void shouldAddTwoSimpleItemWithTheSameName() throws NoSuchFieldException{
                ResponseItem childItemA = new ResponseItem("name");
                childItemA.setContent("Fernando Pessoa");
                item.addChild(childItemA);
                
                ResponseItem childItemB = new ResponseItem("name");
                childItemB.setContent("Machado de Assis");
                item.addChild(childItemB);
                
                List<ResponseItem> children = item.getChildAsList("name");
               
               assertEquals("Fernando Pessoa", children.get(0).getContent());
               assertEquals("Machado de Assis", children.get(1).getContent());
        }
        
        @Test
        public void shouldAddAComplexItem() throws NoSuchFieldException{
                ResponseItem coAuthorItem = new ResponseItem("co-author");  
                ResponseItem nameItem = new ResponseItem("name");
                nameItem.setContent("Eça de Queiroz");
                
                coAuthorItem.addChild(nameItem);
                item.addChild(coAuthorItem);
                
                assertEquals("Eça de Queiroz", item.getChild("co-author").getChild("name").getContent());
        }
        
        @Test
        public void shouldAddAComplexItemAnHaveContent() throws NoSuchFieldException{
                ResponseItem coAuthorItem = new ResponseItem("co-author");  
                ResponseItem nameItem = new ResponseItem("name");
                nameItem.setContent("Eça de Queiroz");
                
                coAuthorItem.addChild(nameItem);
                item.addChild(coAuthorItem);
                item.setContent("1935");
                
                
                assertEquals("Eça de Queiroz", item.getChild("co-author").getChild("name").getContent());
                assertEquals((Integer)1935, item.getContentAsInt());
        }
        
        @Test
        public void shouldAddAComplexWithTwoSimpleItems() throws NoSuchFieldException{
                ResponseItem coAuthorItem = new ResponseItem("co-author");  
                ResponseItem nameItemA = new ResponseItem("name");
                nameItemA.setContent("Eça de Queiroz");
                
                ResponseItem nameItemB = new ResponseItem("name");
                nameItemB.setContent("Olavo Bilac");
                
                coAuthorItem.addChild(nameItemA);
                coAuthorItem.addChild(nameItemB);
                item.addChild(coAuthorItem);
                
                List<ResponseItem> coAuthors = item.getChild("co-author").getChildAsList("name");
                
                assertEquals("Eça de Queiroz", coAuthors.get(0).getContent());
                assertEquals("Olavo Bilac", coAuthors.get(1).getContent());
        }
        
        @Test
        public void shouldGetAListWithOneElement() throws NoSuchFieldException {
                ResponseItem childItem = new ResponseItem("name");
                childItem.setContent("Fernando Pessoa");
                item.addChild(childItem);
                
                assertEquals(1, item.getChildAsList("name").size());
                assertEquals( "Fernando Pessoa", item.getChild("name").getContent());
        }

        @Test (expected=NoSuchFieldException.class)
         public void shouldThrowsAnExceptionWhenTheElementNotExist() throws NoSuchFieldException {
                 item.getChild("sirname");
        }
}
