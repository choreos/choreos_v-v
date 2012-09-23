package eu.choreos.vvws.storews;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import eu.choreos.vvws.common.CD;
import eu.choreos.vvws.common.Customer;


@WebService 
public interface StoreWS {

	@WebMethod
	List<CD> searchByArtist(String artist);
	
	@WebMethod
	List<CD> searchByGenre(String genre);
	
	@WebMethod
	List<CD> searchByTitle(String title);
	
	@WebMethod
	Boolean purchase(CD cd, Customer customer);
}
