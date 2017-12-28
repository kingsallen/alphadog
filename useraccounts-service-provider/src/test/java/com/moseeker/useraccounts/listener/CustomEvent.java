package com.moseeker.useraccounts.listener;

import org.springframework.context.ApplicationEvent;

/**
 * Created by jack on 28/12/2017.
 */
public class CustomEvent extends ApplicationEvent {

    private Business business;

    public CustomEvent(Business business) {
        super(business);
    }

    public Business getBusiness() {
        return (Business)getSource();
    }
}
