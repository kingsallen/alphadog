package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationGroup;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationGroupRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAdminnotificationGroupDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigAdminnotificationGroupDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigAdminnotificationGroupDao extends JooqCrudImpl<ConfigAdminnotificationGroupDO, ConfigAdminnotificationGroupRecord> {

    public ConfigAdminnotificationGroupDao() {
        super(ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP, ConfigAdminnotificationGroupDO.class);
    }

    public ConfigAdminnotificationGroupDao(TableImpl<ConfigAdminnotificationGroupRecord> table, Class<ConfigAdminnotificationGroupDO> configAdminnotificationGroupDOClass) {
        super(table, configAdminnotificationGroupDOClass);
    }
}
