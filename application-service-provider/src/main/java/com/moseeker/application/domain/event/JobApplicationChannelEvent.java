package com.moseeker.application.domain.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author: huangwenjian
 * @desc:
 * @since: 2019-11-12 10:55
 */
public class JobApplicationChannelEvent extends ApplicationEvent {

    private String origin;

    public JobApplicationChannelEvent(Object source, String origin) {
        super(source);
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
