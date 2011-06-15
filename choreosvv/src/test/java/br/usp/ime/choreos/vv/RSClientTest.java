package br.usp.ime.choreos.vv;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

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

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("title", "New Moon");
		parameters.put("author", "Stephanie Mayer");
		
		String id = client.post("/addBook", parameters);
		
		String retrievedBook = client.get("/book/" + id);
		String expectedBook = "{\"title\":\"New Moon\",\"author\":\"Stephanie Mayer\"}";

		assertEquals(expectedBook, retrievedBook);
		
		String deletedBook = client.delete("/book/" + id);
		
		assertEquals(deletedBook, expectedBook);
		assertEquals("", client.get("/book/" + id));
	}
		

}
