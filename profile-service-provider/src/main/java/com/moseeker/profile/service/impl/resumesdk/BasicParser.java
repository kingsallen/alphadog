package com.moseeker.profile.service.impl.resumesdk;


import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojo.profile.Basic;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.resume.Result;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.service.impl.resumesdk.iface.AbstractMutiResumeParser;
import com.moseeker.entity.pojo.resume.ResumeParseException;
import com.moseeker.profile.utils.DictCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 简历-基本信息
 */
@Component
public class BasicParser extends AbstractMutiResumeParser<Result, Basic> {
    Logger logger = LoggerFactory.getLogger(BasicParser.class);

    @Override
    protected Basic parseResume(Result result) throws ResumeParseException {
        // basic信息
        logger.info("profileParser result.getCity():{}", result.getCity());
        logger.info("profileParser result.getGender():{}", result.getGender());
        logger.info("profileParser result.getName():{}", result.getName());
        logger.info("profileParser result.getCont_my_desc():{}", result.getCont_my_desc());
        logger.info("profileParser result.getBirthday():{}", result.getBirthday());
        logger.info("profileParser result.getNationality():{}", result.getNationality());
        Basic basic = new Basic();
        setCity(basic, result.getCity(), result.getLiving_address());
        if (StringUtils.isNotNullOrEmpty(result.getGender())) {
            basic.setGender(DictCode.gender(result.getGender()));
        }
        basic.setName(result.getName());
        basic.setSelfIntroduction(result.getCont_my_desc());
        if (StringUtils.isNotNullOrEmpty(result.getBirthday())) {
            try {
                basic.setBirth(DateUtils.dateRepair(result.getBirthday(), "\\."));
            } catch (Exception e) {
                throw new ResumeParseException()
                        .errorLog("出生日期转换异常: " + e.getMessage())
                        .fieldValue("birthday: " + result.getBirthday());
            }
        }

        if (StringUtils.isNotNullOrEmpty(result.getNationality())) {
            basic.setNationalityName(result.getNationality());
        }
        basic.setQq(result.getQq());
        logger.info("profileParser getBasic:{}", JSON.toJSONString(basic));
        return basic;
    }

    public void setCity(Basic basic, String cityName, String livingAddress) {
        basic.setCityName(org.apache.commons.lang.StringUtils.isNotBlank(cityName)?cityName:livingAddress);
    }

    @Override
    protected List<Result> get(ResumeObj resumeProfile) {
        return new ArrayList<Result>(){{add(resumeProfile.getResult());}};
    }

    @Override
    protected void set(ProfileObj moseekerProfile, List<Basic> basic) {
        if (basic != null && basic.size() > 0) {
            moseekerProfile.setBasic(basic.get(0));
        }
    }
}
