package com.moseeker.common.util;

import java.util.List;


public class StringUtils {

	public static boolean isNullOrEmpty(String str) {

		return (str == null || str.trim().equals(""));
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
    
}
