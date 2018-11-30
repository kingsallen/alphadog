package com.moseeker.profile.domain.referral;

import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.Constant.GenderType;
import com.moseeker.entity.ProfileOtherEntity;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.entity.pojos.RequireFieldInfo;
import com.moseeker.profile.domain.EmployeeReferralProfileNotice;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by moseeker on 2018/11/22.
 */
@Component
public class EmployeeReferralProfileInformation extends EmployeeReferralProfileApdate {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeReferralProfileInformation.class);
    @Autowired
    DictCityDao cityDao;

    @Autowired
    ProfileOtherEntity otherEntity;

    @Override
    protected void validateReferralInfo(EmployeeReferralProfileNotice profileNotice) {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredOneValidate("推荐理由", profileNotice.getReferralReasons());
        if(StringUtils.isNotNullOrEmpty(profileNotice.getEmail())) {
            validateUtil.addRegExpressValidate("邮箱", profileNotice.getEmail(), FormCheck.getEmailExp());
        }
        if(StringUtils.isNotNullOrEmpty(profileNotice.getCompanyBrand())) {
            validateUtil.addStringLengthValidate("就职公司", profileNotice.getCompanyBrand(), null, 200);
        }
        if(StringUtils.isNotNullOrEmpty(profileNotice.getCurrentPosition())) {
            validateUtil.addStringLengthValidate("当前职位", profileNotice.getCurrentPosition(), null, 200);
        }
        logger.info("validateReferralInfo position:{}", profileNotice.getPositionIds().get(0));
        RequireFieldInfo info  = otherEntity.fetchRequireField(profileNotice.getPositionIds().get(0));
        logger.info("validateReferralInfo info:{}", info);
        if(info.isCity()){
            validateUtil.addRequiredValidate("现居住城市", profileNotice.getCity_code());
        }
        if(info.isCompanyName()){
            validateUtil.addRequiredValidate("最近工作的公司/品牌", profileNotice.getCompanyBrand());
        }
        if(info.isDegree()){
            validateUtil.addRequiredValidate("最高学历", profileNotice.getDegree());
        }
        if(info.isPosition()){
            validateUtil.addRequiredValidate("最近职位", profileNotice.getCurrentPosition());
        }
        if(info.isEmail()){
            validateUtil.addRequiredValidate("邮箱", profileNotice.getEmail());
        }
        if(info.isGender()){
            validateUtil.addRequiredValidate("性别", profileNotice.getGender());
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
        if(profileNotice.getCity_code()>0){
            DictCityDO cityDO = cityDao.getCityDOByCode(profileNotice.getCity_code());
            String cityName = "";
            if(cityDO != null){
                cityName = cityDO.getName();
            }
            ProfileExtUtils.createProfileBasicCity(profilePojo, profileNotice.getCity_code(), cityName);
        }

        return profilePojo;
    }

}
