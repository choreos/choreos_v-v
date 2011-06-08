package br.usp.ime.choreos.vvrs;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.usp.ime.choreos.vvrs.model.Book;

@Path("/bookstore")
public class BookStoreRS {

	@PUT
	@Path("/updateBook/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public static Book updateBook(@PathParam("id") int id, Book book) {
		System.out.println(book);
		return BookStore.updateBook(id, book);
        }

	@GET
	@Path("/book/{id}")
	@Produces("application/json")
	public static Book getBookById(@PathParam("id")  int id) throws Exception {
		
		Book book = BookStore.getBookById(id);
		
		return book;
        }
	
	@POST
	@Path("/addBook")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json")
	public static String addBook(@FormParam("title") String title, @FormParam("author") String author) {
		Book newBook = new Book(title, author);
		Integer bookID = BookStore.addBook(newBook);
		return bookID.toString();
        }

	@DELETE
	@Path("/book/{id}")
	@Produces("application/json")
	public static Book removeBook(@PathParam("id") int id) {
		return BookStore.removeBook(id);
        }
	
	@GET
	@Path("searchBook")
	@Produces("application/json")
	public static List<Book>  getBookByTitle(@QueryParam("title") String title) {
		return BookStore.getBookByTitle(title);
        }

	@GET
	@Path("/books")
	@Produces("application/json")
	public static List<Book> getAllBooks() {
		return BookStore.getAllBooks();
        }
}


