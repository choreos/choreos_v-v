package br.usp.ime.choreos.vvrs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/getCatalog")
public class BookStoreRS {

	@GET
	@Produces("text/plain")
	public String getClichedMessage() {
		return "Hello World";
	}
}

class Run {
	public void publishTravelAgencyWS() throws SQLException, ClassNotFoundException, 
	IllegalArgumentException, IOException{
		final String baseUri = "http://localhost:9881/";
		final Map<String, String> initParams = new HashMap<String, String>();

		initParams.put("com.sun.jersey.config.property.packages", 
		"br.usp.ime.ws.travelagency");

		System.out.println("Starting grizzly...");
//		GrizzlyWebContainerFactory.create(baseUri, initParams);
	}
}
