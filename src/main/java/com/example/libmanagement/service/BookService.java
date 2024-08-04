package com.example.libmanagement.service;

import com.example.libmanagement.dao.BookDao;
import com.example.libmanagement.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService{

    private BookDao bookDao;

    @Autowired
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

    public List<Book> listAvailableBooks() {
        return bookDao.listAvailableBooks();
    }

}
