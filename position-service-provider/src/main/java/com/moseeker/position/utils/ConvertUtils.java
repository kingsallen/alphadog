package com.moseeker.position.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.thrift.gen.common.struct.Response;

public class ConvertUtils {
	public static List<? extends TBase > convert(Class<? extends TBase> dist,Response response){
		List<? extends TBase> list=new ArrayList<TBase>();
		int status=response.getStatus();
		if(status==0){
			String data1=response.getData();
			if(!StringUtils.isEmpty(data1)&&!"[]".equals(data1)){
				 list=JSONArray.parseArray(data1, dist);
				 return list;
			}
		}
		return null;
	}
	public static Map<String,Object> convertToJSON(List<? extends TBase> list,String name){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", name);
		map.put("value", list);
		return map;
	}

}
