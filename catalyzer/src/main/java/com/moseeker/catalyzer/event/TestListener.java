package com.moseeker.catalyzer.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 13/12/2017.
 */
@Component
public class TestListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof TestEvent) {
            System.out.println("TestListener：" + event.getSource());
        }
    }
}
