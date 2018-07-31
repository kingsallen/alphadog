package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.dao.configdb.ConfigSysCvTplDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.profiledb.*;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ProfileOtherCareerType;
import com.moseeker.common.constants.ProfileOtherIdentityType;
import com.moseeker.common.constants.ProfileOtherSchoolType;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.biz.ProfileCompletenessImpl;
import com.moseeker.entity.biz.ProfileParseUtil;
import com.moseeker.entity.pojo.profile.info.Internship;
import com.moseeker.entity.pojo.profile.info.ProfileEmailInfo;
import com.moseeker.entity.pojo.profile.info.SchoolWork;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysCvTplDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 简历业务
 * Created by jack on 07/09/2017.
 */
@Service
@CounterIface
public class ProfileOtherEntity {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    public void updateProfileOther(Map<String, Object> otherDatas, ProfileEmailInfo emailInfo) {
        List<Map<String, Object>> keyvalueList = (List<Map<String, Object>>) otherDatas.getOrDefault("keyvalues", new ArrayList<>());
        if (!StringUtils.isEmptyList(keyvalueList)) {
            emailInfo.setOtherIdentity(ProfileOtherIdentityType.getMessageList(keyvalueList));
            emailInfo.setOtherCareer(ProfileOtherCareerType.getMessageList(keyvalueList));
            emailInfo.setOtherSchool(ProfileOtherSchoolType.getMessageList(keyvalueList));
        }
        List<Map<String, Object>> internshipList = (List<Map<String, Object>>) otherDatas.getOrDefault("internship", new ArrayList());
        if (!StringUtils.isEmptyList(internshipList)) {
            List<Internship> shipList = new ArrayList<>();
            for (Map<String, Object> internship : internshipList) {
                Internship ship = new Internship();
                ship.setTime(DateUtils.appendTime(internship.get("internshipStart"), internship.get("internshipEnd"), internship.get("internshipEndUntilNow")));
                ship.setCompany((String) internship.getOrDefault("internshipCompanyName", ""));
                ship.setPosition((String) internship.getOrDefault("internshipJob", ""));
                ship.setDepartment((String) internship.getOrDefault("internshipDepartmentName", ""));
                ship.setDescription((String) internship.getOrDefault("internshipDescriptionHidden", ""));
                shipList.add(ship);
            }
            emailInfo.setOtherInternship(shipList);
        }
        List<Map<String, Object>> schooljobList = (List<Map<String, Object>>) otherDatas.getOrDefault("schooljob", new ArrayList());
        if (!StringUtils.isEmptyList(schooljobList)) {
            List<SchoolWork> schoolList = new ArrayList<>();
            for (Map<String, Object> school : schooljobList) {
                SchoolWork ship = new SchoolWork();
                ship.setTime(DateUtils.appendTime(school.get("schooljobStart"), school.get("schooljobEnd"), school.get("schooljobEndUntilNow")));
                ship.setName((String) school.getOrDefault("schooljobJob", ""));
                ship.setDescription((String) school.getOrDefault("schooljobDescriptionHidden", ""));
                schoolList.add(ship);
            }
            emailInfo.setOtherSchoolWork(schoolList);
        }
        emailInfo.setOtherIdPhoto((String) otherDatas.getOrDefault("photo", ""));

    }


    //获取这份简历在该公司下所有申请职位编号
    public void getApplicationByHrAndApplier(int accountId, int userId, List<JobApplicationDO> applicationDOS, List<Integer> updateList) {
        Query companyAccountQuery = new Query.QueryBuilder().where(HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID.getName(), accountId).buildQuery();
        HrCompanyAccountDO companyAccountDO = hrCompanyAccountDao.getData(companyAccountQuery);
        if (companyAccountDO == null)
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        //查询母公司信息
        HrCompanyDO companyDO = this.selectSuperCompany(companyAccountDO.getCompanyId());
        if (companyAccountDO == null)
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        Query applicationQuery = new Query.QueryBuilder().where(JobApplication.JOB_APPLICATION.COMPANY_ID.getName(), companyDO.getId())
                .and(JobApplication.JOB_APPLICATION.APPLIER_ID.getName(), userId).buildQuery();
        List<JobApplicationDO> applicationDOList = jobApplicationDao.getDatas(applicationQuery);
        Query accountQuery = new Query.QueryBuilder().where(UserHrAccount.USER_HR_ACCOUNT.ID.getName(), accountId).buildQuery();
        UserHrAccountDO accountDO = userHrAccountDao.getData(accountQuery);
        if (accountDO == null)
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        if (accountDO.getAccountType() == 0) {
            for (JobApplicationDO applicationDO : applicationDOList) {
                if (applicationDO.getApplyType() == 0 || (applicationDO.getApplyType() == 1 && applicationDO.getEmailStatus() == 0)) {
                    applicationDOS.add(applicationDO);
                    if (((int) applicationDO.getIsViewed()) == 1) {
                        updateList.add(applicationDO.getId());
                    }
                }
            }
        } else {
            Query positionQuery = new Query.QueryBuilder().where(JobPosition.JOB_POSITION.PUBLISHER.getName(), accountDO.getId()).buildQuery();
            List<JobPositionDO> positionDOList = jobPositionDao.getDatas(positionQuery);
            List<Integer> positionIdList = positionDOList.stream().map(m -> m.getId()).collect(Collectors.toList());
            for (JobApplicationDO applicationDO : applicationDOList) {
                if (applicationDO.getApplyType() == 0 || (applicationDO.getApplyType() == 1 && applicationDO.getEmailStatus() == 0)) {
                    applicationDOS.add(applicationDO);
                    if (positionIdList.contains(applicationDO.getPositionId()) && ((int) applicationDO.getIsViewed()) == 1) {
                        updateList.add(applicationDO.getId());
                    }
                }

            }
        }
    }

    public Map<String, Object> handerOtherInfo(Map<String, Object> parentValues){
        Map<String, Object> otherMap = new HashMap<>();
        List<ConfigSysCvTplDO> tplDOList = cvTplDao.findAll();
        //组装所需要的数据结构
        List<Map<String, Object>> otherList = new ArrayList<>();
        Set<Map.Entry<String, Object>> entries = parentValues.entrySet();
        String photo = "";
        for(Map.Entry<String, Object> entry : entries){
            if(entry.getValue() instanceof String) {
                if("IDPhoto".equals(entry.getKey())){
                    photo =  (String) entry.getValue();
                    continue;
                }
            }else if(entry.getValue() instanceof List){
                List infoList= (List)entry.getValue();
                if(infoList!=null && infoList.size()>0) {
                        otherMap.put(entry.getKey(), infoList);
                }
            }
            long end = System.currentTimeMillis();
        }
        otherMap.put("photo", photo);
        parentValues.remove("IDPhoto");
        for(ConfigSysCvTplDO tplDO : tplDOList) {
            String fieldName=tplDO.getFieldName();
            for(String key:parentValues.keySet()){
                if(parentValues.get(key) instanceof String){
                    if(fieldName.equals(key)){
                        Map<String,Object> map=new HashMap<>();
                        map.put("key",tplDO.getFieldTitle());
                        map.put("value",parentValues.get(key));
                        otherList.add(map);
                        break;
                    }
                }else if(parentValues.get(key) instanceof Map){
                    if(fieldName.equals(key)){
                        Map<String, Object> params = (Map<String, Object>)parentValues.get(key);
                        Map<String,Object> map=new HashMap<>();
                        map.put("key",tplDO.getFieldTitle());
                        map.put("value",params.get("name"));
                        otherList.add(map);
                        break;
                    }
                }

            }
        }
        otherMap.put("keyvalues", otherList);
        return otherMap;
    }

    private HrCompanyDO selectSuperCompany(int companyId) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(HrCompany.HR_COMPANY.ID.getName(), companyId);
        HrCompanyDO companyDO = hrCompanyDao.getData(queryBuilder.buildQuery());
        if (companyDO != null && companyDO.getParentId() != 0) {
            companyDO = selectSuperCompany(companyDO.getParentId());
        }
        return companyDO;
    }

    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;

    @Autowired
    private JobPositionDao jobPositionDao;

    @Autowired
    private JobApplicationDao jobApplicationDao;

    @Autowired
    private HrCompanyDao hrCompanyDao;

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Autowired
    private ConfigSysCvTplDao cvTplDao;
}

