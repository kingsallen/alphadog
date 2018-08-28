package com.moseeker.profile.service.impl.resumesdk.iface;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.db.logdb.tables.records.LogResumeRecordRecord;
import com.moseeker.common.util.EmojiFilter;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.entity.pojo.resume.ResumeParseException;

import java.io.UnsupportedEncodingException;

public class ResumeParserHelper {

    private static final int ERROR_LOG_LENGTH = 200;
    private static final int FIELD_VALUE_LENGTH = 2000;

    public static LogResumeRecordRecord buildLogResumeRecord(ResumeParseException e, ResumeObj resumeProfile, int uid, String fileName, String text){
        LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
        if(StringUtils.isNotNullOrEmpty(e.getErrorLog()) && e.getErrorLog().length()>ERROR_LOG_LENGTH) {
            logResumeRecordRecord.setErrorLog(e.getErrorLog().substring(0,ERROR_LOG_LENGTH));
        } else{
            logResumeRecordRecord.setErrorLog(e.getErrorLog());
        }
        if(StringUtils.isNotNullOrEmpty(e.getFieldValue()) && e.getFieldValue().length()>FIELD_VALUE_LENGTH) {
            logResumeRecordRecord.setFieldValue(e.getFieldValue().substring(0, FIELD_VALUE_LENGTH));
        } else {
            logResumeRecordRecord.setFieldValue(e.getFieldValue());
        }
        logResumeRecordRecord.setUserId(uid);
        logResumeRecordRecord.setFileName(fileName);
        logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeProfile));
        logResumeRecordRecord.setText(text);
        return logResumeRecordRecord;
    }
}
