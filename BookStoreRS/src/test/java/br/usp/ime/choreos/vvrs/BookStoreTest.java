package br.usp.ime.choreos.vvrs;

import static junit.framework.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.usp.ime.choreos.vvrs.model.Book;
import br.usp.ime.choreos.vvrs.model.MockBooks;


public class BookStoreTest {
	
	@Before
	public void setUp() {
		
		BookStore.clear();
	}

	@Test
	public void shouldAddABook() {
		
		Book book = new Book("O sr. Dos Anéis", "Tolkien");
		int id = BookStore.addBook(book);
		
		Book retrieved = BookStore.getBookById(id);
		
		assertEquals(book, retrieved);
	}
	
	@Test
	public void shouldUpdateABook() {
		
		Book book = new Book("O sr. Dos Anéis", "Tolkien");
		int id = BookStore.addBook(book);
		
		book.setAuthor("J. R. R. Tolkien");
		BookStore.updateBook(id, book);
		
		String retrieved = BookStore.getBookById(id).getAuthor();
		
		assertEquals(book.getAuthor(), retrieved);
	}
	
	@Test
	public void shouldRemoveABook() {
		
		Book book = new Book("O sr. Dos Anéis", "Tolkien");
		int id = BookStore.addBook(book);
		
		BookStore.removeBook(id);
		
		Book retrieved = BookStore.getBookById(id);
				
		assertNull(retrieved);
	}
	
	@Test
	public void shouldGetABookByTitle() {
		
		Book book = new Book("O sr. Dos Anéis - As duas torres", "Tolkien");
		BookStore.addBook(book);
		
		List<Book> retrieved = BookStore.getBookByTitle("O sr. dos Anéis");
		
		assertTrue(retrieved.contains(book));
	}

	@Test
	public void shouldGetAllBookByTitle() {

		List<Book> books = MockBooks.bookList;
		List<Book> retrieved = BookStore.getAllBooks();
		
		assertEquals(books, retrieved);
	}

}
