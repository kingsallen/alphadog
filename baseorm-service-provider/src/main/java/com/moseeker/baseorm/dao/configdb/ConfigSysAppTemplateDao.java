package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigSysAppTemplate;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysAppTemplateRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysAppTemplateDO;

/**
* @author xxx
* ConfigSysAppTemplateDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysAppTemplateDao extends StructDaoImpl<ConfigSysAppTemplateDO, ConfigSysAppTemplateRecord, ConfigSysAppTemplate> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE;
   }
}
