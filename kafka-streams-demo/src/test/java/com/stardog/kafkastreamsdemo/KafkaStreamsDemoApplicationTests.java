package com.stardog.kafkastreamsdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaStreamsDemoApplicationTests {

    @Test
    void contextLoads() {

        /*  StardogTransformationAndInjection sti = new StardogTransformationAndInjection();

        String jsonStringInsert = "{\n" +
                "\"id\": \"ID_001\",\n" +
                "\"dateRep\": \"14/12/2020\",\n" +
                "\"day\": 14,\n" +
                "\"month\": 12,\n" +
                "\"year\": 2020,\n" +
                "\"cases\": 746,\n" +
                "\"deaths\": 6,\n" +
                "\"countriesAndTerritories\": \"Dominicana\",\n" +
                "\"geoId\": \"AF\",\n" +
                "\"countryterritoryCode\": \"DOM\",\n" +
                "\"popData2019\": 38041757,\n" +
                "\"continentExp\": \"Asia\",\n" +
                "\"Cumulative_number_for_14_days_of_cases_per_100K\": 9.01377925\n" +
                "}";
        JsonNode record = getJson(jsonStringInsert);
        sti.insert(record);*/
    }

   /* private static JsonNode getJson(String jsonstring){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(jsonstring);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonNode;

    }*/

}
