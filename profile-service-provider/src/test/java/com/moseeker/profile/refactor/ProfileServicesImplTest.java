package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.moseeker.baseorm.db.logdb.tables.records.LogResumeRecordRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.biz.ProfileUtils;
import com.moseeker.entity.pojo.profile.Education;
import com.moseeker.entity.pojo.resume.EducationObj;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.service.impl.ProfileService;
import com.moseeker.profile.thrift.ProfileOtherThriftServiceImpl;
import com.moseeker.profile.thrift.ProfileProjectExpServicesImpl;
import com.moseeker.profile.thrift.ProfileServicesImpl;
import com.moseeker.profile.utils.DegreeSource;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Profile;

import com.moseeker.thrift.gen.profile.struct.ProfileApplicationForm;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ProfileServicesImplTest {
    Logger logger= LoggerFactory.getLogger(ProfileServicesImplTest.class);

    @Autowired
    ProfileServicesImpl service;

    @Autowired
    ProfileService profileService;

    @Autowired
    private ProfileEntity profileEntity;

    @Autowired
    ProfileUtils profileUtils;

    Object response;

    @Test
    public void getProfileByApplicationTest() throws TException {
        String arg="{\"ats_status\":1,\"company_id\":39978,\"conditions\":{\"apply_start\":\"2017-11-11 00:00:00\",\"company_id\":\"39978\",\"dl_url_required\":\"true\",\"apply_stop\":\"2018-02-09 23:59:59\",\"ats_status\":\"1\",\"filter\":\"{}\",\"appid\":\"1\",\"source_id\":\"10\",\"page\":\"1\",\"recommender\":\"true\",\"page_size\":\"50\"},\"conditionsSize\":11,\"dl_url_required\":true,\"filter\":{},\"filterSize\":0,\"recommender\":true,\"setAts_status\":true,\"setCompany_id\":true,\"setConditions\":true,\"setDl_url_required\":true,\"setFilter\":true,\"setRecommender\":true,\"setSource_id\":true,\"source_id\":10}";

        JSONObject obj=JSON.parseObject(arg);

        ProfileApplicationForm form = JSON.toJavaObject(obj,ProfileApplicationForm.class);

        service.getProfileByApplication(form);
    }

    @After
    public void printResponse() {
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void getResumeObj() throws TException, IOException {
        String file="C:\\Users\\xym-moseeker\\Desktop\\ResumeObj.txt";
        try(BufferedReader br=new BufferedReader(new FileReader(file))){

            StringBuilder sb=new StringBuilder();
            String temp=null;
            while( (temp=br.readLine()) !=null){
                sb.append(temp);
            }

            ParserConfig.getGlobalInstance().setAsmEnable(false);
            ResumeObj resumeObj = JSON.parseObject(sb.toString(),ResumeObj.class);

            int uid=1;

            String fileName="";

            logger.info("profileParser resumeObj.getResult().getEducation_objs():{}", JSON.toJSONString(resumeObj.getResult().getEducation_objs()));
            // 教育经历
            List<Education> educationList = new ArrayList<>();
            List<Map<String,Object>> mapList=new ArrayList<>();
            if (resumeObj.getResult().getEducation_objs() != null && resumeObj.getResult().getEducation_objs().size() > 0) {
                for (EducationObj educationObj : resumeObj.getResult().getEducation_objs()) {
                    Education education = new Education();
                    if (educationObj.getEdu_degree() != null) {
                        if (DegreeSource.intToEnum.get(educationObj.getEdu_degree()) != null) {
                            education.setDegree(DegreeSource.intToEnum.get(educationObj.getEdu_degree()));
                        } else {
                            education.setDegree(0);
                        }
                    }
                    try {
                        education.setStartDate(DateUtils.dateRepair(educationObj.getStart_date(), "\\."));
                        if (educationObj.getEnd_date() != null && educationObj.getEnd_date().equals("至今")) {
                            education.setEndUntilNow(1);
                        } else {
                            education.setEndDate(DateUtils.dateRepair(educationObj.getEnd_date(), "\\."));
                        }
                    } catch (ParseException e) {
                        LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                        logResumeRecordRecord.setErrorLog("日期转换异常: " + e.getMessage());
                        logResumeRecordRecord.setFieldValue("education: {\"startDate\": " + educationObj.getStart_date() + "\", \"endDate\":" + educationObj.getEnd_date()+"}");
                        logResumeRecordRecord.setUserId(uid);
                        logResumeRecordRecord.setFileName(fileName);
                        logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
//                        resumeDao.addRecord(logResumeRecordRecord);
                        logger.error(e.getMessage(), e);
                    }
                    // 学校名称
                    education.setCollegeName(educationObj.getEdu_college());
                    // 专业名称
                    education.setMajorName(educationObj.getEdu_major());
                    if (StringUtils.isNotNullOrEmpty(educationObj.getEdu_recruit())) {
                        education.setIsUnified(educationObj.getEdu_recruit().equals("统招") ? 1 : 2);
                    }
                    educationList.add(education);
                    mapList.add(JSON.parseObject(JSON.toJSONString(education)));
                }

            }
            System.out.println(JSON.toJSONString(educationList));
//            List<ProfileEducationRecord> result=profileUtils.mapToEducationRecords(mapList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*File file=new File("C:\\Users\\xym-moseeker\\Downloads\\00229e9e-dcd5-11e7-b9b2-00163e00307d.doc");
        DataInputStream dis=new DataInputStream(new FileInputStream(file));
        // available stream to be read
        int length = dis.available();

        // create buffer
        byte[] buf = new byte[length];

        dis.readFully(buf);

        String fileStr = new String(Base64.encodeBase64(buf), Consts.UTF_8);
        String fileName=file.getName();
        ResumeObj resumeObj = profileEntity.profileParser(fileName, fileStr);
        System.out.println("profileParser resumeObj:"+JSON.toJSONString(resumeObj));*/
    }


    ////@Test
    public void getResource() throws TException {
        try {
            CommonQuery commonQuery = new CommonQuery();
            commonQuery.setEqualFilter(new HashMap<String, String>() {{
                put("id", "170");
            }});
            response = service.getResource(commonQuery);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void getResources() throws TException {
        try {
            CommonQuery commonQuery = new CommonQuery();
            commonQuery.setEqualFilter(new HashMap<String, String>() {{
                put("completeness", "100");
            }});
            response = service.getResources(commonQuery);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void getPagination() throws TException {
        try {
            CommonQuery commonQuery = new CommonQuery();
            commonQuery.setEqualFilter(new HashMap<String, String>() {{
                put("completeness", "80");
            }});
            response = service.getPagination(commonQuery);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void postResource() throws TException {
        try {
            //165890	5d311bd8-53fa-4064-8324-eac6299675d4	1	0	80	2113186	1	2017-05-15 19:30:03	2017-05-15 19:29:47	1000000000000000
            Profile profile = new Profile();
            profile.setUuid("5d311bd8-53fa-4064-8324-eac6299675d4");
            profile.setLang(1);
            profile.setSource(0);
            profile.setCompleteness(80);
            profile.setUser_id(2113186);
            profile.setDisable(new Integer(1).shortValue());
            response = service.postResource(profile);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void postResources() throws TException {
        try {
            Profile profile = new Profile();
            profile.setUuid("5d311bd8-53fa-4064-8324-eac6299675d4");
            profile.setLang(1);
            profile.setSource(0);
            profile.setCompleteness(80);
            profile.setUser_id(2113186);
            profile.setDisable(new Integer(1).shortValue());
            response = service.postResources(new ArrayList<Profile>() {{
                add(profile);
            }});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void getCompleteness() throws TException {
        try {
            response = service.getCompleteness(2113186, null, 165992);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void reCalculateUserCompleteness() throws TException {
        try {
            response = service.reCalculateUserCompleteness(2, null);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void reCalculateUserCompletenessBySettingId() throws TException {
        try {
            response = service.reCalculateUserCompletenessBySettingId(2);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Test
    public void getProfileByApplication() throws TException {
        try {
            String json = "{\"ats_status\":1,\"company_id\":39978,\"conditions\":{\"apply_start\":\"2018-01-04 00:00:00\",\"filter\":\"{}\",\"company_id\":\"39978\",\"dl_url_required\":\"true\",\"apply_stop\":\"2018-04-04 23:59:59\",\"appid\":\"1\",\"ats_status\":\"1\",\"source_id\":\"10\",\"page\":\"1\",\"recommender\":\"true\",\"page_size\":\"50\"},\"conditionsSize\":11,\"dl_url_required\":true,\"filter\":{},\"filterSize\":0,\"recommender\":true,\"setAts_status\":true,\"setCompany_id\":true,\"setConditions\":true,\"setDl_url_required\":true,\"setFilter\":true,\"setRecommender\":true,\"setSource_id\":true,\"source_id\":10}";
            ProfileApplicationForm profileApplicationForm = JSON.parseObject(json,ProfileApplicationForm.class);
            profileService.getProfileByApplication(profileApplicationForm);
            /*String json = "{" +
                    "  \"ats_status\": 0," +
                    "  \"company_id\": 39978," +
                    "  \"conditions\": {" +
                    "    \"page\": \"1\"," +
                    "    \"page_size\": \"1000\"" +
                    "  }," +
                    "  \"dl_url_required\": true," +
                    "  \"filter\": {" +
                    "    " +
                    "  }," +
                    "  \"filterSize\": 0," +
                    "  \"recommender\": true," +
                    "  \"source_id\": 10," +
                    "  \"appid\": 4" +
                    "}";
            ProfileApplicationForm profileApplicationForm = JSON.parseObject(json,ProfileApplicationForm.class);
            response = profileService.getProfileByApplication(profileApplicationForm);*/
//            String json = "{\"ats_status\":1,\"company_id\":39978,\"conditions\":{\"apply_start\":\"2018-01-04 00:00:00\",\"filter\":\"{}\",\"company_id\":\"39978\",\"dl_url_required\":\"true\",\"apply_stop\":\"2018-04-04 23:59:59\",\"appid\":\"1\",\"ats_status\":\"1\",\"source_id\":\"10\",\"page\":\"1\",\"recommender\":\"true\",\"page_size\":\"50\"},\"conditionsSize\":11,\"dl_url_required\":true,\"filter\":{},\"filterSize\":0,\"recommender\":true,\"setAts_status\":true,\"setCompany_id\":true,\"setConditions\":true,\"setDl_url_required\":true,\"setFilter\":true,\"setRecommender\":true,\"setSource_id\":true,\"source_id\":10}";
//            ProfileApplicationForm profileApplicationForm = JSON.parseObject(json,ProfileApplicationForm.class);
//            profileService.getProfileByApplication(profileApplicationForm);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void putResources() throws TException {
        try {
            Profile profile = new Profile();
            profile.setId(165992);
            profile.setUuid("5d311bd8-53fa-4064-8324-eac6299675d4----");
            response = service.putResources(new ArrayList<Profile>() {{
                add(profile);
            }});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void putResource() throws TException {
        try {
            Profile profile = new Profile();
            profile.setId(165992);
            profile.setUuid("5d311bd8-53fa-4064-8324-eac6299675d4");
            response = service.putResource(profile);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void delResources() throws TException {
        try {
            Profile profile = new Profile();
            profile.setId(165992);
            response = service.delResources(new ArrayList<Profile>() {{
                add(profile);
            }});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void delResource() throws TException {
        try {
            Profile profile = new Profile();
            profile.setId(165993);
            response = service.delResource(profile);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

//    @Test
    public void getProfileOtherTest() {
        Response response = profileService.getProfileOther("[{'positionId': 134299, 'profileId': 64}]");
        System.out.println(response);
    }

//    @Test
    public void profileCheckTest() {
        Response response = profileService.checkProfileOther(676202,11896);
        System.out.println(response);
    }

//    @Test
    public void profileParserTest() {
        try {
            Response response = profileService.profileParser(1707240, "77559bba-ad7d-11e7-9d40-00163e000f58.pdf", "");
            System.out.println(response);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void otherFieldCheckTest() {
        Response response = profileService.otherFieldsCheck(985, "{\"salary\": 0, \"residence\": \"天水\", \"gmat\": \"\", \"PoliticalStatus\": \"共青团员\", \"majorrank\": \"5%\", \"height\": \"184\", \"idnumber\": \"62052319920207199X\", \"gre\": \"\", \"ielts\": \"\", \"location\": \"兰州\", \"secondexpectedlocation\": \"深圳\", \"marriage\": \"未婚\", \"schooljob\": [{\"schooljob_end_until_now\": 1, \"schooljob_description_hidden\": \"主要负责横向课题\", \"schooljob_end\": null, \"schooljob_start\": \"2016-11-05\", \"schooljob_job\": \"项目负责人\"}], \"workyears\": \"1\", \"toefl\": \"\"}");
        System.out.println(response);
    }

    @Test
    public void handlerParseData(){
        String json = "{\"account\":{\"uid\":1707240,\"usage_limit\":60300,\"usage_remaining\":46870},\"eval\":{\"salary\":18800},\"result\":{\"age\":\"35\",\"cert_objs\":[],\"cont_basic_info\":\"basic information:\\nenglish name: siqun yan\\nchinese name: 闫斯群\\ngender: male\\nage: 35\\nphone number: 13022222222\\nwechat:frank20170101\\nqq: 156574951\\nemail address: ysq@126.com\\nlocation: shanghai\",\"cont_job_exp\":\"11/2012 – present\\ncompany name: yum!\\nposition: qa supervisor\\nresponsibility:\\n· responsible for coordinating and managing the audit coordination of the east china audit team (12 subordinates) totally 1300 restaurants, as well as the relevant questions related to the audit, feedback and response related issues with the regionalqa departments, and providing relevant data to the corresponding department and so on.\\n· audit scope: kfc in shanghai and hangzhou, phdi in eastern china\\n· supports operation department tomanage daily food safety work, develop special projects, communicate with suppliers, and provide food safety support and training for franchisees and direct restaurants.\\n07/2010 – 11/2012\\ncompany name: silliker testing service(shanghai)co.,ltd.\\nposition: food safety auditor\\nresponsibility:\\n· good hygiene practice audit(ghp)\\n· conducted food safety and hygiene audit on the food department of retailers, hotel chains and catering service industry and following the national food safety laws/regulations and the requirement from the clients (carrefour, ez-mart, ikea, accor, starwood,compass, sodexo)..\\n· verified and checked the staffs’ operation/document control/labelcontrol during raw material reception, inspection, storage, processing and display/selling, pointed out the non-conformities that not comply with sop, helped the operator to correct the deviation, completed audit report according to the requirement of clients and followed up the corrective action plan, provide necessary training for staffs.\\n· good manufacturing practice audit(gmp)\\n· conducted food safety and hygiene audit on the food suppliers of ikea and food retailers following the national food safety laws/regulations and the requirement from the clients (carrefour), completed audit reports and assess the supplier’s capability of food safety management.\\n· good agriculture practice audit(gap)\\n· be responsible for gap audit of mcdonald’s\\n· others: inspection\",\"education_objs\":[{\"edu_college\":\"Jiangnan University\",\"edu_college_type\":\"0\",\"edu_degree\":\"Master\",\"edu_degree_norm\":\"Master\",\"edu_major\":\"Meng Food Engineering\",\"end_date\":\"至今\",\"start_date\":\"2015.09\"},{\"edu_degree\":\"Bachelor\",\"edu_degree_norm\":\"Bachelor\",\"edu_major\":\"Food And Oil Engineer\",\"end_date\":\"2008.07\",\"start_date\":\"2004.09\"}],\"email\":\"ysq@126.com\",\"gender\":\"male\",\"job_exp_objs\":[{\"end_date\":\"至今\",\"job_content\":\"company name: yum!\\nresponsibility:\\n· responsible for coordinating and managing the audit coordination of the east china audit team (12 subordinates) totally 1300 restaurants, as well as the relevant questions related to the audit, feedback and response related issues with the regionalqa departments, and providing relevant data to the corresponding department and so on.\\n· supports operation department tomanage daily food safety work, develop special projects, communicate with suppliers, and provide food safety support and training for franchisees and direct restaurants.\",\"job_cpy\":\"KFC\",\"job_position\":\"QA Supervisor\",\"start_date\":\"2012.11\"},{\"end_date\":\"2012.11\",\"job_content\":\"position: food safety auditor\\nresponsibility:\\n· good hygiene practice audit(ghp)\\n· conducted food safety and hygiene audit on the food department of retailers, hotel chains and catering service industry and following the national food safety laws/regulations and the requirement from the clients (carrefour, ez-mart, ikea, accor, starwood,compass, sodexo)..\\n· verified and checked the staffs’ operation/document control/labelcontrol during raw material reception, inspection, storage, processing and display/selling, pointed out the non-conformities that not comply with sop, helped the operator to correct the deviation, completed audit report according to the requirement of clients and followed up the corrective action plan, provide necessary training for staffs.\\n· good manufacturing practice audit(gmp)\\n· conducted food safety and hygiene audit on the food suppliers of ikea and food retailers following the national food safety laws/regulations and the requirement from the clients (carrefour), completed audit reports and assess the supplier’s capability of food safety management.\\n· good agriculture practice audit(gap)\\n· be responsible for gap audit of mcdonald’s\\n· others: inspection\",\"job_cpy\":\"Silliker Testing Service(Shanghai)Co.,Ltd\",\"job_position\":\"Testing\",\"start_date\":\"2010.07\"}],\"lang_objs\":[],\"living_address\":\"shanghai\",\"name\":\"Siqun Yan\",\"phone\":\"13022222222\",\"proj_exp_objs\":[],\"raw_text\":\"basic information:\\nenglish name: siqun yan\\nchinese name: 闫斯群\\ngender: male\\nage: 35\\nphone number: 13022222222\\nwechat:frank20170101\\nqq: 156574951\\nemail address: ysq@126.com\\nlocation: shanghai\\nworking experience:\\n11/2012 – present\\ncompany name: yum!\\nposition: qa supervisor\\nresponsibility:\\n· responsible for coordinating and managing the audit coordination of the east china audit team (12 subordinates) totally 1300 restaurants, as well as the relevant questions related to the audit, feedback and response related issues with the regionalqa departments, and providing relevant data to the corresponding department and so on.\\n· audit scope: kfc in shanghai and hangzhou, phdi in eastern china\\n· supports operation department tomanage daily food safety work, develop special projects, communicate with suppliers, and provide food safety support and training for franchisees and direct restaurants.\\n07/2010 – 11/2012\\ncompany name: silliker testing service(shanghai)co.,ltd.\\nposition: food safety auditor\\nresponsibility:\\n· good hygiene practice audit(ghp)\\n· conducted food safety and hygiene audit on the food department of retailers, hotel chains and catering service industry and following the national food safety laws/regulations and the requirement from the clients (carrefour, ez-mart, ikea, accor, starwood,compass, sodexo)..\\n· verified and checked the staffs’ operation/document control/labelcontrol during raw material reception, inspection, storage, processing and display/selling, pointed out the non-conformities that not comply with sop, helped the operator to correct the deviation, completed audit report according to the requirement of clients and followed up the corrective action plan, provide necessary training for staffs.\\n· good manufacturing practice audit(gmp)\\n· conducted food safety and hygiene audit on the food suppliers of ikea and food retailers following the national food safety laws/regulations and the requirement from the clients (carrefour), completed audit reports and assess the supplier’s capability of food safety management.\\n· good agriculture practice audit(gap)\\n· be responsible for gap audit of mcdonald’s\\n· others: inspection\\neducation background\\n09/2015-present\\nschool: jiangnan university\\nmajor: meng food engineering\\ndegree: master\\n09/2004-07/2008\\njilin agriculture university\\nmajor: food and oil engineer\\ndegree: bachelor\",\"resume_integrity\":\"54\",\"resume_type\":\"1\",\"skills_objs\":[],\"training_objs\":[],\"work_company\":\"KFC\",\"work_position\":\"QA Supervisor\",\"work_start_time\":\"2010.07\",\"work_year\":\"8.1\"},\"status\":{\"code\":200,\"message\":\"success\"},\"tags\":{\"industries\":[],\"pos_tags\":[{\"tag_name\":\"qa supervisor\",\"tag_weight\":1.0},{\"tag_name\":\"testing\",\"tag_weight\":0.2},{\"tag_name\":\"saas测试工程师\",\"tag_weight\":0.03462491154670715},{\"tag_name\":\"广告测试工程师\",\"tag_weight\":0.03240877032279968},{\"tag_name\":\"解决方案测试工程师\",\"tag_weight\":0.0321308434009552},{\"tag_name\":\"互联网金融测试工程师\",\"tag_weight\":0.03163147687911987},{\"tag_name\":\"自动化测试工具开发工程师\",\"tag_weight\":0.031076688766479493},{\"tag_name\":\"windows桌面开发工程师\",\"tag_weight\":0.030984613895416263},{\"tag_name\":\"微博搜索测试开发工程师\",\"tag_weight\":0.030744280815124512},{\"tag_name\":\"自动化工具开发工程师\",\"tag_weight\":0.030435440540313722}],\"pos_types\":[{\"tag_name\":\"软件工程师\",\"tag_weight\":0.21014514684677124},{\"tag_name\":\"通信技术工程师\",\"tag_weight\":0.0321308434009552},{\"tag_name\":\"高级软件工程师\",\"tag_weight\":0.01014514684677124},{\"tag_name\":\"系统工程师\",\"tag_weight\":0.01014514684677124}],\"skills_tags\":[{\"tag_name\":\"food\",\"tag_weight\":0.5522284757863642},{\"tag_name\":\"east china\",\"tag_weight\":0.4046170434532245},{\"tag_name\":\"assess\",\"tag_weight\":0.18719549733282825},{\"tag_name\":\"auditor\",\"tag_weight\":0.17833548665761464},{\"tag_name\":\"operator\",\"tag_weight\":0.17637356374174096},{\"tag_name\":\"document control\",\"tag_weight\":0.17295277990309324},{\"tag_name\":\"reception\",\"tag_weight\":0.17267606507436606},{\"tag_name\":\"storage\",\"tag_weight\":0.16993468678279158}]}}";
        ResumeObj resumeObj = JSON.parseObject(json,ResumeObj.class);
        //System.out.println(JSON.toJSONString(profileService.handlerParseData(resumeObj,123,"lalala")));
    }
}
