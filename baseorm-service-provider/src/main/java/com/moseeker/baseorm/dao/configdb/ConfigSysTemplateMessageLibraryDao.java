package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigSysTemplateMessageLibrary;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateMessageLibraryRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateMessageLibraryDO;

/**
* @author xxx
* ConfigSysTemplateMessageLibraryDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysTemplateMessageLibraryDao extends StructDaoImpl<ConfigSysTemplateMessageLibraryDO, ConfigSysTemplateMessageLibraryRecord, ConfigSysTemplateMessageLibrary> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY;
   }
}
