package com.example.libmanagement.controller;

import com.example.libmanagement.model.Book;
import com.example.libmanagement.service.BookService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String listBooks(@RequestParam(defaultValue = "") String search, Model model){
        List<Book> bookList;
        if(search.trim().equals("")){
            bookList = bookService.listBooks();
        }else{
            bookList = bookService.listBooks(search);
        }
        model.addAttribute("bookList",bookList);
        return "listbook";
    }

    @GetMapping("/books/add")
    public String addBook(Model model){
        model.addAttribute("book",new Book());
        return "addeditbook";
    }

    @PostMapping("/books")
    public String saveBook(@ModelAttribute(name = "book") Book book,
                           @RequestParam(defaultValue = "no", name = "updateAction") String updateAction,
                           HttpServletResponse response){
        try{
            int status = bookService.saveBook(book, updateAction);
            response.sendRedirect("/books");
        }catch (Exception ex){
            System.out.println("Exception caught");
        }
        return null;
    }

    @GetMapping("/books/{id}")
    public String editBook(@PathVariable Integer id, Model model){
        Book book = bookService.listBookById(id);
        model.addAttribute("book",book);
        return "addeditbook";
    }

}
