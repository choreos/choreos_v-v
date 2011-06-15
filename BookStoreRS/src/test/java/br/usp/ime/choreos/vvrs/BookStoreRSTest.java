package br.usp.ime.choreos.vvrs;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import groovyx.net.http.ContentType;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

public class BookStoreRSTest {
	
	@BeforeClass
	public static void setup() throws IllegalArgumentException, SQLException, ClassNotFoundException, IOException{
		RestAssured.baseURI = "http://choreos.ime.usp.br";
		RestAssured.basePath = "/rest/bookstore";
		RestAssured.port = 53111;
	}
	
	@Test
	public void shouldGetCorrectBook(){
		String retrievedBook = get("/book/0").asString();
		String expectedBook = "{\"title\":\"O Hobbit\",\"author\":\"J. R. R. Tolkien\"}";
		
		assertEquals(expectedBook, retrievedBook);
	}
	
	@Test
	public void shouldAddAndDeleteABook(){
		String bookID = RestAssured.given().parameters("title", "Monalisa Overdrive", "author", "William Gibson").post("/addBook").asString();
		String retrievedBook = get("/book/" + bookID).asString();
		String expectedBook = "{\"title\":\"Monalisa Overdrive\",\"author\":\"William Gibson\"}";
		
		assertEquals(expectedBook, retrievedBook);
				
		String deletedBook = delete("/book/" +  bookID).asString();
		
		assertEquals(expectedBook, deletedBook);
		
		assertFalse(expectedBook.equals(get("/book/" +  bookID)));		
	}
	
	@Test
	public void shouldUpdateBook(){
		String bookJSON = "{\"title\":\"O Hobbit\",\"author\":\"J. R. R. Tolkien\"}";
		String responseString = RestAssured.with().contentType(ContentType.JSON).body(bookJSON).put("/updateBook/0").asString();
		String expectedBook = "{\"title\":\"O Hobbit\",\"author\":\"J. R. R. Tolkien\"}";
		
		assertEquals(expectedBook, responseString);
	}
	
}
