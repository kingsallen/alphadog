package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigSysTemplateMessageColumnConfig;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateMessageColumnConfigRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateMessageColumnConfigDO;

/**
* @author xxx
* ConfigSysTemplateMessageColumnConfigDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysTemplateMessageColumnConfigDao extends StructDaoImpl<ConfigSysTemplateMessageColumnConfigDO, ConfigSysTemplateMessageColumnConfigRecord, ConfigSysTemplateMessageColumnConfig> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigSysTemplateMessageColumnConfig.CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG;
   }
}
