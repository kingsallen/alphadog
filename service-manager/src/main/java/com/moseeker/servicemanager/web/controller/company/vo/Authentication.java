package com.moseeker.servicemanager.web.controller.company.vo;

import com.moseeker.thrift.gen.employee.struct.EmployeeVerificationConfResponse;

import java.util.List;
import java.util.Map;

/**
 * 公司配置的员工认证信息的内容
 * @Author: jack
 * @Date: 2018/8/7
 */
public class Authentication {

    private boolean exist;                      //认证方式是否存在
    public int companyId;                       //公司编号
    public List<String> emailSuffix;            //邮箱后缀
    public short authMode;                      //认证方式 0:不启用员工认证, 1:邮箱认证, 2:自定义认证, 3:姓名手机号认证, 4:邮箱自定义两种认证,5:问答,6:邮箱与问答'
    public String custom;                       //自定义字段内容
    public List<Map<String,String>> questions;  //问答
    public String customHint;                   //提示信息

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public List<String> getEmailSuffix() {
        return emailSuffix;
    }

    public void setEmailSuffix(List<String> emailSuffix) {
        this.emailSuffix = emailSuffix;
    }

    public short getAuthMode() {
        return authMode;
    }

    public void setAuthMode(short authMode) {
        this.authMode = authMode;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public List<Map<String, String>> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Map<String, String>> questions) {
        this.questions = questions;
    }

    public String getCustomHint() {
        return customHint;
    }

    public void setCustomHint(String customHint) {
        this.customHint = customHint;
    }

    public static Authentication clone(EmployeeVerificationConfResponse authentication) {
        Authentication auth = new Authentication();
        if (authentication != null) {

            auth.setExist(authentication.isExists());
            if (authentication.getEmployeeVerificationConf() != null) {
                auth.setAuthMode(authentication.getEmployeeVerificationConf().getAuthMode());
                auth.setCompanyId(authentication.getEmployeeVerificationConf().getCompanyId());
                auth.setCustom(authentication.getEmployeeVerificationConf().getCustom());
                auth.setCustomHint(authentication.getEmployeeVerificationConf().getCustomHint());
                auth.setEmailSuffix(authentication.getEmployeeVerificationConf().getEmailSuffix());
                auth.setQuestions(authentication.getEmployeeVerificationConf().getQuestions());
            }

        } else {
            auth.setExist(false);
        }
        return auth;
    }
}
