package com.moseeker.servicemanager.web.controller.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * profile批量操作参数解析的帮助类。主要用于将参数装成ArrayList格式，方便操作
 * <p>Company: MoSeeker</P>  
 * <p>date: Aug 23, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class ProfileParamUtil {

	public static List<String> getProfilesUserIds(Map<String, Object> data) {
		Object userId = data.get("user_id");
		return obj2List(userId);
	}
	
	public static List<String> getProfilesUUIDs(Map<String, Object> data) {
		Object uuid = data.get("uuid");
		return obj2List(uuid);
	}
	
	public static List<String> getProfilesIds(Map<String, Object> data) {
		Object profileId = data.get("profile_id");
		return obj2List(profileId);
	}
	
	@SuppressWarnings("unchecked")
	private static List<String> obj2List(Object value) {
		if(value instanceof List) {
			return (List<String>)value;
		} else if(value instanceof String) {
			List<String> userIds = new ArrayList<>();
			userIds.add((String)value);
			return userIds;
		} else {
			return  new ArrayList<>(0);
		}
	}
}
