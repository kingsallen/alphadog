package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationGroup;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationGroupRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAdminnotificationGroupDO;

/**
* @author xxx
* ConfigAdminnotificationGroupDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigAdminnotificationGroupDao extends StructDaoImpl<ConfigAdminnotificationGroupDO, ConfigAdminnotificationGroupRecord, ConfigAdminnotificationGroup> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP;
   }
}
