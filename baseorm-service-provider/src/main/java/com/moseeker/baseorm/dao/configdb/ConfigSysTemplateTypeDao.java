package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateTypeRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateTypeDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigSysTemplateTypeDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysTemplateTypeDao extends JooqCrudImpl<ConfigSysTemplateTypeDO, ConfigSysTemplateTypeRecord> {


    public ConfigSysTemplateTypeDao(TableImpl<ConfigSysTemplateTypeRecord> table, Class<ConfigSysTemplateTypeDO> configSysTemplateTypeDOClass) {
        super(table, configSysTemplateTypeDOClass);
    }
}
