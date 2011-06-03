package br.usp.ime.choreos.vvrs;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.usp.ime.choreos.vvrs.model.Book;

@Path("/bookstore")
public class BookStoreRS {

	@PUT
	public static String updateBook(int id, Book book) {
		return BookStore.updateBook(id, book);		
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

	@POST
	@Path("/addBook")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public static String addBook(@FormParam("title") String title, @FormParam("author") String author) {
		Book book = new Book(title, author);
		Integer bookID = BookStore.addBook(book);
		return "" + bookID;

	}

	@DELETE
	@Path("/book/{id}")
	public static String removeBook(@PathParam("id") int id) {

		return BookStore.removeBook(id);
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


