package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysAdministratorRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysAdministratorDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigSysAdministratorDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysAdministratorDao extends JooqCrudImpl<ConfigSysAdministratorDO, ConfigSysAdministratorRecord> {


    public ConfigSysAdministratorDao(TableImpl<ConfigSysAdministratorRecord> table, Class<ConfigSysAdministratorDO> configSysAdministratorDOClass) {
        super(table, configSysAdministratorDOClass);
    }
}
