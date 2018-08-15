package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.userdb.UserRecommendRefusalDao;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.userdb.UserRecommendRefusalDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class UserRecommendRefusalService {
    private Logger logger = LoggerFactory.getLogger(UserRecommendRefusalService.class);

    @Autowired
    UserRecommendRefusalDao userRecommendRefusalDao;

    public void refuseRecommend(UserRecommendRefusalDO userRecommendRefusal) throws BIZException {
        if (userRecommendRefusal.getUserId() == 0
                || userRecommendRefusal.getWechtId() == 0) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }
        UserRecommendRefusalDO lastestData = userRecommendRefusalDao.getLastestDataByUserId(userRecommendRefusal.getUserId());

        if (lastestData != null) {
            try {
                Date lastestRefuseTime = DateUtils.shortTimeToDate(lastestData.getRefuseTime());
                Date now = new Date();
                if(now.getTime() <= lastestRefuseTime.getTime()){
                    return;
                }
            } catch (ParseException e) {
                logger.error("refuse time parse error data:"+ JSON.toJSONString(lastestData),e);
                return;
            }
        }


    }

}
