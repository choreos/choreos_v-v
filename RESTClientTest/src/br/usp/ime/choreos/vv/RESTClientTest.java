package br.usp.ime.choreos.vv;

import com.jayway.restassured.RestAssured;

public class RESTClientTest {
	
	public static void main(String [] args){
		RestAssured.baseURI = "http://api.openlyrics.org";
		RestAssured.port = 80;
		
		String songResponse = RestAssured.get("/search/song?format=json&query=Welcome+to+the+jungle").asString();
		System.out.println("Search for 'Welcome to the Jungle'");
		System.out.println(songResponse);
		
		songResponse = RestAssured.with().parameters("format", "batata", "query", "Wish+you+were+here").
				get("/search/song").asString();
		System.out.println("Search for 'Welcome to the Jungle'");
		System.out.println(songResponse);

	}

}
