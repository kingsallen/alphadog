package com.moseeker.profile.constants;

/**
 * Created by lucky8987 on 17/9/13.
 */
public class ConfigCustomMetaVO {

    public int id; // optional
    public java.lang.String fieldName; // optional
    public java.lang.String fieldTitle; // optional
    public int fieldType; // optional
    public int priority; // optional
    public byte isBasic; // optional
    public int companyId; // optional
    public byte required; // optional
    public java.lang.String fieldDescription; // optional
    public java.lang.String map; // optional
    public int parentId; // optional
    public java.lang.String validateRe; // optional
    public int constantParentCode; // optional
    public java.lang.String constantValue; // optional

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getFieldType() {
        return fieldType;
    }

    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public byte getIsBasic() {
        return isBasic;
    }

    public void setIsBasic(byte isBasic) {
        this.isBasic = isBasic;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public byte getRequired() {
        return required;
    }

    public void setRequired(byte required) {
        this.required = required;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getValidateRe() {
        return validateRe;
    }

    public void setValidateRe(String validateRe) {
        this.validateRe = validateRe;
    }

    public int getConstantParentCode() {
        return constantParentCode;
    }

    public void setConstantParentCode(int constantParentCode) {
        this.constantParentCode = constantParentCode;
    }

    public String getConstantValue() {
        return constantValue;
    }

    public void setConstantValue(String constantValue) {
        this.constantValue = constantValue;
    }
}
