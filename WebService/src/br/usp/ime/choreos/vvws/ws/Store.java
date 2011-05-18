package br.usp.ime.choreos.vvws.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService
public interface Store {
	
	@WebMethod
	List<CD> searchByArtist(String artist);
	
	@WebMethod
	List<CD> searchByGenre(String genre);
	
	@WebMethod
	List<CD> searchByTitle(String title);
	
	@WebMethod
	Boolean purchase(CD cd, Customer customer);
	
}
