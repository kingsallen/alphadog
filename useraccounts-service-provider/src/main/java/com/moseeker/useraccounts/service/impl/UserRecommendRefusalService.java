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

    /**
     * C端用户拒绝某个公众号推荐职位
     * @param userRecommendRefusal 参数
     * @throws BIZException
     */
    public void refuseRecommend(UserRecommendRefusalDO userRecommendRefusal) throws BIZException {
        if (userRecommendRefusal.getUserId() == 0
                || userRecommendRefusal.getWechatId() == 0) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }
        userRecommendRefusalDao.insertData(userRecommendRefusal);
    }

    /**
     * 获取最近一条拒绝推荐记录
     * @param userId
     * @param wechatId
     * @return
     */
    public UserRecommendRefusalDO getLastestRecommendRefusal(int userId,int wechatId) throws BIZException {
        if(userId ==0 || wechatId == 0){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }
        return userRecommendRefusalDao.getLastestRecommendRefusal(userId,wechatId);
    }
}
