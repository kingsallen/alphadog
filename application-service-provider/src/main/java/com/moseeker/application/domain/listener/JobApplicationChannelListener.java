package com.moseeker.application.domain.listener;

import com.moseeker.application.domain.event.JobApplicationChannelEvent;
import com.moseeker.common.util.ConfigPropertiesUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author: huangwenjian
 * @desc: application channel 关联listener
 * @since: 2019-11-12 10:52
 */
@Component
public class JobApplicationChannelListener implements ApplicationListener<JobApplicationChannelEvent> {

    private static String ALPHACLOUD_COMPANY_SAVE_APPLICATION_CHANNEL_URL;

    static {
        try {
            ConfigPropertiesUtil configPropertiesUtil = ConfigPropertiesUtil.getInstance();
            configPropertiesUtil.loadResource("common.properties");
            ALPHACLOUD_COMPANY_SAVE_APPLICATION_CHANNEL_URL = configPropertiesUtil.get("alphacloud.company.channel.save_application.url",String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onApplicationEvent(JobApplicationChannelEvent event) {
        Integer applicationId = event.getApplicationId();
        Integer companyId = event.getCompanyId();
        Integer origin = event.getOrigin();
    }

    private void sendSaveApplicationChannelRequest(Integer applicationId, Integer companyId, Integer origin) {

    }
}
