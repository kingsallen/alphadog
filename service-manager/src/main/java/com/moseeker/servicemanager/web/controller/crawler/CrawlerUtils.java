package com.moseeker.servicemanager.web.controller.crawler;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.servicemanager.common.UrlUtil;
import com.moseeker.thrift.gen.common.struct.Response;

public class CrawlerUtils {

	public Response fetchFirstResume(String userName, String password, String token, int type, int lang, int source,
			int completeness, int appid, int user_id) throws Exception {
		String result = null;
		Map<String, String> param = new HashMap<>();
		ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
		propertiesUtils.loadResource("setting.properties");
		switch (type) {
		case 1:
			param.put("username", userName);
			param.put("password", password);
			//result = "{\"status\": 0, \"resumes\": [{\"other\": {\"gre\": \"\", \"IDPhoto\": \"\", \"companyBrand\": \"\", \"gmat\": \"\", \"idnumber\": \"330329198707154938\", \"majorrank\": \"\", \"residence\": \"\", \"workstate\": \"\", \"recentjob\": \"\", \"position\": \"\", \"workdays\": \"\", \"cet6\": \"\", \"expectsalary\": \"\", \"competition\": \"\", \"CollegeCity\": \"\", \"frequency\": \"\", \"location\": \"\u5317\u4eac-\u660c\u5e73\u533a\", \"Nation\": \"\", \"nightjob\": \"\", \"reward\": \"\", \"industry\": \"\", \"JapaneseLevel\": \"\", \"trip\": \"\", \"language\": \"\", \"CollegeContact\": \"\", \"EmergencyPhone\": \"\", \"toefl\": \"\", \"cet4\": \"\", \"internship\": \"\", \"schooljob\": [{\"position\": \"\u5b66\u4e60\u59d4\u5458\", \"end\": \"2007-07-01\", \"start\": \"2005-09-01\", \"describe\": \"\u6536\u4f5c\u4e1a\u7528\u7684\"}], \"CareerGoals\": \"\", \"PoliticalStatus\": \"\", \"height\": \"170\", \"EmergencyContact\": \"\", \"ReferenceContact\": \"\", \"ielts\": \"\", \"Address\": \"\", \"expectedlocation\": \"\", \"workyears\": \"3-4\u5e74\u5de5\u4f5c\u5e74\u9650\", \"icanstart\": \"\", \"ReferenceName\": \"\", \"residencetype\": \"\", \"marriage\": \"\u672a\u5a5a\", \"StudentFrom\": \"\", \"IsFreshGraduated\": \"\", \"salary\": \"\", \"ReferenceRelation\": \"\", \"gpa\": \"\", \"CollegeContactTel\": \"\", \"weight\": \"\"}, \"basic\": {\"birth\": \"1987-07-15\", \"qq\": \"\", \"self_introduction\": \"\u4ece\u5c0f\u751f\u6d3b\u5728\u826f\u597d\u7684\u5bb6\u5ead\u73af\u5883\u4e0b\uff0c\u53d7\u5230\u5bb6\u5ead\u718f\u9676\uff0c\u7236\u6bcd\u5f71\u54cd\uff0c\u672c\u4eba\u76f8\u4fe1\uff1a\u8bda\u4fe1\u662f\u6839\u672c\uff0c\u6562\u4e8e\u5e94\u5bf9\u6311\u6218\u662f\u5173\u952e\uff0c\u56e2\u961f\u5408\u4f5c\u662f\u6210\u529f\u7684\u6377\u5f84\u3002\u8bda\u4fe1\u80fd\u4e0e\u4eba\u5feb\u901f\u5efa\u7acb\u826f\u597d\u7684\u5173\u7cfb\uff0c\u5e94\u5bf9\u6311\u6218\u4f7f\u81ea\u5df1\u62e5\u6709\u66f4\u5e7f\u535a\u7684\u77e5\u8bc6\u548c\u66f4\u575a\u5f3a\u7684\u54c1\u8d28\uff0c\u56e2\u961f\u5408\u4f5c\u8ba9\u81ea\u5df1\u5728\u4eba\u751f\u9053\u8def\u4e0a\u4e0d\u4f1a\u5b64\u5355\u3002\", \"nationality_name\": \"\u4e2d\u56fd\u5927\u9646\", \"name\": \"\u7fc1\u5251\u98de\", \"gender\": \"1\", \"city_name\": \"\u5317\u4eac-\u660c\u5e73\u533a\"}, \"intentions\": [{\"workstate\": \"3\", \"salary_code\": \"6\", \"tag\": \"java\", \"cities\": [{\"city_code\": \"0\", \"city_name\": \"\u5317\u4eac\"}], \"positions\": [{\"position_name\": \"\u8f6f\u4ef6\u5de5\u7a0b\u5e08\"}], \"worktype\": \"1\", \"industries\": [{\"industry_name\": \"\u8ba1\u7b97\u673a\u8f6f\u4ef6\"}], \"salary_type\": 2}], \"works\": [{\"url\": \"\"}], \"skills\": [], \"attachments\": [], \"workexps\": [{\"end_date\": \"2013-03-01\", \"industry_name\": \"\u8ba1\u7b97\u673a\u8f6f\u4ef6\", \"report_to\": \"\", \"end_until_now\": 0, \"reference\": \"\", \"resign_reason\": \"\", \"underlings\": \"\", \"start_date\": \"2011-12-01\", \"achievement\": \"\", \"department_name\": \"\u8f6f\u4ef6\u5de5\u7a0b\u672c\u90e8 MES\u9879\u76ee\u7ec4\", \"description\": \"\u5916\u6d3e\u77f3\u5316\u76c8\u79d1\u9a7b\u573a\u5f00\u53d1\", \"type\": \"0\", \"company_name\": \"\u4e2d\u79d1\u8f6f\u4fe1\u606f\u79d1\u6280\u80a1\u4efd\u6709\u9650\u516c\u53f8\", \"job\": \"\u8f6f\u4ef6\u5de5\u7a0b\u5e08\"}, {\"end_date\": \"2011-12-01\", \"industry_name\": \"\u8ba1\u7b97\u673a\u8f6f\u4ef6\", \"report_to\": \"\", \"end_until_now\": 0, \"reference\": \"\", \"resign_reason\": \"\", \"underlings\": \"\", \"start_date\": \"2011-07-01\", \"achievement\": \"\", \"department_name\": \"\u7b2c\u516b\u4e8b\u4e1a\u90e8\", \"description\": \"\u5916\u5305\u5230\u4e2d\u822a\u4fe1\u505a\u822a\u7a7a\u884c\u4e1a\u8f6f\u4ef6\", \"type\": \"0\", \"company_name\": \"\u8054\u5408\u6c38\u9053\", \"job\": \"\u8f6f\u4ef6\u5de5\u7a0b\u5e08\"}, {\"end_date\": \"2011-07-01\", \"industry_name\": \"\u8ba1\u7b97\u673a\u8f6f\u4ef6\", \"report_to\": \"\", \"end_until_now\": 0, \"reference\": \"\", \"resign_reason\": \"\", \"underlings\": \"\", \"start_date\": \"2011-02-01\", \"achievement\": \"\", \"department_name\": \"\u8f6f\u4ef6\u7814\u53d1\u90e8\", \"description\": \"\u8d1f\u8d23\u516c\u53f8\u9879\u76ee\u7684\u5f00\u53d1\u4e0e\u7ef4\u62a4\", \"type\": \"0\", \"company_name\": \"\u4e5d\u6e90\u9ad8\u79d1\", \"job\": \"\u8f6f\u4ef6\u5de5\u7a0b\u5e08\"}, {\"end_date\": \"2010-07-01\", \"industry_name\": \"\u8ba1\u7b97\u673a\u8f6f\u4ef6\", \"report_to\": \"\", \"end_until_now\": 0, \"reference\": \"\", \"resign_reason\": \"\", \"underlings\": \"\", \"start_date\": \"2009-09-01\", \"achievement\": \"\", \"department_name\": \"\u6280\u672f\u90e8\", \"description\": \"\u516c\u53f8\u9879\u76ee\u7684\u5f00\u53d1\u4e0e\u7ef4\u62a4\", \"type\": \"0\", \"company_name\": \"\u6ee1\u4f60\u9c9c\u82b1\u793c\u54c1\u8d38\u6613\", \"job\": \"\u8f6f\u4ef6\u5de5\u7a0b\u5e08\"}], \"educations\": [{\"start_date\": \"2005-09-01\", \"end_date\": \"2009-07-01\", \"is_study_abroad\": \"0\", \"college_name\": \"\u4e2d\u539f\u5de5\u5b66\u9662\", \"end_until_now\": 0, \"degree\": \"5\", \"major_name\": \"\u8ba1\u7b97\u673a\u79d1\u5b66\u4e0e\u6280\u672f\", \"description\": \"\u8ba1\u7b97\u673a\u79d1\u5b66\u4e0e\u6280\u672f\"}], \"user\": {\"mobile\": \"15810215191\", \"email\": \"wjf2255@gmail.com\", \"name\": \"\u7fc1\u5251\u98de\"}, \"import\": {\"source\": \"1\", \"account_id\": \"wjf2255@gmail.com\", \"user_name\": \"wjf2255@gmail.com\", \"resume_id\": \"57254251\"}, \"projectexps\": [{\"start_date\": \"2012-09-01\", \"end_date\": \"2013-03-01\", \"hardware\": \"\", \"end_until_now\": 0, \"software\": \"\", \"dev_tool\": \"myeclipse7.0+eclipse3.6\", \"name\": \"\u539f\u6cb9\u50a8\u5907\u5e93\", \"description\": \"\u4e2d\u77f3\u5316\u662f\u4e2d\u56fd\u56fd\u5bb6\u6218\u7565\u77f3\u6cb9\u50a8\u5907\u7684\u91cd\u8981\u53c2\u4e0e\u8005\uff0c\u50a8\u5907\u5e93\u662f\u786c\u4ef6\u8bbe\u65bd\uff0c\u800c\u8be5\u9879\u76ee\u662f\u8f6f\u4ef6\u8bbe\u65bd\u3002\u8be5\u9879\u76ee\u5927\u91cf\u7684\u501f\u9274\u4e86\u7ba1\u9053\u9879\u76ee\u7684\u4e1a\u52a1\u903b\u8f91\uff0c\u5e76\u8003\u8651\u5230\u4e1a\u52a1\u903b\u8f91\u7684\u5e9e\u5927\u800c\u8fdb\u884c\u5206\u5e03\u5f0f\u90e8\u7f72\u3002\r\n\u8be5\u9879\u76ee\u6280\u672f\u7ee7\u627f\u81f3\u7ba1\u9053\u9879\u76ee\uff0c\u4f46\u7531\u4e8e\u8003\u8651\u5230\u524d\u7aef\u6240\u7528\u5230\u7684\u6280\u672f\u8fc7\u800c\u6742\uff0c\u7ef4\u62a4\u6210\u672c\u548c\u5b66\u4e60\u6210\u672c\u9ad8\uff0c\u6240\u4ee5\u5c06\u5176\u9650\u5236\u5728jquery \u548c easyui\u3002\", \"responsibility\": \"\u672c\u4eba\u5728\u8be5\u9879\u76ee\u4e2d\u8f85\u52a9\u9879\u76ee\u7ecf\u7406\u8fdb\u884c\u67b6\u6784\u8bbe\u8ba1\uff0c\u5e76\u8fdb\u884c\u5927\u90e8\u5206\u7684\u4e3b\u4f53\u67b6\u6784\u5f00\u53d1\uff1b\u5236\u4f5c\u5404\u7c7bAPI\u7684\u6587\u6863\uff0c\u4ee5\u53ca\u7ed9\u9879\u76ee\u7ec4\u6210\u5458\u505a\u8bb2\u89e3\uff1b\u63a2\u7d22\u65b0\u6280\u672f\u7684\u5e94\u7528\uff08easyui\uff09\uff0c\u5e76\u8d1f\u8d23\u8bb2\u89e3\uff1b\u534f\u52a9\u6709\u9700\u8981\u7684\u5f00\u53d1\u5c0f\u7ec4\u7b49\u3002\"}, {\"start_date\": \"2011-12-01\", \"end_date\": \"2012-08-01\", \"hardware\": \"\", \"end_until_now\": 0, \"software\": \"windows server 2003 sp2+tomcat6.0+oracle10g\", \"dev_tool\": \"MyEclipse7.0\", \"name\": \"\u65e5\u7167--\u4eea\u5f81\u6570\u5b57\u5316\u7ba1\u9053\", \"description\": \"\u65e5\u7167-\u4eea\u5f81\u7ba1\u9053\u662f\u4e2d\u77f3\u5316\u7ba1\u9053\u516c\u53f8\u7ec4\u7ec7\u5b9e\u65bd\u7684\u65e5\u4eea\u7ba1\u9053\u4fe1\u606f\u5316\u9879\u76ee\uff0c\u5efa\u8bbe\u6210\u4e2d\u56fd\u77f3\u5316\u7b2c\u4e00\u6761\u5177\u6709\u804c\u80fd\u7279\u70b9\u7684\u6570\u5b57\u5316\u7ba1\u9053\uff0c\u4e3a\u77f3\u5316\u7ba1\u9053\u9ad8\u6548\u3001\u5b89\u5168\u3001\u4f18\u8d28\u8fd0\u8425\u63d0\u4f9b\u4e86\u652f\u6301\u548c\u4fdd\u969c\u3002\u6d89\u53ca\u7684\u529f\u80fd\u4e3b\u8981\u5305\u62ec\u7efc\u5408\u5c55\u793a\u3001\u6570\u5b57\u5316\u7ad9\u573a\u3001\u6307\u6807\u7ba1\u7406\u3001\u5de1\u7ebf\u7ba1\u7406\u3001GIS\u5e94\u7528\u3001\u5b8c\u6574\u6027\u6570\u636e\u7ba1\u7406\u3002\u5176\u4e2d\u6d89\u53ca\u7684\u6280\u672f\u6709BO\u3001FLEX\u3001GIS\u3001excel\u62a5\u8868\u64cd\u4f5c\u3001java\u3001.net\u3001WebService\u3001jquery\u3001jquery-ui\u3001ztree\u7b49\u3002\r\n\u5176\u4e2d\uff0cjava\u90e8\u5206\u4e3b\u8981\u57fa\u4e8eSSH\u67b6\u6784\uff0c\u5e76\u5728\u8be5\u9879\u76ee\u4e2d\u5927\u91cf\u4f7f\u7528\u6ce8\u89e3\uff0c\u4ee5\u51cf\u5c11\u4f18\u5316\u914d\u7f6e\u7684\u5de5\u4f5c\u3002\", \"responsibility\": \"\u672c\u4eba\u4e3b\u8981\u53c2\u4e0e\u5230\u667a\u80fd\u7ad9\u573a\u7684\u529f\u80fd\u5f00\u53d1\u548c\u7ef4\u62a4\u3002\"}, {\"start_date\": \"2011-04-01\", \"end_date\": \"2011-06-01\", \"hardware\": \"\", \"end_until_now\": 0, \"software\": \"\", \"dev_tool\": \"MyEclipse7.0\", \"name\": \"jomlan \u9879\u76ee\u4e2d\u95f4\u4ef6\", \"description\": \"Jomlan\u9879\u76ee\u4e2d\u95f4\u4ef6\uff08JMWP\uff09\u662f\u4e3a\u4e86\u80fd\u591f\u5feb\u901f\u5f00\u53d1\u9879\u76ee\u800c\u9884\u5148\u5b9e\u73b0\u901a\u7528\u7684\u529f\u80fd\u7684\u4e00\u4e2a\u9879\u76ee\u3002\u8be5\u9879\u76ee\u4e3b\u8981\u5305\u62ec\u6570\u636e\u5e93\u7684\u57fa\u672c\u64cd\u4f5c\uff0c\u90e8\u5206\u4e1a\u52a1\u6d41\u7a0b\uff0c\u9875\u9762\u7ec4\u4ef6\u548c\u4e09\u5927\u6846\u67b6\u7684wrapper\u3002\", \"responsibility\": \"\u672c\u4eba\u4e3b\u8981\u8d1f\u8d23\u5206\u5c42\u7684\u501f\u53e3\u8bbe\u8ba1\u4e0e\u5b9e\u73b0\uff0c\u90e8\u5206\u9875\u9762\u7ec4\u4ef6\u7684\u8bbe\u8ba1\u4e0e\u5b9e\u73b0\uff0c\u636e\u5e93\u76f8\u5173\u7684\u4e1a\u52a1\u6d41\u7a0b\u7684\u8bbe\u8ba1\u4e0e\u5b9e\u73b0\uff0c\u6570\u636e\u5e93\u64cd\u4f5c\u7684\u63a5\u53e3\u8bbe\u8ba1\u4e0e\u5b9e\u73b0\uff0c\u6570\u636e\u5e93\u9879\u76ee\u7684WEB\u5c42\u548cBIZ\u5c42\u7684\u4ee3\u7406\u7684\u63a5\u53e3\u8bbe\u8ba1\u4e0e\u5b9e\u73b0\u3002\"}, {\"start_date\": \"2011-03-01\", \"end_date\": \"2011-04-01\", \"hardware\": \"\", \"end_until_now\": 0, \"software\": \"\", \"dev_tool\": \"mysql5.1+MyEclipse7.0+tomcat7.0\", \"name\": \"\u672f\u8bed\u67e5\u8be2\u7cfb\u7edf\", \"description\": \"\u672f\u8bed\u7ba1\u7406\u7cfb\u7edf\u662f\u4e00\u79cd\u901a\u8fc7\u63d0\u4f9b\u7684\u7f51\u9875\u63a5\u53e3\u641c\u7d22\u672c\u5730\u5316\u672f\u8bed\u7684\u77e5\u8bc6\u7ba1\u7406\u7cfb\u7edf\uff0c\u5176\u4e0d\u65ad\u5730\u5728\u672f\u8bed\u77e5\u8bc6\u5e93\u4e2d\u6536\u5f55\u548c\u66f4\u65b0\u5e38\u7528\u4fe1\u606f\u6280\u672f\u548c\u4e3b\u6d41\u8f6f\u4ef6\u4ea7\u54c1\u672f\u8bed\u3002\", \"responsibility\": \"\u672c\u4eba\u4e3b\u8981\u8d1f\u8d23\u67e5\u8be2\u7684\u4e8c\u7ea7\u7f13\u5b58\u8bbe\u8ba1\u4e0e\u5b9e\u73b0\u548c\u6743\u9650\u7684\u8bbe\u8ba1\u4e0e\u5b9e\u73b0\u3002\"}, {\"start_date\": \"2010-09-01\", \"end_date\": \"2010-12-01\", \"hardware\": \"\", \"end_until_now\": 0, \"software\": \"windows server 2003+Oracle9i+Tomcat5.5\", \"dev_tool\": \"MyEclipse7.0\", \"name\": \"\u4ed3\u5e93\u7ba1\u7406\u7cfb\u7edf\", \"description\": \"\u8be5\u9879\u76ee\u4e3a\u4ed3\u5e93\u8d27\u7269\u7ba1\u7406\u7cfb\u7edf\uff0c\u4e3b\u8981\u5305\u62ec\u7269\u54c1\u4f7f\u7528\u7533\u8bf7\u3001\u9886\u5bfc\u5ba1\u6838\u3001\u7269\u54c1\u5165\u5e93\u3001\u7269\u54c1\u51fa\u5e93\u3001\u7cfb\u7edf\u8bbe\u7f6e\u3001\u8d27\u7269\u7ba1\u7406\u548c\u4fe1\u606f\u7edf\u8ba1\u7b49\u51e0\u4e2a\u6a21\u5757\u3002\u7269\u54c1\u4f7f\u7528\u7533\u8bf7\u5305\u62ec\u7533\u8bf7\u4eba\u63d0\u4ea4\u7269\u54c1\u4f7f\u7528\u7533\u8bf7\uff0c\u53ef\u5206\u4e3a\u501f\u7528\u54c1\u548c\u6d88\u8017\u54c1\uff0c\u7533\u8bf7\u5355\u52a0\u4e0a\u7b7e\u540d\u63d0\u4ea4\u7ed9\u90e8\u95e8\u9886\u5bfc\uff1b\u9886\u5bfc\u5ba1\u6838\u5206\u4e3a\u5ba1\u6838\u901a\u8fc7\u3001\u5ba1\u6838\u672a\u901a\u8fc7\uff0c\u5ba1\u6838\u901a\u8fc7\u5219\u5c06\u7533\u8bf7\u5355\u63d0\u4ea4\u4e0a\u7ea7\u9886\u5bfc\uff0c\u4e0d\u901a\u8fc7\u5219\u76f4\u63a5\u8fd4\u56de\u3002\u7269\u54c1\u5165\u5e93\u5305\u62ec\u65b0\u7269\u54c1\u5165\u5e93\u548c\u5f52\u8fd8\u7269\u54c1\uff0c\u65b0\u7269\u54c1\u5165\u5e93\u8bb0\u5f55\u7269\u54c1\u5206\u7c7b\u548c\u6700\u65b0\u5355\u4ef7\u3002\u7269\u54c1\u51fa\u5e93\u9700\u8981\u6700\u9ad8\u9886\u5bfc\u5ba1\u6838\u6210\u529f\u7684\u901a\u77e5\u548c\u51fa\u5e93\u8bb0\u5f55\uff1b\u8d27\u7269\u7ba1\u7406\u548c\u4fe1\u606f\u7edf\u8ba1\u5206\u4e3a\u591a\u6761\u4ef6\u67e5\u8be2\u548c\u5206\u7c7b\u67e5\u8be2\uff0c\u64cd\u4f5c\u8005\u53ef\u4ee5\u901a\u8fc7\u4e0d\u540c\u6761\u4ef6\u67e5\u8be2\u9700\u8981\u7684\u4fe1\u606f\uff0c\u6216\u8005\u901a\u8fc7\u7269\u54c1\u5206\u7c7b\u67e5\u8be2\u6240\u9700\u4fe1\u606f\u3002\", \"responsibility\": \"\u8d1f\u8d23\u6574\u4e2a\u9879\u76ee\u7684\u5f00\u53d1\u3002\"}, {\"start_date\": \"2009-12-01\", \"end_date\": \"2010-06-01\", \"hardware\": \"\", \"end_until_now\": 0, \"software\": \"windowsNT+tomcat5.5+mysql5.1\", \"dev_tool\": \"MyEclipse\", \"name\": \"\u7231\u82b1\u65cf\u9c9c\u82b1\u901f\u9012\", \"description\": \"\u8d2d\u4e70\u9c9c\u82b1\u7684\u5546\u52a1\u7f51\u7ad9\u3002\u524d\u53f0\u4e3b\u8981\u5305\u62ec\u7528\u6237\u6ce8\u518c\u767b\u5f55\u6a21\u5757\uff0c\u8d2d\u4e70\u9c9c\u82b1\u793c\u54c1\u7684\u6d41\u7a0b\uff0c\u4e2a\u4eba\u4e2d\u5fc3\u6a21\u5757\uff0c\u82b1\u4ed8\u5b9d\uff08\u5728\u7ebf\u652f\u4ed8\uff09\uff1b\u540e\u53f0\u4e3b\u8981\u5305\u62ec\u82b1\u5e97\u767b\u5f55\u6ce8\u518c\u6a21\u5757\uff0c\u8ba2\u5355\u5904\u7406\u6d41\u7a0b\uff0c\u4fe1\u606f\u4ea4\u4e92\uff08\u4e3b\u8981\u5305\u62ec\u624b\u673a\u77ed\u4fe1\u3001\u90ae\u4ef6\u3001\u7ad9\u5185\u77ed\u6d88\u606f\uff09\uff0c\u6743\u9650\u7ba1\u7406\u6a21\u5757\uff0c\u4fc3\u9500\u6d3b\u52a8\u7684\u7ba1\u7406\u6a21\u5757\u7b49\u3002\", \"responsibility\": \"\u672c\u4eba\u5728\u9879\u76ee\u4e2d\u7684\u4e3b\u8981\u8d1f\u8d23\u53c2\u4e0e\u5bf9\u8be5\u9879\u76ee\u7684\u9700\u6c42\u5206\u6790\u3001\u7cfb\u7edf\u7684\u67b6\u6784\u8bbe\u8ba1\u3001\u6570\u636e\u5e93\u8bbe\u8ba1\u548c\u5404\u4e2a\u6a21\u5757\u7684\u5f00\u53d1\u3002\"}], \"credentials\": [{\"get_date\": \"2006-06-01\", \"name\": \"\u5927\u5b66\u82f1\u8bed\u56db\u7ea7\", \"score\": \"523\"}], \"awards\": [{\"reward_date\": \"2006-05-01\", \"name\": \"\u4f18\u79c0\u73ed\u5e72\u90e8\", \"level\": \"\u7532\u7b49\"}], \"languages\": [{\"name\": \"\u82f1\u8bed\", \"level\": 1}]}]}";
			result = fetchResume(JSON.toJSONString(param), propertiesUtils.get("CRAWLER_JOB51", String.class));
			System.out.println(result);
			break;
		case 2:
			param.put("username", userName);
			param.put("password", password);
			result = fetchResume(JSON.toJSONString(param), propertiesUtils.get("CRAWLER_LIEPIN", String.class));
			break;
		case 3:
			param.put("username", userName);
			param.put("password", password);
			result = fetchResume(JSON.toJSONString(param), propertiesUtils.get("CRAWLER_ZHILIAN", String.class));
			break;
		case 4:
			param.put("token", token);
			result = fetchResume(JSON.toJSONString(param), propertiesUtils.get("CRAWLER_LINEKEDIN", String.class));
			break;
		}
		// result = "{\"status\": 0, \"resumes\": [{\"skills\": [],
		// \"credentials\": [], \"attachment\": [], \"educations\":
		// [{\"end_until_now\": 0, \"major_name\":
		// \"\u8ba1\u7b97\u673a\u79d1\u5b66\u4e0e\u6280\u672f\", \"start\":
		// \"2010-09\", \"degree_name\": \"3\", \"college_name\":
		// \"\u5929\u6d25\u5de5\u4e1a\u5927\u5b66\", \"end\": \"2014-06\"}],
		// \"basic\": {\"self_introduction\":
		// \"\u6211\u662f\u4e00\u4e2a\u6d3b\u6cfc\u5f00\u6717\u7684\u4eba\uff0c\u7ecf\u8fc7\u8fd9\u4e00\u4e2a\u6708\u7684\u627e\u5de5\u4f5c\u6211\u7ec8\u4e8e\u660e\u767d\u4e86\u81ea\u5df1\u7684\u65b9\u5411\uff0c\u6211\u5e0c\u671b\u80fd\u627e\u5230\u4e00\u4e2a\u5408\u9002\u7684\u5de5\u4f5c\uff0c\u7136\u540e\u77e2\u5fd7\u4e0d\u6e1d\u7684\u575a\u6301\u4e0b\u53bb\u3002\",
		// \"city_name\": \"\u5929\u6d25\", \"workstate\": \"4\",
		// \"nationality_name\": \"\u5929\u6d25\", \"mobile\": \"15822226310\",
		// \"username\": \"\u8e47\u667a\u534e\", \"birth\": \"1990-06\",
		// \"gender\": \"1\", \"email\": \"chiwah.keen@gmail.com\"}, \"awards\":
		// [{\"name\": \"\", \"reward_date\": \"\", \"description\": \"\",
		// \"level\": [\"\"]}], \"workexps\": [{\"salary_code\": 2,
		// \"end_until_now\": 0, \"position_name\":
		// \"\u9500\u552e\u4ee3\u8868\", \"description\":
		// \"1\u3001\u6211\u7684\u65e5\u5e38\u5de5\u4f5c\u6709\u53d1\u5e03\u623f\u6e90\uff0c\u8054\u7cfb\u623f\u6e90\uff0c\u8054\u7cfb\u5ba2\u62372\u3001\u6211\u9500\u552e\u7684\u4ea7\u54c1\u662f\u4e8c\u624b\u79df\u8d413\u3001\u6211\u6240\u8d1f\u8d23\u7684\u4ea7\u54c1\u9500\u552e\u533a\u57df\u662f\u5929\u6d25\u5e02\u6cb3\u897f\u533a\u8d8a\u79c0\u8def4\u3001\u6211\u66fe\u53d6\u5f97\u7684\u9500\u552e\u4e1a\u7ee9\u662f\u7a81\u78345000\u5143\",
		// \"start\": \"2012-07\", \"end\": \"2012-09\", \"company_name\":
		// \"\u5929\u6d25\u4e2d\u539f\", \"industry_name\":
		// \"\u623f\u5730\u4ea7/\u5efa\u7b51/\u5efa\u6750/\u5de5\u7a0b\",
		// \"salary_type\": \"2\"}], \"intentions\": [{\"salary_code\": \"0\",
		// \"consider_venture_company_opportunities\": 0, \"industries\":
		// [{\"industry_name\": \"I\"}, {\"industry_name\": \"T\"},
		// {\"industry_name\": \"\u670d\"}, {\"industry_name\": \"\u52a1\"},
		// {\"industry_name\": \"(\"}, {\"industry_name\": \"\u7cfb\"},
		// {\"industry_name\": \"\u7edf\"}, {\"industry_name\": \"/\"},
		// {\"industry_name\": \"\u6570\"}, {\"industry_name\": \"\u636e\"},
		// {\"industry_name\": \"/\"}, {\"industry_name\": \"\u7ef4\"},
		// {\"industry_name\": \"\u62a4\"}, {\"industry_name\": \")\"}],
		// \"cities\": [{\"city_name\": \"\u5317\u4eac\"}], \"positions\":
		// [{\"position_name\":
		// \"\u9500\u552e\u4e1a\u52a1\u3001IT\u8d28\u91cf\u7ba1\u7406/\u6d4b\u8bd5/\u914d\u7f6e\u7ba1\u7406\"}],
		// \"salary_type\": 2}], \"source\": \"4\", \"othermodifytime\":
		// \"2016-05-27 10:31:45\", \"works\": [], \"import\": {},
		// \"projectexps\": [], \"languages\": []}]}";
		return cleanning(result, lang, source, completeness, appid, user_id);
	}

	@SuppressWarnings("unchecked")
	private Response cleanning(String result, int lang, int source, int completeness, int appid, int user_id) {
		Object obj = JSON.parse(result);
		Map<String, Object> messagBean = null;
		if (obj instanceof Map) {
			messagBean = (Map<String, Object>) JSON.parse(result);
		} else if (obj instanceof String) {
			messagBean = (Map<String, Object>) JSON.parse((String) obj);
		}
		if (messagBean.get("status") != null && (Integer) messagBean.get("status") == 0) {
			List<Map<String, Object>> resumes = (List<Map<String, Object>>) messagBean.get("resumes");
			Map<String, Object> resume = resumes.get(0);
			if (resume.get("profile") != null) {
				Map<String, Object> profile = (Map<String, Object>) resume.get("profile");
				if (lang != 0) {
					profile.put("lang", lang);
				}
				if (source != 0) {
					profile.put("source", source);
				} else {
					if (appid == 0) {
						profile.put("source", Constant.PROFILE_SOURCE_UNKNOW);
					} else if(appid == 1) {
						profile.put("source", Constant.PROFILE_SOURCE_PC_IMPORT);
					}  else {
						profile.put("source", source);
					}
				}
				if (completeness != 0) {
					profile.put("completeness", completeness);
				}
				if (user_id > 0) {
					profile.put("user_id", user_id);
				}
			} else {
				Map<String, Object> profile = new HashMap<>();
				profile.put("lang", lang);
				if (appid == 0) {
					profile.put("source", Constant.PROFILE_SOURCE_UNKNOW);
				} else if(appid == 1) {
					profile.put("source", Constant.PROFILE_SOURCE_PC_IMPORT);
				} else {
					profile.put("source", source);
				}
				profile.put("completeness", completeness);
				profile.put("user_id", completeness);
				resume.put("profile", profile);
				profile.put("user_id", user_id);
				resume.put("profile", profile);
			}
			return ResponseUtils.success(resume);
		} else if (messagBean.get("status") != null && (Integer) messagBean.get("status") == 1) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_USER_NOPERMITION);
		} else if (messagBean.get("status") != null && (Integer) messagBean.get("status") == 2) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_IMPORT_FAILED);
		} else if (messagBean.get("status") != null
				&& (Integer) messagBean.get("status") == 3 ) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_LOGIN_FAILED);
		} else if (messagBean.get("status") != null
				&& (Integer) messagBean.get("status") == 4) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_LOGIN2_FAILED);
		} else if (messagBean.get("status") != null
				&& (Integer) messagBean.get("status") == 5) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_SERVICE_PARAM_ERROR);
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_PARAM_ILLEGAL);
	}

	private String fetchResume(String params, String url) throws ConnectException {
		return UrlUtil.sendPost(url, params);
	}

	public static void main(String[] args) {
		String profile = "{\"status\": 0, \"resumes\": [{\"skills\": [], \"credentials\": [], \"attachment\": [], \"educations\": [{\"end_until_now\": 0, \"major_name\": \"\u8ba1\u7b97\u673a\u79d1\u5b66\u4e0e\u6280\u672f\", \"start\": \"2010-09\", \"degree_name\": \"3\", \"college_name\": \"\u5929\u6d25\u5de5\u4e1a\u5927\u5b66\", \"end\": \"2014-06\"}], \"basic\": {\"self_introduction\": \"\u6211\u662f\u4e00\u4e2a\u6d3b\u6cfc\u5f00\u6717\u7684\u4eba\uff0c\u7ecf\u8fc7\u8fd9\u4e00\u4e2a\u6708\u7684\u627e\u5de5\u4f5c\u6211\u7ec8\u4e8e\u660e\u767d\u4e86\u81ea\u5df1\u7684\u65b9\u5411\uff0c\u6211\u5e0c\u671b\u80fd\u627e\u5230\u4e00\u4e2a\u5408\u9002\u7684\u5de5\u4f5c\uff0c\u7136\u540e\u77e2\u5fd7\u4e0d\u6e1d\u7684\u575a\u6301\u4e0b\u53bb\u3002\", \"city_name\": \"\u5929\u6d25\", \"workstate\": \"4\", \"nationality_name\": \"\u5929\u6d25\", \"mobile\": \"15822226310\", \"username\": \"\u8e47\u667a\u534e\", \"birth\": \"1990-06\", \"gender\": \"1\", \"email\": \"chiwah.keen@gmail.com\"}, \"awards\": {\"name\": \"\", \"reward_date\": \"\", \"description\": \"\", \"level\": [\"\"]}, \"workexps\": [{\"salary_code\": 2, \"end_until_now\": 0, \"position_name\": \"\u9500\u552e\u4ee3\u8868\", \"description\": \"1\u3001\u6211\u7684\u65e5\u5e38\u5de5\u4f5c\u6709\u53d1\u5e03\u623f\u6e90\uff0c\u8054\u7cfb\u623f\u6e90\uff0c\u8054\u7cfb\u5ba2\u62372\u3001\u6211\u9500\u552e\u7684\u4ea7\u54c1\u662f\u4e8c\u624b\u79df\u8d413\u3001\u6211\u6240\u8d1f\u8d23\u7684\u4ea7\u54c1\u9500\u552e\u533a\u57df\u662f\u5929\u6d25\u5e02\u6cb3\u897f\u533a\u8d8a\u79c0\u8def4\u3001\u6211\u66fe\u53d6\u5f97\u7684\u9500\u552e\u4e1a\u7ee9\u662f\u7a81\u78345000\u5143\", \"start\": \"2012-07\", \"end\": \"2012-09\", \"company_name\": \"\u5929\u6d25\u4e2d\u539f\", \"industry_name\": \"\u623f\u5730\u4ea7/\u5efa\u7b51/\u5efa\u6750/\u5de5\u7a0b\", \"salary_type\": \"2\"}], \"intentions\": {\"salary_code\": \"0\", \"consider_venture_company_opportunities\": 0, \"industries\": [{\"industry_name\": \"I\"}, {\"industry_name\": \"T\"}, {\"industry_name\": \"\u670d\"}, {\"industry_name\": \"\u52a1\"}, {\"industry_name\": \"(\"}, {\"industry_name\": \"\u7cfb\"}, {\"industry_name\": \"\u7edf\"}, {\"industry_name\": \"/\"}, {\"industry_name\": \"\u6570\"}, {\"industry_name\": \"\u636e\"}, {\"industry_name\": \"/\"}, {\"industry_name\": \"\u7ef4\"}, {\"industry_name\": \"\u62a4\"}, {\"industry_name\": \")\"}], \"cities\": [{\"city_name\": \"\u5317\u4eac\"}], \"positions\": [{\"pposition_name\": \"\u9500\u552e\u4e1a\u52a1\u3001IT\u8d28\u91cf\u7ba1\u7406/\u6d4b\u8bd5/\u914d\u7f6e\u7ba1\u7406\"}], \"salary_type\": 2}, \"source\": \"4\", \"othermodifytime\": \"2016-05-27 10:31:45\", \"works\": [], \"import\": {}, \"projectexps\": [], \"languages\": []}]}";
		CrawlerUtils crawlerUtils = new CrawlerUtils();
		// System.out.println(crawlerUtils.cleanning(profile));

		System.out.println(JSON.parseObject(null));

		System.out.println(UUID.randomUUID());
	}
}
