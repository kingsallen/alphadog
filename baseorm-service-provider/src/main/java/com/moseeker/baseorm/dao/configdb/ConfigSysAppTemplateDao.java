package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysAppTemplateRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysAppTemplateDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigSysAppTemplateDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysAppTemplateDao extends JooqCrudImpl<ConfigSysAppTemplateDO, ConfigSysAppTemplateRecord> {


    public ConfigSysAppTemplateDao(TableImpl<ConfigSysAppTemplateRecord> table, Class<ConfigSysAppTemplateDO> configSysAppTemplateDOClass) {
        super(table, configSysAppTemplateDOClass);
    }
}
