package com.anrong.camel.samples.restsSample;

import org.apache.camel.spi.RestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RestDSLConfig {

    @Bean
    public RestConfiguration restConfiguration(){
       RestConfiguration restConfiguration =  new RestConfiguration();
        restConfiguration.setComponent("jetty");
        restConfiguration.setHost("localhost");
        restConfiguration.setPort(8044);
        restConfiguration.setEnableCORS(true);
        restConfiguration.setBindingMode(RestConfiguration.RestBindingMode.json);
        restConfiguration.setApiHost("localhost");
        restConfiguration.setApiContextPath("/api-doc");

        Map<String,Object> dataFormatPropMap = new HashMap<>();
        dataFormatPropMap.put("prettyPrint","true");
        restConfiguration.setDataFormatProperties(dataFormatPropMap);

        Map<String,Object> apiProMap = new HashMap<>();
        apiProMap.put("api.title", "User API");
        apiProMap.put("api.version", "1.0.0");
        restConfiguration.setApiProperties(apiProMap);
       return restConfiguration;
    }
}
