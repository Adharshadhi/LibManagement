package com.example.libmanagement.controller;

import com.example.libmanagement.model.Book;
import com.example.libmanagement.model.BorrowedBookDetails;
import com.example.libmanagement.model.Customer;
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
    public String listBooks(@RequestParam(defaultValue = "") String search,
                            @RequestParam(defaultValue = "0") Integer filterVal,
                            Model model){
        List<Book> bookList;
        if(search.trim().equals("")){
            bookList = bookService.listBooks();
        }else{
            bookList = bookService.listBooks(search,filterVal);
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

    @PostMapping("/books/delete")
    public String deleteBook(@RequestParam(name = "selectedBooks") String selectedBooks,
                             HttpServletResponse response){
        try{
            int status = bookService.deleteBook(selectedBooks);
            response.sendRedirect("/books");
        }catch (Exception ex){
            System.out.println("Exception caught");
        }
        return null;
    }

    @GetMapping("/customers")
    public String listCustomers(@RequestParam(defaultValue = "") String search,
                            @RequestParam(defaultValue = "0") Integer filterVal,
                            Model model){
        List<Customer> customerList;
        if(search.trim().equals("")){
            customerList = bookService.listCustomers();
        }else{
            customerList = bookService.listCustomers(search,filterVal);
        }
        model.addAttribute("customerList",customerList);
        return "listcustomer";
    }

    @GetMapping("/customers/add")
    public String addCustomer(Model model){
        model.addAttribute("customer",new Customer());
        return "addeditcustomer";
    }

    @GetMapping("/records/{id}")
    public String listRecords(@PathVariable Integer id, Model model){
        List<BorrowedBookDetails> borrowedBookDetailsList = bookService.listRecords(id);
        model.addAttribute("borrowedBookDetailsList",borrowedBookDetailsList);
        return "listborrowerdetails";
    }

    @PostMapping("/customers")
    public String saveCustomer(@ModelAttribute(name = "customer") Customer customer,
                           @RequestParam(defaultValue = "no", name = "updateAction") String updateAction,
                           HttpServletResponse response){
        try{
            int status = bookService.saveCustomer(customer, updateAction);
            response.sendRedirect("/customers");
        }catch (Exception ex){
            System.out.println("Exception caught!" + ex.getMessage());
        }
        return null;
    }

    @GetMapping("/customers/{id}")
    public String editCustomer(@PathVariable Integer id, Model model){
        Customer customer = bookService.listCustomerById(id);
        model.addAttribute("customer",customer);
        return "addeditcustomer";
    }

    @PostMapping("/customers/delete")
    public String deleteCustomer(@RequestParam(name = "selectedCustomers") String selectedCustomers,
                             HttpServletResponse response){
        try{
            int status = bookService.deleteCustomer(selectedCustomers);
            response.sendRedirect("/customers");
        }catch (Exception ex){
            System.out.println("Exception caught");
        }
        return null;
    }

    @GetMapping("/records/add")
    public String addRecord(Model model){
        BorrowedBookDetails borrowedBookDetails = new BorrowedBookDetails();
        List<Customer> customerList = bookService.listCustomers();
        List<Book> bookList = bookService.listBooks();
        model.addAttribute("borrowedBookDetails",borrowedBookDetails);
        model.addAttribute("customerList",customerList);
        model.addAttribute("bookList",bookList);
        return "addeditrecord";
    }

    @PostMapping("/records")
    public String saveRecord(@ModelAttribute BorrowedBookDetails borrowedBookDetails,
                             @RequestParam Integer selectedCustomer,
                             @RequestParam Integer selectedBook,
                             HttpServletResponse response
                             ){

        try{
            Customer customer = bookService.listCustomerById(selectedCustomer);
            Book book = bookService.listBookById(selectedBook);
            borrowedBookDetails.setCustomer(customer);
            borrowedBookDetails.setBook(book);
            int status = bookService.saveRecord(borrowedBookDetails);
            response.sendRedirect("/customers");
        }catch (Exception ex){
            System.out.println("Exception caught!" + ex.getMessage());
        }
        return null;
    }

}
