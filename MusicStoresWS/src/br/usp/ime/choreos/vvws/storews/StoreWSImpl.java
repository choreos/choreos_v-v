package br.usp.ime.choreos.vvws.storews;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import br.usp.ime.choreos.vvws.common.CD;
import br.usp.ime.choreos.vvws.common.Customer;
import br.usp.ime.choreos.vvws.common.MockCDs;

@WebService(endpointInterface="br.usp.ime.choreos.vvws.storews.StoreWS")
public class StoreWSImpl implements StoreWS {

	List<CD> cds = MockCDs.CDList;
	
	@Override
	public List<CD> searchByArtist(String artist) {

		List<CD> results = new ArrayList<CD>();
		
		for(CD cd : cds){
			if(cd.getArtist().contains(artist)){
				results.add(cd);
			}
		}
			
		return results;
	}

	@Override
	public List<CD> searchByGenre(String genre) {
		
		List<CD> results = new ArrayList<CD>();
		
		for(CD cd : cds){
			if(cd.getGenre().contains(genre)){
				results.add(cd);
			}
		}
			
		return results;
	
	}

	@Override
	public List<CD> searchByTitle(String title) {
		
		List<CD> results = new ArrayList<CD>();
		
		for(CD cd : cds){
			if(cd.getTitle().contains(title)){
				results.add(cd);
			}
		}
			
		return results;
	}

	@Override
	public Boolean purchase(CD cd, Customer customer) {
		return true;
	}

	
}
