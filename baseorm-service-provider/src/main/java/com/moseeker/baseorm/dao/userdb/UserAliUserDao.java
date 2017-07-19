package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserAliUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserAliUserRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserAliUserDO;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 *
 * Created by jack on 19/07/2017.
 */
@Repository
public class UserAliUserDao extends JooqCrudImpl<UserAliUserDO, UserAliUserRecord> {

    public UserAliUserDao() {
        super(UserAliUser.USER_ALI_USER, UserAliUserDO.class);
    }

    public UserAliUserDao(TableImpl<UserAliUserRecord> table, Class<UserAliUserDO> userAliUserDOClass) {
        super(table, userAliUserDOClass);
    }

    public UserAliUserRecord getByUserId(int userId) {
        Result<UserAliUserRecord> result = create.selectFrom(UserAliUser.USER_ALI_USER)
                .where(UserAliUser.USER_ALI_USER.USER_ID.eq(userId))
                .limit(1)
                .fetch();
        if (result != null && result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }
}
