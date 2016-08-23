package com.moseeker.servicemanager.web.controller.util;

import java.util.List;

/**
 * 
 * 批量查询profile的参数信息 
 * <p>Company: MoSeeker</P>  
 * <p>date: Aug 23, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class ProfilesParam {

	private int size;
	private List<Integer> userIds;
	private List<String> uuids;
	private List<Integer> profileIds;
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<Integer> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}
	public List<String> getUuids() {
		return uuids;
	}
	public void setUuids(List<String> uuids) {
		this.uuids = uuids;
	}
	public List<Integer> getProfileIds() {
		return profileIds;
	}
	public void setProfileIds(List<Integer> profileIds) {
		this.profileIds = profileIds;
	}
}
