package com.springrediskafka.spring_redis_kafka;

import lombok.Data;

import java.io.Serializable;

@Data
public class Book implements Serializable
{
    private int id;
    private String author;
    private String title;

    public Book(int id, String author ,String title) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

