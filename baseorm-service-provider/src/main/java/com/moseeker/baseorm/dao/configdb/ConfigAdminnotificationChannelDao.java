package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationChannelRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAdminnotificationChannelDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigAdminnotificationChannelDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigAdminnotificationChannelDao extends JooqCrudImpl<ConfigAdminnotificationChannelDO, ConfigAdminnotificationChannelRecord> {


    public ConfigAdminnotificationChannelDao(TableImpl<ConfigAdminnotificationChannelRecord> table, Class<ConfigAdminnotificationChannelDO> configAdminnotificationChannelDOClass) {
        super(table, configAdminnotificationChannelDOClass);
    }
}
