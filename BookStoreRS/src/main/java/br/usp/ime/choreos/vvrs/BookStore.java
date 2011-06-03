package br.usp.ime.choreos.vvrs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.usp.ime.choreos.vvrs.model.Book;
import br.usp.ime.choreos.vvrs.model.MockBooks;

public class BookStore {

	private static Map<Integer, Book> books = MockBooks.bookList; 
	
	public static Integer addBook(Book book) {
		Integer newID = MockBooks.getNextId();
		books.put(newID, book);
		return newID;
        }

	public static Book getBookById( int id) {

		Book book = books.get(id);
		return book;
        }

	public static String updateBook(int id, Book book) {
		Book insertedBook = books.put(id, book);
		if (insertedBook != null) return insertedBook.toString();
		return null;
        }

	public static String removeBook(int id) {
		
		Book book = books.remove(id);
		if (book != null) return book.toString();
		return null;
        }

	public static void clear() {

		books.clear();
        }

	public static List<Book> getBookByTitle(String title) {

		List<Book> found = new ArrayList<Book>();
		
		for (Book b: books.values()) {
			if (b.getTitle().toUpperCase().contains(title.toUpperCase()))
				found.add(b);
		}
		
		return found;
        }

	public static List<Book> getAllBooks() {
			List<Book> found = new ArrayList<Book>(books.values());
			return found;
        }
}


