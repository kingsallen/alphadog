package com.moseeker.profile.service.impl.resumefileupload;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.commonservice.utils.ProfileDocCheckTool;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.profile.User;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.resumefileupload.iface.AbstractResumeFileParser;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.profile.service.impl.vo.FileNameData;
import com.moseeker.profile.service.impl.vo.ProfileDocParseResult;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ReferralProfileParser extends AbstractResumeFileParser {

    @Autowired
    EmployeeEntity employeeEntity;

    @Override
    protected boolean checkUser(Integer id) {
        UserEmployeeDO employeeDO = employeeEntity.getEmployeeByID(id);
        if (employeeDO == null || employeeDO.getId() <= 0) {
            throw ProfileException.PROFILE_EMPLOYEE_NOT_EXIST;
        }
        return true;
    }

    @Override
    protected JSONObject getProfileObject(ProfileObj profileObj, FileNameData fileNameData, ProfileDocParseResult profileDocParseResult) {
        JSONObject jsonObject =  ProfileExtUtils.convertToReferralProfileJson(profileObj);
        ProfileExtUtils.createAttachment(jsonObject, fileNameData, Constant.USER_PARSE_PROFILE_DOCUMENT);
        ProfileExtUtils.createReferralUser(jsonObject, profileDocParseResult.getName(), profileDocParseResult.getMobile());
        return jsonObject;
    }
    @Override
    protected String getRedisKey() {
        return KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString();
    }

    @Override
    protected ProfileDocParseResult setProfileDocParseResult(ProfileDocParseResult profileDocParseResult, User user, Integer userId) {
        profileDocParseResult.setMobile(user.getMobile());
        profileDocParseResult.setName(user.getName());
        return profileDocParseResult;
    }


}