package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigCronjobsRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigCronjobsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigCronjobsDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigCronjobsDao extends JooqCrudImpl<ConfigCronjobsDO, ConfigCronjobsRecord> {


    public ConfigCronjobsDao(TableImpl<ConfigCronjobsRecord> table, Class<ConfigCronjobsDO> configCronjobsDOClass) {
        super(table, configCronjobsDOClass);
    }
}
