package com.moseeker.profile.service.impl.retriveprofile.tasks;

import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.util.SmsSender;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.profile.exception.Category;
import com.moseeker.profile.exception.ExceptionFactory;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import com.moseeker.profile.service.impl.retriveprofile.parameters.CratePasswordTaskParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建密码
 * 密码发送失败，不影响整体流程
 * Created by jack on 10/07/2017.
 */
@Component
public class CreatePasswordTask implements Task<CratePasswordTaskParam, Boolean> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SmsSender smsSender;

    @Autowired
    UserUserDao userUserDao;

    public Boolean handler(CratePasswordTaskParam param) throws CommonException {
        if (param != null && param.getUserId() > 0) {
            UserUserRecord userUserRecord = userUserDao.getUserById(param.getUserId());
            if (userUserRecord == null) {
                throw ExceptionFactory.buildException(Category.VALIDATION_USER_ILLEGAL_PARAM);
            }
            if (StringUtils.isNullOrEmpty(userUserRecord.getPassword())) {
                String plainPassword = StringUtils.getRandomString(6);
                Map<String, String> params = new HashMap<>();
                params.put("name", param.getMobile());
                params.put("code", plainPassword);
                boolean result = smsSender.sendSMS(param.getMobile(),"SMS_5895237",params);
                if (result) {
                    userUserRecord.setId(param.getUserId());
                    userUserRecord.setPassword(MD5Util.encryptSHA(plainPassword));
                    userUserDao.updateRecord(userUserRecord);
                    logger.info("CreatePasswordExcutor 简历回收，生成用户密码");
                    return Boolean.TRUE;
                } else {
                    logger.error("CreatePasswordTask 发送密码失败!!!=========");
                }
            }
        }
        return Boolean.FALSE;
    }
}
