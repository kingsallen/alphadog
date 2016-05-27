package com.moseeker.servicemanager.web.controller.crawler;

import com.moseeker.servicemanager.common.UrlUtil;

public class CrawlerUtils {
	
	public static String LINEKEDIN = "";
	public static String LIEPIN = "http://crawl.bj.moseeker.com:9999/resume/liepin.html";
	public static String JOB51 = "http://crawl.bj.moseeker.com:9999/resume/51job.html";
	public static String ZHILIAN = "http://crawl.bj.moseeker.com:9999/resume/zhaopin.html";

	public String fetchFirstResume(String userName, String password, int type) {
		String result = null;
		switch(type) {
			case 1: result = fetchResume(userName, password, JOB51); break;
			case 2: result = fetchResume(userName, password, LIEPIN); break;
			case 3: result = fetchResume(userName, password, ZHILIAN); break;
			case 4: result = fetchResume(userName, password, LINEKEDIN); break;
		}
		return cleanning(result);
	}

	private String cleanning(String result) {
		
		return null;
	}

	private String fetchResume(String userName, String password, String url) {
		return UrlUtil.sendPost(url, "?"+userName+"&"+password);
	}
}
