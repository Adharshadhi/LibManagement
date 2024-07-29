package com.example.libmanagement.controller;

import com.example.libmanagement.model.Book;
import com.example.libmanagement.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
