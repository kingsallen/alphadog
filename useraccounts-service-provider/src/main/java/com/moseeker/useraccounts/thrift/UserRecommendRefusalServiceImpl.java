package com.moseeker.useraccounts.thrift;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.dao.struct.userdb.UserRecommendRefusalDO;
import com.moseeker.thrift.gen.useraccounts.service.UserRecommendRefusalService.Iface;
import com.moseeker.useraccounts.service.impl.UserRecommendRefusalService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRecommendRefusalServiceImpl implements Iface {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRecommendRefusalService userRecommendRefusalService;

    @Override
    public void refuseRecommend(UserRecommendRefusalDO userRecommendRefusal) throws TException {
        try {
            userRecommendRefusalService.refuseRecommend(userRecommendRefusal);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public UserRecommendRefusalDO getLastestRecommendRefusal(int userId, int wechatId) throws TException {
        try {
            UserRecommendRefusalDO result = userRecommendRefusalService.getLastestRecommendRefusal(userId,wechatId);
            if(result == null){
                result = new UserRecommendRefusalDO();
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }
}
