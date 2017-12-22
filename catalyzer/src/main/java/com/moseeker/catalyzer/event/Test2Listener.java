package com.moseeker.catalyzer.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 13/12/2017.
 */
@Component
public class Test2Listener implements SmartApplicationListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == TestEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return sourceType == String.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("Test2Listener：" + event.getSource());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
