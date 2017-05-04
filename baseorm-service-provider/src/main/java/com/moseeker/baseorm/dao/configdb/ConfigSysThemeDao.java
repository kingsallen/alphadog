package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysThemeRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysThemeDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigSysThemeDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysThemeDao extends JooqCrudImpl<ConfigSysThemeDO, ConfigSysThemeRecord> {


    public ConfigSysThemeDao(TableImpl<ConfigSysThemeRecord> table, Class<ConfigSysThemeDO> configSysThemeDOClass) {
        super(table, configSysThemeDOClass);
    }
}
