package com.example.libmanagement.dao;

import com.example.libmanagement.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDao {

    @PersistenceContext
    EntityManager entityManager;

    public List<Book> listBooks() {
        TypedQuery<Book> typedQuery = entityManager.createQuery("FROM Book",Book.class);
        List<Book> bookList = typedQuery.getResultList();
        return bookList;
    }

}
