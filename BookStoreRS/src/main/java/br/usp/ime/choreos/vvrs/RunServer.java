package br.usp.ime.choreos.vvrs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

public class RunServer {

	public void publishBookStoreRS() throws SQLException, ClassNotFoundException, 
	IllegalArgumentException, IOException{
		final String baseUri = "http://localhost:9881/";
		final Map<String, String> initParams = new HashMap<String, String>();

		initParams.put("com.sun.jersey.config.property.packages", 
		"br.usp.ime.choreos.vvrs");

		GrizzlyWebContainerFactory.create(baseUri, initParams);
	}

	public static void main(String[] args) throws IllegalArgumentException, SQLException, ClassNotFoundException, IOException {
		
		RunServer run = new RunServer();
		run.publishBookStoreRS();
	}
}
