package com.moseeker.profile.domain.referral;

import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.Constant.GenderType;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.profile.domain.EmployeeReferralProfileNotice;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by moseeker on 2018/11/22.
 */
@Component
public class EmployeeReferralProfileInformation extends EmployeeReferralProfileApdate {

    @Autowired
    DictCityDao cityDao;

    @Override
    protected void validateReferralInfo(EmployeeReferralProfileNotice profileNotice) {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("推荐人与被推荐人关系", profileNotice.getRelationship());
        validateUtil.addStringLengthValidate("推荐理由文本", profileNotice.getReferralText(), null, 101);
        validateUtil.addSensitiveValidate("推荐理由文本", profileNotice.getReferralText(), null, null);
        if(StringUtils.isNotNullOrEmpty(profileNotice.getEmail())) {
            validateUtil.addRegExpressValidate("邮箱", profileNotice.getEmail(), FormCheck.getEmailExp());
        }
        if(StringUtils.isNotNullOrEmpty(profileNotice.getCompanyBrand())) {
            validateUtil.addStringLengthValidate("就职公司", profileNotice.getCompanyBrand(), null, 200);
        }
        if(StringUtils.isNotNullOrEmpty(profileNotice.getCurrentPosition())) {
            validateUtil.addStringLengthValidate("当前职位", profileNotice.getCurrentPosition(), null, 200);
        }
        String result = validateUtil.validate();
        if (StringUtils.isNotNullOrEmpty(result)) {
            throw ProfileException.validateFailed(result);
        }
    }

    @Override
    protected ProfilePojo getProfilePojo(EmployeeReferralProfileNotice profileNotice) {
        GenderType genderType = GenderType.instanceFromValue(profileNotice.getGender());
        if (genderType == null) {
            genderType = GenderType.Secret;
        }
        ProfilePojo profilePojo = new ProfilePojo();
        ProfileExtUtils.createReferralProfileData(profilePojo);
        ProfileExtUtils.createProfileBasic(profilePojo, genderType);
        ProfileExtUtils.createReferralUser(profilePojo, profileNotice.getName(), profileNotice.getMobile(), profileNotice.getEmail());
        Map<String, Object> params = new HashMap<>();
        if (profileNotice.getDegree()>0) {
            params.put("degree", profileNotice.getDegree());
        }
        if (StringUtils.isNotNullOrEmpty(profileNotice.getCompanyBrand())) {
            params.put("companyBrand", profileNotice.getCompanyBrand());
        }
        if (StringUtils.isNotNullOrEmpty(profileNotice.getCurrentPosition())) {
            params.put("current_position", profileNotice.getCurrentPosition());
        }
        ProfileExtUtils.createReferralProfileOtherData(profilePojo, params);
        if(StringUtils.isNotNullOrEmpty(profileNotice.getCity_name())){
            DictCityDO cityDO = cityDao.getCityByNameOrEname(profileNotice.getCity_name());
            int cityCode = 0;
            if(cityDO != null){
                cityCode = cityDO.getCode();
            }
            ProfileExtUtils.createProfileBasicCity(profilePojo, cityCode, profileNotice.getCity_name());
        }

        return profilePojo;
    }

}
