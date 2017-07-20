package com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow;

import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.constants.UserSource;
import com.moseeker.profile.service.impl.retriveprofile.parameters.UserTaskParam;
import com.moseeker.profile.service.impl.retriveprofile.tasks.UserTask;
import org.springframework.stereotype.Component;

/**
 * 标记支付宝回收简历生成用户的来源
 * Created by jack on 10/07/2017.
 */
@Component
public class UserTaskAliPaySource extends UserTask {

    /**
     * 标记用户来源，标记为阿里简历回收
     * @param param 参数信息
     * @return
     */
    @Override
    protected UserUserRecord initUser(UserTaskParam param) {
        UserUserRecord userUserRecord1 = super.initUser(param);
        userUserRecord1.setSource((short)UserSource.RETRIEVE_PROFILE_ZHIFUBAO.getValue());
        return userUserRecord1;
    }
}
