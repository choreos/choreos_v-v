package br.usp.ime.choreos.vvrs;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.usp.ime.choreos.vvrs.model.Book;
import br.usp.ime.choreos.vvrs.model.MockBooks;

@Path("/bookstore")
public class BookStoreRS {

	private static List<Book> books = MockBooks.bookList; 
	
	@POST
	@Path("/addBook")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static String addBook(@FormParam("title") String title, @FormParam("author") String author ) {
	       
		Book newBook = new Book(title, author);
		books.add(newBook);
		int id = books.size()-1;
		return  Integer.toString(id);
        }

	@GET
	@Path("/book/{id}")
	public static String getBookById(@PathParam("id")  int id) {
		
		Book book = BookStore.getBookById(id);
		
		if (book != null)
			return book.toString();
		else
			return "Not found!";
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
	
	@GET
	@Path("searchBook")
	public static String  getBookByTitle(@QueryParam("title") String title) {

		return BookStore.getBookByTitle(title).toString();
        }

	@GET
	@Path("/books")
	public static String getAllBooks() {

		return BookStore.getAllBooks().toString();
        }
}


