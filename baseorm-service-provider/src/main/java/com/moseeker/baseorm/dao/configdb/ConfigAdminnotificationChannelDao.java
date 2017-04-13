package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationChannel;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationChannelRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAdminnotificationChannelDO;

/**
* @author xxx
* ConfigAdminnotificationChannelDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigAdminnotificationChannelDao extends StructDaoImpl<ConfigAdminnotificationChannelDO, ConfigAdminnotificationChannelRecord, ConfigAdminnotificationChannel> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigAdminnotificationChannel.CONFIG_ADMINNOTIFICATION_CHANNEL;
   }
}
