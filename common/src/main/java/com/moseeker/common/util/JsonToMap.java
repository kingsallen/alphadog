package com.moseeker.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 利用FastJson的 SON.parse 方法解析json字符串，并将解析后的JSONArray转成ArrayList<Object>,
 * JSONObject转成HashMap<String,Object>
 * <p>Company: MoSeeker</P>  
 * <p>date: Aug 22, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class JsonToMap {

	/**
	 * 将字符串转成HashMap<String,Object>
	 * @param jsonStr json格式的字符串
	 * @return HashMap<String,Ojbect>
	 */
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!jsonStr.startsWith("{") || !jsonStr.endsWith("}")) {
			return map;
		}
		Object object = JSON.parse(jsonStr);
		if (object instanceof JSONObject) {
			map = JSONObject2HashMap((JSONObject)object);
		} else if (object instanceof JSONObject) {
			map.put(object.toString(), object);
		} else {
			map.put(object.toString(), object);
		}
		return map;
	}
	
	/**
	 * JSONObject转成HashMap<String, Object>
	 * @param jsonObject JSONObject类型数据
	 * @return HashMap<String, Object>数据
	 */
	private static HashMap<String, Object> JSONObject2HashMap(JSONObject jsonObject) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(jsonObject != null) {
			for(Entry<String, Object> entry : jsonObject.entrySet()) {
				if(entry.getValue() instanceof JSONObject) {
					map.put(entry.getKey(),JSONObject2HashMap((JSONObject)entry.getValue()));
				} else if(entry.getValue() instanceof JSONArray) {
					map.put(entry.getKey(), JSONArray2ArrayList((JSONArray)entry.getValue()));
				} else {
					map.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return map;
	}
	
	/**
	 * JSONArray转ArrayList<Object>
	 * @param jsonArray JSONArray格式的数据
	 * @return ArrayList<Object>格式的数据
	 */
	private static ArrayList<Object> JSONArray2ArrayList(JSONArray jsonArray) {
		ArrayList<Object> list = new ArrayList<>();
		if(jsonArray != null) {
			for(Object obj : jsonArray) {
				if(obj instanceof JSONObject) {
					list.add(JSONObject2HashMap((JSONObject)obj));
				} else if(obj instanceof JSONArray) {
					list.add(JSONArray2ArrayList((JSONArray)obj));
				} else {
					list.add(obj);
				}
			}
		}
		return list;
	}
}
