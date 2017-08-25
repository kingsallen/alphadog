package com.moseeker.profile.service.impl.retriveprofile.flow;

import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.util.query.Query;
import com.moseeker.profile.service.impl.retriveprofile.parameters.UserTaskParam;
import com.moseeker.profile.service.impl.retriveprofile.tasks.UserTask;
import org.springframework.stereotype.Component;

/**
 * 普通用户
 * Created by jack on 24/08/2017.
 */
@Component
public class NomalUserTask extends UserTask {

    @Override
    protected Query initQuery(UserTaskParam param) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where("mobile", param.getMobile()).and("source", UserSource.RETRIEVE_PROFILE.getValue());
        return queryBuilder.buildQuery();
    }

    /**
     * 标记用户来源，标记为阿里简历回收
     * @param param 参数信息
     * @return
     */
    @Override
    protected UserUserRecord initUser(UserTaskParam param) {
        UserUserRecord userUserRecord1 = super.initUser(param);
        userUserRecord1.setSource((short) UserSource.RETRIEVE_PROFILE.getValue());
        return userUserRecord1;
    }
}
