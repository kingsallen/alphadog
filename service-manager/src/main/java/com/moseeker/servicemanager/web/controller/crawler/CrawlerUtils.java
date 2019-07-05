package com.moseeker.servicemanager.web.controller.crawler;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.UrlUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.web.controller.profile.ImportCVForm;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CrawlerUtils {

    Logger logger = LoggerFactory.getLogger(CrawlerUtils.class);

    CompanyServices.Iface companyServices = ServiceManager.SERVICE_MANAGER.getService(CompanyServices.Iface.class);

    private static final String PROFILE_IMPORT_UPPER_LIMIT = "PROFILE_IMPORT_UPPER_LIMIT";

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    public Response fetchFirstResume(ImportCVForm form) throws Exception {
        String userName = form.getUsername();
        String password = form.getPassword();
        String token = form.getToken();
        String unionid = form.getUnionid();
        String version = form.getVersion();
        String maimai_appid = form.getMaimai_appid();
        String mobile = form.getMobile();

        int type = form.getType();
        int lang = form.getLang();
        int source = form.getSource();
        int completeness = form.getCompleteness();
        int appid = form.getAppid();
        int user_id = form.getUser_id();
        int ua = form.getUa();

        ChannelType channelType = ChannelType.instaceFromInteger(type);
        try {
            isAllowImport(form);
        } catch (BIZException e){
            return ResponseUtils.fail(e.getCode(),e.getMessage());
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }

        String result = null;
        Map<String, String> param = new HashMap<>();
        ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
        propertiesUtils.loadResource("setting.properties");
        switch (channelType) {
            case JOB51:
                param.put("username", userName);
                param.put("password", password);
                //result = "{\"status\": 0, \"resumes\": [{\"awards\": [{\"reward_date\": \"2007-06-01\", \"level\": \"A\u7ea7\", \"name\": \"\u5956\u98792\"}, {\"reward_date\": \"2006-01-01\", \"level\": \"B\u7ea7\", \"name\": \"\u8fd9\u662f\u5956\u9879\"}], \"workexps\": [{\"start_date\": \"2007-01-01\", \"industry_name\": \"\u65b0\u80fd\u6e90\", \"type\": \"2\", \"underlings\": \"12\", \"department_name\": \"\u8fd9\u662f\u90e8\u95e82\", \"reference\": \"\u81ea\u5df1\", \"description\": \"\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff02\", \"industry_code\": 2104, \"resign_reason\": \"\u8fd9\u662f\u79bb\u804c\u539f\u56e0\", \"end_date\": null, \"job\": \"\u6c7d\u8f66\u673a\u6784\u5de5\u7a0b\u5e08\", \"company\": {\"company_scale\": 3, \"company_introduction\": \"\", \"company_name\": \"\u8fd9\u662f\u516c\u53f8\u540d\u79f02\", \"company_property\": 1, \"company_industry_code\": 2104, \"company_industry\": \"\u65b0\u80fd\u6e90\"}, \"end_until_now\": 1, \"achievement\": \"\u8fd9\u662f\u4e3b\u8981\u4e1a\u7ee9\", \"report_to\": \"\u522b\u4eba\"}, {\"start_date\": \"2006-01-01\", \"industry_name\": \"\u5a31\u4e50/\u4f11\u95f2/\u4f53\u80b2\", \"type\": \"0\", \"underlings\": \"\", \"department_name\": \"\u8fd9\u662f\u90e8\u95e81\", \"reference\": \"\", \"description\": \"\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u54c8\u54c8\u54c8\u54c8\u5475\u5475\u54bd\u597d\u7684\u597d\u7684\", \"industry_code\": 1903, \"resign_reason\": \"\", \"end_date\": \"2007-01-01\", \"job\": \"\u5ba2\u670d\u603b\u76d1\", \"company\": {\"company_scale\": 3, \"company_introduction\": \"\", \"company_name\": \"\u8fd9\u662f\u516c\u53f8\u540d\u79f01\", \"company_property\": 1, \"company_industry_code\": 1903, \"company_industry\": \"\u5a31\u4e50/\u4f11\u95f2/\u4f53\u80b2\"}, \"end_until_now\": 0, \"achievement\": \"\", \"report_to\": \"\"}], \"import\": {\"source\": \"1\", \"account_id\": \"jamie_qmx@163.com\", \"resume_id\": \"340444791\", \"user_name\": \"jamie_qmx@163.com\"}, \"educations\": [{\"start_date\": \"2008-02-01\", \"major_name\": \"\u7269\u7406\u5b66\", \"end_until_now\": 0, \"college_name\": \"\u6d59\u6c5f\u5927\u5b66\", \"end_date\": \"2010-05-01\", \"is_study_abroad\": \"1\", \"description\": \"\u8fd9\u662f\u6d77\u5916\u5927\u5b66\u4e13\u4e1a\u63cf\u8ff0\r\n\u6d77\u5916\u63cf\u8ff0\u66f4\u591a\u6309\u56fd\u5bb6\u71ac\u8fc7\u6765\", \"degree\": \"7\"}, {\"start_date\": \"2006-02-01\", \"major_name\": \"\u6d4b\u7ed8\u5de5\u7a0b\", \"end_until_now\": 1, \"college_name\": \"\u534e\u4e1c\u7406\u5de5\u5927\u5b66\", \"end_date\": null, \"is_study_abroad\": \"0\", \"description\": \"\u8fd9\u662f\u4e13\u4e1a\u63cf\u8ff01\r\n\u8fd9\u662f\u4e13\u4e1a\u63cf\u8ff02\r\n\u8fd9\u662f\u4e13\u4e1a\u63cf\u8ff03\", \"degree\": \"3\"}, {\"start_date\": \"2006-01-01\", \"major_name\": \"\u8ba1\u7b97\u673a\u79d1\u5b66\u4e0e\u6280\u672f\", \"end_until_now\": 0, \"college_name\": \"\u7b2c\u4e8c\u519b\u533b\u5927\u5b66\", \"end_date\": \"2007-01-01\", \"is_study_abroad\": \"1\", \"description\": \"\u7b2c\u4e8c\u519b\u533b\u5927\u5b66\u63cf\u8ff01\r\n\u7b2c\u4e8c\u519b\u533b\u5927\u5b66\u63cf\u8ff02\", \"degree\": \"5\"}], \"skills\": [{\"month\": 8, \"level\": 3, \"name\": \"ASP\"}, {\"month\": 99, \"level\": 4, \"name\": \"BAAN\"}], \"other\": {\"Nation\": \"\", \"workyears\": \"5-7\u5e74\u5de5\u4f5c\u5e74\u9650\", \"schooljob\": [{\"end\": \"2006-02-01\", \"describe\": \"\u8fd9\u662f\u804c\u52a1\u63cf\u8ff01\r\n\u8fd9\u662f\u804c\u52a1\u63cf\u8ff02\r\n\u8fd9\u662f\u804c\u52a1\u63cf\u8ff03\", \"position\": \"\u8fd9\u662f\u6821\u5185\u804c\u52a1\u540d\u79f0\", \"start\": \"2006-01-01\"}], \"companyBrand\": \"\", \"location\": \"\u5408\u80a5-\u7476\u6d77\u533a\", \"internship\": \"\", \"expectedlocation\": \"\", \"cet4\": \"\", \"idnumber\": \"612323199601012323\", \"gmat\": \"60\", \"IDPhoto\": \"\", \"height\": \"188\", \"workstate\": \"\", \"ReferenceName\": \"\", \"cet6\": \"\", \"industry\": \"\", \"CollegeContactTel\": \"\", \"residencetype\": \"\", \"trip\": \"\", \"ReferenceRelation\": \"\", \"weight\": \"\", \"icanstart\": \"\", \"reward\": \"\", \"StudentFrom\": \"\", \"workdays\": \"\", \"language\": \"\", \"EmergencyPhone\": \"\", \"PoliticalStatus\": \"\u56e2\u5458\", \"salary\": \"\", \"JapaneseLevel\": \"\", \"expectsalary\": \"\", \"gpa\": \"\", \"Address\": \"\", \"majorrank\": \"\", \"toefl\": \"5\", \"CareerGoals\": \"\", \"recentjob\": \"\", \"IsFreshGraduated\": \"\", \"CollegeCity\": \"\", \"nightjob\": \"\", \"competition\": \"\", \"marriage\": \"\u672a\u5a5a\", \"gre\": \"70\", \"frequency\": \"\", \"EmergencyContact\": \"\", \"residence\": \"\", \"ReferenceContact\": \"\", \"ielts\": \"50\", \"position\": \"\", \"CollegeContact\": \"\"}, \"projectexps\": [{\"dev_tool\": \"python\", \"start_date\": \"2007-01-01\", \"description\": \"\u8fd9\u662f\u9879\u76ee\u63cf\u8ff02\u81f3\u4eca\", \"hardware\": \"pc\", \"end_until_now\": 1, \"software\": \"ORACLE\", \"end_date\": null, \"name\": \"\u8fd9\u662f\u9879\u76ee\u540d\u79f02\u81f3\u4eca\", \"responsibility\": \"\u8fd9\u662f\u9879\u76ee\u8d23\u4efb\u63cf\u8ff02\u81f3\u4eca\"}, {\"dev_tool\": \"JAVA\", \"start_date\": \"2006-01-01\", \"description\": \"\u8fd9\u662f\u9879\u76ee\u63cf\u8ff01\", \"hardware\": \"PC\", \"end_until_now\": 0, \"software\": \"ORACLE\", \"end_date\": \"2006-06-01\", \"name\": \"\u8fd9\u662f\u9879\u76ee\u540d\u79f01\", \"responsibility\": \"\u8fd9\u662f\u9879\u76ee\u8d23\u4efb\u63cf\u8ff01\"}], \"basic\": {\"nationality_name\": \"\u4e2d\u56fd\u5927\u9646\", \"city_name\": \"\u5408\u80a5-\u7476\u6d77\u533a\", \"birth\": \"1996-01-01\", \"self_introduction\": \"\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u3002\u3002\u3002\", \"qq\": \"333\", \"gender\": \"1\", \"name\": \"\u6c5f\u95e8\u4f18\u4e00\"}, \"credentials\": [{\"get_date\": \"2007-01-01\", \"score\": \"333\", \"name\": \"\u8bc1\u4e662\"}, {\"get_date\": \"2006-01-01\", \"score\": \"6\", \"name\": \"\u6258\u798f\"}], \"user\": {\"mobile\": \"15900428801\", \"name\": \"\u6c5f\u95e8\u4f18\u4e00\", \"email\": \"jamie_qmx@163.com\"}, \"attachments\": [], \"intentions\": [{\"industries\": [{\"industry_code\": 1302, \"industry_name\": \"\u6279\u53d1/\u96f6\u552e\"}, {\"industry_code\": 1602, \"industry_name\": \"\u516c\u5173/\u5e02\u573a\u63a8\u5e7f/\u4f1a\u5c55\"}, {\"industry_code\": 1803, \"industry_name\": \"\u4e13\u4e1a\u670d\u52a1(\u54a8\u8be2\u3001\u4eba\u529b\u8d44\u6e90\u3001\u8d22\u4f1a)\"}, {\"industry_code\": 2204, \"industry_name\": \"\u519c/\u6797/\u7267/\u6e14\"}, {\"industry_code\": 0, \"industry_name\": \"\"}, {\"industry_code\": 0, \"industry_name\": \"\"}], \"salary_type\": 1, \"worktype\": \"1\", \"positions\": [{\"position_code\": \"180301\", \"position_name\": \"\u9879\u76ee\u603b\u76d1\"}, {\"position_code\": \"150201\", \"position_name\": \"\u5316\u5de5\u6280\u672f\u5e94\u7528/\u5316\u5de5\u5de5\u7a0b\u5e08\"}, {\"position_code\": \"110805\", \"position_name\": \"\u7535\u6c14\u5de5\u7a0b\u5e08/\u6280\u672f\u5458\"}, {\"position_code\": \"150112\", \"position_name\": \"\u533b\u7597\u5668\u68b0\u9500\u552e\u4ee3\u8868\"}, {\"position_code\": \"140612\", \"position_name\": \"\u6570\u63a7\u7f16\u7a0b\"}], \"salary_code\": \"5\", \"cities\": [{\"city_code\": \"0\", \"city_name\": \"\u4e0a\u6d77\"}, {\"city_code\": \"0\", \"city_name\": \"\u5e7f\u5dde\"}], \"tag\": \"\u8fd9\u662f \u6c42\u804c\u610f\u5411 \u5173\u952e\u5b57\", \"workstate\": \"3\"}], \"languages\": [{\"level\": 4, \"name\": \"\u82f1\u8bed\"}, {\"level\": 3, \"name\": \"\u65e5\u8bed\"}], \"works\": [{\"url\": \"www.baidu.123.com\"}]}]}";
                result = fetchResume(JSON.toJSONString(param), propertiesUtils.get("CRAWLER_JOB51", String.class));
                break;
            case LIEPIN:
                param.put("code", token);
                param.put("username", userName);
                param.put("password", password);
                if(StringUtils.isNullOrEmpty(token)) {
                    result = fetchResume(JSON.toJSONString(param), propertiesUtils.get("CRAWLER_LIEPIN", String.class));
                } else{
                    result = fetchResume(JSON.toJSONString(param), propertiesUtils.get("CRAWLER_LIEPIN_API", String.class));
                }
                //result = "{\"status\": 0, \"resumes\": [{\"awards\": [], \"workexps\": [{\"start_date\": \"2015-06-01\", \"end_date\": null, \"city_name\": \"\u4e0a\u6d77\", \"salary_code\": 6, \"underlings\": \"13\", \"department_name\": \"\u7814\u53d1\u90e8\", \"company\": {\"company_scale\": 4, \"company_introduction\": \"\u8fd9\u662f\u6700\u8fd1\u4e00\u5bb6\u516c\u53f8\u7684\u7b80\u4ecb\", \"company_name\": \"\u8fd9\u662f\u6700\u8fd1\u4e00\u5bb6\u516c\u53f8\u540d\u79f0\", \"company_property\": 4, \"company_industry_code\": 2103, \"company_industry\": \"\u7535\u6c14/\u7535\u529b/\u6c34\u5229\"}, \"description\": \"\u8fd9\u662f\u5de5\u4f5c\u804c\u8d23\u7684\u63cf\u8ff0\uff0c\u8981\u5341\u4e2a\u5b57ah\u54c8\u554a\u54c8\", \"job\": \"\u8d44\u6599\u5458\", \"salary_type\": 2, \"end_until_now\": 1, \"achievement\": \"\u8fd9\u662f\u5de5\u4f5c\u4e1a\u7ee9\", \"report_to\": \"\u6c47\u62a5\u5bf9\u8c61\u540d\u79f0\"}, {\"start_date\": \"2012-04-01\", \"end_date\": \"2013-06-01\", \"city_name\": \"\u4e0a\u6d77\", \"salary_code\": 6, \"underlings\": \"13\", \"department_name\": \"\u7814\u53d1\u90e8\", \"company\": {\"company_scale\": 4, \"company_introduction\": \"\u8fd9\u662f\u6700\u8fd1\u4e00\u5bb6\u516c\u53f8\u7684\u7b80\u4ecb\", \"company_name\": \"\u8fd9\u662f\u6700\u8fd1\u4e00\u5bb6\u516c\u53f8\u540d\u79f0\", \"company_property\": 4, \"company_industry_code\": 2103, \"company_industry\": \"\u7535\u6c14/\u7535\u529b/\u6c34\u5229\"}, \"description\": \"\u8fd9\u662f\u5de5\u4f5c\u804c\u8d23\u7684\u63cf\u8ff0\uff0c\u8981\u5341\u4e2a\u5b57ah\u54c8\u554a\u54c8\", \"job\": \"\u8fd9\u662f\u804c\u4f4d\u540d\u79f0\", \"salary_type\": 2, \"end_until_now\": 0, \"achievement\": \"\u8fd9\u662f\u5de5\u4f5c\u4e1a\u7ee9\", \"report_to\": \"\u6c47\u62a5\u5bf9\u8c61\u540d\u79f0\"}, {\"start_date\": \"2010-04-01\", \"end_date\": \"2012-05-01\", \"city_name\": \"\u5b81\u6ce2\", \"salary_code\": 7, \"underlings\": \"3\", \"department_name\": \"\u6295\u8d44\u90e8\", \"company\": {\"company_scale\": 6, \"company_introduction\": \"\u8fd9\u662f\u516c\u53f8\u7b80\u4ecb\u516c\u53f8\u7b80\u4ecb\u516c\u53f8\u7b80\u4ecb\", \"company_name\": \"\u8fd9\u662f\u7b2c\u4e8c\u5bb6\u516c\u53f8\u540d\u79f0\", \"company_property\": 1, \"company_industry_code\": 1704, \"company_industry\": \"\u7269\u4e1a\u7ba1\u7406/\u5546\u4e1a\u4e2d\u5fc3\"}, \"description\": \"\u8fd9\u662f\u5de5\u4f5c\u4e1a\u7ee9\u5de5\u4f5c\u4e1a\u7ee9\u5de5\u4f5c\u4e1a\u7ee9\", \"job\": \"\u8bc1\u5238\u5206\u6790\u5e08\", \"salary_type\": 2, \"end_until_now\": 0, \"achievement\": \"\u8fd9\u662f\u5de5\u4f5c\u4e1a\u7ee9\u5de5\u4f5c\u4e1a\u7ee9\uff0c\u6295\u8d44\u7ecf\u7406\u554a\u6295\u8d44\u7ecf\u7406\", \"report_to\": \"\u8001\u5927\"}], \"import\": {\"source\": \"2\", \"account_id\": \"\", \"resume_id\": \"2c6a9394Q37734b5e\", \"user_name\": \"jamie_qmx@163.com\"}, \"educations\": [{\"start_date\": \"2011-09-01\", \"major_name\": \"\u7ecf\u6d4e\u7ba1\u7406\", \"end_until_now\": 0, \"college_name\": \"\u5317\u4eac\u5927\u5b66\", \"end_date\": \"2014-07-01\", \"is_unified\": 1, \"degree\": \"6\"}, {\"start_date\": \"2007-09-01\", \"major_name\": \"\u56fd\u9645\u7ecf\u6d4e\u4e0e\u8d38\u6613\", \"end_until_now\": 0, \"college_name\": \"\u6d59\u6c5f\u5927\u5b66\", \"end_date\": \"2011-07-01\", \"is_unified\": 1, \"degree\": \"5\"}], \"skills\": [], \"other\": {\"Nation\": \"\", \"workyears\": \"\", \"companyBrand\": \"\", \"location\": \"\u4e0a\u6d77\", \"internship\": \"\", \"expectedlocation\": \"\", \"cet4\": \"\", \"idnumber\": \"\", \"gmat\": \"\", \"IDPhoto\": \"\", \"height\": \"\", \"EmergencyPhone\": \"\", \"ReferenceName\": \"\", \"cet6\": \"\", \"industry\": \"\", \"CollegeContactTel\": \"\", \"residencetype\": \"\", \"trip\": \"\", \"ReferenceRelation\": \"\", \"weight\": \"\", \"icanstart\": \"\", \"reward\": \"\", \"StudentFrom\": \"\", \"workdays\": \"\", \"language\": \"\", \"workstate\": \"\u5728\u804c\uff0c\u770b\u770b\u65b0\u673a\u4f1a\", \"PoliticalStatus\": \"\", \"salary\": \"0\u5143/\u5e74\", \"JapaneseLevel\": \"\", \"expectsalary\": \"\", \"gpa\": \"\", \"Address\": \"\", \"majorrank\": \"\", \"toefl\": \"\", \"CareerGoals\": \"\", \"recentjob\": \"\", \"IsFreshGraduated\": \"\", \"CollegeCity\": \"\", \"nightjob\": \"\", \"competition\": \"\", \"marriage\": \"\", \"gre\": \"\", \"frequency\": \"\", \"EmergencyContact\": \"\", \"residence\": \"\", \"ReferenceContact\": \"\", \"ielts\": \"\", \"position\": \"\", \"CollegeContact\": \"\"}, \"projectexps\": [{\"start_date\": \"2012-05-01\", \"description\": \"\u9879\u76ee\u55b5\u55b5\u55b5\u9879\u76ee\u55b5\u55b5\u55b5\u9879\u76ee\u55b5\u55b5\u55b5\u9879\u76ee\u55b5\u55b5\u55b5\u9879\u76ee\u55b5\u55b5\u55b5\u9879\u76ee\u55b5\u55b5\u55b5\u9879\u76ee\u55b5\u55b5\u55b5\u9879\u76ee\u55b5\u55b5\u55b5\u9879\u76ee\u55b5\u55b5\u55b5\u9879\u76ee\u55b5\u55b5\u55b5\", \"role\": \"\u53d1\u8d27\u5458\", \"end_until_now\": 0, \"achievement\": \"\u9879\u76ee\u4e1a\u7ee9\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u4e1a\u7ee9\", \"end_date\": \"2014-04-01\", \"company_name\": \"\u8fd9\u662f\u7b2c\u4e8c\u5bb6\u516c\u53f8\u540d\u79f0\", \"name\": \"\u4e00\u4e2a\u9879\u76ee\u7684\u540d\u79f0\", \"responsibility\": \"\u9879\u76ee\u804c\u8d23\u804c\u8d23\u9879\u76ee\u804c\u8d23\u804c\u8d23\u9879\u76ee\u804c\u8d23\u804c\u8d23\u9879\u76ee\u804c\u8d23\u804c\u8d23\u9879\u76ee\u804c\u8d23\u804c\u8d23\u9879\u76ee\u804c\u8d23\u804c\u8d23\u9879\u76ee\u804c\u8d23\u804c\u8d23\u9879\u76ee\u804c\u8d23\u804c\u8d23\u9879\u76ee\u804c\u8d23\u804c\u8d23\u9879\u76ee\u804c\u8d23\u804c\u8d23\u9879\u76ee\u804c\u8d23\u804c\u8d23\u9879\u76ee\u804c\u8d23\u804c\u8d23\u9879\u76ee\u804c\u8d23\u804c\u8d23\u9879\u76ee\u804c\u8d23\u804c\u8d23\"}, {\"start_date\": \"2010-06-01\", \"description\": \"\u9879\u76ee\u63cf\u8ff0\u9879\u76ee\u63cf\u8ff0\u9879\u76ee\u63cf\u8ff0\", \"role\": \"\u9879\u76ee\u7814\u7a76\u5458\", \"end_until_now\": 0, \"achievement\": \"\u9879\u76ee\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\u9879\u76ee\u4e1a\u7ee9\", \"end_date\": \"2012-05-01\", \"company_name\": \"\u8fd9\u662f\u6700\u8fd1\u4e00\u5bb6\u516c\u53f8\u540d\u79f0\", \"name\": \"\u5f88\u5927\u7684\u9879\u76ee\", \"responsibility\": \"\u9879\u76ee\u804c\u8d23\u9879\u76ee\u804c\u8d23\u9879\u76ee\u804c\u8d23\u9879\u76ee\u804c\u8d23\u9879\u76ee\u804c\u8d23\u9879\u76ee\u804c\u8d23\u9879\u76ee\u804c\u8d23\u9879\u76ee\u804c\u8d23\u9879\u76ee\u804c\u8d23\u9879\u76ee\u804c\u8d23\u9879\u76ee\u804c\u8d23\u9879\u76ee\u804c\u8d23\u9879\u76ee\u804c\u8d23\u9879\u76ee\u804c\u8d23\"}], \"basic\": {\"birth\": \"1976-01-01\", \"self_introduction\": \"\u81ea\u6211\u8bc4\u4ef7\u554a\uff0c\u81ea\u6211\u8bc4\u4ef7\u81ea\u6211\u8bc4\u4ef7\u554a\uff0c\u81ea\u6211\u8bc4\u4ef7\u81ea\u6211\u8bc4\u4ef7\u554a\uff0c\u81ea\u6211\u8bc4\u4ef7\u81ea\u6211\u8bc4\u4ef7\u554a\uff0c\u81ea\u6211\u8bc4\u4ef7\u81ea\u6211\u8bc4\u4ef7\u554a\uff0c\u81ea\u6211\u8bc4\u4ef7\", \"city_name\": \"\u4e0a\u6d77\", \"gender\": \"1\", \"name\": \"\u6c5f\u95e8\u9519\"}, \"credentials\": [], \"user\": {\"mobile\": \"13041151211\", \"name\": \"\u6c5f\u95e8\u9519\", \"email\": \"jamie_qmx@163.com\"}, \"attachments\": [], \"intentions\": [{\"industries\": [{\"industry_code\": 1101, \"industry_name\": \"\u8ba1\u7b97\u673a\u8f6f\u4ef6\"}, {\"industry_code\": 1105, \"industry_name\": \"\u901a\u4fe1/\u7535\u4fe1\u8fd0\u8425\u3001\u589e\u503c\u670d\u52a1\"}, {\"industry_code\": 1302, \"industry_name\": \"\u6279\u53d1/\u96f6\u552e\"}], \"salary_type\": 1, \"positions\": null, \"salary_code\": 0, \"cities\": [{\"city_name\": \"\u6210\u90fd\"}, {\"city_name\": \"\u5408\u80a5\"}, {\"city_name\": \"\u6d77\u53e3\"}], \"workstate\": \"1\"}], \"languages\": [{\"name\": \"\u82f1\u8bed\"}, {\"name\": \"\u65e5\u8bed\"}, {\"name\": \"\u5fb7\u8bed\"}, {\"name\": \"\u666e\u901a\u8bdd\"}], \"works\": []}]}";
                break;
            case ZHILIAN:
                param.put("username", userName);
                param.put("password", password);
                result = fetchResume(JSON.toJSONString(param), propertiesUtils.get("CRAWLER_ZHILIAN", String.class));
                //result = "{\"status\": 0, \"resumes\": [{\"awards\": [{\"reward_date\": \"2014-02-01\", \"description\": \"\u8fd9\u662f\u83b7\u5956\u63cf\u8ff01\u8fd9\u662f\u83b7\u5956\u63cf\u8ff02\u8fd9\u662f\u83b7\u5956\u63cf\u8ff03\", \"name\": \"\u8fd9\u662f\u83b7\u5f97\u5956\u9879\"}, {\"reward_date\": \"2007-08-01\", \"description\": \"\u5956\u52b1\u63cf\u8ff0\uff0c\u5956\u52b1\u63cf\u8ff0\", \"name\": \"\u5956\u52b1\u5956\u52b1\u540d\u79f0\"}, {\"reward_date\": \"2009-11-01\", \"description\": \"\u5956\u52b1\u63cf\u8ff02\", \"name\": \"\u5956\u52b1\u540d\u79f02\"}], \"workexps\": [{\"industry_code\": 1103, \"company\": {\"company_scale\": 1, \"company_introduction\": \"\", \"company_name\": \"\u8fd9\u662f\u516c\u53f8\u540d1\", \"company_property\": 1, \"company_industry_code\": 1103, \"company_industry\": \"\u8ba1\u7b97\u673a\u670d\u52a1(\u7cfb\u7edf\u3001\u6570\u636e\u670d\u52a1\u3001\u7ef4\u4fee)\"}, \"start_date\": \"2005-01-01\", \"industry_name\": \"\u8ba1\u7b97\u673a\u670d\u52a1(\u7cfb\u7edf\u3001\u6570\u636e\u670d\u52a1\u3001\u7ef4\u4fee)\", \"job\": \"\u8fd9\u662f\u804c\u4f4d1\", \"salary_code\": 2, \"end_until_now\": 1, \"salary_type\": \"8\", \"end_date\": null, \"description\": \"\u8fd9\u662f\u516c\u53f81\u5de5\u4f5c\u63cf\u8ff0\u5185\u5bb9\u3002\u3002\u3002\"}, {\"industry_code\": 1503, \"company\": {\"company_scale\": 0, \"company_introduction\": \"\", \"company_name\": \"\u8fd9\u662f\u516c\u53f8\u540d\", \"company_property\": 1, \"company_industry_code\": 1503, \"company_industry\": \"\u52a0\u5de5\u5236\u9020\uff08\u539f\u6750\u6599\u52a0\u5de5/\u78e8\u5177\uff09\"}, \"start_date\": \"2002-02-01\", \"industry_name\": \"\u52a0\u5de5\u5236\u9020\uff08\u539f\u6750\u6599\u52a0\u5de5/\u78e8\u5177\uff09\", \"job\": \"\u8fd9\u662f\u804c\u4f4d\u540d\u79f0\", \"salary_code\": 2, \"end_until_now\": 0, \"salary_type\": \"1\", \"end_date\": \"2006-05-01\", \"description\": \"\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0123\"}], \"import\": {\"source\": \"3\", \"account_id\": \"jamie_qmx@163.com\", \"resume_id\": \"JM652070274R90250000000\", \"user_name\": \"jamie_qmx@163.com\"}, \"educations\": [{\"start_date\": \"2013-12-01\", \"major_name\": \"\u8fd9\u662f\u4e13\u4e1a\u540d\u79f0\", \"end_until_now\": 0, \"college_name\": \"\u8fd9\u662f\u5b66\u6821\u540d\u79f0\", \"end_date\": \"2016-01-01\", \"is_unified\": \"2\", \"degree\": \"7\"}, {\"start_date\": \"2005-01-01\", \"major_name\": \"\u7ba1\u7406\u79d1\u5b66\", \"end_until_now\": 0, \"college_name\": \"\u8fd9\u662f\u5b66\u6821\u540d\u79f01\", \"end_date\": \"2006-01-01\", \"is_unified\": \"1\", \"degree\": \"4\"}], \"skills\": [{\"month\": \"66\u4e2a\u6708\", \"level\": 4, \"name\": \"\u8ba1\u7b97\u673a\uff0fIT\"}, {\"month\": \"44\u4e2a\u6708\", \"level\": 2, \"name\": \"\u751f\u4ea7\u6280\u5de5\"}], \"other\": {\"Nation\": \"\", \"workyears\": \"\", \"schooljob\": [{\"end\": \"2005-02-01\", \"describe\": \"\u8fd9\u662f\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb91\u8fd9\u662f\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb92\u8fd9\u662f\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb93\u8fd9\u662f\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb94\", \"position\": \"\u8fd9\u662f\u5b9e\u8df5\u540d\u79f0\", \"start\": \"2005-01-01\"}, {\"end\": null, \"describe\": \"\u8fd9\u662f\u5728\u6837\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb9\u3002\u3002\u3002\", \"position\": \"\u8fd9\u662f\u5b9e\u8df5\u540d\u79f01\", \"start\": \"2005-01-01\"}], \"companyBrand\": \"\", \"location\": \"\u4e0a\u6d77\", \"internship\": \"\", \"expectedlocation\": \"\", \"cet4\": \"\", \"idnumber\": \"\", \"gmat\": \"\", \"IDPhoto\": \"\", \"height\": \"\", \"workstate\": \"\u6211\u76ee\u524d\u5728\u804c\uff0c\u6b63\u8003\u8651\u6362\u4e2a\u65b0\u73af\u5883\uff08\u5982\u6709\u5408\u9002\u7684\u5de5\u4f5c\u673a\u4f1a\uff0c\u5230\u5c97\u65f6\u95f4\u4e00\u4e2a\u6708\u5de6\u53f3\uff09\", \"ReferenceName\": \"\", \"cet6\": \"\", \"industry\": \"\", \"CollegeContactTel\": \"\", \"residencetype\": \"\", \"trip\": \"\", \"ReferenceRelation\": \"\", \"weight\": \"\", \"icanstart\": \"\", \"reward\": \"\", \"StudentFrom\": \"\", \"workdays\": \"\", \"language\": \"\", \"EmergencyPhone\": \"\", \"PoliticalStatus\": \"\", \"salary\": \"\", \"JapaneseLevel\": \"\", \"expectsalary\": \"\", \"gpa\": \"\", \"Address\": \"\", \"majorrank\": \"\", \"toefl\": \"\", \"CareerGoals\": \"\", \"recentjob\": \"\", \"IsFreshGraduated\": \"\", \"CollegeCity\": \"\", \"nightjob\": \"\", \"competition\": \"\", \"marriage\": \"\", \"gre\": \"\", \"frequency\": \"\", \"EmergencyContact\": \"\", \"residence\": \"\u4e0a\u6d77\", \"ReferenceContact\": \"\", \"ielts\": \"\", \"position\": \"\", \"CollegeContact\": \"\"}, \"projectexps\": [{\"start_date\": \"2015-01-01\", \"name\": \"\u8fd9\u662f\u9879\u76ee\u540d\u79f0\", \"end_until_now\": 0, \"end_date\": \"2016-02-01\", \"description\": \"\u5ba1\u9879\u76ee\u63cf\u8ff01\u5ba1\u9879\u76ee\u63cf\u8ff02\u5ba1\u9879\u76ee\u63cf\u8ff03\u5ba1\u9879\u76ee\u63cf\u8ff04\", \"responsibility\": \"\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23111\"}, {\"start_date\": \"2005-01-01\", \"name\": \"\u8fd9\u662f\u9879\u76ee\u540d\u79f01\", \"end_until_now\": 1, \"end_date\": null, \"description\": \"\u8fd9\u662f\u9879\u76ee\u63cf\u8ff0\u5185\u5bb9\u3002\u3002\u3002\", \"responsibility\": \"\u8fd9\u662f\u9879\u76ee\u4e2d\u804c\u8d23\u5185\u5bb9\u3002\u3002\u3002\"}], \"basic\": {\"birth\": \"1989-06-01\", \"self_introduction\": \"\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9123\", \"city_name\": \"\u4e0a\u6d77\", \"gender\": \"2\", \"name\": \"\u6c5f\u95e8\u6613\"}, \"credentials\": [{\"reward_date\": \"2016-01-01\", \"name\": \"\u6258\u798f\"}, {\"reward_date\": \"2016-01-01\", \"name\": \"\u5168\u56fd\u8ba1\u7b97\u673a\u5e94\u7528\u6280\u672f\u8bc1\u4e66\"}, {\"reward_date\": \"2009-01-01\", \"name\": \"\u521d\u7ea7\u5de5\u7a0b\u5e08\"}, {\"reward_date\": \"2016-01-01\", \"name\": \"GRE\"}], \"user\": {\"mobile\": \"15900428801\", \"name\": \"\u6c5f\u95e8\u6613\", \"email\": \"jamie_qmx@163.com\"}, \"attachments\": [], \"intentions\": [{\"industries\": [{\"industry_code\": 1306, \"industry_name\": \"\u5bb6\u5177/\u5bb6\u7535/\u73a9\u5177/\u793c\u54c1\"}], \"salary_type\": 2, \"worktype\": \"1\", \"positions\": [{\"position_code\": \"0\", \"position_name\": \"\u9500\u552e\u7ba1\u7406\u3001\u5ba2\u670d/\u552e\u524d/\u552e\u540e\u6280\u672f\u652f\u6301\u3001\u5e02\u573a\"}], \"salary_code\": \"5\", \"cities\": [{\"city_code\": \"0\", \"city_name\": \"\u5317\u4eac\"}, {\"city_code\": \"0\", \"city_name\": \"\u4e0a\u6d77\"}, {\"city_code\": \"0\", \"city_name\": \"\u5929\u6d25\"}], \"workstate\": \"2\"}], \"languages\": [{\"level\": 4, \"name\": \"\u82f1\u8bed\"}, {\"level\": 2, \"name\": \"\u65e5\u8bed\"}, {\"level\": 2, \"name\": \"\u6cd5\u8bed\"}], \"works\": []}]}";
                break;
            case LINKEDIN:
                if(StringUtils.isNullOrEmpty(token)){
                    param.put("username", userName);
                    param.put("password", password);
                    param.put("userid", user_id+"");
                    param.put("verify_code", form.getCode());
                    result = fetchResume(JSON.toJSONString(param), propertiesUtils.get("CRAWLER_LINEKEDIN_SCRAPER", String.class));
                }else {
                    param.put("token", token);
                    result = fetchResume(JSON.toJSONString(param), propertiesUtils.get("CRAWLER_LINEKEDIN", String.class));
                }
                //result = "{\"status\": 0, \"resumes\":[{\"other\": {},\"workexps\": [{\"company\": {\"company_name\": \"IBM China\",\"company_introduction\": \"\"},\"end_until_now\": 1,\"description\": \"doing is better than perfect.\",\"end_date\": \"\",\"job\": \"Software Engineer\",\"start_date\": \"2007-07-01\"}],\"credentials\": [],\"import\": {\"resume_id\": \"PjrMx2C9nE\",\"source\": \"4\",\"username\": \"\",\"account_id\": \"\"},\"educations\": [],\"skills\": [],\"basic\": {\"birth\": \" \",\"name\": \"Wang Yaofeng\",\"nationality_name\": \"China\",\"city_name\": \"Shanghai City\",\"gender\": \"0\"},\"user\": {\"name\": \"Wang Yaofeng\",\"mobile\": \"\"},\"works\": [],\"languages\": [],\"awards\": [],\"attachments\": [],\"intentions\": [],\"projectexps\": []}]}";
                break;
            case VERYEAST:
                param.put("username", userName);
                param.put("password", password);
                result = fetchResume(JSON.toJSONString(param), propertiesUtils.get("CRAWLER_VERYEAST", String.class));
                //result = "{\"status\": 0, \"resumes\": [{\"awards\": [{\"reward_date\": \"2014-02-01\", \"description\": \"\u8fd9\u662f\u83b7\u5956\u63cf\u8ff01\u8fd9\u662f\u83b7\u5956\u63cf\u8ff02\u8fd9\u662f\u83b7\u5956\u63cf\u8ff03\", \"name\": \"\u8fd9\u662f\u83b7\u5f97\u5956\u9879\"}, {\"reward_date\": \"2007-08-01\", \"description\": \"\u5956\u52b1\u63cf\u8ff0\uff0c\u5956\u52b1\u63cf\u8ff0\", \"name\": \"\u5956\u52b1\u5956\u52b1\u540d\u79f0\"}, {\"reward_date\": \"2009-11-01\", \"description\": \"\u5956\u52b1\u63cf\u8ff02\", \"name\": \"\u5956\u52b1\u540d\u79f02\"}], \"workexps\": [{\"industry_code\": 1103, \"company\": {\"company_scale\": 1, \"company_introduction\": \"\", \"company_name\": \"\u8fd9\u662f\u516c\u53f8\u540d1\", \"company_property\": 1, \"company_industry_code\": 1103, \"company_industry\": \"\u8ba1\u7b97\u673a\u670d\u52a1(\u7cfb\u7edf\u3001\u6570\u636e\u670d\u52a1\u3001\u7ef4\u4fee)\"}, \"start_date\": \"2005-01-01\", \"industry_name\": \"\u8ba1\u7b97\u673a\u670d\u52a1(\u7cfb\u7edf\u3001\u6570\u636e\u670d\u52a1\u3001\u7ef4\u4fee)\", \"job\": \"\u8fd9\u662f\u804c\u4f4d1\", \"salary_code\": 2, \"end_until_now\": 1, \"salary_type\": \"8\", \"end_date\": null, \"description\": \"\u8fd9\u662f\u516c\u53f81\u5de5\u4f5c\u63cf\u8ff0\u5185\u5bb9\u3002\u3002\u3002\"}, {\"industry_code\": 1503, \"company\": {\"company_scale\": 0, \"company_introduction\": \"\", \"company_name\": \"\u8fd9\u662f\u516c\u53f8\u540d\", \"company_property\": 1, \"company_industry_code\": 1503, \"company_industry\": \"\u52a0\u5de5\u5236\u9020\uff08\u539f\u6750\u6599\u52a0\u5de5/\u78e8\u5177\uff09\"}, \"start_date\": \"2002-02-01\", \"industry_name\": \"\u52a0\u5de5\u5236\u9020\uff08\u539f\u6750\u6599\u52a0\u5de5/\u78e8\u5177\uff09\", \"job\": \"\u8fd9\u662f\u804c\u4f4d\u540d\u79f0\", \"salary_code\": 2, \"end_until_now\": 0, \"salary_type\": \"1\", \"end_date\": \"2006-05-01\", \"description\": \"\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0123\"}], \"import\": {\"source\": \"3\", \"account_id\": \"jamie_qmx@163.com\", \"resume_id\": \"JM652070274R90250000000\", \"user_name\": \"jamie_qmx@163.com\"}, \"educations\": [{\"start_date\": \"2013-12-01\", \"major_name\": \"\u8fd9\u662f\u4e13\u4e1a\u540d\u79f0\", \"end_until_now\": 0, \"college_name\": \"\u8fd9\u662f\u5b66\u6821\u540d\u79f0\", \"end_date\": \"2016-01-01\", \"is_unified\": \"2\", \"degree\": \"7\"}, {\"start_date\": \"2005-01-01\", \"major_name\": \"\u7ba1\u7406\u79d1\u5b66\", \"end_until_now\": 0, \"college_name\": \"\u8fd9\u662f\u5b66\u6821\u540d\u79f01\", \"end_date\": \"2006-01-01\", \"is_unified\": \"1\", \"degree\": \"4\"}], \"skills\": [{\"month\": \"66\u4e2a\u6708\", \"level\": 4, \"name\": \"\u8ba1\u7b97\u673a\uff0fIT\"}, {\"month\": \"44\u4e2a\u6708\", \"level\": 2, \"name\": \"\u751f\u4ea7\u6280\u5de5\"}], \"other\": {\"Nation\": \"\", \"workyears\": \"\", \"schooljob\": [{\"end\": \"2005-02-01\", \"describe\": \"\u8fd9\u662f\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb91\u8fd9\u662f\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb92\u8fd9\u662f\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb93\u8fd9\u662f\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb94\", \"position\": \"\u8fd9\u662f\u5b9e\u8df5\u540d\u79f0\", \"start\": \"2005-01-01\"}, {\"end\": null, \"describe\": \"\u8fd9\u662f\u5728\u6837\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb9\u3002\u3002\u3002\", \"position\": \"\u8fd9\u662f\u5b9e\u8df5\u540d\u79f01\", \"start\": \"2005-01-01\"}], \"companyBrand\": \"\", \"location\": \"\u4e0a\u6d77\", \"internship\": \"\", \"expectedlocation\": \"\", \"cet4\": \"\", \"idnumber\": \"\", \"gmat\": \"\", \"IDPhoto\": \"\", \"height\": \"\", \"workstate\": \"\u6211\u76ee\u524d\u5728\u804c\uff0c\u6b63\u8003\u8651\u6362\u4e2a\u65b0\u73af\u5883\uff08\u5982\u6709\u5408\u9002\u7684\u5de5\u4f5c\u673a\u4f1a\uff0c\u5230\u5c97\u65f6\u95f4\u4e00\u4e2a\u6708\u5de6\u53f3\uff09\", \"ReferenceName\": \"\", \"cet6\": \"\", \"industry\": \"\", \"CollegeContactTel\": \"\", \"residencetype\": \"\", \"trip\": \"\", \"ReferenceRelation\": \"\", \"weight\": \"\", \"icanstart\": \"\", \"reward\": \"\", \"StudentFrom\": \"\", \"workdays\": \"\", \"language\": \"\", \"EmergencyPhone\": \"\", \"PoliticalStatus\": \"\", \"salary\": \"\", \"JapaneseLevel\": \"\", \"expectsalary\": \"\", \"gpa\": \"\", \"Address\": \"\", \"majorrank\": \"\", \"toefl\": \"\", \"CareerGoals\": \"\", \"recentjob\": \"\", \"IsFreshGraduated\": \"\", \"CollegeCity\": \"\", \"nightjob\": \"\", \"competition\": \"\", \"marriage\": \"\", \"gre\": \"\", \"frequency\": \"\", \"EmergencyContact\": \"\", \"residence\": \"\u4e0a\u6d77\", \"ReferenceContact\": \"\", \"ielts\": \"\", \"position\": \"\", \"CollegeContact\": \"\"}, \"projectexps\": [{\"start_date\": \"2015-01-01\", \"name\": \"\u8fd9\u662f\u9879\u76ee\u540d\u79f0\", \"end_until_now\": 0, \"end_date\": \"2016-02-01\", \"description\": \"\u5ba1\u9879\u76ee\u63cf\u8ff01\u5ba1\u9879\u76ee\u63cf\u8ff02\u5ba1\u9879\u76ee\u63cf\u8ff03\u5ba1\u9879\u76ee\u63cf\u8ff04\", \"responsibility\": \"\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23111\"}, {\"start_date\": \"2005-01-01\", \"name\": \"\u8fd9\u662f\u9879\u76ee\u540d\u79f01\", \"end_until_now\": 1, \"end_date\": null, \"description\": \"\u8fd9\u662f\u9879\u76ee\u63cf\u8ff0\u5185\u5bb9\u3002\u3002\u3002\", \"responsibility\": \"\u8fd9\u662f\u9879\u76ee\u4e2d\u804c\u8d23\u5185\u5bb9\u3002\u3002\u3002\"}], \"basic\": {\"iso_code_2\":\"cn\",\"iso_code_3\":\"chn\",\"birth\": \"1989-06-01\", \"self_introduction\": \"\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9123\", \"city_name\": \"\u4e0a\u6d77\", \"gender\": \"2\", \"name\": \"\u6c5f\u95e8\u6613\"}, \"credentials\": [{\"reward_date\": \"2016-01-01\", \"name\": \"\u6258\u798f\"}, {\"reward_date\": \"2016-01-01\", \"name\": \"\u5168\u56fd\u8ba1\u7b97\u673a\u5e94\u7528\u6280\u672f\u8bc1\u4e66\"}, {\"reward_date\": \"2009-01-01\", \"name\": \"\u521d\u7ea7\u5de5\u7a0b\u5e08\"}, {\"reward_date\": \"2016-01-01\", \"name\": \"GRE\"}], \"user\": {\"mobile\": \"15900428801\", \"name\": \"\u6c5f\u95e8\u6613\", \"email\": \"jamie_qmx@163.com\"}, \"attachments\": [], \"intentions\": [{\"industries\": [{\"industry_code\": 1306, \"industry_name\": \"\u5bb6\u5177/\u5bb6\u7535/\u73a9\u5177/\u793c\u54c1\"}], \"salary_type\": 2, \"worktype\": \"1\", \"positions\": [{\"position_code\": \"0\", \"position_name\": \"\u9500\u552e\u7ba1\u7406\u3001\u5ba2\u670d/\u552e\u524d/\u552e\u540e\u6280\u672f\u652f\u6301\u3001\u5e02\u573a\"}], \"salary_code\": \"5\", \"cities\": [{\"city_code\": \"0\", \"city_name\": \"\u5317\u4eac\"}, {\"city_code\": \"0\", \"city_name\": \"\u4e0a\u6d77\"}, {\"city_code\": \"0\", \"city_name\": \"\u5929\u6d25\"}], \"workstate\": \"2\"}], \"languages\": [{\"level\": 4, \"name\": \"\u82f1\u8bed\"}, {\"level\": 2, \"name\": \"\u65e5\u8bed\"}, {\"level\": 2, \"name\": \"\u6cd5\u8bed\"}], \"works\": []}]}";
                break;
            case MAIMAI:
                param.put("unionid", unionid);
                param.put("version", version);
                param.put("token", token);
                param.put("appid", maimai_appid);
                result = fetchResume(JSON.toJSONString(param), propertiesUtils.get("CRAWLER_MAIMAI", String.class));
                //result = "{\"status\": 0, \"resumes\": [{\"awards\": [{\"reward_date\": \"2014-02-01\", \"description\": \"\u8fd9\u662f\u83b7\u5956\u63cf\u8ff01\u8fd9\u662f\u83b7\u5956\u63cf\u8ff02\u8fd9\u662f\u83b7\u5956\u63cf\u8ff03\", \"name\": \"\u8fd9\u662f\u83b7\u5f97\u5956\u9879\"}, {\"reward_date\": \"2007-08-01\", \"description\": \"\u5956\u52b1\u63cf\u8ff0\uff0c\u5956\u52b1\u63cf\u8ff0\", \"name\": \"\u5956\u52b1\u5956\u52b1\u540d\u79f0\"}, {\"reward_date\": \"2009-11-01\", \"description\": \"\u5956\u52b1\u63cf\u8ff02\", \"name\": \"\u5956\u52b1\u540d\u79f02\"}], \"workexps\": [{\"industry_code\": 1103, \"company\": {\"company_scale\": 1, \"company_introduction\": \"\", \"company_name\": \"\u8fd9\u662f\u516c\u53f8\u540d1\", \"company_property\": 1, \"company_industry_code\": 1103, \"company_industry\": \"\u8ba1\u7b97\u673a\u670d\u52a1(\u7cfb\u7edf\u3001\u6570\u636e\u670d\u52a1\u3001\u7ef4\u4fee)\"}, \"start_date\": \"2005-01-01\", \"industry_name\": \"\u8ba1\u7b97\u673a\u670d\u52a1(\u7cfb\u7edf\u3001\u6570\u636e\u670d\u52a1\u3001\u7ef4\u4fee)\", \"job\": \"\u8fd9\u662f\u804c\u4f4d1\", \"salary_code\": 2, \"end_until_now\": 1, \"salary_type\": \"8\", \"end_date\": null, \"description\": \"\u8fd9\u662f\u516c\u53f81\u5de5\u4f5c\u63cf\u8ff0\u5185\u5bb9\u3002\u3002\u3002\"}, {\"industry_code\": 1503, \"company\": {\"company_scale\": 0, \"company_introduction\": \"\", \"company_name\": \"\u8fd9\u662f\u516c\u53f8\u540d\", \"company_property\": 1, \"company_industry_code\": 1503, \"company_industry\": \"\u52a0\u5de5\u5236\u9020\uff08\u539f\u6750\u6599\u52a0\u5de5/\u78e8\u5177\uff09\"}, \"start_date\": \"2002-02-01\", \"industry_name\": \"\u52a0\u5de5\u5236\u9020\uff08\u539f\u6750\u6599\u52a0\u5de5/\u78e8\u5177\uff09\", \"job\": \"\u8fd9\u662f\u804c\u4f4d\u540d\u79f0\", \"salary_code\": 2, \"end_until_now\": 0, \"salary_type\": \"1\", \"end_date\": \"2006-05-01\", \"description\": \"\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0\u8fd9\u662f\u5de5\u4f5c\u63cf\u8ff0123\"}], \"import\": {\"source\": \"3\", \"account_id\": \"jamie_qmx@163.com\", \"resume_id\": \"JM652070274R90250000000\", \"user_name\": \"jamie_qmx@163.com\"}, \"educations\": [{\"start_date\": \"2013-12-01\", \"major_name\": \"\u8fd9\u662f\u4e13\u4e1a\u540d\u79f0\", \"end_until_now\": 0, \"college_name\": \"\u8fd9\u662f\u5b66\u6821\u540d\u79f0\", \"end_date\": \"2016-01-01\", \"is_unified\": \"2\", \"degree\": \"7\"}, {\"start_date\": \"2005-01-01\", \"major_name\": \"\u7ba1\u7406\u79d1\u5b66\", \"end_until_now\": 0, \"college_name\": \"\u8fd9\u662f\u5b66\u6821\u540d\u79f01\", \"end_date\": \"2006-01-01\", \"is_unified\": \"1\", \"degree\": \"4\"}], \"skills\": [{\"month\": \"66\u4e2a\u6708\", \"level\": 4, \"name\": \"\u8ba1\u7b97\u673a\uff0fIT\"}, {\"month\": \"44\u4e2a\u6708\", \"level\": 2, \"name\": \"\u751f\u4ea7\u6280\u5de5\"}], \"other\": {\"Nation\": \"\", \"workyears\": \"\", \"schooljob\": [{\"end\": \"2005-02-01\", \"describe\": \"\u8fd9\u662f\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb91\u8fd9\u662f\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb92\u8fd9\u662f\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb93\u8fd9\u662f\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb94\", \"position\": \"\u8fd9\u662f\u5b9e\u8df5\u540d\u79f0\", \"start\": \"2005-01-01\"}, {\"end\": null, \"describe\": \"\u8fd9\u662f\u5728\u6837\u5b9e\u8df5\u63cf\u8ff0\u5185\u5bb9\u3002\u3002\u3002\", \"position\": \"\u8fd9\u662f\u5b9e\u8df5\u540d\u79f01\", \"start\": \"2005-01-01\"}], \"companyBrand\": \"\", \"location\": \"\u4e0a\u6d77\", \"internship\": \"\", \"expectedlocation\": \"\", \"cet4\": \"\", \"idnumber\": \"\", \"gmat\": \"\", \"IDPhoto\": \"\", \"height\": \"\", \"workstate\": \"\u6211\u76ee\u524d\u5728\u804c\uff0c\u6b63\u8003\u8651\u6362\u4e2a\u65b0\u73af\u5883\uff08\u5982\u6709\u5408\u9002\u7684\u5de5\u4f5c\u673a\u4f1a\uff0c\u5230\u5c97\u65f6\u95f4\u4e00\u4e2a\u6708\u5de6\u53f3\uff09\", \"ReferenceName\": \"\", \"cet6\": \"\", \"industry\": \"\", \"CollegeContactTel\": \"\", \"residencetype\": \"\", \"trip\": \"\", \"ReferenceRelation\": \"\", \"weight\": \"\", \"icanstart\": \"\", \"reward\": \"\", \"StudentFrom\": \"\", \"workdays\": \"\", \"language\": \"\", \"EmergencyPhone\": \"\", \"PoliticalStatus\": \"\", \"salary\": \"\", \"JapaneseLevel\": \"\", \"expectsalary\": \"\", \"gpa\": \"\", \"Address\": \"\", \"majorrank\": \"\", \"toefl\": \"\", \"CareerGoals\": \"\", \"recentjob\": \"\", \"IsFreshGraduated\": \"\", \"CollegeCity\": \"\", \"nightjob\": \"\", \"competition\": \"\", \"marriage\": \"\", \"gre\": \"\", \"frequency\": \"\", \"EmergencyContact\": \"\", \"residence\": \"\u4e0a\u6d77\", \"ReferenceContact\": \"\", \"ielts\": \"\", \"position\": \"\", \"CollegeContact\": \"\"}, \"projectexps\": [{\"start_date\": \"2015-01-01\", \"name\": \"\u8fd9\u662f\u9879\u76ee\u540d\u79f0\", \"end_until_now\": 0, \"end_date\": \"2016-02-01\", \"description\": \"\u5ba1\u9879\u76ee\u63cf\u8ff01\u5ba1\u9879\u76ee\u63cf\u8ff02\u5ba1\u9879\u76ee\u63cf\u8ff03\u5ba1\u9879\u76ee\u63cf\u8ff04\", \"responsibility\": \"\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23\u662f\u8fd9\u6211\u7684\u9879\u76ee\u804c\u8d23111\"}, {\"start_date\": \"2005-01-01\", \"name\": \"\u8fd9\u662f\u9879\u76ee\u540d\u79f01\", \"end_until_now\": 1, \"end_date\": null, \"description\": \"\u8fd9\u662f\u9879\u76ee\u63cf\u8ff0\u5185\u5bb9\u3002\u3002\u3002\", \"responsibility\": \"\u8fd9\u662f\u9879\u76ee\u4e2d\u804c\u8d23\u5185\u5bb9\u3002\u3002\u3002\"}], \"basic\": {\"birth\": \"1989-06-01\", \"self_introduction\": \"\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9\u8fd9\u662f\u81ea\u6211\u8bc4\u4ef7\u5185\u5bb9123\", \"city_name\": \"\u4e0a\u6d77\", \"gender\": \"2\", \"name\": \"\u6c5f\u95e8\u6613\"}, \"credentials\": [{\"reward_date\": \"2016-01-01\", \"name\": \"\u6258\u798f\"}, {\"reward_date\": \"2016-01-01\", \"name\": \"\u5168\u56fd\u8ba1\u7b97\u673a\u5e94\u7528\u6280\u672f\u8bc1\u4e66\"}, {\"reward_date\": \"2009-01-01\", \"name\": \"\u521d\u7ea7\u5de5\u7a0b\u5e08\"}, {\"reward_date\": \"2016-01-01\", \"name\": \"GRE\"}], \"user\": {\"mobile\": \"15900428801\", \"name\": \"\u6c5f\u95e8\u6613\", \"email\": \"jamie_qmx@163.com\"}, \"attachments\": [], \"intentions\": [{\"industries\": [{\"industry_code\": 1306, \"industry_name\": \"\u5bb6\u5177/\u5bb6\u7535/\u73a9\u5177/\u793c\u54c1\"}], \"salary_type\": 2, \"worktype\": \"1\", \"positions\": [{\"position_code\": \"0\", \"position_name\": \"\u9500\u552e\u7ba1\u7406\u3001\u5ba2\u670d/\u552e\u524d/\u552e\u540e\u6280\u672f\u652f\u6301\u3001\u5e02\u573a\"}], \"salary_code\": \"5\", \"cities\": [{\"city_code\": \"0\", \"city_name\": \"\u5317\u4eac\"}, {\"city_code\": \"0\", \"city_name\": \"\u4e0a\u6d77\"}, {\"city_code\": \"0\", \"city_name\": \"\u5929\u6d25\"}], \"workstate\": \"2\"}], \"languages\": [{\"level\": 4, \"name\": \"\u82f1\u8bed\"}, {\"level\": 2, \"name\": \"\u65e5\u8bed\"}, {\"level\": 2, \"name\": \"\u6cd5\u8bed\"}], \"works\": []}]}";
                break;
        }
        logger.info("fetchFirstResume:" + result);
        return cleanning(result, lang, source, completeness, appid, user_id, ua, channelType,form);
    }

    /**
     * 导入次数需要做限制，每人每天只能导入三次
     *
     * @param form 导入参数
     * @return
     */
    private boolean isAllowImport(ImportCVForm form) throws Exception {
        // 检测渠道是否存在
        ChannelType channelType = ChannelType.instaceFromInteger(form.getType());
        if (channelType == null) {
            return false;
        }

        /*
        导入次数需要做限制，每人每天只能导入三次
         */
        int user_id = form.getUser_id();
        StringBuffer sb = new StringBuffer();
        sb.append(user_id).append("_").append(channelType);
        String userIdStr = sb.toString();
        if (!redisClient.isAllowed(PROFILE_IMPORT_UPPER_LIMIT, userIdStr, Constant.PROFILE_IMPORT_UPPER_LIMIT, "1")) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.CRAWLER_SERVICE_IMPORT_UPPER_LIMIT);
        }

        /*
        最佳东方C端导入要检测开关
         */
        if(channelType == ChannelType.VERYEAST) {
            HrCompanyConfDO companyConfDO = companyServices.getCompanyConfById(form.getCompany_id());
            if (companyConfDO == null || companyConfDO.getVeryeastSwitch() == 0){
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.NO_AUTH_IMPORT_VERYEAST_PROFILE);
            }
        }

        return true;
    }

    /**
     * 如果导入失败，减少导入次数
     *
     * @param user_id     用户编号
     * @param channelType 导入渠道
     * @return
     */
    private void decre(int user_id, ChannelType channelType) {

        if (channelType == null) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(user_id);
        sb.append("_");
        sb.append(channelType);
        String userIdStr = sb.toString();
        redisClient.decr(0, PROFILE_IMPORT_UPPER_LIMIT, userIdStr, channelType.name());
    }

    @SuppressWarnings("unchecked")
    private Response cleanning(String result, int lang, int source, int completeness, int appid, int user_id, int ua,
                               ChannelType channelType,ImportCVForm form) {
        Object obj = JSON.parse(result);
        Map<String, Object> messagBean = null;
        if (obj instanceof Map) {
            messagBean = (Map<String, Object>) JSON.parse(result);
        } else if (obj instanceof String) {
            messagBean = (Map<String, Object>) JSON.parse((String) obj);
        }
        /**
         *
         * 状态码  python  alphadog
         * 			1		32001
         * 			2		32002
         * 			3		32003
         * 			-1		32004
         * 			4		32005
         * 			-3		32006
         * 			-5		32007
         * 			5		32010
         * 		    6       32011
         * 		    7       32012
         * 			-2		其他
         * */
        if (messagBean.get("status") != null && (Integer) messagBean.get("status") == 0) {
            List<Map<String, Object>> resumes = (List<Map<String, Object>>) messagBean.get("resumes");
            if (resumes == null || resumes.isEmpty()) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_RESUME_EMPTY);
            }
            Map<String, Object> resume = resumes.get(0);
            if (resume == null || resume.isEmpty()) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_RESUME_EMPTY);
            }
            if (resume.get("profile") != null) {
                Map<String, Object> profile = (Map<String, Object>) resume.get("profile");
                if (lang != 0) {
                    profile.put("lang", lang);
                }
                int sourceResult = createSource(source, appid, ua);
                profile.put("source", sourceResult);
                if (completeness != 0) {
                    profile.put("completeness", completeness);
                }
                if (user_id > 0) {
                    profile.put("user_id", user_id);
                }
            } else {
                Map<String, Object> profile = new HashMap<>();
                profile.put("lang", lang);
                int sourceResult = createSource(source, appid, ua);
                profile.put("source", sourceResult);
                profile.put("completeness", completeness);
                profile.put("user_id", user_id);
                resume.put("profile", profile);
            }
            return ResponseUtils.success(resume);
        } else if (messagBean.get("status") != null && (Integer) messagBean.get("status") == 1) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_USER_NOPERMITION);
        } else if (messagBean.get("status") != null && (Integer) messagBean.get("status") == 2) {
            decre(user_id, channelType);
            return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_IMPORT_FAILED);
        } else if (messagBean.get("status") != null
                && (Integer) messagBean.get("status") == 3) {
            decre(user_id, channelType);
            return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_LOGIN_FAILED);
        } else if (messagBean.get("status") != null
                && (Integer) messagBean.get("status") == 4) {
            decre(user_id, channelType);
            return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_LOGIN2_FAILED);
        } else if (messagBean.get("status") != null
                && (Integer) messagBean.get("status") == 5) {
            decre(user_id, channelType);
            return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_SERVICE_PROFILE_EMPTY);
        } else if (messagBean.get("status") != null
                && (Integer) messagBean.get("status") == 6) {
            if(!isZhilianGetVerifyCode(channelType,form)) {
                decre(user_id, channelType);
            }
            return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_SERVICE_NEED_VERIFY_CODE);
        } else if (messagBean.get("status") != null
                && (Integer) messagBean.get("status") == 7) {
            decre(user_id, channelType);
            return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_SERVICE_ACCOUNT_LIMIT);
        } else if (messagBean.get("status") != null
                && (Integer) messagBean.get("status") == 8) {
            decre(user_id, channelType);
            return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_SERVICE_VERIFY_CODE_WRONG);
        } else if (messagBean.get("status") != null
                && (Integer) messagBean.get("status") == 9) {
            decre(user_id, channelType);
            return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_SERVICE_TIME_OUT);
        } else if (messagBean.get("status") != null
                && (Integer) messagBean.get("status") == 10) {
            decre(user_id, channelType);
            return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_SERVICE_MUCH_PASSWORD_WRONG);
        }
        decre(user_id, channelType);
        return ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_PARAM_ILLEGAL);
    }

    private String fetchResume(String params, String url) throws ConnectException {
        int timeOut = 1 * 60 * 1000;
        return UrlUtil.sendPost(url, params, timeOut, timeOut);
    }

    private int createSource(int source, int appId, int ua) {
        int sourceResult = source;
        if (source != 0) {
            if (ua == 2) {
                sourceResult = Constant.PROFILE_SOURCE_MOBILE_BROWSER;
            }
        } else {
            if (appId == 0) {
                sourceResult = Constant.PROFILE_SOURCE_UNKNOW;
            } else if (appId == 1) {
                sourceResult = Constant.PROFILE_SOURCE_PC_IMPORT;
            } else if (appId == 2) {
                sourceResult = Constant.PROFILE_SOURCE_WEIXIN_TEGETHER_IMPORT;
            } else if (appId == 3) {
                sourceResult = Constant.PROFILE_SOURCE_WEIXIN_COMPANY_IMPORT;
            } else {
                sourceResult = Constant.PROFILE_SOURCE_UNKNOW;
            }
        }

        return sourceResult;
    }

    private boolean isZhilianGetVerifyCode(ChannelType channelType,ImportCVForm form){
        return channelType == ChannelType.ZHILIAN && StringUtils.isNullOrEmpty(form.getCode());
    }
}
