package com.adhi.libmanagement.controller;

import com.adhi.libmanagement.model.Book;
import com.adhi.libmanagement.service.BookService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    private static final String CLASS_NAME = "com.adhi.libmanagement.controller.BookController";

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String getHome(){
        return "index";
    }

    @GetMapping("/error")
    public String errorPage(){
        return "error";
    }

    @GetMapping("/books")
    public String listBooks(@RequestParam(defaultValue = "") String search,
                            @RequestParam(defaultValue = "0") Integer filterVal,
                            Model model){
        List<Book> bookList;
        try{
            if(search.trim().equals("")){
                bookList = bookService.listBooks();
            }else{
                bookList = bookService.listBooks(search,filterVal);
            }
            model.addAttribute("bookList",bookList);
            return "listbook";
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listBooks method -> " +  ex.getMessage());
            return "error";
        }

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
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in saveBook method -> " +  ex.getMessage());
            return "error";
        }
        return null;
    }

    @GetMapping("/books/{id}")
    public String editBook(@PathVariable Integer id, Model model){
        try{
            Book book = bookService.listBookById(id);
            model.addAttribute("book",book);
            return "addeditbook";
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in editBook method -> " +  ex.getMessage());
            return "error";
        }
    }

    @PostMapping("/books/delete")
    public String deleteBook(@RequestParam(name = "selectedBooks") String selectedBooks,
                             HttpServletResponse response){
        try{
            int status = bookService.deleteBook(selectedBooks);
            response.sendRedirect("/books");
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in deleteBook method -> " +  ex.getMessage());
            return "error";
        }
        return null;
    }

    @GetMapping("/dashboards")
    public String listDashboards(Model model){
        try{
            model.addAttribute("dashboardNum",bookService.getDashboardTotalNum());
            return "listdashboard";
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listDashboards method -> " +  ex.getMessage());
            return "error";
        }
    }

}
