package com.anrong.camel.samples.messageChannelSample;

import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

@Configuration
public class MessageChannelConfig {

    @Bean
    public JmsComponent jms(){
        ConnectionFactory activeMQJMSConnectionFactory =  new ActiveMQJMSConnectionFactory("tcp://localhost:61616");
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(activeMQJMSConnectionFactory);
        return jmsComponent;
    }
}
