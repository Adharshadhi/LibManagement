package com.adhi.libmanagement.dao;

import com.adhi.libmanagement.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BookDao {

    private static final String CLASS_NAME = "com.adhi.libmanagement.dao.BookDao";
    @PersistenceContext
    EntityManager entityManager;

    public List<Book> listBooks() {
        try{
            TypedQuery<Book> typedQuery = entityManager.createQuery("FROM Book",Book.class);
            List<Book> bookList = typedQuery.getResultList();
            return bookList;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listBooks method -> " +  ex.getMessage());
        }
        return null;
    }

    public List<Book> listBooks(String query, Map<String, Object> queryParams) {
        try{
            TypedQuery<Book> typedQuery = entityManager.createQuery(query,Book.class);
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                typedQuery.setParameter(entry.getKey(), entry.getValue());
            }
            List<Book> bookList = typedQuery.getResultList();
            return bookList;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listBooks method(String query, Map<String, Object> queryParams) -> " +  ex.getMessage());
        }
        return null;
    }

    public int saveBook(Book book, String updateAction){
        try {
            if(updateAction.equalsIgnoreCase("yes")){
                entityManager.merge(book);
            }else{
                entityManager.persist(book);
            }
            return 1;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in saveBook method -> " +  ex.getMessage());
            return 0;
        }
    }

    public Book listBookById(Integer id) {
        try{
            TypedQuery<Book> typedQuery = entityManager.createQuery("FROM Book WHERE bookId = :bookId",Book.class);
            typedQuery.setParameter("bookId",id);
            Book book = typedQuery.getSingleResult();
            return book;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listBookById method -> " +  ex.getMessage());
        }
        return null;
    }

    public int deleteBook(Integer bookId){
        try {
            Book book = listBookById(bookId);
            entityManager.remove(book);
            return 1;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in deleteBook method -> " +  ex.getMessage());
            return 0;
        }
    }

    public List<Book> listAvailableBooks() {
        try{
            TypedQuery<Book> typedQuery = entityManager.createQuery("FROM Book WHERE availableQuantity > 0",Book.class);
            List<Book> bookList = typedQuery.getResultList();
            return bookList;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listAvailableBooks method -> " +  ex.getMessage());
        }
        return null;
    }

    public long getDashboardTotalNum() {
        try{
            String queryToExecute = "SELECT count(*) AS total FROM dashboards";
            Query query = entityManager.createNativeQuery(queryToExecute);
            long result = (long) query.getSingleResult();
            return result;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in getDashboardTotalNum method -> " +  ex.getMessage());
        }
        return 0;
    }

}
