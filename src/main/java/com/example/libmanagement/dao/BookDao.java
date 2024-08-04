package com.example.libmanagement.dao;

import com.example.libmanagement.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BookDao {

    @PersistenceContext
    EntityManager entityManager;

    public List<Book> listBooks() {
        TypedQuery<Book> typedQuery = entityManager.createQuery("FROM Book",Book.class);
        List<Book> bookList = typedQuery.getResultList();
        return bookList;
    }

    public List<Book> listBooks(String query, Map<String, Object> queryParams) {
        TypedQuery<Book> typedQuery = entityManager.createQuery(query,Book.class);
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }
        List<Book> bookList = typedQuery.getResultList();
        return bookList;
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
            System.out.println("Exception caught!");
            return 0;
        }
    }

    public Book listBookById(Integer id) {
        TypedQuery<Book> typedQuery = entityManager.createQuery("FROM Book WHERE bookId = :bookId",Book.class);
        typedQuery.setParameter("bookId",id);
        Book book = typedQuery.getSingleResult();
        return book;
    }

    public int deleteBook(Integer bookId){
        try {
            Book book = listBookById(bookId);
            entityManager.remove(book);
            return 1;
        }catch (Exception ex){
            System.out.println("Exception caught!");
            return 0;
        }
    }

    public List<Book> listAvailableBooks() {
        TypedQuery<Book> typedQuery = entityManager.createQuery("FROM Book WHERE availableQuantity > 0",Book.class);
        List<Book> bookList = typedQuery.getResultList();
        return bookList;
    }

}
