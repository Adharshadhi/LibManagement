package com.example.libmanagement.controller;

import com.example.libmanagement.model.Book;
import com.example.libmanagement.service.BookService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String getHome(){
        return "index";
    }

    @GetMapping("/books")
    public String listBooks(Model model){
        List<Book> bookList = bookService.listBooks();
        model.addAttribute("bookList",bookList);
        return "listbook";
    }

    @GetMapping("/books/add")
    public String addBook(Model model){
        model.addAttribute("book",new Book());
        return "addeditbook";
    }

    @PostMapping("/books")
    public String saveBook(@ModelAttribute(name = "book") Book book, HttpServletResponse response){
        try{
            int status = bookService.saveBook(book);
            System.out.println(status);
            response.sendRedirect("/books");
        }catch (Exception ex){
            System.out.println("Exception caught");
        }
        return null;
    }

}
