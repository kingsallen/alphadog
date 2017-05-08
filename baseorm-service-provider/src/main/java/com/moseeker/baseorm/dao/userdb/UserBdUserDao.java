package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserBdUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserBdUserRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserBdUserDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* UserBdUserDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserBdUserDao extends JooqCrudImpl<UserBdUserDO, UserBdUserRecord> {

    public UserBdUserDao() {
        super(UserBdUser.USER_BD_USER, UserBdUserDO.class);
    }

    public UserBdUserDao(TableImpl<UserBdUserRecord> table, Class<UserBdUserDO> userBdUserDOClass) {
        super(table, userBdUserDOClass);
    }
}
