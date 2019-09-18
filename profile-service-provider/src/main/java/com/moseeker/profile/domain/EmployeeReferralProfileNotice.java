package com.moseeker.profile.domain;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.constant.ReferralScene;
import com.moseeker.baseorm.constant.ReferralType;
import com.moseeker.common.constants.AlphaCloudProvider;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.exception.ApplicationException;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.ReferralServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private int city_code;
    private String currentPosition;
    private int degree;
    private String companyBrand;
    private ReferralScene referralScene;
    private Map<String, String> otherFields ;
    private CompanyCustomize companyCustomize ;

    private EmployeeReferralProfileNotice(int employeeId, String name, String mobile, List<String> referralReasons,
                                         List<Integer> positionIds, byte relationship, String referralText, ReferralType referralType,
                                         byte gender, String email, int city_code, String currentPosition, int degree, String companyBrand,
                                          ReferralScene referralScene,Map<String, String> otherFields,CompanyCustomize companyCustomize) {
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
        this.city_code = city_code;
        this.currentPosition = currentPosition;
        this.degree = degree;
        this.companyBrand = companyBrand;
        this.referralScene = referralScene;
        this.otherFields = otherFields;
        this.companyCustomize = companyCustomize;

    }


    public static class EmployeeReferralProfileBuilder {

        private Logger logger = LoggerFactory.getLogger(this.getClass());

        private int employeeId;
        private String name;
        private String mobile;
        private Map<String, String> otherFields ;
        private boolean checkPositionTemplate ;
        private List<String> referralReasons;
        private List<Integer> positionIds;
        private byte relationship;
        private String  referralText;
        private ReferralType referralType;
        private byte gender;
        private String email;
        private int city_code;
        private String currentPosition;
        private int degree;
        private String companyBrand;
        private ReferralScene referralScene;
        private CompanyCustomize companyCustomize ;


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
                                                                  String currentPosition, byte gender, int city_code) {
            this.email = email;
            this.degree = degree;
            this.currentPosition = currentPosition;
            this.companyBrand = companyBrand;
            this.gender = gender;
            this.city_code = city_code;
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
        public EmployeeReferralProfileBuilder buildOtherFields(Map<String,String> otherFields) {
            this.otherFields = otherFields;
            this.checkPositionTemplate = true ;
            if(otherFields != null && otherFields.containsKey("email")){
                this.email = otherFields.remove("email");
            }
            return this;
        }


        public EmployeeReferralProfileNotice buildEmployeeReferralProfileNotice() throws CommonException {

            ValidateUtil validateUtil = new ValidateUtil();
            if (referralReasons != null) {
                String reasons = referralReasons.stream().collect(Collectors.joining(","));
                validateUtil.addStringLengthValidate("推荐理由", reasons, null, 512);
            }
            validateUtil.addRequiredOneValidate("推荐职位", positionIds);
            validateUtil.addRequiredStringValidate("候选人姓名", name);
            validateUtil.addStringLengthValidate("候选人姓名", name, null, 100);
            validateUtil.addRequiredStringValidate("手机号码", mobile);
            validateUtil.addRegExpressValidate("手机号码", mobile, FormCheck.getMobileExp());
            validateUtil.addIntTypeValidate("推荐方式", referralType.getValue(), 1, 4);
            validateUtil.addRequiredValidate("推荐人与被推荐人关系", relationship);
            validateUtil.addStringLengthValidate("推荐理由文本", referralText, "推荐理由文本长度过长", "推荐理由文本长度过长",null, 201);
            validateUtil.addSensitiveValidate("推荐理由文本", referralText, null, null);
            String validateResult = validateUtil.validate();
            if(com.moseeker.common.util.StringUtils.isEmptyList(referralReasons) && com.moseeker.common.util.StringUtils.isNullOrEmpty(referralText)){
                validateResult =validateResult+ "推荐理由标签和文本必填任一一个；";
            }
            if(checkPositionTemplate){
                companyCustomize = addValidateReferralByTemplate(validateUtil);
            }
            if (StringUtils.isNotBlank(validateResult)) {
                throw ProfileException.validateFailed(validateResult);
            }

            EmployeeReferralProfileNotice profileNotice = new EmployeeReferralProfileNotice(employeeId, name, mobile,
                    referralReasons, positionIds, relationship, referralText, referralType,gender, email,  city_code,
                    currentPosition, degree, companyBrand, referralScene,otherFields,companyCustomize);
            logger.info("EmployeeReferralProfileNotice:{}", JSON.toJSONString(profileNotice));
            return profileNotice;
        }

        /**
         * 根据职位模板进行必填项校验
         */
        private CompanyCustomize addValidateReferralByTemplate(ValidateUtil validateUtil) {
            String url = AlphaCloudProvider.Position.buildURL(ReferralServiceImpl.REFERRAL_TEMPLATE_FIELDS_URL_PATH);
            logger.debug("validateReferralByTemplate 获取职位模板 url : {},postionIdList:{}",url,this.positionIds);
            String resText = HttpClient.sendGet(url,"positionIds",positionIds);
            logger.debug("validateReferralByTemplate 获取职位模板 resText: {}",resText);
            ReferralPositionTemplateVO vo = HttpClient.getDataFromJsonString(resText,ReferralPositionTemplateVO.class);
            if(vo != null && vo.getFields() != null) {
                vo.getFields().stream().filter(ReferralPositionTemplateVO.Config::isRequired)
                        .forEach(config -> validateUtil.addRequiredValidate(StringUtils.isBlank(config.getFieldTitle().trim())?
                                config.getFieldName(): config.getFieldTitle(),getOtherField(config.getFieldName())));
                return vo.getCompanyCustomize();
            }
            return null;
        }

        private String getOtherField(String fieldName){
            return "email".equals(fieldName) ? email : otherFields.get(fieldName);
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

    public int getCity_code() {
        return city_code;
    }

    public void setCity_code(int city_code) {
        this.city_code = city_code;
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

    public Map<String, String> getOtherFields() {
        return otherFields;
    }

    public void setOtherFields(Map<String, String> otherFields) {
        this.otherFields = otherFields;
    }

    public CompanyCustomize getCompanyCustomize() {
        return companyCustomize;
    }

    public void setCompanyCustomize(CompanyCustomize companyCustomize) {
        this.companyCustomize = companyCustomize;
    }
}
