package com.moseeker.profile.service.impl.resumefileupload;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.commonservice.utils.ProfileDocCheckTool;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.entity.UserWxEntity;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.profile.User;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.resumefileupload.iface.AbstractResumeFileParser;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.profile.service.impl.vo.FileNameData;
import com.moseeker.profile.service.impl.vo.ProfileDocParseResult;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class UserProfileParser extends AbstractResumeFileParser {

    @Autowired
    UserAccountEntity userAccountEntity;

    @Resource(name = "cacheClient")
    private RedisClient client;

    @Override
    public Map<String, Object> checkUser(Integer id) {
        UserUserRecord userRecord = userAccountEntity.getUserRecordbyId(id);
        if (userRecord == null || userRecord.getId() <= 0) {
            throw ProfileException.PROFILE_USER_NOTEXIST;
        }
        Map<String, Object> map = new HashMap<>(1 >> 4);
        map.put("UserUserRecord", userRecord);
        return map;
    }

    @Override
    protected JSONObject getProfileObject(ProfileObj profileObj, FileNameData fileNameData, ProfileDocParseResult profileDocParseResult) {
        JSONObject jsonObject =  ProfileExtUtils.convertToUserProfileJson(profileObj);
        ProfileExtUtils.createAttachment(jsonObject, fileNameData, Constant.EMPLOYEE_PARSE_PROFILE_DOCUMENT);
        ProfileExtUtils.createUserProfile(jsonObject, profileDocParseResult.getName(), profileDocParseResult.getMobile());
        return jsonObject;
    }

    @Override
    protected String getRedisKey() {
        return KeyIdentifier.WECHAT_UPLOAD_RESUME_FILE.toString();
    }

    @Override
    protected ProfileDocParseResult setProfileDocParseResult(ProfileDocParseResult profileDocParseResult, User user, Integer userId) {
        UserUserRecord userRecord = userAccountEntity.getUserRecordbyId(userId);
        if (userRecord == null || userRecord.getId() <= 0) {
            throw ProfileException.PROFILE_USER_NOTEXIST;
        }
        boolean validMobile = Pattern.compile("[0-9]*").matcher(userRecord.getUsername()).matches();
        if(userRecord.getUsername()!=null&&validMobile){
            profileDocParseResult.setMobile(userRecord.getUsername());
            profileDocParseResult.setMobileeditable(false);
        }else{
            profileDocParseResult.setMobile(user.getMobile());
        }
        profileDocParseResult.setName(user.getName());
        return profileDocParseResult;
    }

}
