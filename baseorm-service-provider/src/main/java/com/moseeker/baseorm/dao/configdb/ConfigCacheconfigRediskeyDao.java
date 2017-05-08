package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigCacheconfigRediskey;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigCacheconfigRediskeyRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigCacheconfigRediskeyDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigCacheconfigRediskeyDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigCacheconfigRediskeyDao extends JooqCrudImpl<ConfigCacheconfigRediskeyDO, ConfigCacheconfigRediskeyRecord> {

    public ConfigCacheconfigRediskeyDao() {
        super(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY, ConfigCacheconfigRediskeyDO.class);
    }

    public ConfigCacheconfigRediskeyDao(TableImpl<ConfigCacheconfigRediskeyRecord> table, Class<ConfigCacheconfigRediskeyDO> configCacheconfigRediskeyDOClass) {
        super(table, configCacheconfigRediskeyDOClass);
    }
}
