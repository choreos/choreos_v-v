package br.usp.ime.choreos.vvrs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.usp.ime.choreos.vvrs.model.Book;
import br.usp.ime.choreos.vvrs.model.MockBooks;

public class BookStore {

	private static List<Book> books = MockBooks.bookList; 
	
	public static int addBook(Book book) {
	        
		books.add(book);
		return books.size()-1;
        }

	public static Book getBookById( int id) {

		if (books.size() > id)
			return books.get(id);
		else
			return null;
        }

	public static void updateBook(int id, Book book) {

		books.set(id, book);
        }

	public static void removeBook(int id) {

		books.remove(id);
        }

	public static void clear() {

		books.clear();
        }

	public static List<Book> getBookByTitle(String title) {

		List<Book> found = new ArrayList<Book>();
		
		for (Book b: books) {
			if (b.getTitle().toUpperCase().contains(title.toUpperCase()))
				found.add(b);
		}
		
		return found;
        }

	public static List<Book> getAllBooks() {

		return Collections.unmodifiableList(books);
        }
}


