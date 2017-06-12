package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysH5StyleTpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysH5StyleTplRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysH5StyleTplDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigSysH5StyleTplDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysH5StyleTplDao extends JooqCrudImpl<ConfigSysH5StyleTplDO, ConfigSysH5StyleTplRecord> {

    public ConfigSysH5StyleTplDao() {
        super(ConfigSysH5StyleTpl.CONFIG_SYS_H5_STYLE_TPL, ConfigSysH5StyleTplDO.class);
    }

    public ConfigSysH5StyleTplDao(TableImpl<ConfigSysH5StyleTplRecord> table, Class<ConfigSysH5StyleTplDO> configSysH5StyleTplDOClass) {
        super(table, configSysH5StyleTplDOClass);
    }
}
