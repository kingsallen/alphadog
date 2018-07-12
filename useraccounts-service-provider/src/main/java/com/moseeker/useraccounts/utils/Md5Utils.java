package com.moseeker.useraccounts.utils;

import com.moseeker.common.util.MD5Util;
import com.moseeker.useraccounts.service.thirdpartyaccount.EmailNotification;
import org.apache.http.message.BasicNameValuePair;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * md5加密工具类
 *
 * @author cjm
 * @date 2018-05-28 13:33
 **/
public class Md5Utils {

    private static final String SECRECT_KEY;

    static{
        SECRECT_KEY = EmailNotification.getConfig("liepin_position_api_secretkey");
    }

    /**
     * 用于猎聘对接时的md5加密字符串
     * @param
     * @author  cjm
     * @date  2018/5/28
     * @return
     */
    public static String getMD5SortKey(List<String> list, Map<String , String> map) throws Exception {
        StringBuilder paras = new StringBuilder();
        Collections.sort(list);
        for(String paraName : list) {
            paras.append(map.get(paraName));
        }
        paras.append(SECRECT_KEY);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(paras.toString().getBytes("UTF-8"));
        byte b[] = md.digest();
        StringBuffer buf = new StringBuffer("");
        int i;
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }


    public static List<String> mapToList(Map<String , String> map) {
        List<String> list = new ArrayList<String>();
        Set<String> keys = map.keySet();
        for(String key : keys) {
            list.add(key);
        }
        return list;
    }

}
