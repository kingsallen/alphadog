package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationMembers;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationMembersRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAdminnotificationMembersDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigAdminnotificationMembersDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigAdminnotificationMembersDao extends JooqCrudImpl<ConfigAdminnotificationMembersDO, ConfigAdminnotificationMembersRecord> {

    public ConfigAdminnotificationMembersDao() {
        super(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS, ConfigAdminnotificationMembersDO.class);
    }

    public ConfigAdminnotificationMembersDao(TableImpl<ConfigAdminnotificationMembersRecord> table, Class<ConfigAdminnotificationMembersDO> configAdminnotificationMembersDOClass) {
        super(table, configAdminnotificationMembersDOClass);
    }
}
