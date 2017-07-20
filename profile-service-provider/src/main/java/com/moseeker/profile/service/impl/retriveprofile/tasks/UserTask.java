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
import com.moseeker.profile.service.impl.retriveprofile.Task;
import com.moseeker.profile.service.impl.retriveprofile.parameters.UserTaskParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户任务。根据username 查询，如果不存在，则添加用户
 * Created by jack on 09/07/2017.
 */
@Component
public class UserTask implements Task<UserTaskParam, Integer> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserUserDao userUserDao;

    public Integer handler(UserTaskParam param) throws CommonException {
        if (param != null) {
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredStringValidate("手机号码", param.getMobile(), null, null);
            if (!StringUtils.isNullOrEmpty(validateUtil.validate())) {
                throw ExceptionFactory.buildException(Category.VALIDATION_USERNAME_REQUIRED);
            }
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where("username", param.getMobile());
            UserUserRecord userUserRecord1 = userUserDao.getRecord(queryBuilder.buildQuery());
            if (userUserRecord1 == null) {
                userUserRecord1 = initUser(param);
                try {
                    return userUserDao.addRecord(userUserRecord1).getId();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    /** 添加出错，在重新查找一次用户信息 */
                    return reFindUser(param.getMobile()).getId();
                }
            } else {
                return userUserRecord1.getId();
            }
        } else {
            throw ExceptionFactory.buildException(Category.VALIDATION_USER_ILLEGAL_PARAM);
        }
    }

    /**
     * 初始化用户信息 支付宝简历回收生成的用户，用户来源和其他简历回收的用来不一样，这个需要重写
     * @param param 参数信息
     * @return 用户信息
     */
    protected UserUserRecord initUser(UserTaskParam param) {
        UserUserRecord userUserRecord = new UserUserRecord();
        userUserRecord.setUsername(param.getMobile());
        if (StringUtils.isNotNullOrEmpty(param.getEmail())) {
            userUserRecord.setEmail(param.getEmail());
        }
        if (StringUtils.isNotNullOrEmpty(param.getName())) {
            userUserRecord.setName(param.getName());
            if (StringUtils.isNullOrEmpty(userUserRecord.getNickname())) {
                userUserRecord.setNickname(param.getName());
            }
        }
        if (userUserRecord.getMobile() == null) {
            userUserRecord.setMobile(Long.valueOf(userUserRecord.getUsername()));
        }
        userUserRecord.setPassword("");
        userUserRecord.setSource((short) UserSource.RETRIEVE_PROFILE.getValue());
        return userUserRecord;
    }

    /**
     * 重新查找用户信息
     * @param username
     */
    private UserUserRecord reFindUser(String username) throws CommonException {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where("username", username);
        UserUserRecord userUserRecord1 = userUserDao.getRecord(queryBuilder.buildQuery());
        if (userUserRecord1 != null) {
            return userUserRecord1;
        } else {
            throw ExceptionFactory.buildException(Category.PROFILE_USER_NOTEXIST);
        }
    }
}
