package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserIntention;
import com.moseeker.baseorm.db.userdb.tables.records.UserIntentionRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserIntentionDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* UserIntentionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserIntentionDao extends JooqCrudImpl<UserIntentionDO, UserIntentionRecord> {

    public UserIntentionDao() {
        super(UserIntention.USER_INTENTION, UserIntentionDO.class);
    }

    public UserIntentionDao(TableImpl<UserIntentionRecord> table, Class<UserIntentionDO> userIntentionDOClass) {
        super(table, userIntentionDOClass);
    }
}
