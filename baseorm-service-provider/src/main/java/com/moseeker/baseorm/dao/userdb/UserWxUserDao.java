package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

/**
* @author xxx
* UserWxUserDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserWxUserDao extends JooqCrudImpl<UserWxUserDO, UserWxUserRecord> {

    public UserWxUserDao() {
        super(UserWxUser.USER_WX_USER, UserWxUserDO.class);
    }

    public UserWxUserDao(TableImpl<UserWxUserRecord> table, Class<UserWxUserDO> userWxUserDOClass) {
        super(table, userWxUserDOClass);
    }

    public UserWxUserRecord getWXUserByUserId(int userId) throws SQLException {
        UserWxUserRecord wxuser = null;
        if(userId > 0) {
            try (
                    Connection conn = DBConnHelper.DBConn.getConn();
                    DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            ) {
                wxuser = create.selectFrom(UserWxUser.USER_WX_USER).where(UserWxUser.USER_WX_USER.SYSUSER_ID.equal(userId)).limit(1).fetchOne();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return wxuser;
    }
}
