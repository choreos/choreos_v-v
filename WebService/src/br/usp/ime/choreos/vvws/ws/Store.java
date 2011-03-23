package br.usp.ime.choreos.vvws.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.choreos.vvws.model.CD;
import br.usp.ime.choreos.vvws.model.Customer;

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
