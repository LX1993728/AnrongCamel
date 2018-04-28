package com.anrong.camel.custom;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * Created by liudh on 2018/4/2.
 */
public class MyEndpoint extends DefaultEndpoint {
    @Override
    public Producer createProducer() throws Exception {
        return null;
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
