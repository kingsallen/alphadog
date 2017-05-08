package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserSettings;
import com.moseeker.baseorm.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserSettingsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

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
}
