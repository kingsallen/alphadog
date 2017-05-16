package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserSettings;
import com.moseeker.baseorm.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.userdb.UserSettingsDO;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

/**
* @author xxx
* UserSettingsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserSettingsDao extends JooqCrudImpl<UserSettingsDO, UserSettingsRecord> {

    public UserSettingsDao() {
        super(UserSettings.USER_SETTINGS, UserSettingsDO.class);
    }

    public UserSettingsDao(TableImpl<UserSettingsRecord> table, Class<UserSettingsDO> userSettingsDOClass) {
        super(table, userSettingsDOClass);
    }

    public UserSettingsRecord getUserSettingsById(int userId) {
        UserSettingsRecord record = null;
        Connection conn = null;
        try {
            if(userId > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                Result<UserSettingsRecord> result = create.selectFrom(UserSettings.USER_SETTINGS)
                        .where(UserSettings.USER_SETTINGS.USER_ID.equal((int)(userId)))
                        .limit(1).fetch();
                if(result != null && result.size() > 0) {
                    record = result.get(0);
                }
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
