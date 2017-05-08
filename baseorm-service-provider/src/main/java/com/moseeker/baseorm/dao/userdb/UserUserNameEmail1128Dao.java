package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserUserNameEmail1128;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserNameEmail1128Record;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserNameEmail1128DO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* UserUserNameEmail1128Dao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserUserNameEmail1128Dao extends JooqCrudImpl<UserUserNameEmail1128DO, UserUserNameEmail1128Record> {

    public UserUserNameEmail1128Dao() {
        super(UserUserNameEmail1128.USER_USER_NAME_EMAIL1128, UserUserNameEmail1128DO.class);
    }

    public UserUserNameEmail1128Dao(TableImpl<UserUserNameEmail1128Record> table, Class<UserUserNameEmail1128DO> userUserNameEmail1128DOClass) {
        super(table, userUserNameEmail1128DOClass);
    }
}
