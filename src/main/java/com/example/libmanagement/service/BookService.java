package com.example.libmanagement.service;

import com.example.libmanagement.dao.BookDao;
import com.example.libmanagement.model.Book;
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

}
