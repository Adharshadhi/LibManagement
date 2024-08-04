package com.adhi.libmanagement.controller;

import com.adhi.libmanagement.model.Book;
import com.adhi.libmanagement.service.CustomerService;
import com.adhi.libmanagement.model.BorrowedBookDetails;
import com.adhi.libmanagement.model.Customer;
import com.adhi.libmanagement.service.BookService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {

    private static final String CLASS_NAME = "com.adhi.libmanagement.controller.CustomerController";
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
        try {
            if(search.trim().equals("")){
                customerList = customerService.listCustomers();
            }else{
                customerList = customerService.listCustomers(search,filterVal);
            }
            model.addAttribute("customerList",customerList);
            return "listcustomer";
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listCustomers method -> " +  ex.getMessage());
            return "error";
        }
    }

    @GetMapping("/customers/add")
    public String addCustomer(Model model){
        model.addAttribute("customer",new Customer());
        return "addeditcustomer";
    }

    @GetMapping("/records/{id}")
    public String listRecords(@PathVariable Integer id, Model model){
        try{
            List<BorrowedBookDetails> borrowedBookDetailsList = customerService.listRecords(id);
            model.addAttribute("borrowedBookDetailsList",borrowedBookDetailsList);
            return "listborrowerdetails";
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listRecords method -> " +  ex.getMessage());
            return "error";
        }
    }

    @PostMapping("/customers")
    public String saveCustomer(@ModelAttribute(name = "customer") Customer customer,
                               @RequestParam(defaultValue = "no", name = "updateAction") String updateAction,
                               HttpServletResponse response){
        try{
            int status = customerService.saveCustomer(customer, updateAction);
            response.sendRedirect("/customers");
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in saveCustomer method -> " +  ex.getMessage());
            return "error";
        }
        return null;
    }

    @GetMapping("/customers/{id}")
    public String editCustomer(@PathVariable Integer id, Model model){
        try{
            Customer customer = customerService.listCustomerById(id);
            model.addAttribute("customer",customer);
            return "addeditcustomer";
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in editCustomer method -> " +  ex.getMessage());
            return "error";
        }
    }

    @PostMapping("/customers/delete")
    public String deleteCustomer(@RequestParam(name = "selectedCustomers") String selectedCustomers,
                                 HttpServletResponse response){
        try{
            int status = customerService.deleteCustomer(selectedCustomers);
            response.sendRedirect("/customers");
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in deleteCustomer method -> " +  ex.getMessage());
            return "error";
        }
        return null;
    }

    @GetMapping("/records/add")
    public String addRecord(Model model){
        try{
            BorrowedBookDetails borrowedBookDetails = new BorrowedBookDetails();
            List<Customer> customerList = customerService.listCustomers();
            List<Book> bookList = bookService.listAvailableBooks();
            model.addAttribute("borrowedBookDetails",borrowedBookDetails);
            model.addAttribute("customerList",customerList);
            model.addAttribute("bookList",bookList);
            return "addeditrecord";
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in addRecord method -> " +  ex.getMessage());
            return "error";
        }
    }

    @PostMapping("/records")
    public String saveRecord(@ModelAttribute BorrowedBookDetails borrowedBookDetails,
                             @RequestParam Integer selectedCustomer,
                             @RequestParam Integer selectedBook,
                             @RequestParam(defaultValue = "no", name = "updateAction") String updateAction,
                             HttpServletResponse response
    ){

        try{
            Customer customer = customerService.listCustomerById(selectedCustomer);
            Book book = bookService.listBookById(selectedBook);
            borrowedBookDetails.setCustomer(customer);
            borrowedBookDetails.setBook(book);
            int status = customerService.saveRecord(borrowedBookDetails, book, updateAction);
            response.sendRedirect("/customers");
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in saveRecord method -> " +  ex.getMessage());
            return "error";
        }
        return null;
    }

    @GetMapping("/records/edit/{id}")
    public String editRecord(@PathVariable Integer id, Model model){
        try{
            BorrowedBookDetails borrowedBookDetails = customerService.listRecordById(id);
            List<Customer> customerList = customerService.listCustomers();
            List<Book> bookList = bookService.listBooks();
            model.addAttribute("borrowedBookDetails",borrowedBookDetails);
            model.addAttribute("customerList",customerList);
            model.addAttribute("bookList",bookList);
            return "addeditrecord";
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in editRecord method -> " +  ex.getMessage());
            return "error";
        }
    }

    @PostMapping("/records/delete")
    public String deleteRecord(@RequestParam(name = "selectedRecords") String selectedRecords,
                                 HttpServletResponse response){
        try{
            int status = customerService.deleteRecord(selectedRecords);
            response.sendRedirect("/customers");
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in deleteRecord method -> " +  ex.getMessage());
            return "error";
        }
        return null;
    }

}
