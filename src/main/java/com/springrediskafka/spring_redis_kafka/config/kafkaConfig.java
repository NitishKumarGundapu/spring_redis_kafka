package com.springrediskafka.spring_redis_kafka.config;

import com.springrediskafka.spring_redis_kafka.IssuedBook;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class kafkaConfig
{
        @Bean
        public ProducerFactory bookProducerFactory()
        {
                Map<String , Object> config = new HashMap<>();

                config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
                config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
                config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
                return new DefaultKafkaProducerFactory(config);
        }

        @Bean
        public KafkaTemplate<String , IssuedBook> bookKafkaTemplate()
        {
                return new KafkaTemplate<>(bookProducerFactory());
        }

        @Bean
        public ProducerFactory producerFactory()
        {
                Map<String , Object> config = new HashMap<>();

                config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
                config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
                config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
                return new DefaultKafkaProducerFactory(config);
        }

        @Bean
        public KafkaTemplate<String , String> kafkaTemplate()
        {
                return new KafkaTemplate<>(producerFactory());
        }

        @Bean
        public ConsumerFactory<String , String> consumerFactory()
        {
                Map<String , Object> config = new HashMap<>();

                config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
                config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
                config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
                config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

                return new DefaultKafkaConsumerFactory<String,String>(config);
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String,String> kafkaListenerContainerFactory()
        {
                ConcurrentKafkaListenerContainerFactory<String,String> factory = new ConcurrentKafkaListenerContainerFactory();
                factory.setConsumerFactory(consumerFactory());
                return factory;
        }

        @Bean
        public ConsumerFactory<String , IssuedBook> bookConsumerFactory()
        {
                Map<String , Object> config = new HashMap<>();

                config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
                config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
                config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
                config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

                return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),
                        new JsonDeserializer<>(IssuedBook.class));
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, IssuedBook> bookKafkaListenerContainerFactory()
        {
                ConcurrentKafkaListenerContainerFactory<String,IssuedBook> factory = new ConcurrentKafkaListenerContainerFactory<>();
                factory.setConsumerFactory(bookConsumerFactory());
                return factory;
        }
}
