package com.moseeker.baseorm.dao.userdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.userdb.tables.UserSettings;
import com.moseeker.baseorm.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.userdb.UserSettingsDO;

/**
* @author xxx
* UserSettingsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserSettingsDao extends StructDaoImpl<UserSettingsDO, UserSettingsRecord, UserSettings> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = UserSettings.USER_SETTINGS;
   }
}
