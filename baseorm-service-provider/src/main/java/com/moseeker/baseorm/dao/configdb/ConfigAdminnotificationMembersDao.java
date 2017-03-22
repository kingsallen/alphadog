package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationMembers;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationMembersRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAdminnotificationMembersDO;

/**
* @author xxx
* ConfigAdminnotificationMembersDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigAdminnotificationMembersDao extends StructDaoImpl<ConfigAdminnotificationMembersDO, ConfigAdminnotificationMembersRecord, ConfigAdminnotificationMembers> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS;
   }
}
