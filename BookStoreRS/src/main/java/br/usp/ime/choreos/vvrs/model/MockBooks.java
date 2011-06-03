package br.usp.ime.choreos.vvrs.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class MockBooks {

	private static AtomicInteger ai = new AtomicInteger();
	public static Map<Integer, Book> bookList = new HashMap<Integer, Book>();
	
	static {
		bookList.put(ai.getAndIncrement(),  new Book("O Hobbit", "J. R. R. Tolkien"));
		bookList.put(ai.getAndIncrement(),  new Book("O Senhor dos Anéis - A Sociedade do Anel", "J. R. R. Tolkien"));
		bookList.put(ai.getAndIncrement(),  new Book("O Senhor dos Anéis - As Duas Torres", "J. R. R. Tolkien"));
		bookList.put(ai.getAndIncrement(),  new Book("O Senhor dos Anéis - O Retorno do Rei", "J. R. R. Tolkien"));
		bookList.put(ai.getAndIncrement(),  new Book("O Silmarillion", "J. R. R. Tolkien"));
	}	
	
	public static Integer getNextId(){
		return ai.getAndIncrement();
	}
}
