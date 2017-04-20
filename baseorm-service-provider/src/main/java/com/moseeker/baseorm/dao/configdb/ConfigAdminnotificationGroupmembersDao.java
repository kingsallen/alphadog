package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationGroupmembers;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationGroupmembersRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAdminnotificationGroupmembersDO;

/**
* @author xxx
* ConfigAdminnotificationGroupmembersDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigAdminnotificationGroupmembersDao extends StructDaoImpl<ConfigAdminnotificationGroupmembersDO, ConfigAdminnotificationGroupmembersRecord, ConfigAdminnotificationGroupmembers> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigAdminnotificationGroupmembers.CONFIG_ADMINNOTIFICATION_GROUPMEMBERS;
   }
}
