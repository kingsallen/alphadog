package com.moseeker.profile.service.impl.resumesdk;

import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.dictdb.DictCountryDao;
import com.moseeker.baseorm.dao.dictdb.DictPositionDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.entity.pojo.profile.*;
import com.moseeker.entity.pojo.resume.ResumeParseException;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ResumeSDK简历数据完善工具
 * @Author: jack
 * @Date: 2018/8/2
 */
@Repository
public class ProfileObjRepository {

    @Autowired
    UserUserDao userUserDao;

    @Autowired
    DictCountryDao dictCountryDao;

    @Autowired
    DictCityDao dictCityDao;

    @Autowired
    private DictPositionDao dictPositionDao;

    /**
     * 利用现有的数据，从数据库中补填简历信息
     * @param profileObj 简历数据
     */
    public List<ResumeParseException> fillProfile(ProfileObj profileObj) {

        List<ResumeParseException> exceptions = new ArrayList<>();
        fillUser(profileObj.getUser());
        fillBasic(profileObj.getBasic());
        exceptions.addAll(fillIntention(profileObj.getIntentions()));

        return exceptions;
    }

    /**
     * 利用现有的数据，从数据库中补填简历信息
     * @param profileObj 简历信息
     * @param userId 用户编号
     * @return 异常信息
     */
    public List<ResumeParseException> fillProfile(ProfileObj profileObj, int userId) {

        List<ResumeParseException> exceptions = new ArrayList<>();
        fillUser(profileObj.getUser(), userId);
        fillBasic(profileObj.getBasic());
        exceptions.addAll(fillIntention(profileObj.getIntentions()));
        return exceptions;
    }

    /**
     * 利用现有的数据，从数据库中补填期望信息
     * @param intentions
     * @return 异常信息
     */
    private List<ResumeParseException> fillIntention(List<Intentions> intentions) {

        List<ResumeParseException> resumeParseExceptions = new ArrayList<>();
        if (intentions != null && intentions.size() > 0) {
            List<String> cityNameList = new ArrayList<>();
            List<String> positionNameList = new ArrayList<>();

            intentions.forEach(intentions1 -> {
                if (intentions1.getCities() != null && intentions1.getCities().size() > 0) {
                    List<String> cityName = intentions1.getCities()
                            .stream()
                            .map(City::getCityName)
                            .collect(Collectors.toList());
                    cityNameList.addAll(cityName);
                }

                if (intentions1.getPositions() != null && intentions1.getPositions().size() > 0) {
                    List<String> positionNames = intentions1.getPositions()
                            .stream()
                            .map(Positions::getPositionName)
                            .collect(Collectors.toList());
                    positionNameList.addAll(positionNames);
                }

            });

            List<DictCityDO> cityDOList = dictCityDao.getByNameList(cityNameList);
            List<DictPositionDO> positionDOList = dictPositionDao.getByNameList(positionNameList);

            intentions.forEach(intentions1 -> {
                if (intentions1.getCities() != null && intentions1.getCities().size() > 0) {
                    intentions1.getCities().forEach(city -> {
                        Optional<DictCityDO> optional = cityDOList
                                .stream()
                                .filter(dictCityDO -> dictCityDO.getName().equals(city.getCityName()) ||
                                        dictCityDO.getEname().equals(city.getCityName()))
                                .findAny();
                        if (optional.isPresent()) {
                            city.setCityCode(optional.get().getCode());
                        } else {
                            ResumeParseException resumeParseException = new ResumeParseException();
                            resumeParseException.setErrorLog("期望城市转换异常: ");
                            resumeParseException.setFieldValue("expectJlocation: " + city.getCityName());
                            resumeParseExceptions.add(resumeParseException);
                        }
                    });
                }

                if (intentions1.getPositions() != null && intentions1.getPositions().size() > 0) {
                    intentions1.getPositions().forEach(positions -> {
                        Optional<DictPositionDO> optional = positionDOList
                                .stream()
                                .filter(dictPositionDO -> dictPositionDO.getName().equals(positions.getPositionName()))
                                .findAny();
                        if (optional.isPresent()) {
                            positions.setPositionCode(optional.get().getCode());
                        } else {
                            ResumeParseException resumeParseException = new ResumeParseException();
                            resumeParseException.setErrorLog("期望职能转换异常: ");
                            resumeParseException.setFieldValue("expectJob: " + positions.getPositionName());
                            resumeParseExceptions.add(resumeParseException);
                        }
                    });
                }
            });
        }
        return resumeParseExceptions;
    }

    /**
     * 补充所在地点对应code的数据
     * @param basic 基本信息
     */
    private void fillBasic(Basic basic) {
        if (basic != null && StringUtils.isNotBlank(basic.getCityName())) {
            DictCityDO city = dictCityDao.getCityByNameOrEname(basic.getCityName());
            if (city != null) {
                basic.setCityCode(city.getCode());
            }
        }
    }

    /**
     * 补充用户信息
     * @param user 用户数据
     * @param userId 用户编号
     */
    private void fillUser(User user, int userId) {
        if (user != null) {
            if (userId > 0) {
                UserUserDO userUserDO = userUserDao.getUser(userId);
                fillUser(user, userUserDO);
            } else {
                if (StringUtils.isNotBlank(user.getMobile())) {
                    UserUserDO userUserDO = userUserDao.getUnIdentifyUserByMobile(user.getMobile());
                    fillUser(user, userUserDO);
                }
            }
        }
    }

    /**
     * 补充用户信息
     * @param user 用户数据
     * @param userUserDO 用户
     */
    private void fillUser(User user, UserUserDO userUserDO) {
        if (user != null && userUserDO != null) {
            if (StringUtils.isNotBlank(userUserDO.getEmail())) {
                user.setEmail(userUserDO.getEmail());
            }
            if (StringUtils.isNotBlank(userUserDO.getName())) {
                user.setName(userUserDO.getName());
            }
            user.setUid(String.valueOf(userUserDO.getId()));
        }
    }

    private void fillUser(User user) {
        fillUser(user, 0);
    }
}
