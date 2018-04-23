package com.moseeker.entity.biz;

/**
 * Created by moseeker on 2018/4/23.
 */
public class EmailContextReplaceUtils {

    public static String replaceUtil(String context, String companyName, String positionName, String profileName,
            String hrName, String wechatName){

        context = context.replace("#公司简称#",companyName);
        context = context.replace("#职位名称#",positionName);
        context = context.replace("#HR姓名#",hrName);
        context = context.replace("#求职者姓名#",profileName);
        context = context.replace("#公众号名称#",wechatName);
        return  context;
    }
}
