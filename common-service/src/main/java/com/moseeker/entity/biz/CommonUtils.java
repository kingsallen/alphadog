package com.moseeker.entity.biz;

import com.moseeker.common.util.StringUtils;

/**
 * Created by moseeker on 2018/4/23.
 */
public class CommonUtils {

    public static String replaceUtil(String context, String companyName, String positionName, String profileName,
            String hrName, String wechatName){

        context = context.replace("#公司简称#",companyName);
        context = context.replace("#职位名称#",positionName);
        context = context.replace("#HR姓名#",hrName);
        context = context.replace("#求职者姓名#",profileName);
        context = context.replace("#公众号名称#",wechatName);
        return  context;
    }

    public static  String appendUrl(String url, String CDN) {

        String logo = "";
        if (StringUtils.isNotNullOrEmpty(url)) {
            if (url.startsWith("http")) {
                logo = url;
            } else {
                logo = CDN + url;
            }
            if (!logo.startsWith("https") && logo.startsWith("http")) {
                logo = logo.replace("http", "https");
            }
        }
        return logo;
    }
}
