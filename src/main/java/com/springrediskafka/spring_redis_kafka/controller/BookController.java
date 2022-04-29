package com.springrediskafka.spring_redis_kafka.controller;

import com.springrediskafka.spring_redis_kafka.Book;
import com.springrediskafka.spring_redis_kafka.IssuedBook;
import com.springrediskafka.spring_redis_kafka.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class BookController
{
    @Autowired
    private BookProducer bookProducer = new BookProducer();
    @Autowired
    public static final String TOPIC = "ke";
    @Autowired
    private BookService bookService;

    @PostMapping("/book/c")
    public ResponseEntity<String> saveBook(@RequestBody Book book) {
        boolean result = bookService.saveBook(book);
        if (result)
            return ResponseEntity.ok("Book Added Successfully");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/book/r")
    public ResponseEntity<List<Book>> fetchAllBook()
    {
        List<Book> books;
        books = bookService.fetchAllBook();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/book/r/{id}")
    public ResponseEntity<String> fetchBookById(@PathVariable("id") Long id) {
        Book book;
        book = bookService.fetchBookById(id);
        if(book == null)
            return ResponseEntity.ok("book does not exist");
        return ResponseEntity.ok(book.toString());
    }

    public boolean existsId(@PathVariable("id") Long id) {
        Book book;
        book = bookService.fetchBookById(id);
        try
        {
            if (book.toString() != "")
                return true;
            else
                return false;
        }
        catch (Exception e){
            return false;}
    }

    @GetMapping("book/e")
    public ResponseEntity<String> existsId2(@Param("author") String author , @Param("title") String title) {
        List<Book> books;
        books = bookService.fetchAllBook();
        for (int i=0; i<books.size() ; i++){
            if (Objects.equals(books.get(i).getAuthor(), author) && Objects.equals(books.get(i).getTitle(), title)) {
                return ResponseEntity.ok("Book Exists! Details of the book are \n\n" + books.get(i).toString());
            }
        }
        return ResponseEntity.ok("Book does not exist");
    }

    @GetMapping("/book/u/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") Long id, @RequestBody Book book) {
        if (id.toString().equals(Integer.toString(book.getId())))
        {
            boolean result = bookService.updateBook(id,book);
            if(result)
                return ResponseEntity.ok("book Updated Successfully!!");
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else
        {
            System.out.println(id.toString());
            System.out.println(Integer.toString(book.getId()));
            return ResponseEntity.ok("IDs did not match");
        }
    }

    @GetMapping("/book/d/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long id) {
        boolean result = bookService.deleteBook(id);
        if(result)
            return ResponseEntity.ok("Book deleted Successfully!!");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /*------------------------------------------------------------------------------------------------------------*/

    @GetMapping("/issue/r/{id}")
    public ResponseEntity<String> fetchBookById2(@PathVariable("id") Long id) {
        IssuedBook book;
        book = bookService.fetchBookById2(id);
        if(book == null)
            return ResponseEntity.ok("book does not exist");
        return ResponseEntity.ok(book.toString());
    }

    private boolean existsId2(Long id)
    {
        IssuedBook book = new IssuedBook();
        book = bookService.fetchBookById2(id);
        try
        {
            if (book == null)
                return false;
            else
                return true;
        }
        catch (Exception e){
            return false;}
    }

    @GetMapping("/issue/r")
    public ResponseEntity<List<IssuedBook>> fetchAllIssueBook()
    {
        List<IssuedBook> books;
        books = bookService.fetchAllBook2();
        return ResponseEntity.ok(books);
    }

    @PostMapping("/issue")
    public ResponseEntity<String> issueBook(@RequestBody IssuedBook book) {
        if(existsId((long) book.getId()))
        {
            String k = bookProducer.post(book);
            return ResponseEntity.ok(k);
        }
        else
            return ResponseEntity.ok("book does not exist");
    }

    @KafkaListener(topics = "ke3",groupId = "group_json",
            containerFactory = "bookKafkaListenerContainerFactory")
    public ResponseEntity<String> consumeJson(IssuedBook book)
    {
        if (existsId((long) book.getId())) {
            ResponseEntity<String> l = deleteBook((long) book.getId());
            boolean result = bookService.saveBook2(book);
            if (result) {
                return ResponseEntity.ok("book issued");
            }
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else
            return ResponseEntity.ok("book does not exists");
    }

    @GetMapping("/unissue/{id}")
    public ResponseEntity<String> unissueId(@PathVariable("id") Long id)
    {
        if(existsId2(id)) {
            String k = bookProducer.post2(id.toString());
            return ResponseEntity.ok(k);
        }
        else
            return ResponseEntity.ok("Book does not exists");
    }
    @KafkaListener(topics = "ke2",groupId = "group_id",
            containerFactory = "kafkaListenerContainerFactory")
    public ResponseEntity<String> consumeString(String id)
    {
        if(existsId2(Long.valueOf(id))){
            IssuedBook book1 = bookService.fetchBookById2(Long.valueOf(id));
            Book book = new Book(book1.getId(),book1.getAuthor() ,book1.getTitle());
            ResponseEntity<String> l = deleteBook2((long) book.getId());
            ResponseEntity<String> l1 = saveBook(book);
            return ResponseEntity.ok("Book Unissued");
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    public ResponseEntity<String> deleteBook2(@PathVariable("id") Long id) {
        boolean result = bookService.deleteBook2(id);
        if(result)
            return ResponseEntity.ok("Book deleted Successfully!!");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
