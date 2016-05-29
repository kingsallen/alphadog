package com.moseeker.servicemanager.web.controller.crawler;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.moseeker.servicemanager.common.UrlUtil;

public class CrawlerUtils {
	
	public static String LINEKEDIN = "http://crawl.bj.moseeker.com:9999/resume/linkedin.html";
	public static String LIEPIN = "http://crawl.bj.moseeker.com:9999/resume/liepin.html";
	public static String JOB51 = "http://crawl.bj.moseeker.com:9999/resume/51job.html";
	public static String ZHILIAN = "http://crawl.bj.moseeker.com:9999/resume/zhaopin.html";

	public String fetchFirstResume(String userName, String password, String token, int type) {
		String result = null;
		switch(type) {
			case 1: result = fetchResume("?"+userName+"&"+password, JOB51); break;
			case 2: result = fetchResume("?"+userName+"&"+password, LIEPIN); break;
			case 3: result = fetchResume("?"+userName+"&"+password, ZHILIAN); break;
			case 4: result = fetchResume("?"+token, LINEKEDIN); break;
		}
		return cleanning(result);
	}

	@SuppressWarnings("unchecked")
	private String cleanning(String result) {
		Map<String, Object> messagBean = (Map<String, Object>)JSON.parse(result);
		List<Map<String, Object>> resumes = (List<Map<String, Object>>)messagBean.get("resumes");
		Map<String, Object> resume = resumes.get(0);
		return JSON.toJSONString(resume);
	}

	private String fetchResume(String params, String url) {
		return UrlUtil.sendPost(url, params);
	}
	
	public static void main(String[] args) {
		String profile = "{\"status\": 0, \"resumes\": [{\"skills\": [], \"credentials\": [], \"attachment\": [], \"educations\": [{\"end_until_now\": 0, \"major_name\": \"\u8ba1\u7b97\u673a\u79d1\u5b66\u4e0e\u6280\u672f\", \"start\": \"2010-09\", \"degree_name\": \"3\", \"college_name\": \"\u5929\u6d25\u5de5\u4e1a\u5927\u5b66\", \"end\": \"2014-06\"}], \"basic\": {\"self_introduction\": \"\u6211\u662f\u4e00\u4e2a\u6d3b\u6cfc\u5f00\u6717\u7684\u4eba\uff0c\u7ecf\u8fc7\u8fd9\u4e00\u4e2a\u6708\u7684\u627e\u5de5\u4f5c\u6211\u7ec8\u4e8e\u660e\u767d\u4e86\u81ea\u5df1\u7684\u65b9\u5411\uff0c\u6211\u5e0c\u671b\u80fd\u627e\u5230\u4e00\u4e2a\u5408\u9002\u7684\u5de5\u4f5c\uff0c\u7136\u540e\u77e2\u5fd7\u4e0d\u6e1d\u7684\u575a\u6301\u4e0b\u53bb\u3002\", \"city_name\": \"\u5929\u6d25\", \"workstate\": \"4\", \"nationality_name\": \"\u5929\u6d25\", \"mobile\": \"15822226310\", \"username\": \"\u8e47\u667a\u534e\", \"birth\": \"1990-06\", \"gender\": \"1\", \"email\": \"chiwah.keen@gmail.com\"}, \"awards\": {\"name\": \"\", \"reward_date\": \"\", \"description\": \"\", \"level\": [\"\"]}, \"workexps\": [{\"salary_code\": 2, \"end_until_now\": 0, \"position_name\": \"\u9500\u552e\u4ee3\u8868\", \"description\": \"1\u3001\u6211\u7684\u65e5\u5e38\u5de5\u4f5c\u6709\u53d1\u5e03\u623f\u6e90\uff0c\u8054\u7cfb\u623f\u6e90\uff0c\u8054\u7cfb\u5ba2\u62372\u3001\u6211\u9500\u552e\u7684\u4ea7\u54c1\u662f\u4e8c\u624b\u79df\u8d413\u3001\u6211\u6240\u8d1f\u8d23\u7684\u4ea7\u54c1\u9500\u552e\u533a\u57df\u662f\u5929\u6d25\u5e02\u6cb3\u897f\u533a\u8d8a\u79c0\u8def4\u3001\u6211\u66fe\u53d6\u5f97\u7684\u9500\u552e\u4e1a\u7ee9\u662f\u7a81\u78345000\u5143\", \"start\": \"2012-07\", \"end\": \"2012-09\", \"company_name\": \"\u5929\u6d25\u4e2d\u539f\", \"industry_name\": \"\u623f\u5730\u4ea7/\u5efa\u7b51/\u5efa\u6750/\u5de5\u7a0b\", \"salary_type\": \"2\"}], \"intentions\": {\"salary_code\": \"0\", \"consider_venture_company_opportunities\": 0, \"industries\": [{\"industry_name\": \"I\"}, {\"industry_name\": \"T\"}, {\"industry_name\": \"\u670d\"}, {\"industry_name\": \"\u52a1\"}, {\"industry_name\": \"(\"}, {\"industry_name\": \"\u7cfb\"}, {\"industry_name\": \"\u7edf\"}, {\"industry_name\": \"/\"}, {\"industry_name\": \"\u6570\"}, {\"industry_name\": \"\u636e\"}, {\"industry_name\": \"/\"}, {\"industry_name\": \"\u7ef4\"}, {\"industry_name\": \"\u62a4\"}, {\"industry_name\": \")\"}], \"cities\": [{\"city_name\": \"\u5317\u4eac\"}], \"positions\": [{\"pposition_name\": \"\u9500\u552e\u4e1a\u52a1\u3001IT\u8d28\u91cf\u7ba1\u7406/\u6d4b\u8bd5/\u914d\u7f6e\u7ba1\u7406\"}], \"salary_type\": 2}, \"source\": \"4\", \"othermodifytime\": \"2016-05-27 10:31:45\", \"works\": [], \"import\": {}, \"projectexps\": [], \"languages\": []}]}";
		CrawlerUtils crawlerUtils = new CrawlerUtils();
		System.out.println(crawlerUtils.cleanning(profile));
		
		
		System.out.println(JSON.parseObject(null));
		
		System.out.println(UUID.randomUUID());
	}
	
	@Test
	public void MessageBeanTest() {
		System.out.println(cleanning(""));
	}
}
