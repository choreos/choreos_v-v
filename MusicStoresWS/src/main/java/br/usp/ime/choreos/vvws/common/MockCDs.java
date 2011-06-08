package br.usp.ime.choreos.vvws.common;

import java.util.ArrayList;
import java.util.List;



public class MockCDs {

	public static List<CD> CDList = new ArrayList<CD>();
	
	static {
		CDList.add(new CD("The dark side of the moon", "Pink Floyd", "Psychodelic Rock", 9));
		CDList.add(new CD("Led Zeppelin I", "Led Zeppelin", "Hard Rock", 8));
		CDList.add(new CD("Have a nice day", "Bon Jovi", "Rock", 10));
		CDList.add(new CD("Help!", "The Beatles", "Rock", 7));
		CDList.add(new CD("Sevilla Barbershop", "Rossini", "Opera", 4));
		CDList.add(new CD("Notcurnes", "Frederic Chopin", "Classical", 15));
		CDList.add(new CD("Black Album", "Metallica", "Metal", 12));
	}

}
