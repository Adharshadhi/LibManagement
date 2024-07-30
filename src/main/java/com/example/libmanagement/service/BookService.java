package com.example.libmanagement.service;

import com.example.libmanagement.dao.BookDao;
import com.example.libmanagement.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService{

    private BookDao bookDao;

    public BookService(BookDao bookDao){
        this.bookDao = bookDao;
    }

    public List<Book> listBooks() {
        return bookDao.listBooks();
    }

    public List<Book> listBooks(String search) {
        return bookDao.listBooks(search);
    }

    @Transactional
    public int saveBook(Book book, String updateAction){
        return bookDao.saveBook(book, updateAction);
    }

    public Book listBookById(Integer id){
        return bookDao.listBookById(id);
    }

}
