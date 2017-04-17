package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationEvents;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationEventsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAdminnotificationEventsDO;

/**
* @author xxx
* ConfigAdminnotificationEventsDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigAdminnotificationEventsDao extends StructDaoImpl<ConfigAdminnotificationEventsDO, ConfigAdminnotificationEventsRecord, ConfigAdminnotificationEvents> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS;
   }
}
