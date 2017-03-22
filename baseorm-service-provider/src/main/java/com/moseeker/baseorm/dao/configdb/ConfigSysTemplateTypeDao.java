package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigSysTemplateType;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateTypeRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateTypeDO;

/**
* @author xxx
* ConfigSysTemplateTypeDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysTemplateTypeDao extends StructDaoImpl<ConfigSysTemplateTypeDO, ConfigSysTemplateTypeRecord, ConfigSysTemplateType> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigSysTemplateType.CONFIG_SYS_TEMPLATE_TYPE;
   }
}
