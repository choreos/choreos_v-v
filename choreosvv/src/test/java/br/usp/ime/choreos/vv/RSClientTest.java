package br.usp.ime.choreos.vv;

import static com.jayway.restassured.RestAssured.get;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;


public class RSClientTest {
	
	private static RSClient client;
	
	@BeforeClass
	public static void setup() throws IllegalArgumentException, SQLException, ClassNotFoundException, IOException{
		client = new RSClient("http://localhost", 9881);
	}
	
	@Test
	public void shouldGetCorrectBook(){
		String retrievedBook = client.get("/bookstore/book/0");
		String expectedBook = "Book [title=O Hobbit, author=J. R. R. Tolkien]";
		
		assertEquals(retrievedBook, expectedBook);
	}
	
	@Test
	public void shouldDeleteFirstBook(){
		String deletedBook = client.delete("/bookstore/book/0");
		String expectedBook = "Book [title=O Hobbit, author=J. R. R. Tolkien]";
		
		assertEquals(deletedBook, expectedBook);
		
		assertEquals("Not found!", get("/bookstore/book/0").asString());
	}

}
