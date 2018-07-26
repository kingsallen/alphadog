package com.moseeker.profile.service.impl.resumesdk;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.profile.User;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.service.impl.resumesdk.iface.IResumeParser;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 简历-用户
 */
@Component
public class UserParser implements IResumeParser {
    Logger logger = LoggerFactory.getLogger(UserParser.class);

    @Autowired
    protected UserUserDao userDao;

    @Override
    public ProfileObj parseResume(ProfileObj moseekerProfile, ResumeObj resumeProfile, int uid, String fileName) {
        // 查询
        UserUserRecord userUser = userDao.getUserById(uid);
        if (userUser != null) {
            User user = new User();
            user.setEmail(userUser.getEmail());
            user.setMobile(String.valueOf(userUser.getMobile()));
            user.setUid(String.valueOf(uid));
            user.setName(userUser.getName());
            moseekerProfile.setUser(user);
        }else{
            User user = new User();
            user.setEmail(resumeProfile.getResult().getEmail());
            user.setMobile(resumeProfile.getResult().getPhone());
            if(uid!=0){
                user.setUid(String.valueOf(uid));
            }
            user.setName(resumeProfile.getResult().getName());
            moseekerProfile.setUser(user);
        }
        logger.info("profileParser getUser:{}", JSON.toJSONString(moseekerProfile.getUser()));
        return moseekerProfile;
    }
}
