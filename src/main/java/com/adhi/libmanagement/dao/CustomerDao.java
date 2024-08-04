package com.adhi.libmanagement.dao;

import com.adhi.libmanagement.model.BorrowedBookDetails;
import com.adhi.libmanagement.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CustomerDao {

    private static final String CLASS_NAME = "com.adhi.libmanagement.dao.CustomerDao";

    @PersistenceContext
    EntityManager entityManager;

    public List<Customer> listCustomers() {
        try{
            TypedQuery<Customer> typedQuery = entityManager.createQuery("FROM Customer",Customer.class);
            List<Customer> customerList = typedQuery.getResultList();
            return customerList;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listCustomers method -> " +  ex.getMessage());
            return null;
        }
    }

    public List<BorrowedBookDetails> listRecords(Integer id) {
        try{
            TypedQuery<BorrowedBookDetails> typedQuery = entityManager.createQuery("FROM BorrowedBookDetails WHERE customer.customerId = :customerId",BorrowedBookDetails.class);
            typedQuery.setParameter("customerId",id);
            List<BorrowedBookDetails> borrowedBookDetailsList = typedQuery.getResultList();
            return borrowedBookDetailsList;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listRecords method -> " +  ex.getMessage());
            return null;
        }
    }

    public int saveCustomer(Customer customer, String updateAction){
        try {
            if(updateAction.equalsIgnoreCase("yes")){
                entityManager.merge(customer);
            }else{
                entityManager.persist(customer);
            }
            return 1;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in saveCustomer method -> " +  ex.getMessage());
            return 0;
        }
    }

    public Customer listCustomerById(Integer id) {
        try{
            TypedQuery<Customer> typedQuery = entityManager.createQuery("FROM Customer WHERE customerId = :customerId",Customer.class);
            typedQuery.setParameter("customerId",id);
            Customer customer = typedQuery.getSingleResult();
            return customer;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listCustomerById method -> " +  ex.getMessage());
            return null;
        }

    }

    public int deleteCustomer(Integer customerId){
        try {
            Customer customer = listCustomerById(customerId);
            entityManager.remove(customer);
            return 1;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in deleteCustomer method -> " +  ex.getMessage());
            return 0;
        }
    }

    public List<Customer> listCustomers(String query, Map<String, Object> queryParams) {
        try{
            TypedQuery<Customer> typedQuery = entityManager.createQuery(query,Customer.class);
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                typedQuery.setParameter(entry.getKey(), entry.getValue());
            }
            List<Customer> customerList = typedQuery.getResultList();
            return customerList;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listCustomers method -> " +  ex.getMessage());
            return null;
        }
    }

    public int saveRecord(BorrowedBookDetails borrowedBookDetails, String updateAction){
        try {
            if(updateAction.equalsIgnoreCase("yes")){
                entityManager.merge(borrowedBookDetails);
            }else{
                entityManager.persist(borrowedBookDetails);
            }
            return 1;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in saveRecord method -> " +  ex.getMessage());
            return 0;
        }
    }

    public BorrowedBookDetails listRecordById(Integer id) {
        try{
            TypedQuery<BorrowedBookDetails> typedQuery = entityManager.createQuery("FROM BorrowedBookDetails WHERE borrowId = :borrowId",BorrowedBookDetails.class);
            typedQuery.setParameter("borrowId",id);
            BorrowedBookDetails borrowedBookDetails = typedQuery.getSingleResult();
            return borrowedBookDetails;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listRecordById method -> " +  ex.getMessage());
            return null;
        }
    }

    public int deleteRecord(Integer recordId){
        try {
            BorrowedBookDetails borrowedBookDetails = listRecordById(recordId);
            entityManager.remove(borrowedBookDetails);
            return 1;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in deleteRecord method -> " +  ex.getMessage());
            return 0;
        }
    }

}
