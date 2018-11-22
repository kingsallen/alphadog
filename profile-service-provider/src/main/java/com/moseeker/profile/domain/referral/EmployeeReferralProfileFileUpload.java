package com.moseeker.profile.domain.referral;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.biz.ProfileParseUtil;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.profile.domain.EmployeeReferralProfileNotice;
import com.moseeker.profile.exception.ProfileException;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by moseeker on 2018/11/22.
 */
public class EmployeeReferralProfileFileUpload extends EmployeeReferralProfileApdate {

    @Resource(name = "cacheClient")
    private RedisClient client;

    @Autowired
    ProfileParseUtil profileParseUtil;

    @Override
    protected void validateReferralInfo(EmployeeReferralProfileNotice profileNotice) {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("推荐人与被推荐人关系", profileNotice.getRelationship());
        validateUtil.addStringLengthValidate("推荐理由文本", profileNotice.getReferralText(), null, 101);
        String validateResult = validateUtil.validate();
        if (StringUtils.isNotBlank(validateResult)) {
            throw ProfileException.validateFailed(validateResult);
        }
    }

    @Override
    protected ProfilePojo getProfilePojo(EmployeeReferralProfileNotice profileNotice) {
        String profilePojoStr = client.get(AppId.APPID_ALPHADOG.getValue(),
                KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(), String.valueOf(profileNotice.getEmployeeId()));
        if (StringUtils.isBlank(profilePojoStr)) {
            throw ProfileException.REFERRAL_PROFILE_NOT_EXIST;
        }
        JSONObject jsonObject = JSONObject.parseObject(profilePojoStr);
        ProfilePojo profilePojo = ProfilePojo.parseProfile(jsonObject, profileParseUtil.initParseProfileParam());
        profilePojo.getUserRecord().setName(profileNotice.getName());
        profilePojo.getUserRecord().setMobile(Long.parseLong(profileNotice.getMobile()));
        return profilePojo;
    }

}
