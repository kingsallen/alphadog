package com.moseeker.profile.service.impl.resumesdk;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.dictdb.DictPositionDao;
import com.moseeker.baseorm.dao.logdb.LogResumeDao;
import com.moseeker.baseorm.db.logdb.tables.records.LogResumeRecordRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.pojo.profile.City;
import com.moseeker.entity.pojo.profile.Intentions;
import com.moseeker.entity.pojo.profile.Positions;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.service.impl.resumesdk.iface.IResumeParser;
import com.moseeker.profile.utils.DictCode;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictPositionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 简历-期望
 */
@Component
public class IntentionsParser implements IResumeParser {
    Logger logger = LoggerFactory.getLogger(IntentionsParser.class);

    @Autowired
    private DictCityDao dictCityDao;

    @Autowired
    private DictPositionDao dictPositionDao;

    @Autowired
    protected LogResumeDao resumeDao;

    @Override
    public ProfileObj parseResume(ProfileObj profileObj, ResumeObj resumeObj, int uid, String fileName){
        // 期望
        Intentions intentions = new Intentions();
        if (resumeObj.getResult().getExpect_jnature() != null) {
            intentions.setWorktype(DictCode.workType(resumeObj.getResult().getExpect_jnature()));
        }

        if (StringUtils.isNotNullOrEmpty(resumeObj.getResult().getExpect_salary())) {
            try{
                int topSalary = Arrays.stream(resumeObj.getResult().getExpect_salary().replaceAll("[\\u4E00-\\u9FA5|/]", "").split("-|~", 2)).max(String::compareTo).map(m -> Integer.valueOf(m)).get();
                intentions.setSalaryCode(String.valueOf(DictCode.salary(topSalary)));
            } catch (Exception e) {
                LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                logResumeRecordRecord.setErrorLog("期望薪资转换异常: " + e.getMessage());
                logResumeRecordRecord.setFieldValue("expectSalary: " + resumeObj.getResult().getExpect_salary());
                logResumeRecordRecord.setUserId(uid);
                logResumeRecordRecord.setFileName(fileName);
                logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
                resumeDao.addRecord(logResumeRecordRecord);
                logger.error(e.getMessage(), e);
            }
        }

        // 期望城市
        if (StringUtils.isNotNullOrEmpty(resumeObj.getResult().getExpect_jlocation())) {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where("name", resumeObj.getResult().getExpect_jlocation());
            DictCityDO dictCityDO = dictCityDao.getData(queryBuilder.buildQuery());
            if (dictCityDO == null || dictCityDO.getCode() == 0) {
                LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                logResumeRecordRecord.setErrorLog("期望城市转换异常: ");
                logResumeRecordRecord.setFieldValue("expectJlocation: " + resumeObj.getResult().getExpect_jlocation());
                logResumeRecordRecord.setUserId(uid);
                logResumeRecordRecord.setFileName(fileName);
                logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
                resumeDao.addRecord(logResumeRecordRecord);
            } else {
                City city = new City();
                city.setCityCode(dictCityDO.getCode());
                city.setCityName(dictCityDO.getName());
                intentions.setCities(new ArrayList<City>(){{add(city);}});
            }
        }

        // 期望职能
        if (StringUtils.isNotNullOrEmpty(resumeObj.getResult().getExpect_job())) {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(new Condition("name", resumeObj.getResult().getExpect_job(), ValueOp.LIKE));
            DictPositionDO dictPositionDO = dictPositionDao.getData(queryBuilder.buildQuery());
            if (dictPositionDO == null || dictPositionDO.getCode() == 0) {
                LogResumeRecordRecord logResumeRecordRecord = new LogResumeRecordRecord();
                logResumeRecordRecord.setErrorLog("期望职能转换异常: ");
                logResumeRecordRecord.setFieldValue("expectJob: " + resumeObj.getResult().getExpect_job());
                logResumeRecordRecord.setUserId(uid);
                logResumeRecordRecord.setFileName(fileName);
                logResumeRecordRecord.setResultData(JSONObject.toJSONString(resumeObj));
                resumeDao.addRecord(logResumeRecordRecord);
            } else {
                Positions positions = new Positions();
                positions.setPositionCode(dictPositionDO.getCode());
                positions.setPositionName(dictPositionDO.getName());
                intentions.setPositions(new ArrayList<Positions>(){{add(positions);}});
            }
        }

        List<Intentions> intentionsList = new ArrayList<>();
        intentionsList.add(intentions);
        profileObj.setIntentions(intentionsList);

        return profileObj;
    }
}
