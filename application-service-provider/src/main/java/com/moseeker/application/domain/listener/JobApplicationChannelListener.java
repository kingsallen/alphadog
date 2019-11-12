package com.moseeker.application.domain.listener;

import com.moseeker.application.domain.event.JobApplicationChannelEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author: huangwenjian
 * @desc:
 * @since: 2019-11-12 10:52
 */
@Component
public class JobApplicationChannelListener implements ApplicationListener<JobApplicationChannelEvent> {

    @Override
    public void onApplicationEvent(JobApplicationChannelEvent event) {
        
    }
}
