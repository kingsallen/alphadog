package com.moseeker.common.util;

import java.util.List;
import java.util.Random;


public class StringUtils {

	public static boolean isNullOrEmpty(String str) {

		return (str == null || str.trim().equals(""));
	}
	
	public static boolean isNotNullOrEmpty(String str) {

		return (str != null && str.trim().equals(""));
	}
	
	/**
	 * TODO(判断对象是否为空)
	 * @param obj
	 * @return
	*/
	public static boolean isEmptyObject(Object obj){
		if(StringUtils.toString(obj).equals("")){
			return true;
		}else{
			return false;
		} 
	}
	
	/**
	 * TODO(对象转换为String)
	 * @param obj
	 * @return
	*/
	public static String toString(Object obj) {
		if (obj == null || "".equals(obj.toString())
				|| "null".equals(obj.toString())) {
			return "";
		} else {
			String objValue = obj.toString().trim();
			return objValue;
		}
	}
	
	/**
	 * TODO(判断list是否为空)
	 * @param list
	 * @return
	*/
	@SuppressWarnings("rawtypes")
	public static boolean isEmptyList(List list){
			if(list!=null&&list.size()>0){
				return false;
			}else{
				return true;
		} 
	}

	/**
	 * 生成随机字符串， 作为密码等。
	 * @param length 表示生成字符串的长度  
	 * @return
	*/	
	public static String getRandomString(int length) { 
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }   	
    
}
