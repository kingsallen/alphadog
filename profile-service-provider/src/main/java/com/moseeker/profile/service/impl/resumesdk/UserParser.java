package com.moseeker.profile.service.impl.resumesdk;

import com.alibaba.fastjson.JSON;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.profile.User;
import com.moseeker.entity.pojo.resume.Result;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.service.impl.resumesdk.iface.AbstractMutiResumeParser;
import com.moseeker.entity.pojo.resume.ResumeParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * todo 需要查找数据库中是否存在，如果存在则补填相对应的信息
 * 简历-用户
 */
@Component
public class UserParser extends AbstractMutiResumeParser<Result, User> {
    Logger logger = LoggerFactory.getLogger(UserParser.class);

    @Override
    public User parseResume(Result result) {
        User user = new User();
        user.setEmail(result.getEmail());
        user.setMobile(result.getPhone());
        user.setName(result.getName());
        logger.info("profileParser getUser:{}", JSON.toJSONString(user));
        return user;
    }

    @Override
    protected List<Result> get(ResumeObj resumeProfile) {
        return new ArrayList<Result>(){{add(resumeProfile.getResult());}};
    }

    @Override
    protected void set(ProfileObj moseekerProfile, List<User> r) {
        if (r != null && r.size() > 0) {
            moseekerProfile.setUser(r.get(0));
        }
    }
}
