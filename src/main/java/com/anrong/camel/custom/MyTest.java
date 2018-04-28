package com.anrong.camel.custom;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.jolokia.client.J4pClient;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.junit.Test;

import java.util.Map;

/**
 * Created by liudh on 2018/4/2.
 */
public class MyTest {
    @Test
    public void test() throws Exception {
        CamelContext camelContext = new DefaultCamelContext();

//        camelContext.addComponent("suit", new MyModule());

        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start").transform(body().append(" World!")).to("mock:result");
            }
        });

        camelContext.start();
        Object object = new Object();
        synchronized (object) {
            object.wait();
        }

        System.out.println("退出进程");
    }

    @Test
    public void test2() throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        ProducerTemplate template = camelContext.createProducerTemplate();

        template.sendBody("direct:start", "This is a test message");
    }

    @Test
    public void test3() throws Exception {
        J4pClient j4pClient = new J4pClient("http://localhost:8080/jolokia");
        J4pReadRequest req = new J4pReadRequest("java.lang:type=Memory",
                "HeapMemoryUsage");
        J4pReadResponse resp = j4pClient.execute(req);
        Map<String,Long> vals = resp.getValue();
        long used = vals.get("used");
        long max = vals.get("max");
        int usage = (int) (used * 100 / max);
        System.out.println("Memory usage: used: " + used +
                " / max: " + max + " = " + usage + "%");
    }

}
