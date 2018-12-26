package com.moseeker.position.utils;

import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.constants.position.liepin.LiepinPositionOperateConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.*;

/**
 * md5加密工具类
 *
 * @author cjm
 * @date 2018-05-28 13:33
 **/
public class Md5Utils {

    private static Logger logger = LoggerFactory.getLogger(Md5Utils.class);

    /**
     * 用于猎聘对接时的md5加密字符串
     * @param
     * @author  cjm
     * @date  2018/5/28
     * @return
     */
    public static String getMD5SortKey(List<String> list, Map<String , Object> map, String secret) {
        StringBuilder paras = new StringBuilder();
        Collections.sort(list);
        for(String paraName : list) {
            String paramValue = String.valueOf(map.get(paraName));
            if(StringUtils.isNullOrEmpty(paramValue) || "null".equals(paramValue)){
                continue;
            }
            paras.append(paramValue);
        }
        paras.append(secret);
        logger.info("==============paras:{}=============", paras.toString());
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

    /**
     * md5加密字符串，key与value用=号连接
     * @param
     * @author  cjm
     * @date  2018/5/28
     * @return
     */
    public static String getMD5SortKeyWithEqual(String secret, List<String> list, Map<String , Object> map) throws Exception {
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
