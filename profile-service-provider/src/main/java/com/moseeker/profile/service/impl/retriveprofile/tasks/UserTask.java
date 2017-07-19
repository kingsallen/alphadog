package com.moseeker.profile.service.impl.retriveprofile.tasks;

import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.profile.exception.Category;
import com.moseeker.profile.exception.ExceptionFactory;
import com.moseeker.profile.service.impl.retriveprofile.RetrieveParam;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import com.moseeker.profile.service.impl.serviceutils.ProfilePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户任务。根据username 查询，如果不存在，则添加用户
 * Created by jack on 09/07/2017.
 */
@Component
public class UserTask extends Task {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserUserDao userUserDao;

    @Override
    protected void handler(RetrieveParam param) throws CommonException {
        if (param.getUserUserRecord() != null) {
            UserUserRecord userUserRecord = param.getUserUserRecord();
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredStringValidate("手机号码", userUserRecord.getUsername(), null, null);
            if (!StringUtils.isNullOrEmpty(validateUtil.validate())) {
                throw ExceptionFactory.buildException(Category.VALIDATION_USERNAME_REQUIRED);
            }
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where("username", userUserRecord.getUsername());
            UserUserRecord userUserRecord1 = userUserDao.getRecord(queryBuilder.buildQuery());
            if (userUserRecord1 == null) {
                userUserRecord1 = initUser(userUserRecord);
                try {
                    userUserRecord1 = userUserDao.addRecord(userUserRecord1);
                } catch (Exception e) {
                    reFindUser(param.getProfilePojo());
                    logger.error(e.getMessage(), e);
                }
            }
            param.getProfilePojo().setUserRecord(userUserRecord1);
            param.setUserUserRecord(userUserRecord1);
            param.getUserAliUserRecord().setUserId(userUserRecord1.getId());
        } else {
            throw ExceptionFactory.buildException(Category.VALIDATION_USER_ILLEGAL_PARAM);
        }
    }

    /**
     * 初始化用户信息 支付宝简历回收生成的用户，用户来源和其他简历回收的用来不一样，这个需要重写
     * @param userUserRecord 用户信息
     * @return 用户信息
     */
    protected UserUserRecord initUser(UserUserRecord userUserRecord) {
        UserUserRecord userUserRecord1 = new UserUserRecord();
        userUserRecord1.setUsername(userUserRecord.getUsername());
        if (StringUtils.isNotNullOrEmpty(userUserRecord.getEmail())) {
            userUserRecord1.setEmail(userUserRecord.getEmail());
        }
        if (StringUtils.isNotNullOrEmpty(userUserRecord.getNickname())) {
            userUserRecord1.setNickname(userUserRecord.getNickname());
        }
        if (StringUtils.isNotNullOrEmpty(userUserRecord.getName())) {
            userUserRecord1.setName(userUserRecord.getName());
            if (StringUtils.isNullOrEmpty(userUserRecord1.getNickname())) {
                userUserRecord1.setNickname(userUserRecord.getName());
            }
        }
        userUserRecord1.setPassword("");
        userUserRecord1.setSource((short) UserSource.RETRIEVE_PROFILE.getValue());
        return userUserRecord1;
    }

    /**
     * 重新查找用户信息
     * @param profilePojo
     */
    private void reFindUser(ProfilePojo profilePojo) throws CommonException {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where("username", profilePojo.getUserRecord().getUsername());
        UserUserRecord userUserRecord1 = userUserDao.getRecord(queryBuilder.buildQuery());
        if (userUserRecord1 != null) {
            profilePojo.setUserRecord(userUserRecord1);
        } else {
            throw ExceptionFactory.buildException(Category.PROFILE_USER_NOTEXIST);
        }
    }
}
