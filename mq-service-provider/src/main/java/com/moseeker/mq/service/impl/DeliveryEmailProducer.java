package com.moseeker.mq.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictConstantDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.profiledb.ProfileBasicDao;
import com.moseeker.baseorm.dao.profiledb.ProfileEducationDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.profiledb.ProfileWorkexpDao;
import com.moseeker.baseorm.db.dictdb.tables.DictConstant;
import com.moseeker.baseorm.db.profiledb.tables.ProfileBasic;
import com.moseeker.baseorm.db.profiledb.tables.ProfileEducation;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.ProfileWorkexp;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import static com.moseeker.common.constants.Constant.CDN_URL;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.MandrillMailListConsumer;
import com.moseeker.entity.ProfileOtherEntity;
import com.moseeker.entity.pojo.profile.info.*;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictConstantDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileBasicDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileEducationDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileWorkexpDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.profile.service.ProfileOtherThriftService;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import java.util.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Created by moseeker on 2017/12/19.
 */
@Service
@CounterIface
public class DeliveryEmailProducer {

    private static Logger logger = LoggerFactory.getLogger(EmailProducer.class);

    @Autowired
    private Environment env;


    @Autowired
    private ProfileProfileDao profileDao;
    @Autowired
    private ProfileBasicDao basicDao;
    @Autowired
    private ProfileEducationDao educationDao;
    @Autowired
    private ProfileWorkexpDao workexpDao;
    @Autowired
    private DictConstantDao dictConstantDao;
    @Autowired
    private HrCompanyDao companyDao;

    @Autowired
    private ProfileOtherEntity profileEntity;

    @Autowired
    private MandrillMailListConsumer mandrillMailListConsumer;

    WholeProfileServices.Iface profileServices = ServiceManager.SERVICE_MANAGER.getService(WholeProfileServices.Iface.class);
    ProfileOtherThriftService.Iface profileOtherService = ServiceManager.SERVICE_MANAGER
            .getService(ProfileOtherThriftService.Iface.class);
    /**
     * 当是简历投递时发送邮件中需要的信息
     * @param company   公司信息
     * @param position  职位信息
     * @param user      候选人对象
     * @return
     */
    public  Map<String, Object> emailBady(HrCompanyDO company, JobPositionDO position, UserUserDO user){

        ProfileProfileDO profileDO = profileDao.getData(new Query.QueryBuilder().where(ProfileProfile.PROFILE_PROFILE.USER_ID.getName(),
                user.getId()).buildQuery());
        ProfileBasicDO basicDO = basicDao.getData(new Query.QueryBuilder().where(ProfileBasic.PROFILE_BASIC.PROFILE_ID.getName(),
                profileDO.getId()).buildQuery());
        List<ProfileWorkexpDO> workexpDOList = workexpDao.getDatas(new Query.QueryBuilder().where(ProfileWorkexp.PROFILE_WORKEXP.PROFILE_ID.getName(),
                profileDO.getId()).orderBy(ProfileWorkexp.PROFILE_WORKEXP.START.getName(), Order.DESC).buildQuery());
        List<ProfileEducationDO> educationDOList = educationDao.getDatas(new Query.QueryBuilder().where(ProfileEducation.PROFILE_EDUCATION.PROFILE_ID.getName(),
                profileDO.getId()).orderBy(ProfileEducation.PROFILE_EDUCATION.START.getName(), Order.DESC).buildQuery());

        //获取学历字典
        List<DictConstantDO> degree = dictConstantDao.getDatas(new Query.QueryBuilder().
                where(DictConstant.DICT_CONSTANT.PARENT_CODE.getName(), Constant.DICT_CONSTANT_DEGREE_USER)
                .orderBy(DictConstant.DICT_CONSTANT.PRIORITY.getName(),Order.ASC).buildQuery());
        //性别字典
        List<DictConstantDO> gender = dictConstantDao.getDatas(new Query.QueryBuilder().
                where(DictConstant.DICT_CONSTANT.PARENT_CODE.getName(), Constant.DICT_CONSTANT_GENDER_USER)
                .orderBy(DictConstant.DICT_CONSTANT.PRIORITY.getName(),Order.ASC).buildQuery());
        //语言字典
        List<DictConstantDO> language = dictConstantDao.getDatas(new Query.QueryBuilder().
                where(DictConstant.DICT_CONSTANT.PARENT_CODE.getName(), Constant.DICT_CONSTANT_LANGUAGE_FRUENCY)
                .orderBy(DictConstant.DICT_CONSTANT.PRIORITY.getName(),Order.ASC).buildQuery());
        Map<String, Object> map = new HashMap<>();
        map.put("company_name", company.getAbbreviation());
        map.put("position_name", position.getTitle());
        //邮件头像默认地址
        map.put("heading", env.getProperty("email.user.heading.url"));
        //hr端人才详情路径
        String resume_url = env.getProperty("email.resume.info.url");
        if(resume_url != null)
            resume_url = resume_url.replace("{}", user.getId()+"");
        map.put("profile_full_url", resume_url);
        if(user != null) {
            if (user.getHeadimg() != null && !user.getHeadimg().isEmpty()) {
                String headImgUrl = user.getHeadimg().trim().startsWith("http")? user.getHeadimg() : CDN_URL+user.getHeadimg();
                map.put("heading", headImgUrl);
            }
            if (user.getName() != null && !user.getName().isEmpty()) {
                map.put("user_name", user.getName());
            } else {
                map.put("user_name", user.getNickname());
            }
        }
        //获取教育信息
        String educations = "";
        if (degree != null && degree.size() > 0 && educationDOList != null && educationDOList.size() > 0) {
            for (DictConstantDO constantDO : degree) {
                if (constantDO.getCode() == educationDOList.get(0).getDegree())
                    educations = constantDO.getName();
            }
            List<Map<String, String>> eduList = new ArrayList<>();
            for(int i = 0 ; i< educationDOList.size(); i++){
                if(i>1){break;}
                ProfileEducationDO education = educationDOList.get(i);
                Map<String, String> educationMap = new HashMap<>();
                educationMap.put("startTime", education.getStartTime().substring(0,7));
                String endTime = "";
                if(1==education.getEndUntilNow() || education.getEndTime()==null || education.getEndTime().length()<7) {
                    endTime = "至今";
                }else{
                    endTime = education.getEndTime().substring(0,7);
                }
                educationMap.put("endTime", endTime);
                educationMap.put("college_name", education.getCollegeName());
                educationMap.put("major_name", education.getCollegeName());
                educationMap.put("degree","");
                for (DictConstantDO constantDO : degree) {
                    if (constantDO.getCode() == education.getDegree())
                        educationMap.put("degree", constantDO.getName());
                }
                eduList.add(educationMap);
            }
            map.put("educationList", eduList);
        }
        map.put("degree_name", educations);
        //获取基本信息
        if(basicDO != null) {
            String profileGender = "";
            if (gender != null && gender.size() > 0) {
                for (DictConstantDO constantDO : gender) {
                    if (constantDO.getCode() == basicDO.getGender())
                        profileGender = constantDO.getName();
                }
            }
            if("未填写".equals(profileGender))
                profileGender = "";
            map.put("gender_name", profileGender);
            map.put("city_name", basicDO.getCityName());
            Date date = new Date();
            String years = DateUtils.formatDate(date, "yyyy");
            map.put("birth", "");
            if(basicDO.getBirth()!=null) {
                String startBirth = basicDO.getBirth().substring(0, 4);
                int birth = Integer.parseInt(years) - Integer.parseInt(startBirth);
                map.put("birth", birth + "岁");
            }
        }
        //获取工作经验
        if(workexpDOList!= null && workexpDOList.size()>0){
            List<Map<String, String>> workList = new ArrayList<>();
            for(int i = 0 ; i< workexpDOList.size(); i++) {
                if(i>2){break;}
                ProfileWorkexpDO workexpDO = workexpDOList.get(i);
                Map<String, String> workMap = new HashMap<>();
                workMap.put("workStartTime", workexpDO.getStartTime().substring(0, 7));
                String endTime = "";
                if (1 == workexpDO.getEndUntilNow() || workexpDO.getEndTime() == null || workexpDO.getEndTime().length()<7) {
                    endTime = "至今";
                } else {
                    endTime = workexpDO.getEndTime().substring(0, 7);
                }
                workMap.put("workEndTime", endTime);
                workMap.put("workJob", workexpDO.getJob());
                HrCompanyDO companyDO = companyDao.getData(new Query.QueryBuilder().where("id",
                        workexpDO.getCompanyId()).buildQuery());
                workMap.put("workCompany", "");
                if (companyDO != null){
                    if(StringUtils.isNotNullOrEmpty(companyDO.getName())){
                        workMap.put("workCompany", companyDO.getName());
                    }else{
                        workMap.put("workCompany", companyDO.getAbbreviation());
                    }
                }
                workList.add(workMap);
            }
            map.put("workList", workList);
        }
        return map;
    }

    /**
     * 当是简历投递时发送邮件中需要的信息
     * @param company   公司信息
     * @param position  职位信息
     * @param user      候选人对象
     * @return
     */
    public ProfileEmailInfo emailBadyV2(HrCompanyDO company, JobPositionDO position, UserUserDO user) throws TException {

        Response response = profileServices.getResource(user.getId(), 0, "");

        String result = response.getData();
        Map<String, Object> data = JSON.parseObject(result, Map.class);

        Response responseOther = profileOtherService.getProfileOtherByPositionNotViewApplication(user.getId(), position.getPublisher(), position.getId());
        String resultOther = responseOther.getData();
        Map<String, Object> otherDatas = JSON.parseObject(resultOther, Map.class);
        //获取学历字典
        List<DictConstantDO> degree = dictConstantDao.getDatas(new Query.QueryBuilder().
                where(DictConstant.DICT_CONSTANT.PARENT_CODE.getName(), Constant.DICT_CONSTANT_DEGREE_USER)
                .orderBy(DictConstant.DICT_CONSTANT.PRIORITY.getName(), Order.ASC).buildQuery());
        //语言字典
        List<DictConstantDO> languageDict = dictConstantDao.getDatas(new Query.QueryBuilder().
                where(DictConstant.DICT_CONSTANT.PARENT_CODE.getName(), Constant.DICT_CONSTANT_LANGUAGE_FRUENCY)
                .orderBy(DictConstant.DICT_CONSTANT.PRIORITY.getName(), Order.ASC).buildQuery());
        ProfileEmailInfo emailInfo = new ProfileEmailInfo();
        emailInfo.setCompanyName(company.getAbbreviation());
        emailInfo.setPositionName(position.getTitle());
        if(StringUtils.isNotNullOrEmpty(company.getLogo())) {
            String logo = company.getLogo().trim().startsWith("http") ? company.getLogo() : env.getProperty("http.cdn.url") + company.getLogo();
            emailInfo.setCompanyLogo(logo);
        }else{
            emailInfo.setCompanyLogo(Constant.COMPANY_LOGO_DEFAULT);
        }
        //邮件头像默认地址
        emailInfo.setHeadimg(env.getProperty("email.user.heading.url"));
        //hr端人才详情路径
        String resume_url = env.getProperty("email.resume.info.url");
        if (resume_url != null)
            resume_url = resume_url.replace("{}", user.getId() + "");
        emailInfo.setResumeLink(resume_url);
        if (user != null) {
            if (user.getHeadimg() != null && !user.getHeadimg().isEmpty()) {
                String headImgUrl = user.getHeadimg().trim().startsWith("http") ? user.getHeadimg() : env.getProperty("http.cdn.url") + user.getHeadimg();
                emailInfo.setHeadimg(headImgUrl);
            }
            if (user.getName() != null && !user.getName().isEmpty()) {
                emailInfo.setUserName(user.getName());
            } else {
                emailInfo.setUserName(user.getNickname());
            }
        }
        BasicInfo basic = new BasicInfo();
        Intention intentions = new Intention();
        if (data != null) {
            if (data.get("basic") != null) {
                Map<String, Object> basicData = (Map<String, Object>) data.get("basic");
                emailInfo.setNationalityName((String) basicData.getOrDefault("nationality_name", ""));
                emailInfo.setGenderName((String) basicData.getOrDefault("gender_name", ""));
                emailInfo.setCityName((String) basicData.getOrDefault("city_name", ""));
                Date date = new Date();
                String years = DateUtils.formatDate(date, "yyyy");
                if (basicData.get("birth") != null) {
                    String startBirth = ((String) basicData.get("birth")).substring(0, 4);
                    int birth = Integer.parseInt(years) - Integer.parseInt(startBirth);
                    emailInfo.setAge(birth + "岁");
                    String birthStr = ((String) basicData.get("birth")).replace("-", ".");
                    basic.setBirth(birthStr);
                }
                emailInfo.setIntroduction((String) basicData.getOrDefault("self_introduction", ""));
                //新增电子邮箱和联系方式字段
                emailInfo.setMobile(basicData.get("mobile") == null ? "" : String.valueOf(basicData.get("mobile")));
                emailInfo.setEmail((String) basicData.getOrDefault("email", ""));
            }
            if (otherDatas != null) {
                profileEntity.updateProfileOther(otherDatas, emailInfo);
            }
            //获取教育信息
            List<Map<String, Object>> educationList = (List<Map<String, Object>>) data.getOrDefault("educations", null);
            if (degree != null && degree.size() > 0 && !StringUtils.isEmptyList(educationList)) {
                for (DictConstantDO constantDO : degree) {
                    if (educationList.get(0).get("degree") != null) {
                        if (constantDO.getCode() == (int) educationList.get(0).get("degree"))
                            basic.setDegree(constantDO.getName());
                    }
                }
                basic.setMajor((String) educationList.get(0).getOrDefault("major_name", ""));
                basic.setCollege((String) educationList.get(0).getOrDefault("college_name", ""));

                List<EduExps> eduList = new ArrayList<>();
                for (int i = 0; i < educationList.size(); i++) {
                    Map<String, Object> education = educationList.get(i);
                    EduExps eduExps = new EduExps();
                    eduExps.setTime(DateUtils.appendTime(education.get("start_date"), education.get("end_date"), education.get("end_until_now")));
                    eduExps.setCollege((String) education.getOrDefault("college_name", ""));
                    eduExps.setMajor((String) education.getOrDefault("major_name", ""));
                    String description = (String) education.getOrDefault("description", "");
                    eduExps.setDescription(mandrillMailListConsumer.replaceHTMLEnterToBr(description));
                    if (education.get("degree") != null) {
                        for (DictConstantDO constantDO : degree) {
                            if (constantDO.getCode() == (int) education.get("degree"))
                                eduExps.setDegree(constantDO.getName());
                        }
                    }
                    eduList.add(eduExps);
                }
                emailInfo.setEduExps(eduList);
            }
            //获取期望信息
            List<Map<String, Object>> intentionList = (List<Map<String, Object>>) data.getOrDefault("intentions", new ArrayList<>());
            if (!StringUtils.isEmptyList(intentionList)) {
                Map<String, Object> intention = intentionList.get(0);
                intentions.setEmployeeType((String) intention.getOrDefault("worktype_name", ""));
                intentions.setMonthSalary((String) intention.getOrDefault("salary_code_name", ""));
                List<Map<String, Object>> cities = (List<Map<String, Object>>) intention.getOrDefault("cities", new ArrayList<>());
                if (!StringUtils.isEmptyList(cities)) {
                    StringBuffer cityName = new StringBuffer();
                    for (Map<String, Object> city : cities) {
                        if (city.get("city_name") != null) {
                            cityName.append(city.get("city_name") + ",");
                        }
                    }
                    intentions.setCity(cityName.substring(0, cityName.length() - 1));
                }
                List<Map<String, Object>> positionsList = (List<Map<String, Object>>) intention.getOrDefault("positions", new ArrayList<>());
                if (!StringUtils.isEmptyList(positionsList)) {
                    StringBuffer positionName = new StringBuffer();
                    for (Map<String, Object> positions : positionsList) {
                        if (positions.get("position_name") != null) {
                            positionName.append(positions.get("position_name") + ",");
                        }
                    }
                    intentions.setJob(positionName.substring(0, positionName.length() - 1));
                }
                intentions.setWorkStatus((String) intention.getOrDefault("workstate_name", ""));
                List<Map<String, Object>> industriesList = (List<Map<String, Object>>) intention.getOrDefault("industries", null);
                if(!StringUtils.isEmptyList(industriesList)){
                    StringBuffer industrieName= new StringBuffer();
                    for(Map<String, Object> industries : industriesList){
                        if(industries.get("industry_name")!=null){
                            industrieName.append(industries.get("industry_name")+",");
                        }
                    }
                    intentions.setIndustry(industrieName.substring(0,industrieName.length()-1));
                }

            }
            emailInfo.setIntention(intentions);

            //获取工作经验
            List<Map<String, Object>> workexpList = (List<Map<String, Object>>) data.getOrDefault("workexps", new ArrayList<>());
            if (!StringUtils.isEmptyList(workexpList)) {
                List<WorkExps> workList = new ArrayList<>();
                basic.setPosition((String) workexpList.get(0).getOrDefault("job", ""));
                for (Map<String, Object> workexp : workexpList) {
                    WorkExps workExps = new WorkExps();
                    workExps.setTime(DateUtils.appendTime(workexp.get("start_date"), workexp.get("end_date"), workexp.get("end_until_now")));
                    String description = (String) workexp.getOrDefault("description", "");
                    workExps.setDescription(mandrillMailListConsumer.replaceHTMLEnterToBr(description));
                    workExps.setCompany((String) workexp.getOrDefault("company_name", ""));
                    workExps.setDepartment((String) workexp.getOrDefault("department_name", ""));
                    workExps.setPosition((String) workexp.getOrDefault("job", ""));
                    workList.add(workExps);
                }
                emailInfo.setWorkExps(workList);
            }
            //获取语言
            List<Map<String, Object>> languageList = (List<Map<String, Object>>) data.getOrDefault("languages", new ArrayList<>());
            if (!StringUtils.isEmptyList(languageList)) {
                List<Languages> languagesList = new ArrayList<>();
                for (Map<String, Object> languageData : languageList) {
                    Languages languages = new Languages();
                    languages.setLanguage((String) languageData.getOrDefault("name", ""));
                    if (languageData.get("level") != null) {
                        for (DictConstantDO constant : languageDict) {
                            if (constant.getCode() == (int) languageData.get("level")) {
                                languages.setLevel(constant.getName());
                            }
                        }
                    }
                    languagesList.add(languages);
                }
                emailInfo.setLanguages(languagesList);
            }

            //获取技能
            List<Map<String, Object>> skillsList = (List<Map<String, Object>>) data.getOrDefault("skills", new ArrayList<>());
            if (!StringUtils.isEmptyList(skillsList)) {
                List<String> strList = new ArrayList<>();
                for (Map<String, Object> skills : skillsList) {
                    strList.add((String) skills.getOrDefault("name", ""));
                }
                emailInfo.setSkills(strList);
            }
            //获取证书
            List<Map<String, Object>> credentialsList = (List<Map<String, Object>>) data.getOrDefault("credentials", new ArrayList<>());
            if (!StringUtils.isEmptyList(credentialsList)) {
                List<String> strList = new ArrayList<>();
                for (Map<String, Object> skills : credentialsList) {
                    strList.add((String) skills.getOrDefault("name", ""));
                }
                emailInfo.setCredentials(strList);
            }
            //获取个人作品
            List<Map<String, Object>> worksList = (List<Map<String, Object>>) data.getOrDefault("works", new ArrayList<>());
            if (!StringUtils.isEmptyList(worksList)) {
                Works works = new Works();
                Map<String, Object> worksData = worksList.get(0);
                works.setCover((String) worksData.getOrDefault("cover", ""));
                works.setUrl((String) worksData.getOrDefault("url", ""));
                String description = (String) worksData.getOrDefault("description", "");
                works.setDescription(mandrillMailListConsumer.replaceHTMLEnterToBr(description));
                emailInfo.setWorks(works);
            }
        }
        emailInfo.setBasicInfo(basic);
        return emailInfo;
    }


    /**
     * 当是邮件投递时发送附件邮件的信息
     * @param company   公司信息
     * @param position  职位信息
     * @param user      候选人对象
     * @return
     */
    public  Map<String, Object> annexEmailBody(HrCompanyDO company, JobPositionDO position, UserUserDO user){

        Map<String, Object> map = new HashMap<>();
        map.put("company_name", company.getAbbreviation());
        map.put("position_name", position.getTitle());
        map.put("heading", env.getProperty("email.user.heading.url"));
        String resume_url = env.getProperty("email.resume.info.url")+"";
        if(resume_url != null)
            resume_url = resume_url.replace("{}", user.getId()+"");
        map.put("profile_full_url", resume_url);
        if(user != null) {
            if (user.getHeadimg() != null && !user.getHeadimg().isEmpty()) {
                String headImgUrl = user.getHeadimg().trim().startsWith("http")? user.getHeadimg() : CDN_URL+user.getHeadimg();
                map.put("heading", headImgUrl);
            }
            if (user.getName() != null && !user.getName().isEmpty()) {
                map.put("user_name", user.getName());
            } else {
                map.put("user_name", user.getNickname());
            }
        }
        return map;
    }
}
