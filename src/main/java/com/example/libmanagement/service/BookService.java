package com.example.libmanagement.service;

import com.example.libmanagement.dao.BookDao;
import com.example.libmanagement.model.Book;
import com.example.libmanagement.model.BorrowedBookDetails;
import com.example.libmanagement.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService{

    private BookDao bookDao;

    public BookService(BookDao bookDao){
        this.bookDao = bookDao;
    }

    public List<Book> listBooks() {
        return bookDao.listBooks();
    }

    public List<Book> listBooks(String search, Integer filterVal) {
        StringBuilder query = new StringBuilder();
        query.append(" FROM Book ");
        Map<String, Object> queryParams = new LinkedHashMap<>();

        switch (filterVal){
            case 1 -> {
                query.append(" WHERE title Like :title ");
                queryParams.put("title",'%' + search + '%');
            }
            case 2 -> {
                query.append(" WHERE author Like :author ");
                queryParams.put("author",'%' + search + '%');
            }
            case 3 -> {
                query.append(" WHERE publisher Like :publisher ");
                queryParams.put("publisher",'%' + search + '%');
            }
            case 4 -> {
                query.append(" WHERE publicationYear = :publicationYear ");
                queryParams.put("publicationYear",Integer.valueOf(search));
            }
            case 5 -> {
                query.append(" WHERE category Like :category ");
                queryParams.put("category",'%' + search + '%');
            }
            default -> {
                query.append(" WHERE availableQuantity = :availableQuantity ");
                queryParams.put("availableQuantity",Integer.valueOf(search));
            }
        }

        return bookDao.listBooks(query.toString(), queryParams);
    }

    @Transactional
    public int saveBook(Book book, String updateAction){
        return bookDao.saveBook(book, updateAction);
    }

    public Book listBookById(Integer id){
        return bookDao.listBookById(id);
    }

    @Transactional
    public int deleteBook(String selectedBooks){
        String[] bookIdArr = selectedBooks.split(",");
        int status = 0;
        for(String bookId : bookIdArr){
            status = bookDao.deleteBook(Integer.valueOf(bookId));
        }
        return status;
    }

    public List<Customer> listCustomers() {
        return bookDao.listCustomers();
    }

    public List<BorrowedBookDetails> listRecords(Integer id) {
        return bookDao.listRecords(id);
    }

    @Transactional
    public int saveCustomer(Customer customer, String updateAction){
        return bookDao.saveCustomer(customer, updateAction);
    }

    public Customer listCustomerById(Integer id){
        return bookDao.listCustomerById(id);
    }

    @Transactional
    public int deleteCustomer(String selectedCustomers){
        String[] customerIdArr = selectedCustomers.split(",");
        int status = 0;
        for(String customerId : customerIdArr){
            status = bookDao.deleteCustomer(Integer.valueOf(customerId));
        }
        return status;
    }

    public List<Customer> listCustomers(String search, Integer filterVal) {
        StringBuilder query = new StringBuilder();
        query.append(" FROM Customer ");
        Map<String, Object> queryParams = new LinkedHashMap<>();

        switch (filterVal){
            case 1 -> {
                query.append(" WHERE firstName Like :firstName ");
                queryParams.put("firstName",'%' + search + '%');
            }
            case 2 -> {
                query.append(" WHERE lastName Like :lastName ");
                queryParams.put("lastName",'%' + search + '%');
            }
            case 3 -> {
                query.append(" WHERE email Like :email ");
                queryParams.put("email",'%' + search + '%');
            }
            case 4 -> {
                query.append(" WHERE phone Like :phone ");
                queryParams.put("phone",'%' + search + '%');
            }
            default -> {
                query.append(" WHERE address Like :address ");
                queryParams.put("address",'%' + search + '%');
            }
        }

        return bookDao.listCustomers(query.toString(), queryParams);
    }

    @Transactional
    public int saveRecord(BorrowedBookDetails borrowedBookDetails, Book book) {
        try{
            bookDao.saveRecord(borrowedBookDetails);
            book.setAvailableQuantity(book.getAvailableQuantity()-1);
            bookDao.saveBook(book,"yes");
            return 1;
        }catch (Exception ex){
            System.out.println("Exception caught!");
            return 0;
        }
    }

    public List<Book> listAvailableBooks() {
        return bookDao.listAvailableBooks();
    }

}
