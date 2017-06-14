package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysCvTpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysCvTplRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysCvTplDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigSysCvTplDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysCvTplDao extends JooqCrudImpl<ConfigSysCvTplDO, ConfigSysCvTplRecord> {

    public ConfigSysCvTplDao() {
        super(ConfigSysCvTpl.CONFIG_SYS_CV_TPL, ConfigSysCvTplDO.class);
    }

    public ConfigSysCvTplDao(TableImpl<ConfigSysCvTplRecord> table, Class<ConfigSysCvTplDO> configSysCvTplDOClass) {
        super(table, configSysCvTplDOClass);
    }
}
