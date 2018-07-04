package com.moseeker.profile.service.impl.resumesdk;


import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojo.profile.Basic;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.resume.Result;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.service.impl.resumesdk.iface.AbstractResumeParser;
import com.moseeker.profile.service.impl.resumesdk.iface.ResumeParserHelper;
import com.moseeker.profile.utils.DictCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 简历-基本信息
 */
@Component
public class BasicParser extends AbstractResumeParser<Result,Basic> {
    Logger logger = LoggerFactory.getLogger(BasicParser.class);

    @Override
    protected Basic parseResume(Result result) throws ResumeParserHelper.ResumeParseException {
        // basic信息
        logger.info("profileParser result.getCity():{}", result.getCity());
        logger.info("profileParser result.getGender():{}", result.getGender());
        logger.info("profileParser result.getName():{}", result.getName());
        logger.info("profileParser result.getCont_my_desc():{}", result.getCont_my_desc());
        logger.info("profileParser result.getBirthday():{}", result.getBirthday());
        Basic basic = new Basic();
        basic.setCityName(result.getCity());
        if(StringUtils.isNotNullOrEmpty(result.getGender())){
            basic.setGender(String.valueOf(DictCode.gender(result.getGender())));
        }
        basic.setName(result.getName());
        basic.setSelfIntroduction(result.getCont_my_desc());
        if (StringUtils.isNotNullOrEmpty(result.getBirthday())) {
            try {
                basic.setBirth(DateUtils.dateRepair(result.getBirthday(), "\\."));
            } catch (Exception e) {
                throw new ResumeParserHelper.ResumeParseException()
                        .errorLog("出生日期转换异常: " + e.getMessage())
                        .fieldValue("birthday: " + result.getBirthday());
            }
        }
        logger.info("profileParser getBasic:{}", JSON.toJSONString(basic));
        return basic;
    }

    @Override
    protected Result get(ResumeObj resumeProfile) {
        return resumeProfile.getResult();
    }

    @Override
    protected void set(ProfileObj moseekerProfile, Basic basic) {
        moseekerProfile.setBasic(basic);
    }
}
