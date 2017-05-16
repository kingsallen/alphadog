package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
* @author xxx
* UserUserDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserUserDao extends JooqCrudImpl<UserUserDO, UserUserRecord> {

    public UserUserDao() {
        super(UserUser.USER_USER, UserUserDO.class);
    }

    public UserUserDao(TableImpl<UserUserRecord> table, Class<UserUserDO> userUserDOClass) {
        super(table, userUserDOClass);
    }

    public List<UserUserRecord> getUserByIds(List<Integer> cityCodes) {

        List<UserUserRecord> records = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            SelectWhereStep<UserUserRecord> select = create.selectFrom(UserUser.USER_USER);
            SelectConditionStep<UserUserRecord> selectCondition = null;
            if (cityCodes != null && cityCodes.size() > 0) {
                for (int i = 0; i < cityCodes.size(); i++) {
                    if (i == 0) {
                        selectCondition = select.where(UserUser.USER_USER.ID.equal((int)(cityCodes.get(i))));
                    } else {
                        selectCondition.or(UserUser.USER_USER.ID.equal((int)(cityCodes.get(i))));
                    }
                }
            }
            records = selectCondition.fetch();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                // do nothing
            }
        }

        return records;
    }

    public UserUserRecord getUserById(int id) {
        UserUserRecord record = null;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            Result<UserUserRecord> result = create.selectFrom(UserUser.USER_USER)
                    .where(UserUser.USER_USER.ID.equal((int)(id)))
                    .and(UserUser.USER_USER.IS_DISABLE.equal((byte) 0)).fetch();
            if (result != null && result.size() > 0) {
                record = result.get(0);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                // do nothing
            }
        }

        return record;
    }
}
