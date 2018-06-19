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

    static {
        liepinUserBindUrl = EmailNotification.getConfig("liepin_user_bind_url");
    }

}
