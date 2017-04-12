package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigCacheconfigRediskey;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigCacheconfigRediskeyRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigCacheconfigRediskeyDO;

/**
* @author xxx
* ConfigCacheconfigRediskeyDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigCacheconfigRediskeyDao extends StructDaoImpl<ConfigCacheconfigRediskeyDO, ConfigCacheconfigRediskeyRecord, ConfigCacheconfigRediskey> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY;
   }
}
