package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigSysH5StyleTpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysH5StyleTplRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysH5StyleTplDO;

/**
* @author xxx
* ConfigSysH5StyleTplDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysH5StyleTplDao extends StructDaoImpl<ConfigSysH5StyleTplDO, ConfigSysH5StyleTplRecord, ConfigSysH5StyleTpl> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigSysH5StyleTpl.CONFIG_SYS_H5_STYLE_TPL;
   }
}
