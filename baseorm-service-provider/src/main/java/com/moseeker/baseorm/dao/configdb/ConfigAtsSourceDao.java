package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAtsSourceRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAtsSourceDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigAtsSourceDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigAtsSourceDao extends JooqCrudImpl<ConfigAtsSourceDO, ConfigAtsSourceRecord> {


    public ConfigAtsSourceDao(TableImpl<ConfigAtsSourceRecord> table, Class<ConfigAtsSourceDO> configAtsSourceDOClass) {
        super(table, configAtsSourceDOClass);
    }
}
