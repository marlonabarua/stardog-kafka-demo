package com.stardog.kafkastreamsdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaStreamsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsDemoApplication.class, args);

    }

}
