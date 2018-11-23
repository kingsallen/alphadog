package com.moseeker.profile.domain;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.constant.ReferralScene;
import com.moseeker.baseorm.constant.ReferralType;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.exception.ApplicationException;
import com.moseeker.profile.exception.ProfileException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 消息模板
 *
 * Created by jack on 23/01/2018.
 */
public class EmployeeReferralProfileNotice {

    private int employeeId;
    private String name;
    private String mobile;
    private List<String> referralReasons;
    private List<Integer> positionIds;
    private byte relationship;
    private String  referralText;
    private ReferralType referralType;
    private String email;
    private byte gender;
    private String city_name;
    private String currentPosition;
    private int degree;
    private String companyBrand;
    private ReferralScene referralScene;

    private EmployeeReferralProfileNotice(int employeeId, String name, String mobile, List<String> referralReasons,
                                         List<Integer> positionIds, byte relationship, String referralText, ReferralType referralType,
                                         byte gender, String email, String city_name, String currentPosition, int degree, String companyBrand,
                                          ReferralScene referralScene) {
        this.employeeId = employeeId;
        this.name = name;
        this.mobile = mobile;
        this.referralReasons = referralReasons;
        this.positionIds = positionIds;
        this.relationship = relationship;
        this.referralText = referralText;
        this.referralType = referralType;
        this.gender = gender;
        this.email = email;
        this.city_name = city_name;
        this.currentPosition = currentPosition;
        this.degree = degree;
        this.companyBrand = companyBrand;
        this.referralScene = referralScene;

    }


    public static class EmployeeReferralProfileBuilder {

        private Logger logger = LoggerFactory.getLogger(this.getClass());

        private int employeeId;
        private String name;
        private String mobile;
        private List<String> referralReasons;
        private List<Integer> positionIds;
        private byte relationship;
        private String  referralText;
        private ReferralType referralType;
        private byte gender;
        private String email;
        private String city_name;
        private String currentPosition;
        private int degree;
        private String companyBrand;
        private ReferralScene referralScene;

        public EmployeeReferralProfileBuilder (int employeeId, String name, String mobile, List<String> referralReasons,
                ReferralScene referralScene) {
            this.employeeId = employeeId;
            this.name= name;
            this.mobile = mobile;
            this.referralReasons = referralReasons;
            this.referralScene = referralScene;
        }

        public EmployeeReferralProfileBuilder buildRecomReason(byte relationship, String referralText, byte referralType) {
            this.relationship = relationship;
            this.referralText = referralText;
            ReferralType type = ReferralType.instanceFromValue(referralType);
            if (type == null) {
                throw ApplicationException.APPLICATION_REFERRAL_TYPE_NOT_EXIST;
            }
            this.referralType = type;
            return this;
        }

        public EmployeeReferralProfileBuilder buildKeyInformation(String email, int degree, String companyBrand,
                                                                  String currentPosition, byte gender, String city_name) {
            this.email = email;
            this.degree = degree;
            this.currentPosition = currentPosition;
            this.companyBrand = companyBrand;
            this.gender = gender;
            this.city_name = city_name;
            return this;
        }
        public EmployeeReferralProfileBuilder buildPosition(int positionId) {
            List<Integer> positionIds = new ArrayList<>();
            positionIds.add(positionId);
            this.positionIds = positionIds;
            return this;
        }
        public EmployeeReferralProfileBuilder buildPosition(List<Integer> positionIds) {
            this.positionIds = positionIds;
            return this;
        }

        public EmployeeReferralProfileNotice buildEmployeeReferralProfileNotice() throws CommonException {

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredOneValidate("推荐理由", referralReasons);
            if (referralReasons != null) {
                String reasons = referralReasons.stream().collect(Collectors.joining(","));
                validateUtil.addStringLengthValidate("推荐理由", reasons, null, 512);
            }
            validateUtil.addRequiredStringValidate("候选人姓名", name);
            validateUtil.addStringLengthValidate("候选人姓名", name, null, 100);
            validateUtil.addRequiredStringValidate("手机号码", mobile);
            validateUtil.addRegExpressValidate("手机号码", mobile, FormCheck.getMobileExp());
            validateUtil.addIntTypeValidate("推荐方式", referralType.getValue(), 1, 4);
            String validateResult = validateUtil.validate();
            if (StringUtils.isNotBlank(validateResult)) {
                throw ProfileException.validateFailed(validateResult);
            }

            EmployeeReferralProfileNotice profileNotice = new EmployeeReferralProfileNotice(employeeId, name, mobile,
                    referralReasons, positionIds, relationship, referralText, referralType,gender, email,  city_name,
                    currentPosition, degree, companyBrand, referralScene);
            logger.info("EmployeeReferralProfileNotice:{}", JSON.toJSONString(profileNotice));
            return profileNotice;
        }
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<String> getReferralReasons() {
        return referralReasons;
    }

    public void setReferralReasons(List<String> referralReasons) {
        this.referralReasons = referralReasons;
    }

    public List<Integer> getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(List<Integer> positionIds) {
        this.positionIds = positionIds;
    }

    public byte getRelationship() {
        return relationship;
    }

    public void setRelationship(byte relationship) {
        this.relationship = relationship;
    }

    public String getReferralText() {
        return referralText;
    }

    public void setReferralText(String referralText) {
        this.referralText = referralText;
    }

    public ReferralType getReferralType() {
        return referralType;
    }

    public void setReferralType(ReferralType referralType) {
        this.referralType = referralType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getCompanyBrand() {
        return companyBrand;
    }

    public void setCompanyBrand(String companyBrand) {
        this.companyBrand = companyBrand;
    }

    public ReferralScene getReferralScene() {
        return referralScene;
    }

    public void setReferralScene(ReferralScene referralScene) {
        this.referralScene = referralScene;
    }
}
