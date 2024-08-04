package com.example.libmanagement.controller;

import com.example.libmanagement.model.Book;
import com.example.libmanagement.model.BorrowedBookDetails;
import com.example.libmanagement.model.Customer;
import com.example.libmanagement.service.BookService;
import com.example.libmanagement.service.CustomerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {

    private CustomerService customerService;
    private BookService bookService;

    @Autowired
    public CustomerController(CustomerService customerService, BookService bookService){
        this.customerService = customerService;
        this.bookService = bookService;
    }

    @GetMapping("/customers")
    public String listCustomers(@RequestParam(defaultValue = "") String search,
                                @RequestParam(defaultValue = "0") Integer filterVal,
                                Model model){
        List<Customer> customerList;
        if(search.trim().equals("")){
            customerList = customerService.listCustomers();
        }else{
            customerList = customerService.listCustomers(search,filterVal);
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
        List<BorrowedBookDetails> borrowedBookDetailsList = customerService.listRecords(id);
        model.addAttribute("borrowedBookDetailsList",borrowedBookDetailsList);
        return "listborrowerdetails";
    }

    @PostMapping("/customers")
    public String saveCustomer(@ModelAttribute(name = "customer") Customer customer,
                               @RequestParam(defaultValue = "no", name = "updateAction") String updateAction,
                               HttpServletResponse response){
        try{
            int status = customerService.saveCustomer(customer, updateAction);
            response.sendRedirect("/customers");
        }catch (Exception ex){
            System.out.println("Exception caught!" + ex.getMessage());
        }
        return null;
    }

    @GetMapping("/customers/{id}")
    public String editCustomer(@PathVariable Integer id, Model model){
        Customer customer = customerService.listCustomerById(id);
        model.addAttribute("customer",customer);
        return "addeditcustomer";
    }

    @PostMapping("/customers/delete")
    public String deleteCustomer(@RequestParam(name = "selectedCustomers") String selectedCustomers,
                                 HttpServletResponse response){
        try{
            int status = customerService.deleteCustomer(selectedCustomers);
            response.sendRedirect("/customers");
        }catch (Exception ex){
            System.out.println("Exception caught");
        }
        return null;
    }

    @GetMapping("/records/add")
    public String addRecord(Model model){
        BorrowedBookDetails borrowedBookDetails = new BorrowedBookDetails();
        List<Customer> customerList = customerService.listCustomers();
        List<Book> bookList = bookService.listAvailableBooks();
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
            Customer customer = customerService.listCustomerById(selectedCustomer);
            Book book = bookService.listBookById(selectedBook);
            borrowedBookDetails.setCustomer(customer);
            borrowedBookDetails.setBook(book);
            int status = customerService.saveRecord(borrowedBookDetails, book);
            response.sendRedirect("/customers");
        }catch (Exception ex){
            System.out.println("Exception caught!" + ex.getMessage());
        }
        return null;
    }

}
