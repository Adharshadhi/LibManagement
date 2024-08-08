package com.adhi.libmanagement.service;

import com.adhi.libmanagement.model.Book;
import com.adhi.libmanagement.dao.BookDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService{

    private static final String CLASS_NAME = "com.adhi.libmanagement.service.BookService";

    private BookDao bookDao;

    @Autowired
    public BookService(BookDao bookDao){
        this.bookDao = bookDao;
    }

    public List<Book> listBooks() {
        try{
            return bookDao.listBooks();
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listBooks method -> " +  ex.getMessage());
            return null;
        }

    }

    public List<Book> listBooks(String search, Integer filterVal) {
        StringBuilder query = new StringBuilder();
        query.append(" FROM Book ");
        Map<String, Object> queryParams = new LinkedHashMap<>();
        try{
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
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listBooks(String search, Integer filterVal) method -> " +  ex.getMessage());
            return null;
        }
    }

    @Transactional
    public int saveBook(Book book, String updateAction){
        try{
            return bookDao.saveBook(book, updateAction);
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in saveBook method -> " +  ex.getMessage());
            return 0;
        }
    }

    public Book listBookById(Integer id){
        try{
            return bookDao.listBookById(id);
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listBookById method -> " +  ex.getMessage());
            return null;
        }

    }

    @Transactional
    public int deleteBook(String selectedBooks){
        try{
            String[] bookIdArr = selectedBooks.split(",");
            int status = 0;
            for(String bookId : bookIdArr){
                status = bookDao.deleteBook(Integer.valueOf(bookId));
            }
            return status;
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in deleteBook method -> " +  ex.getMessage());
            return 0;
        }
    }

    public List<Book> listAvailableBooks() {
        try{
            return bookDao.listAvailableBooks();
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in listAvailableBooks method -> " +  ex.getMessage());
            return null;
        }
    }

    public long getDashboardTotalNum(){
        try{
            return bookDao.getDashboardTotalNum();
        }catch (Exception ex){
            System.out.println("Exception occurred at-> " + CLASS_NAME + " in getDashboardTotalNum method -> " +  ex.getMessage());
            return 0;
        }
    }

}
