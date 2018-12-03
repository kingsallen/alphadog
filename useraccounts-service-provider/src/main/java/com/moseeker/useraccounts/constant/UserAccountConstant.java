package com.moseeker.useraccounts.constant;

import com.moseeker.useraccounts.service.thirdpartyaccount.EmailNotification;

/**
 * 用户绑定时所用常量
 *
 * @author cjm
 * @date 2018-05-28 14:06
 **/

public class UserAccountConstant {

    public static String liepinUserBindUrl;
    public static String job58UserBindUrl;
    public static String job58UserRefreshUrl;
    public static String job58UserGetUrl;

    static {
        liepinUserBindUrl = EmailNotification.getConfig("liepin_user_bind_url");
        job58UserBindUrl = EmailNotification.getConfig("58job_user_bind_url");
        job58UserGetUrl = EmailNotification.getConfig("58job_userinfo_get_url");
        job58UserRefreshUrl = EmailNotification.getConfig("58job_user_refresh_url");
    }

}
