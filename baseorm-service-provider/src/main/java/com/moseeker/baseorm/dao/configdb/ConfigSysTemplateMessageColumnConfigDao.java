package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysTemplateMessageColumnConfig;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateMessageColumnConfigRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateMessageColumnConfigDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigSysTemplateMessageColumnConfigDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysTemplateMessageColumnConfigDao extends JooqCrudImpl<ConfigSysTemplateMessageColumnConfigDO, ConfigSysTemplateMessageColumnConfigRecord> {

    public ConfigSysTemplateMessageColumnConfigDao() {
        super(ConfigSysTemplateMessageColumnConfig.CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG, ConfigSysTemplateMessageColumnConfigDO.class);
    }

    public ConfigSysTemplateMessageColumnConfigDao(TableImpl<ConfigSysTemplateMessageColumnConfigRecord> table, Class<ConfigSysTemplateMessageColumnConfigDO> configSysTemplateMessageColumnConfigDOClass) {
        super(table, configSysTemplateMessageColumnConfigDOClass);
    }
}
