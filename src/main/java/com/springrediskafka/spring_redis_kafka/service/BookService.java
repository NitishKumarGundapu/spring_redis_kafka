package com.springrediskafka.spring_redis_kafka.service;

import com.springrediskafka.spring_redis_kafka.repository.BookDao;
import com.springrediskafka.spring_redis_kafka.Book;
import com.springrediskafka.spring_redis_kafka.IssuedBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService
{
    @Autowired
    private BookDao bookDao;

    public boolean saveBook(Book book) {
        return bookDao.saveBook(book);
    }

    public List<Book> fetchAllBook() {
        return bookDao.fetchAllBook();
    }

    public Book fetchBookById(Long id) {
        return bookDao.fetchBookById(id);
    }

    public boolean deleteBook(Long id) {
        return bookDao.deleteBook(id);
    }

    public boolean updateBook(Long id, Book book) {
        return bookDao.updateBook(id,book);
    }

    public boolean saveBook2(IssuedBook book) {
        return bookDao.saveBook2(book);
    }

    public List<IssuedBook> fetchAllBook2() {
        return bookDao.fetchAllBook2();
    }

    public IssuedBook fetchBookById2(Long id) {
        return bookDao.fetchBookById2(id);
    }

    public boolean deleteBook2(Long id) {
        return bookDao.deleteBook2(id);
    }
}
