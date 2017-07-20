package com.moseeker.profile.service.impl.retriveprofile.tasks;

import com.moseeker.baseorm.dao.userdb.UserAliUserDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserAliUserRecord;
import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.exception.Category;
import com.moseeker.profile.exception.ExceptionFactory;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import com.moseeker.profile.service.impl.retriveprofile.parameters.UserAliTaskParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 处理userdb.user_ali_user账号信息
 * Created by jack on 19/07/2017.
 */
@Component
public class UserAliTask implements Task<UserAliTaskParam, Boolean> {

    @Autowired
    UserAliUserDao aliUserDao;

    @Override
    public Boolean handler(UserAliTaskParam param) throws CommonException {
        try {
            UserAliUserRecord userAliUserRecord = aliUserDao.getByUserId(param.getUserId());
            if (userAliUserRecord == null) {
                userAliUserRecord = new UserAliUserRecord();
                userAliUserRecord.setUserId(param.getUserId());
                userAliUserRecord.setUid(param.getUid());
                aliUserDao.addRecord(userAliUserRecord);
            } else {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            throw ExceptionFactory.buildException(Category.PROFILE_ALLREADY_EXIST);
        }
    }
}
