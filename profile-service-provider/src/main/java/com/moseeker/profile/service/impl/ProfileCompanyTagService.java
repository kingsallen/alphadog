package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolHrTalentDao;
import com.moseeker.common.util.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zztaiwll on 18/4/19.
 */
@Service
public class ProfileCompanyTagService {
    @Autowired
    private TalentpoolHrTalentDao talentpoolHrTalentDao;
    @Autowired
    private ProfileProfileDao profileProfileDao;

    public void  handlerCompanyTag(int profileId){
        int userId=this.getUserIdFromProfile(profileId);
        if(userId>0){
            boolean flag=this.validateUsertalent(userId);
            if(flag){

            }
        }
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
