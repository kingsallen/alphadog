package com.moseeker.useraccounts.pojo;

import java.util.List;

/**
 * 
 * 版本更新 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 10, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class NewsletterData {

	private int show_new_version;
	private String version;
	private String update_time;
	private List<String> update_list;
	
	public int getShow_new_version() {
		return show_new_version;
	}
	public void setShow_new_version(int show_new_version) {
		this.show_new_version = show_new_version;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public List<String> getUpdate_list() {
		return update_list;
	}
	public void setUpdate_list(List<String> update_list) {
		this.update_list = update_list;
	}
}
