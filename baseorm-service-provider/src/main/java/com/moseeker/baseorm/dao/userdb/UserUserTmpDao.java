package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserUserTmp;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserTmpRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserTmpDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* UserUserTmpDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserUserTmpDao extends JooqCrudImpl<UserUserTmpDO, UserUserTmpRecord> {

    public UserUserTmpDao() {
        super(UserUserTmp.USER_USER_TMP, UserUserTmpDO.class);
    }

    public UserUserTmpDao(TableImpl<UserUserTmpRecord> table, Class<UserUserTmpDO> userUserTmpDOClass) {
        super(table, userUserTmpDOClass);
    }
}
