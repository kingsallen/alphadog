package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateMessageLibraryRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateMessageLibraryDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigSysTemplateMessageLibraryDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysTemplateMessageLibraryDao extends JooqCrudImpl<ConfigSysTemplateMessageLibraryDO, ConfigSysTemplateMessageLibraryRecord> {


    public ConfigSysTemplateMessageLibraryDao(TableImpl<ConfigSysTemplateMessageLibraryRecord> table, Class<ConfigSysTemplateMessageLibraryDO> configSysTemplateMessageLibraryDOClass) {
        super(table, configSysTemplateMessageLibraryDOClass);
    }
}
