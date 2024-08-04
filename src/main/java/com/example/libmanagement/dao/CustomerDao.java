package com.example.libmanagement.dao;

import com.example.libmanagement.model.BorrowedBookDetails;
import com.example.libmanagement.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CustomerDao {

    @PersistenceContext
    EntityManager entityManager;

    public List<Customer> listCustomers() {
        TypedQuery<Customer> typedQuery = entityManager.createQuery("FROM Customer",Customer.class);
        List<Customer> customerList = typedQuery.getResultList();
        return customerList;
    }

    public List<BorrowedBookDetails> listRecords(Integer id) {
        TypedQuery<BorrowedBookDetails> typedQuery = entityManager.createQuery("FROM BorrowedBookDetails WHERE customer.customerId = :customerId",BorrowedBookDetails.class);
        typedQuery.setParameter("customerId",id);
        List<BorrowedBookDetails> borrowedBookDetailsList = typedQuery.getResultList();
        return borrowedBookDetailsList;
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
            System.out.println("Exception caught!" + ex.getMessage());
            return 0;
        }
    }

    public Customer listCustomerById(Integer id) {
        TypedQuery<Customer> typedQuery = entityManager.createQuery("FROM Customer WHERE customerId = :customerId",Customer.class);
        typedQuery.setParameter("customerId",id);
        Customer customer = typedQuery.getSingleResult();
        return customer;
    }

    public int deleteCustomer(Integer customerId){
        try {
            Customer customer = listCustomerById(customerId);
            entityManager.remove(customer);
            return 1;
        }catch (Exception ex){
            System.out.println("Exception caught!");
            return 0;
        }
    }

    public List<Customer> listCustomers(String query, Map<String, Object> queryParams) {
        TypedQuery<Customer> typedQuery = entityManager.createQuery(query,Customer.class);
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }
        List<Customer> customerList = typedQuery.getResultList();
        return customerList;
    }

    public int saveRecord(BorrowedBookDetails borrowedBookDetails){
        try {
            entityManager.persist(borrowedBookDetails);
            return 1;
        }catch (Exception ex){
            System.out.println("Exception caught!");
            return 0;
        }
    }

}
