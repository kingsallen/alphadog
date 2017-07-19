package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserAliUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserAliUserRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserAliUserDO;
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
}
