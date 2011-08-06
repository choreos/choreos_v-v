package eu.choreos.vv;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.RSClient;


public class RSClientTest {
	
	private static RSClient client;
	
	@BeforeClass
	public static void setup() throws IllegalArgumentException, SQLException, ClassNotFoundException, IOException{
		client = new RSClient("http://choreos.ime.usp.br", "/rest/bookstore", 53111);
	}
	
	@Test
	public void shouldGetCorrectBook(){
		String retrievedBook = client.get("/book/0");
		String expectedBook = "{\"title\":\"O Hobbit\",\"author\":\"J. R. R. Tolkien\"}";
		
		assertEquals(expectedBook, retrievedBook);
	}
	
	@Test
	public void shouldAddAndDeleteAtBook(){
		
		RSClient client = new RSClient("http://choreos.ime.usp.br", "/rest/bookstore", 53111);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("title", "The Hobbit");
		parameters.put("author", "J. R. R. Tolkien");
		
		String id = client.post("/addBook", parameters);
		
		String retrievedBook = client.get("/book/" + id);
		String expectedBook = "{\"title\":\"The Hobbit\",\"author\":\"J. R. R. Tolkien\"}";

		assertEquals(expectedBook, retrievedBook);
		
		String deletedBook = client.delete("/book/" + id);
		
		assertEquals(deletedBook, expectedBook);
		assertEquals("", client.get("/book/" + id));
	}
		

}
