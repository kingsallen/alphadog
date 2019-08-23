package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import static com.moseeker.baseorm.db.userdb.tables.UserWorkwx.USER_WORKWX;
import com.moseeker.baseorm.db.userdb.tables.records.UserWorkwxRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWorkwxDO;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;


/**
* @author xxx
* UserWorkwxDao
* 2019-06-07
*/
@Repository
public class UserWorkwxDao extends JooqCrudImpl<UserWorkwxDO, UserWorkwxRecord> {

    public UserWorkwxDao() {
        super(USER_WORKWX, UserWorkwxDO.class);
    }

    public UserWorkwxDao(TableImpl<UserWorkwxRecord> table, Class<UserWorkwxDO> UserWorkwxDOClass) {
        super(table, UserWorkwxDOClass);
    }

    public UserWorkwxRecord getWorkwxByCompanyIdAndUserId(int userId,int companyId) {
        UserWorkwxRecord record = null;
        if(userId > 0) {
            record = create.selectFrom(USER_WORKWX)
                    .where(USER_WORKWX.SYSUSER_ID.eq(userId))
                    .and(USER_WORKWX.COMPANY_ID.eq(companyId))
                    .and(USER_WORKWX.DISABLE.notEqual((byte)1))
                    .orderBy(USER_WORKWX.CREATE_TIME.desc())
                    .limit(1)
                    .fetchOne();
        }
        return record;
    }

    public int unbindSysUser(int userId,int companyId) {
        if(userId > 0) {
            HashMap setMap = new HashMap();
            setMap.put(USER_WORKWX.SYSUSER_ID,0);
            return create.update(USER_WORKWX)
                    .set(setMap)
                    .where(USER_WORKWX.SYSUSER_ID.eq(userId))
                    .and(USER_WORKWX.COMPANY_ID.eq(companyId))
                    .and(USER_WORKWX.DISABLE.notEqual((byte) 1))
                    .execute();
        }
        return 0;
    }

    public int delete(int userId,int companyId) {
        if(userId > 0) {
            return create.update(USER_WORKWX)
                    .set(USER_WORKWX.DISABLE,(byte)1)
                    .where(USER_WORKWX.SYSUSER_ID.eq(userId))
                    .and(USER_WORKWX.COMPANY_ID.eq(companyId))
                    .and(USER_WORKWX.DISABLE.notEqual((byte) 1))
                    .execute();
        }
        return 0;
    }

}
