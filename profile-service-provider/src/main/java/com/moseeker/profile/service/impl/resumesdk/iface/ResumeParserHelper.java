package com.moseeker.profile.service.impl.resumesdk.iface;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.db.logdb.tables.records.LogResumeRecordRecord;
import com.moseeker.entity.pojo.resume.ResumeObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResumeParserHelper {
    static LogResumeRecordRecord buildLogResumeRecord(ResumeParseException e, ResumeObj resumeProfile, int uid, String fileName){
        LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
        logResumeRecordRecord.setErrorLog(e.getErrorLog());
        logResumeRecordRecord.setFieldValue(e.getFieldValue());
        logResumeRecordRecord.setUserId(uid);
        logResumeRecordRecord.setFileName(fileName);
        logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeProfile));
        return logResumeRecordRecord;
    }

    public static class ResumeParseException extends RuntimeException {
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
}
