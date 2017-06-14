package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationEvents;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationEventsRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAdminnotificationEventsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigAdminnotificationEventsDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigAdminnotificationEventsDao extends JooqCrudImpl<ConfigAdminnotificationEventsDO, ConfigAdminnotificationEventsRecord> {


    public ConfigAdminnotificationEventsDao() {
        super(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS, ConfigAdminnotificationEventsDO.class);
    }

    public ConfigAdminnotificationEventsDao(TableImpl<ConfigAdminnotificationEventsRecord> table, Class<ConfigAdminnotificationEventsDO> configAdminnotificationEventsDOClass) {
        super(table, configAdminnotificationEventsDOClass);
    }
}
