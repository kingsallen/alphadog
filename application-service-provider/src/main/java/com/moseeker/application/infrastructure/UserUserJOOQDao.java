package com.moseeker.application.infrastructure;

import com.moseeker.baseorm.db.userdb.tables.UserUser;
import java.util.List;
import org.jooq.Configuration;
import static org.jooq.impl.DSL.using;

/**
 * 由于历史遗留问题导致 UserHrAccountDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 * Created by jack on 16/01/2018.
 */
public class UserUserJOOQDao extends com.moseeker.baseorm.db.userdb.tables.daos.UserUserDao {

    public UserUserJOOQDao(Configuration configuration) {
        super(configuration);
    }


    /**
     *
     * @param userIdList
     * @return
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserUser> fetchUserByID(List<Integer> userIdList) {

        List<com.moseeker.baseorm.db.userdb.tables.pojos.UserUser> userUser = using(configuration())
                .selectFrom(UserUser.USER_USER)
                .where(UserUser.USER_USER.ID.in(userIdList))
                .fetchInto(com.moseeker.baseorm.db.userdb.tables.pojos.UserUser.class);

        return userUser;
    }

}
