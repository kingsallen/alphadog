package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigAtsSource;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAtsSourceRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAtsSourceDO;

/**
* @author xxx
* ConfigAtsSourceDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigAtsSourceDao extends StructDaoImpl<ConfigAtsSourceDO, ConfigAtsSourceRecord, ConfigAtsSource> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigAtsSource.CONFIG_ATS_SOURCE;
   }
}
