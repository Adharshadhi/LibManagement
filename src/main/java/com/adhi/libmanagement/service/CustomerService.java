package com.adhi.libmanagement.service;

import com.adhi.libmanagement.dao.CustomerDao;
import com.adhi.libmanagement.dao.BookDao;
import com.adhi.libmanagement.model.Book;
import com.adhi.libmanagement.model.BorrowedBookDetails;
import com.adhi.libmanagement.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private static final String CLASS_NAME = "com.adhi.libmanagement.service.CustomerService";

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
        try{
            return customerDao.listRecords(id);
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listRecords method -> " +  ex.getMessage());
            return null;
        }
    }

    @Transactional
    public int saveCustomer(Customer customer, String updateAction){
        try{
            return customerDao.saveCustomer(customer, updateAction);
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in saveCustomer method -> " +  ex.getMessage());
            return 0;
        }

    }

    public Customer listCustomerById(Integer id){
        try{
            return customerDao.listCustomerById(id);
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listCustomerById method -> " +  ex.getMessage());
            return null;
        }
    }

    @Transactional
    public int deleteCustomer(String selectedCustomers){
        try{
            String[] customerIdArr = selectedCustomers.split(",");
            int status = 0;
            for(String customerId : customerIdArr){
                status = customerDao.deleteCustomer(Integer.valueOf(customerId));
            }
            return status;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in deleteCustomer method -> " +  ex.getMessage());
            return 0;
        }
    }

    public List<Customer> listCustomers(String search, Integer filterVal) {
        StringBuilder query = new StringBuilder();
        query.append(" FROM Customer ");
        Map<String, Object> queryParams = new LinkedHashMap<>();
        try {
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
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listCustomers method -> " +  ex.getMessage());
            return null;
        }
    }

    @Transactional
    public int saveRecord(BorrowedBookDetails borrowedBookDetails, Book book, String updateAction) {
        try{
            customerDao.saveRecord(borrowedBookDetails, updateAction);
            book.setAvailableQuantity(book.getAvailableQuantity()-1);
            bookDao.saveBook(book,"yes");
            return 1;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in saveRecord method -> " +  ex.getMessage());
            return 0;
        }
    }

    public BorrowedBookDetails listRecordById(Integer id) {
        try{
            return customerDao.listRecordById(id);
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listRecordById method -> " +  ex.getMessage());
            return null;
        }
    }

    @Transactional
    public int deleteRecord(String selectedRecords){
        try{
            String[] recordIdArr = selectedRecords.split(",");
            int status = 0;
            for(String recordId : recordIdArr){
                status = customerDao.deleteRecord(Integer.valueOf(recordId));
            }
            return status;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in deleteRecord method -> " +  ex.getMessage());
            return 0;
        }
    }

}
