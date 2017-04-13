package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigSysCvTpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysCvTplRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysCvTplDO;

/**
* @author xxx
* ConfigSysCvTplDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysCvTplDao extends StructDaoImpl<ConfigSysCvTplDO, ConfigSysCvTplRecord, ConfigSysCvTpl> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigSysCvTpl.CONFIG_SYS_CV_TPL;
   }
}
