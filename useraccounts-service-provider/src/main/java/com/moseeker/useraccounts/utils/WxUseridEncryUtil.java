package com.moseeker.useraccounts.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 由于微信职位链接转发时需要加密userid，本是在微信端做的，但是由于要发消息模板，所以在这里加上加密工具，暂无解密
 * 微信职位链接用户id加密工具
 */
public class WxUseridEncryUtil {

    public static String encry(long num, int level){
        String index = "xef3mghi68RpOPrsyQSFXNTqz0tuHIaVW5DMUEw2bcdYZ4v7JKLB1AjknCGl9";
        int base = index.length();
        if(level > 0){
            level--;
            if(level > 0){
                num += (long)Math.pow(base, level);
            }
        }
        List<String> out = new ArrayList<>();
        int t = (int) (Math.log(num)/Math.log(base));
        do {
            long bcp = (long) Math.pow(base, t);
            int a = (int) (num / bcp % base);
            out.add(index.substring(a, a + 1));
            num = num - (a * bcp);
            t--;
        } while (t >= 0);
        Collections.reverse(out);
        return String.join("",out);
    }

}
