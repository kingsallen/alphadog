package com.moseeker.mq.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictConstantDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.profiledb.ProfileBasicDao;
import com.moseeker.baseorm.dao.profiledb.ProfileEducationDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.profiledb.ProfileWorkexpDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.dictdb.tables.DictConstant;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictConstantDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictConstantPojo;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileBasicDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileEducationDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileWorkexpDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import java.util.*;
import javax.annotation.Resource;
import org.jooq.Condition;
import org.jooq.impl.DSL;
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

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Autowired
    private UserUserDao userDao;
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

    public  Map<String, Object> emailBady(HrCompanyDO company, JobPositionDO position, UserUserDO user){

        ProfileProfileDO profileDO = profileDao.getData(new Query.QueryBuilder().where("user_id",
                user.getId()).buildQuery());
        ProfileBasicDO basicDO = basicDao.getData(new Query.QueryBuilder().where("profile_id",
                profileDO.getId()).buildQuery());
        List<ProfileWorkexpDO> workexpDOList = workexpDao.getDatas(new Query.QueryBuilder().where("profile_id",
                profileDO.getId()).orderBy("start", Order.DESC).buildQuery());
        List<ProfileEducationDO> educationDOList = educationDao.getDatas(new Query.QueryBuilder().where("profile_id",
                profileDO.getId()).orderBy("start", Order.DESC).buildQuery());

        List<DictConstantDO> degree = dictConstantDao.getDatas(new Query.QueryBuilder().
                where("parent_code", Constant.DICT_CONSTANT_DEGREE_USER).orderBy("priority",Order.ASC).buildQuery());
        List<DictConstantDO> gender = dictConstantDao.getDatas(new Query.QueryBuilder().
                where("parent_code", Constant.DICT_CONSTANT_GENDER_USER).orderBy("priority",Order.ASC).buildQuery());
        List<DictConstantDO> language = dictConstantDao.getDatas(new Query.QueryBuilder().
                where("parent_code", Constant.DICT_CONSTANT_LANGUAGE_FRUENCY).orderBy("priority",Order.ASC).buildQuery());
        Map<String, Object> map = new HashMap<>();
        map.put("company_name", company.getAbbreviation());
        map.put("position_name", position.getTitle());
        map.put("heading", env.getProperty("email.user.heading.url"));
        String resume_url = env.getProperty("email.resume.Info.url")+"";
        if(resume_url != null)
            resume_url = resume_url.replace("{}", user.getId()+"");
        map.put("profile_full_url", resume_url);
        if(user != null) {
            if (user.getHeadimg() != null && !user.getHeadimg().isEmpty()) {
                map.put("heading", user.getHeadimg());
            }
            if (user.getName() != null && !user.getName().isEmpty()) {
                map.put("user_name", user.getName());
            } else {
                map.put("user_name", user.getNickname());
            }
        }
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
                if(1==education.getEndUntilNow() || education.getEndTime()==null){
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

        if(workexpDOList!= null && workexpDOList.size()>0){
            List<Map<String, String>> workList = new ArrayList<>();
            for(int i = 0 ; i< workexpDOList.size(); i++) {
                if(i>2){break;}
                ProfileWorkexpDO workexpDO = workexpDOList.get(i);
                Map<String, String> workMap = new HashMap<>();
                workMap.put("workStartTime", workexpDO.getStartTime().substring(0, 7));
                String endTime = "";
                if (1 == workexpDO.getEndUntilNow() || workexpDO.getEndTime() == null) {
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


    public  Map<String, Object> annexEmailBody(HrCompanyDO company, JobPositionDO position, UserUserDO user){

        Map<String, Object> map = new HashMap<>();
        map.put("company_name", company.getAbbreviation());
        map.put("position_name", position.getTitle());
        map.put("heading", env.getProperty("email.user.heading.url"));
        String resume_url = env.getProperty("email.resume.Info.url")+"";
        if(resume_url != null)
            resume_url = resume_url.replace("{}", user.getId()+"");
        map.put("profile_full_url", resume_url);
        if(user != null) {
            if (user.getHeadimg() != null && !user.getHeadimg().isEmpty()) {
                map.put("heading", user.getHeadimg());
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
