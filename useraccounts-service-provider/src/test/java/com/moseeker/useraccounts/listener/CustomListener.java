package com.moseeker.useraccounts.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 28/12/2017.
 */
@Component
public class CustomListener implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("CustomerListener onApplicationEvent : " + event.getBusiness().getName());
    }
}
