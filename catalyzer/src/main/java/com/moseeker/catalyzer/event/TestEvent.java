package com.moseeker.catalyzer.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by jack on 13/12/2017.
 */
public class TestEvent extends ApplicationEvent {

    public TestEvent(final Object object) {
        super(object);
    }
}
