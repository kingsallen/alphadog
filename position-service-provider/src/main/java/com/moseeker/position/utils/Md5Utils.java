package com.moseeker.position.utils;

import com.moseeker.common.util.MD5Util;

import java.util.*;

/**
 * md5加密工具类
 *
 * @author cjm
 * @date 2018-05-28 13:33
 **/
public class Md5Utils {

    private static String SECRET_KEY = "EA36911A20A58DB1C41D0631094DE514";

    /**
     * 用于猎聘对接时的md5加密字符串
     * @param
     * @author  cjm
     * @date  2018/5/28
     * @return
     */
    public static String getMD5SortKey(List<String> list, Map<String , Object> map) {
        StringBuilder paras = new StringBuilder();
        Collections.sort(list);
        for(String paraName : list) {
            if(null == map.get(paraName)){
                continue;
            }
            paras.append(map.get(paraName));
        }
        paras.append(SECRET_KEY);
        return MD5Util.md5(paras.toString()).toLowerCase();
    }


    public static List<String> mapToList(Map<String , Object> map) {
        List<String> list = new ArrayList<String>();
        Set<String> keys = map.keySet();
        for(String key : keys) {
            list.add(key);
        }
        return list;
    }



}
