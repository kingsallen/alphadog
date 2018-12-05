package com.moseeker.useraccounts.utils;

import java.security.MessageDigest;
import java.util.*;

/**
 * md5加密工具类
 *
 * @author cjm
 * @date 2018-05-28 13:33
 **/
public class Md5Utils {

    /**
     * 用于猎聘对接时的md5加密字符串
     * @param
     * @author  cjm
     * @date  2018/5/28
     * @return
     */
    public static String getMD5SortKey(String secret, List<String> list, Map<String , String> map) throws Exception {
        StringBuilder paras = new StringBuilder();
        Collections.sort(list);
        for(String paraName : list) {
            paras.append(map.get(paraName));
        }
        paras.append(secret);
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

    /**
     * md5加密字符串，key与value用=号连接
     * @param
     * @author  cjm
     * @date  2018/5/28
     * @return
     */
    public static String getMD5SortKeyWithEqual(String secret, List<String> list, Map<String , String> map) throws Exception {
        StringBuilder paras = new StringBuilder();
        Collections.sort(list);
        for(String paraName : list) {
            paras.append(paraName).append("=").append(map.get(paraName));
        }
        paras.append(secret);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(paras.toString().getBytes("UTF-8"));
        byte[] byteArr = md.digest();
        StringBuilder sb = new StringBuilder("");
        int i;
        for (byte aByte : byteArr) {
            i = aByte;
            if (i < 0){
                i += 256;
            }
            if (i < 16){
                sb.append("0");
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }

}
