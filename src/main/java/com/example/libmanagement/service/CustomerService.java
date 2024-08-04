package com.example.libmanagement.service;

import com.example.libmanagement.dao.BookDao;
import com.example.libmanagement.dao.CustomerDao;
import com.example.libmanagement.model.Book;
import com.example.libmanagement.model.BorrowedBookDetails;
import com.example.libmanagement.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private CustomerDao customerDao;
    private BookDao bookDao;

    @Autowired
    public CustomerService(CustomerDao customerDao, BookDao bookDao){
        this.customerDao = customerDao;
        this.bookDao = bookDao;
    }

    public List<Customer> listCustomers() {
        return customerDao.listCustomers();
    }

    public List<BorrowedBookDetails> listRecords(Integer id) {
        return customerDao.listRecords(id);
    }

    @Transactional
    public int saveCustomer(Customer customer, String updateAction){
        return customerDao.saveCustomer(customer, updateAction);
    }

    public Customer listCustomerById(Integer id){
        return customerDao.listCustomerById(id);
    }

    @Transactional
    public int deleteCustomer(String selectedCustomers){
        String[] customerIdArr = selectedCustomers.split(",");
        int status = 0;
        for(String customerId : customerIdArr){
            status = customerDao.deleteCustomer(Integer.valueOf(customerId));
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

        return customerDao.listCustomers(query.toString(), queryParams);
    }

    @Transactional
    public int saveRecord(BorrowedBookDetails borrowedBookDetails, Book book) {
        try{
            customerDao.saveRecord(borrowedBookDetails);
            book.setAvailableQuantity(book.getAvailableQuantity()-1);
            bookDao.saveBook(book,"yes");
            return 1;
        }catch (Exception ex){
            System.out.println("Exception caught!");
            return 0;
        }
    }

}
