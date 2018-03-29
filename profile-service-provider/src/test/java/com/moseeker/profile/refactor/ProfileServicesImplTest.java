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
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;
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
            List<ProfileEducationRecord> result=profileUtils.mapToEducationRecords(mapList);
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
            String json = "{" +
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
            response = profileService.getProfileByApplication(profileApplicationForm);
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

}
