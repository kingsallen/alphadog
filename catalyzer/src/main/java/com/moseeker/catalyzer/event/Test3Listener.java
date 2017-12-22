package com.moseeker.catalyzer.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

/**
 * Created by jack on 14/12/2017.
 */
public class Test3Listener implements SmartApplicationListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType.isAssignableFrom(Test2Event.class);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return sourceType.isAssignableFrom(String.class);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("Test3Listener：" + event.getSource());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
