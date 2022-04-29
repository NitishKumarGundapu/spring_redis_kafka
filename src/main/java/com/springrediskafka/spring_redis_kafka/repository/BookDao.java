package com.springrediskafka.spring_redis_kafka.repository;

import com.springrediskafka.spring_redis_kafka.Book;
import com.springrediskafka.spring_redis_kafka.IssuedBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDao
{
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String KEY1 = "LIBRARY";
    private static final String KEY2 = "ISSUED";

    public boolean saveBook(Book book) {
        try
        {
            redisTemplate.opsForHash().put(KEY1 , Integer.toString(book.getId()) , book);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveBook2(IssuedBook book) {
        try
        {
            redisTemplate.opsForHash().put(KEY2 , Integer.toString(book.getId()) , book);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> fetchAllBook() {
        List books;
        books = redisTemplate.opsForHash().values(KEY1);
        return books;
    }

    public Book fetchBookById(Long id) {
        Book book;
        book = (Book) redisTemplate.opsForHash().get(KEY1,id.toString());
        return book;
    }

    public boolean deleteBook(Long id) {
        try {
            redisTemplate.opsForHash().delete(KEY1,id.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(Long id, Book book) {
        try {
            redisTemplate.opsForHash().delete(KEY1,id.toString());
            redisTemplate.opsForHash().put(KEY1 , Integer.toString(book.getId()) , book);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<IssuedBook> fetchAllBook2()
    {
        List<IssuedBook> books;
        books = redisTemplate.opsForHash().values(KEY2);
        return books;
    }

    public IssuedBook fetchBookById2(Long id) {
        IssuedBook book;
        book = (IssuedBook) redisTemplate.opsForHash().get(KEY2,id.toString());
        return book;
    }

    public boolean deleteBook2(Long id) {
        try {
            redisTemplate.opsForHash().delete(KEY2,id.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
