package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserWorkwx;
import com.moseeker.baseorm.db.userdb.tables.records.UserWorkwxRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWorkwxDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;


/**
* @author xxx
* UserWorkwxDao
* 2019-06-07
*/
@Repository
public class UserWorkwxDao extends JooqCrudImpl<UserWorkwxDO, UserWorkwxRecord> {

    public UserWorkwxDao() {
        super(UserWorkwx.USER_WORKWX, UserWorkwxDO.class);
    }

    public UserWorkwxDao(TableImpl<UserWorkwxRecord> table, Class<UserWorkwxDO> UserWorkwxDOClass) {
        super(table, UserWorkwxDOClass);
    }

    public UserWorkwxRecord getWorkwxByCompanyIdAndUserId(int userId,int companyId) {
        UserWorkwxRecord record = null;
        if(userId > 0) {
            record = create.selectFrom(UserWorkwx.USER_WORKWX)
                    .where(UserWorkwx.USER_WORKWX.SYSUSER_ID.eq(userId))
                    .and(UserWorkwx.USER_WORKWX.COMPANY_ID.eq(companyId))
                    .and(UserWorkwx.USER_WORKWX.DISABLE.notEqual((byte)1))
                    .orderBy(UserWorkwx.USER_WORKWX.CREATE_TIME.desc())
                    .limit(1)
                    .fetchOne();
        }
        return record;
    }


}
