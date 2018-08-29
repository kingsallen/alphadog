package com.moseeker.entity.pojo.resume;

/**
 * Resume SDK 解析异常 
 * @Author: jack
 * @Date: 2018/8/1
 */
public class ResumeParseException extends RuntimeException {
    private String errorLog;
    private String fieldValue;

    public ResumeParseException errorLog(String errorLog) {
        this.errorLog = errorLog;
        return this;
    }
    public ResumeParseException fieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
        return this;
    }

    public String getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
