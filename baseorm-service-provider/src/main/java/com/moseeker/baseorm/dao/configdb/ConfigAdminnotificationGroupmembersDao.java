package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationGroupmembers;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationGroupmembersRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAdminnotificationGroupmembersDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigAdminnotificationGroupmembersDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigAdminnotificationGroupmembersDao extends JooqCrudImpl<ConfigAdminnotificationGroupmembersDO, ConfigAdminnotificationGroupmembersRecord> {

    public ConfigAdminnotificationGroupmembersDao() {
        super(ConfigAdminnotificationGroupmembers.CONFIG_ADMINNOTIFICATION_GROUPMEMBERS, ConfigAdminnotificationGroupmembersDO.class);
    }

    public ConfigAdminnotificationGroupmembersDao(TableImpl<ConfigAdminnotificationGroupmembersRecord> table, Class<ConfigAdminnotificationGroupmembersDO> configAdminnotificationGroupmembersDOClass) {
        super(table, configAdminnotificationGroupmembersDOClass);
    }
}
