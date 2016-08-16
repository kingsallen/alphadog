package com.moseeker.application.dao.impl;

import com.moseeker.application.dao.UserUserDao;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import org.jooq.DSLContext;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 用户数据
 *
 * Created by zzh on 16/8/16.
 */
@Repository
public class UserUserDaoImpl extends BaseDaoImpl<UserUserRecord, UserUser> implements UserUserDao{

    @Override
    protected void initJOOQEntity() {
        this.tableLike = UserUser.USER_USER;
    }

    /**
     * 根据用户ID获取用户
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public UserUserRecord getUserUserRecord(long userId) throws Exception{
        UserUserRecord record = null;
        Connection conn = null;
        try {
            if(userId > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                record = create.selectFrom(UserUser.USER_USER)
                        .where(UserUser.USER_USER.ID.equal(UInteger.valueOf(userId)))
                        .limit(1).fetchOne();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                //do nothing
            }
        }
        return record;
    }

}
