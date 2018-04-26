package controllers;

import models.Book;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.books.*;
import play.data.FormFactory;
import javax.inject.Inject;


import java.util.Set;

public class BooksController extends Controller{

    @Inject
    FormFactory formFactory;

    // METHOD FOR SHOWING JOBS TO USER
    public Result index(){
        Set<Book> books = Book.allBooks();
        return ok(index.render(books)) ;
    }

    public Result create(){
        Form<Book> bookform = formFactory.form(Book.class);
        return ok (create.render(bookform));

    }

    public Result save(){
        Form<Book> bookform = formFactory.form(Book.class).bindFromRequest();
        Book book = bookform.get();
        Book.add(book);

        return redirect(routes.BooksController.index());


    }

    // will need the ID of which job is being edited
    public Result edit(Integer id){

        Book book = Book.findById(id);
        if (book==null){
            return notFound("Book not found");
        }
        Form<Book> bookform = formFactory.form(Book.class).fill(book);
        return ok(edit.render(bookform));
    }

    public Result update(){
        Form<Book> bookform = formFactory.form(Book.class).bindFromRequest();
        Book book = bookform.get();
        Book oldBook = Book.findById(book.id);
        if (oldBook == null){
            return notFound("Book not found");
        }
        oldBook.title = book.title;
        oldBook.author = book.author;
        oldBook.price = book.price;

        return redirect(routes.BooksController.index());
    }

    public Result destroy(Integer id){
        Book book = Book.findById(id);
        if (book == null){
            return notFound("Book not found");
        }

        Book.remove(book);

        return redirect(routes.BooksController.index());
    }

    public Result show(Integer id){
        Book book = Book.findById(id);
        if(book==null){
            return notFound("book not found");
        }
        return ok(show.render(book));

    }
}
