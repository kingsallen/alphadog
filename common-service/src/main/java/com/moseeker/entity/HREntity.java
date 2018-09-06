package com.moseeker.entity;

import com.moseeker.baseorm.dao.hrdb.HrWxHrChatListDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import static com.moseeker.entity.exception.HRException.MOBILE_EXIST;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.moseeker.entity.exception.HRException.USER_NOT_EXISTS;
import static com.moseeker.entity.exception.HRException.USER_UPDATEMOBILE_SAMEMOBILE;

/**
 * HR基础业务
 * Created by jack on 28/08/2017.
 */
@Service
public class HREntity {

    @Autowired
    UserHrAccountDao hrAccountDao;

    @Autowired
    UserWxUserDao wxUserDao;

    @Autowired
    HrWxHrChatListDao chatListDao;

    /**
     * 修改HR手机号码
     *
     * @param mobile 手机号码
     * @param hrID   HR账号编号
     * @return true 修改成功 false 修改失败
     * @throws CommonException 异常信息
     */
    public boolean updateMobile(int hrID, String mobile) throws CommonException {
        UserHrAccountDO hr = hrAccountDao.getValidAccount(hrID);
        if (hr == null) {
            throw USER_NOT_EXISTS;
        }
        if (mobile.trim().equals(hr.getMobile().trim())) {
            throw USER_UPDATEMOBILE_SAMEMOBILE;
        }
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(UserHrAccount.USER_HR_ACCOUNT.MOBILE.getName(), mobile.trim())
                .and(new Condition(UserHrAccount.USER_HR_ACCOUNT.ID.getName(), hrID, ValueOp.NEQ))
                .and(new Condition(UserHrAccount.USER_HR_ACCOUNT.DISABLE.getName(), AbleFlag.ENABLE.getValue()));
        int count = hrAccountDao.getCount(queryBuilder.buildQuery());
        if (count > 0) {
            throw MOBILE_EXIST;
        }
        int retryCount = 0;
        while (retryCount < 3) {
            int result = hrAccountDao.updateMobile(hr, mobile);
            if (result == 0) {
                retryCount++;
            } else {
                if(StringUtils.isNullOrEmpty(hr.getRemarkName()) && StringUtils.isNullOrEmpty(hr.getUsername())){
                    UserWxUserDO wxUser = wxUserDao.getWXUserById(hr.getWxuserId());
                    if(wxUser == null || StringUtils.isNullOrEmpty(wxUser.getNickname())){
                        chatListDao.updateWelcomeStatusByHrAccountId(hr.getId());
                    }
                }
                return true;
            }
        }
        return false;
    }
}
