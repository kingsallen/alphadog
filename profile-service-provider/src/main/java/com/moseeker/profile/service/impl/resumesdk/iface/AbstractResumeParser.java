package com.moseeker.profile.service.impl.resumesdk.iface;

import com.moseeker.baseorm.dao.logdb.LogResumeDao;
import com.moseeker.baseorm.db.logdb.tables.records.LogResumeRecordRecord;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.resume.ResumeObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.moseeker.profile.service.impl.resumesdk.iface.ResumeParserHelper.ResumeParseException;
import static com.moseeker.profile.service.impl.resumesdk.iface.ResumeParserHelper.buildLogResumeRecord;

/**
 * 把ResumeSDK中的一个字段解析成仟寻简历的一个字段
 * @param <T> ResumeSDK需要解析字段的类型
 * @param <R> 解析完成的仟寻简历字段类型
 */
public abstract class AbstractResumeParser<T, R> implements IResumeParser {
    Logger logger = LoggerFactory.getLogger(AbstractResumeParser.class);

    @Autowired
    protected LogResumeDao resumeDao;

    /**
     * 解析ResumeSDK对象到仟寻简历的一个字段
     * @param t ResumeSDK对象
     * @return 解析完后的结果
     * @throws ResumeParseException 解析报错，需要存到log_resume_record表
     */
    protected abstract R parseResume(T t) throws ResumeParseException;

    /**
     * 从ResumeSDK返回的对象中取出当前类需要处理的字段
     * @param resumeProfile ResumeSDK返回的对象
     * @return 需要处理的字段
     */
    protected abstract T get(ResumeObj resumeProfile);

    /**
     * 把{@link #parseResume(R r)}解析完成的结果存入ProfileObj对象中
     * @param moseekerProfile 返回给前端的，仟寻简历对象
     * @param r 解析完成的结果
     */
    protected abstract void set(ProfileObj moseekerProfile, R r);

    @Override
    public ProfileObj parseResume(ProfileObj moseekerProfile, ResumeObj resumeProfile, int uid, String fileName) {
        T t = get(resumeProfile);
        if (t != null) {
            try {
                R r = parseResume(t);
                set(moseekerProfile, r);
            } catch (ResumeParseException e) {
                logger.error(e.getMessage(), e);
                LogResumeRecordRecord logResumeRecordRecord = buildLogResumeRecord(e, resumeProfile, uid, fileName);
                resumeDao.addRecord(logResumeRecordRecord);
            }
        }

        return moseekerProfile;
    }


}
