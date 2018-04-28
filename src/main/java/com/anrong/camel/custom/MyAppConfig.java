package com.anrong.camel.custom;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * Created by liudh on 2018/4/2.
 */
public class MyAppConfig {
    @Autowired
    CamelContext camelContext;

//    @Bean
//    MyModule myModule() {
//        return new MyModule();
//    }
}
