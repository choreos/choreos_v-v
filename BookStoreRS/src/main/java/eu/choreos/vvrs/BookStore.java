package eu.choreos.vvrs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.choreos.vvrs.model.Book;
import eu.choreos.vvrs.model.MockBooks;


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

	public static Book updateBook(int id, Book book) {
		return books.put(id, book);
        }

	public static Book removeBook(int id) {
		Book book = books.remove(id);
		return book;
        }

	public static void clear() {

		books.clear();
        }

	public static List<Book> getBookByTitle(String title) {

		List<Book> found = new ArrayList<Book>();
		
		for (Book b: books.values()) {
			if (b.title.toUpperCase().contains(title.toUpperCase()))
				found.add(b);
		}
		
		return found;
        }

	public static List<Book> getAllBooks() {
			List<Book> found = new ArrayList<Book>(books.values());
			return found;
        }
}


