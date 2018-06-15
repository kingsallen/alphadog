package com.moseeker.useraccounts.schedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时刷新猎聘hr账号token
 *
 * @author cjm
 * @date 2018-06-15 9:10
 **/

@Component
@EnableScheduling
public class RefreshLiepinTokenSchedule {



    @Scheduled(cron="0 */1 * * * ?")
    public void refreshLiepinToken() {


    }
}
