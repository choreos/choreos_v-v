package br.usp.ime.choreos.vvrs.model;

import java.util.ArrayList;
import java.util.List;


public class MockBooks {

	public static List<Book> bookList = new ArrayList<Book>();
	
	static {
		bookList.add(new Book("O Hobbit", "J. R. R. Tolkien"));
		bookList.add(new Book("O Senhor dos Anéis - A Sociedade do Anel", "J. R. R. Tolkien"));
		bookList.add(new Book("O Senhor dos Anéis - As Duas Torres", "J. R. R. Tolkien"));
		bookList.add(new Book("O Senhor dos Anéis - O Retorno do Rei", "J. R. R. Tolkien"));
		bookList.add(new Book("O silmarillion", "J. R. R. Tolkien"));
	}	
}
