package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* UserUserDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserUserDao extends JooqCrudImpl<UserUserDO, UserUserRecord> {


    public UserUserDao(TableImpl<UserUserRecord> table, Class<UserUserDO> userUserDOClass) {
        super(table, userUserDOClass);
    }
}
