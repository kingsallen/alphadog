package com.moseeker.application.domain.listener;

import com.moseeker.application.domain.event.JobApplicationChannelEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author: huangwenjian
 * @desc: application channel 关联listener
 * @since: 2019-11-12 10:52
 */
@Component
public class JobApplicationChannelListener implements ApplicationListener<JobApplicationChannelEvent> {

    @Override
    public void onApplicationEvent(JobApplicationChannelEvent event) {
        Integer applicationId = event.getApplicationId();
        Integer companyId = event.getCompanyId();
        Integer origin = event.getOrigin();

    }

    private void sendSaveApplicationChannelRequest(Integer applicationId, Integer companyId, Integer origin) {
    }
}
