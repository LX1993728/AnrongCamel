package com.anrong.camel.custom;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by liudh on 2018/4/2.
 */

@Service
public class MyModule extends DefaultComponent {

    @Override
    protected Endpoint createEndpoint(String s, String s1, Map<String, Object> map) throws Exception {
        System.out.println(s);
        System.out.println(s1);
        System.out.println("自定义节点");

        return null;
    }
}
