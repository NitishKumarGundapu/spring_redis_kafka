package com.springrediskafka.spring_redis_kafka;

import lombok.Data;
import lombok.Generated;

import java.io.Serializable;

@Data
@Generated
public class IssuedBook implements Serializable {

    private int id;
    private String author;
    private String title;
    private String name;
    private String date;

    public IssuedBook() {
    }

    public IssuedBook(int id, String title, String name, String date,String author) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.author = author;
        this.date = date;
    }

    @Override
    public String toString() {
        return "IssuedBook{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
