package com.stardog.kafkastreamsdemo;

import com.fasterxml.jackson.databind.JsonNode;
import com.stardog.ext.spring.SnarlTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StardogTransformationAndInjection {

    private SnarlTemplate snarlTemplate;

    public StardogTransformationAndInjection (){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        snarlTemplate =  (SnarlTemplate) context.getBean("template");
    }

    public void insert(JsonNode record){

        String sub = "covid:"+record.get("countriesAndTerritories").asText();

        snarlTemplate.add(sub, "rdf:type", "Country");
        snarlTemplate.add(sub, "rdf:has-countryterritoryCode", record.get("countryterritoryCode").asText());
        snarlTemplate.add(sub, "rdf:has-geoId", record.get("geoId").asText());
        snarlTemplate.add(sub, "rdf:has-popData2019", record.get("popData2019").asText());
        snarlTemplate.add(sub, "rdf:has-dateRep", record.get("dateRep").asText());
        snarlTemplate.add(sub, "rdf:is-in", record.get("continentExp").asText());
        snarlTemplate.add(sub, "rdf:has-dateRep", record.get("dateRep").asText());
        snarlTemplate.add(sub, "rdf:has-cases", record.get("cases").asText());
        String daysCases = record.get("Cumulative_number_for_14_days_of_cases_per_100K") == null?"0.0":record.get("Cumulative_number_for_14_days_of_cases_per_100K").asText();
        snarlTemplate.add(sub, "rdf:has-Cumulative_number_for_14_days_of_cases_per_100K", daysCases);
        //See records
        //System.out.println(record);
    }


}
