package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigCronjobs;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigCronjobsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigCronjobsDO;

/**
* @author xxx
* ConfigCronjobsDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigCronjobsDao extends StructDaoImpl<ConfigCronjobsDO, ConfigCronjobsRecord, ConfigCronjobs> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigCronjobs.CONFIG_CRONJOBS;
   }
}
