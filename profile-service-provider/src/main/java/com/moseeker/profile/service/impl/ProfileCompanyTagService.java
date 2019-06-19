package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolHrTalentDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.profile.config.Sender;
import org.aspectj.apache.bcel.util.ClassLoaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zztaiwll on 18/4/19.
 */
@Service
public class ProfileCompanyTagService {

    Logger logger = LoggerFactory.getLogger(ProfileCompanyTagService.class);
    @Autowired
    private TalentpoolHrTalentDao talentpoolHrTalentDao;
    @Autowired
    private ProfileProfileDao profileProfileDao;
    @Autowired
    private Sender sender;
    @Autowired
    private JobApplicationDao jobApplicationDao;

    public void handlerCompanyTag(int profileId){
        handlerCompanyTag(profileId,0);
    }
    public void handlerProfileCompanyTag(int profileId,int userId){
        handlerCompanyTag(profileId,userId);
    }

    public void  handlerCompanyTag(int profileId,int userId){
        if(userId==0){
            userId=this.getUserIdFromProfile(profileId);
            logger.info("======查询得到的user_id是======"+userId);
        }
        handlerCompanyTagByUserId(userId);
    }

    public void  handlerCompanyTagByUserId(int userId){
        if(userId>0){
            boolean flag=this.validateUsertalent(userId);
            logger.debug("handlerCompanyTagTalent handlerCompanyTag flag:{}",flag);
            if(flag){
                Set<Integer> userIdSet=new HashSet<>();
                userIdSet.add(userId);
                Set<Integer> companyIdSet=this.getCompanySetByApplierId(userId);
                logger.debug("handlerCompanyTagTalent handlerCompanyTag companyIdSet:{}",companyIdSet);
                if(!StringUtils.isEmptySet(companyIdSet)){
                    Map<String,Object> message=new HashMap<>();
                    message.put("user_ids",userIdSet);
                    message.put("company_ids",companyIdSet);
                    logger.debug("handlerCompanyTagTalent handlerCompanyTag message:{}",message);
                    sender.send(JSON.toJSONString(message),80000);
                }
            }
        }
    }

    /*
     获取该人投递的公司
     */
    private Set<Integer> getCompanySetByApplierId(int userId){
        Query query=new Query.QueryBuilder().where("applier_id",userId).buildQuery();
        List<JobApplicationRecord> list=jobApplicationDao.getRecords(query);
        Set<Integer> result=new HashSet<>();
        if(!StringUtils.isEmptyList(list)){
            for(JobApplicationRecord jobApplicationRecord:list){
                result.add(jobApplicationRecord.getCompanyId());
            }
        }
        return result;
    }
    /*
     判断这个人是否被收藏
     */
    private boolean validateUsertalent(int userId){
        Query query=new Query.QueryBuilder().where("user_id",userId).buildQuery();
        int count=talentpoolHrTalentDao.getCount(query);
        if(count>0){
            return true;
        }
        return false;
    }
    /*
     根据profiledb.profile_profile.id获取userId
     */
    private int getUserIdFromProfile(int profileId){
        Query query=new Query.QueryBuilder().where("id",profileId).buildQuery();
        Map<String,Object>  map=profileProfileDao.getMap(query);
        if(map!=null&&!map.isEmpty()){
            int userId=(int)map.get("user_id");
            return userId;
        }
        return 0;
    }

}
