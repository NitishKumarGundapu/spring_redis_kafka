package com.springrediskafka.spring_redis_kafka.controller;

import com.springrediskafka.spring_redis_kafka.IssuedBook;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kafka")
public class BookProducer
{
    @Autowired
    KafkaTemplate<String, IssuedBook> kafkaTemplate;
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate2;
    public static final String TOPIC = "ke3";
    public static final String TOPIC2 = "ke2";

    public String post(IssuedBook book)
    {
        kafkaTemplate.send(TOPIC,book);
        return "Book Issued Successfully";
    }

    public String post2(String id)
    {
        kafkaTemplate2.send(TOPIC2,id);
        return "Book Unissued Sucessfully";
    }
}
