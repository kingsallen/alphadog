package com.moseeker.profile.service.impl.retriveprofile.tasks;

import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.constants.UserSource;
import org.springframework.stereotype.Component;

/**
 * 标记支付宝回收简历生成用户的来源
 * Created by jack on 10/07/2017.
 */
@Component
public class AliPayUserTask extends UserTask {

    @Override
    protected UserUserRecord initUser(UserUserRecord userUserRecord) {
        UserUserRecord userUserRecord1 = super.initUser(userUserRecord);
        userUserRecord1.setSource((short)UserSource.RETRIEVE_PROFILE_ZHIFUBAO.getValue());
        return userUserRecord1;
    }
}
