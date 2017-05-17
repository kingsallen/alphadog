package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.userdb.tables.UserSettings;
import com.moseeker.baseorm.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserSettingsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

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

    public int updateProfileUpdateTime(List<Integer> idArray) {
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());
        return create.update(ProfileProfile.PROFILE_PROFILE)
                .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                .where(ProfileProfile.PROFILE_PROFILE.USER_ID
                        .in(create.select(UserSettings.USER_SETTINGS.USER_ID)
                                .from(UserSettings.USER_SETTINGS)
                                .where(UserSettings.USER_SETTINGS.ID.in(idArray))))
                .execute();
    }

    public int updateProfileUpdateTimeByUserId(List<Integer> idArray) {
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());
        return create.update(ProfileProfile.PROFILE_PROFILE)
                .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                .where(ProfileProfile.PROFILE_PROFILE.USER_ID.in(idArray))
                .execute();
    }
}
