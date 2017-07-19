package com.moseeker.profile.service.impl.retriveprofile.tasks;

import com.moseeker.baseorm.dao.userdb.UserAliUserDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserAliUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.profile.exception.Category;
import com.moseeker.profile.exception.ExceptionFactory;
import com.moseeker.profile.service.impl.retriveprofile.RetrieveParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 标记支付宝回收简历生成用户的来源
 * Created by jack on 10/07/2017.
 */
@Component
public class AliPayUserTask extends UserTask {

    @Autowired
    UserAliUserDao aliUserDao;

    @Override
    protected UserUserRecord initUser(UserUserRecord userUserRecord) {
        UserUserRecord userUserRecord1 = super.initUser(userUserRecord);
        if (userUserRecord1.getMobile() == null) {
            userUserRecord1.setMobile(Long.valueOf(userUserRecord1.getUsername()));
        }
        userUserRecord1.setSource((short)UserSource.RETRIEVE_PROFILE_ZHIFUBAO.getValue());
        return userUserRecord1;
    }

    @Override
    protected void handler(RetrieveParam param) throws CommonException {
        super.handler(param);
        if (checkParam(param.getUserAliUserRecord().getUserId(), param.getUserAliUserRecord().getUid())) {
            saveAliUser(param.getUserAliUserRecord().getUserId(), param.getUserAliUserRecord().getUid());
        }
    }

    /**
     * 添加阿里用户信息
     * @param userId C端用户编号
     * @param uid 阿里用户编号
     * @return 是否创建成功 true 成功 false 失败
     * @throws CommonException
     */
    private boolean saveAliUser(int userId, String uid) throws CommonException {
        try {
            UserAliUserRecord userAliUserRecord = aliUserDao.getByUserId(userId);
            if (userAliUserRecord == null) {
                userAliUserRecord = new UserAliUserRecord();
                userAliUserRecord.setUserId(userId);
                userAliUserRecord.setUid(uid);
                aliUserDao.addRecord(userAliUserRecord);
            } else {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw ExceptionFactory.buildException(Category.PROFILE_ALLREADY_EXIST);
        }
    }

    /**
     * 校验参数是否正确
     * @param userId C端用户编号
     * @param uid 阿里用户编号
     * @return
     * @throws CommonException 业务异常
     */
    private boolean checkParam(int userId, String uid) throws CommonException {
        if (userId == 0) {
            throw ExceptionFactory.buildException(com.moseeker.common.exception.Category.PROGRAM_EXCEPTION);
        }
        if (StringUtils.isNullOrEmpty(uid) || uid.length() > 64) {
            throw ExceptionFactory.buildException(Category.VALIDATION_ALI_USER_ILLEGAL_PARAM);
        }
        return true;
    }
}
