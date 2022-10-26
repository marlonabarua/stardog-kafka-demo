package com.stardog.kafkastreamsdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class StardogKafkaConsummer {

    @Autowired
    private StardogTransformationAndInjection stardogTransformationAndInjection;

    @KafkaListener(topics = "${source.topic}")
    public void kafkaListener(String record){
        JsonNode jsonRecord = getJson(record);
        stardogTransformationAndInjection.insert(jsonRecord);
    }

    private JsonNode getJson(String jsonstring){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(jsonstring);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonNode;

    }
}
