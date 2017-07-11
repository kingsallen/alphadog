package com.moseeker.profile.service.impl.retriveprofile.tasks;

import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.util.SmsSender;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.profile.service.impl.retriveprofile.RetrieveParam;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建密码
 * Created by jack on 10/07/2017.
 */
@Component
public class CreatePasswordTask extends Task {

    @Autowired
    SmsSender smsSender;

    @Autowired
    UserUserDao userUserDao;

    @Override
    protected void handler(RetrieveParam param) throws CommonException {
        String plainPassword = StringUtils.getRandomString(6);
        UserUserRecord userUserRecord = param.getUserUserRecord();
        Map<String, String> params = new HashMap<>();
        params.put("code", plainPassword);
        boolean result = smsSender.sendSMS(userUserRecord.getUsername(),"SMS_5755096",params);
        if (result) {
            userUserRecord.setPassword(MD5Util.encryptSHA(plainPassword));
            userUserDao.updateRecord(userUserRecord);
            logger.info("CreatePasswordExcutor 简历回收，生成用户密码");
        } else {
            logger.error("CreatePasswordTask 发送密码失败!!!=========");
            //WarnService.notify(new RedisException("", int appid, String location, String eventKey));
            //throw ExceptionFactory.buildException(Category.VALIDATION_SMS_FAILTURE);
        }
    }
}
