package com.moseeker.profile.service.impl.resumesdk.iface;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.db.logdb.tables.records.LogResumeRecordRecord;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.entity.pojo.resume.ResumeParseException;

public class ResumeParserHelper {

    public static LogResumeRecordRecord buildLogResumeRecord(ResumeParseException e, ResumeObj resumeProfile, int uid, String fileName, String text){
        LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
        logResumeRecordRecord.setErrorLog(e.getErrorLog());
        logResumeRecordRecord.setFieldValue(e.getFieldValue());
        logResumeRecordRecord.setUserId(uid);
        logResumeRecordRecord.setFileName(fileName);
        logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeProfile));
        logResumeRecordRecord.setText(text);
        return logResumeRecordRecord;
    }
}
