package com.moseeker.profile.domain;

import java.util.List;


public class ReferralPositionTemplateVO {

    private List<Integer> templateIds  ;
    private List<Config> fields ;
    private CompanyCustomize companyCustomize ;

    public static class Config{
        //private String errorMsg  ;// "身份证名拼音最多只允许输入30个英文字母"
        private String fieldDescription ;// "请填写身份证名拼音"
        private String fieldName ;// "fullnamepinyin"
        private String fieldTitle ;// "身份证名拼音"
        //private int fieldType ;// 0
        //private String id   ;// 92
        private int required ; // 0:必填 1：选填
        //private int isBasic ; // 0
        //private String validate_re ; //  "^[a-zA-Z]{1,30}$"

        public String getFieldDescription() {
            return fieldDescription;
        }

        public void setFieldDescription(String fieldDescription) {
            this.fieldDescription = fieldDescription;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldTitle() {
            return fieldTitle;
        }

        public void setFieldTitle(String fieldTitle) {
            this.fieldTitle = fieldTitle;
        }

        public int getRequired() {
            return required;
        }

        public void setRequired(int required) {
            this.required = required;
        }

        public boolean isRequired(){
            return required == 0 ;
        }
    }

    public List<Integer> getTemplateIds() {
        return templateIds;
    }

    public void setTemplateIds(List<Integer> templateIds) {
        this.templateIds = templateIds;
    }

    public List<Config> getFields() {
        return fields;
    }

    public void setFields(List<Config> fields) {
        this.fields = fields;
    }

    public CompanyCustomize getCompanyCustomize() {
        return companyCustomize;
    }

    public void setCompanyCustomize(CompanyCustomize companyCustomize) {
        this.companyCustomize = companyCustomize;
    }
}
