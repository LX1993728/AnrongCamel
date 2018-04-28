package com.anrong.camel.controllers;

import org.apache.camel.*;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.RoutesDefinition;
import org.apache.camel.model.rest.RestDefinition;
import org.apache.camel.model.rest.RestsDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 *  @author liuxun
 *
 */

@RestController
public class CamelCommonController {
    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

    @Autowired
    private CamelContext camelContext;

    @GetMapping({"/distribute"})
    public String distribute(String flowId, String data){
        producerTemplate.sendBody("direct:" + flowId, data);
        return "success";

    }

    /**
     *
     * @param type 0：标识读取 重加载classpath下的rests和routes 1：标识读取指定文件夹下的rests和routes
     * @return
     * @throws Exception
     */
    @GetMapping("/reload")
    @ResponseBody
    public Object reload(Integer type) throws Exception{
       if (type == null){
           type = 0; // 默认加载classpath下的默认路径
       }

       List<Route> routes =  camelContext.getRoutes();
        for (Route route: routes) {
           if (camelContext.getRouteStatus(route.getId())
                    .isStarted()){
               camelContext.stopRoute(route.getId());
           }
            camelContext.removeRoute(route.getId());
        }

        List<RestDefinition> restDefinitions = camelContext.getRestDefinitions();
        for(RestDefinition restDefinition:restDefinitions){
            restDefinition.delete();
        }

        Collection<Endpoint> endpoints = camelContext.getEndpoints();
        for(Endpoint endpoint:endpoints){
            camelContext.removeEndpoint(endpoint);
        }

        List<String> componentNames = camelContext.getComponentNames();
        for(String s:componentNames){
            camelContext.removeComponent(s);
        }
        String  classPathRoutesDir = this.getClass().getResource("/camel/").getPath();
        String  classPathRestsDir = this.getClass().getResource("/camel-rest/").getPath();
        File routesDir = new File(type==0? classPathRoutesDir : "C:\\aaa\\routes\\");
        File restDir = new File(type==0? classPathRestsDir:"C:\\aaa\\rests\\");
        //判断该文件或目录是否存在
        if (!routesDir.exists()) {
            routesDir.mkdirs();
        }
        if (!restDir.exists()){
            restDir.mkdirs();
        }
        for (File f: restDir.listFiles()) {
            if (f.getName().endsWith(".xml") && f.isFile()){
                InputStream inputStream = new FileInputStream(f);
                RestsDefinition restsDefinition = camelContext.loadRestsDefinition(inputStream);
                camelContext.addRestDefinitions(restsDefinition.getRests());
                for(RestDefinition xmlDef : restsDefinition.getRests()) {
                    List<RouteDefinition> routeDefs = xmlDef.asRouteDefinition(camelContext);
                    camelContext.addRouteDefinitions(routeDefs);
                }
                inputStream.close();
            }
        }
        for (File file: routesDir.listFiles()) {
            if (file.getName().endsWith(".xml") && file.isFile()){
                InputStream inputStream = new FileInputStream(file);
                RoutesDefinition routesDefinitions = camelContext.loadRoutesDefinition(inputStream);
                camelContext.addRouteDefinitions(routesDefinitions.getRoutes());
                inputStream.close();
            }
        }
        return "RELOAD_CAMEL_RESOURCES_SUCCESS";
    }
}
