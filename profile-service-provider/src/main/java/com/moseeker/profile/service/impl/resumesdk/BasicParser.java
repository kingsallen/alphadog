package com.moseeker.profile.service.impl.resumesdk;


import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.dictdb.DictCountryDao;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojo.profile.Basic;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.resume.Result;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.service.impl.resumesdk.iface.AbstractResumeParser;
import com.moseeker.profile.service.impl.resumesdk.iface.ResumeParserHelper;
import com.moseeker.profile.utils.DictCode;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 简历-基本信息
 */
@Component
public class BasicParser extends AbstractResumeParser<Result, Basic> {
    Logger logger = LoggerFactory.getLogger(BasicParser.class);

    @Autowired
    DictCountryDao dictCountryDao;

    @Autowired
    DictCityDao dictCityDao;

    @Override
    protected Basic parseResume(Result result) throws ResumeParserHelper.ResumeParseException {
        // basic信息
        logger.info("profileParser result.getCity():{}", result.getCity());
        logger.info("profileParser result.getGender():{}", result.getGender());
        logger.info("profileParser result.getName():{}", result.getName());
        logger.info("profileParser result.getCont_my_desc():{}", result.getCont_my_desc());
        logger.info("profileParser result.getBirthday():{}", result.getBirthday());
        logger.info("profileParser result.getNationality():{}", result.getNationality());
        Basic basic = new Basic();
        if (StringUtils.isNotNullOrEmpty(result.getCity())) {
            setCity(basic, result.getCity());
        } else if (StringUtils.isNotNullOrEmpty(result.getLiving_address())) {
            setCity(basic, result.getLiving_address());
        }
        if (StringUtils.isNotNullOrEmpty(result.getGender())) {
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

        if (StringUtils.isNotNullOrEmpty(result.getNationality())) {
            DictCountryDO countryDO = dictCountryDao.getCountryByNameOrEName(result.getNationality());
            if (countryDO != null) {
                basic.setNationalityCode(countryDO.getCode());
                basic.setNationalityName(countryDO.getName());
            }
        }
        basic.setQq(result.getQq());
        logger.info("profileParser getBasic:{}", JSON.toJSONString(basic));
        return basic;
    }

    public void setCity(Basic basic, String cityName) {
        DictCityDO city = dictCityDao.getCityByNameOrEname(cityName);
        if (city != null) {
            basic.setCityName(city.getName());
            basic.setCityCode(city.getCode());
        } else {
            basic.setCityName(cityName);
        }
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
