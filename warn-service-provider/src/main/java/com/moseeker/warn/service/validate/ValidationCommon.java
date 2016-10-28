package com.moseeker.warn.service.validate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ValidationCommon {
	public static boolean isvalid(JSONObject objs){
		JSONArray jsay=objs.getJSONArray("jsay");
		for(int i=0;i<jsay.size();i++){
			JSONObject obj=jsay.getJSONObject(i);
			String type=obj.getString("type");
			if("time".equals(type)){
				long lasttime=obj.getLongValue("time");
				long nowtime=System.currentTimeMillis();
				long time=nowtime-lasttime;
				long configtime=objs.getLongValue("showtime");
				if(time<configtime*1000){
					return false;
				}
			}
			
		}
		return true;
	}
	public static void main(String args[]){
		JSONObject obj=new JSONObject();
		obj.put("showtime", 10L);
		JSONArray jsay=new JSONArray();
		JSONObject objs=new JSONObject();
		objs.put("time", System.currentTimeMillis());
		objs.put("type", "time");
		JSONObject objs1=new JSONObject();
		objs1.put("time", System.currentTimeMillis()+100000);
		objs1.put("type", "time");
		jsay.add(objs);
		jsay.add(objs1);
		obj.put("jsay", jsay);
		System.out.println(ValidationCommon.isvalid(obj));
	}
}
