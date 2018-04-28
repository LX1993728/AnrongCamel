package com.anrong.camel.custom;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by liudh on 2018/4/2.
 */
//@Component
public class MyRoute extends RouteBuilder {

    public MyRoute() {

    }

    @Override
    public void configure() throws Exception {
//        from("timer:foo").to("log:bar");

//        from("timer://timer1?period=1000")
//                .process(new Processor() {
//                    public void process(Exchange msg) {
//                        System.out.println(msg.getMessage());
//                        System.out.println(Thread.currentThread().getName());
//                    }
//                });
//        this.getContext().addComponent("suit", new MyModule());

        from("jetty:http://0.0.0.0:9080/camel")
                .log("${body.type}")
//                .to("mock:result")
                .choice()
                .when().simple("")
                    .to("direct:a")
                .otherwise()
                    .to("direct:b")
                .setBody(body().append("{\"success\": true}"));


//        from("direct-vm:start").log("准备调用").to("http://192.168.1.140:10009/app/queryAll").log("成功").end();
        from("jetty:http://0.0.0.0:8282/dynamicRouterCamel?httpClient.timeout=1000")
                .log("日志")
                .bean(XxxServer.class, "b")
                .multicast()
                .to("direct:a", "direct:b", "direct:c")
                .process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
//                String str = exchange.getIn().getBody().toString();
//                System.out.println(str);
                Object obj = exchange.getIn().getBody();

//                exchange.getOut().setBody();
                System.out.println(Thread.currentThread().getName());
                System.out.println(exchange.getIn().getBody());
//                while (true) {
//                    Thread.sleep(5000);
//                }
            }

        }).process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                System.out.println(Thread.currentThread().getName());
                System.out.println(exchange.getIn().getBody());
//                Thread.sleep(5000);
//                int a = 1/0;
            }

        }).log("结束日志").setBody(body().append("TEST RESPONSE"));

        from("direct:a").log("我是a: " + Thread.currentThread().getName());
        from("direct:b").log("我是b: " + Thread.currentThread().getName());
        from("direct:c").log("我是c: " + Thread.currentThread().getName());

//        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
//                .contextPath("/camel/rest").port(8083);
//
//        rest("/say")
//                .get("/hello").to("direct:hello")
//                .get("/bye").consumes("application/json").to("direct:bye")
//                .post("/bye").to("mock:update");
//
//        from("direct:hello")
//                .transform().constant("Hello World");
//        from("direct:bye")
//                .transform().constant("Bye World");

//        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
//                .contextPath("/camel/rest").port(8080);
//
//        rest("/user")
//                .get("/{id}").produces("application/json").to("direct:getUser")
//                .post().consumes("application/json").type(User.class).to("direct:createUser")
//                .delete("/{id}").to("direct:deleteUser");
//
//        from("direct:getUser")
//                .log("GET /user/${header.id} request received!")
//                .setBody(simple("${header.id}"))
//                .to("bean:usersBean?method=getUser");
//
//        from("direct:createUser")
//                .log("POST /user/ request received!")
//                .to("bean:usersBean?method=createUser");
//
//        from("direct:deleteUser")
//                .log("DELETE /user/${header.id} request received!")
//                .setBody(simple("${header.id}"))
//                .to("bean:usersBean?method=deleteUser");

//        from("direct:start").transform(body().append(" World!")).to("mock:result");



    }


}
