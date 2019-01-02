package com.moseeker.useraccounts.constant;

import com.moseeker.useraccounts.service.thirdpartyaccount.EmailNotification;

/**
 * @author cjm
 * @date 2018-11-28 14:35
 **/
public class Job58Constant {

    public static final String SECRECT_KEY;
    public static final String APP_KEY;

    static {
        SECRECT_KEY = EmailNotification.getConfig("58job_api_secret");
        APP_KEY = EmailNotification.getConfig("58job_api_app_key");
    }
}
