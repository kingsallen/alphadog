package com.moseeker.baseorm.dao.configdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationMembers;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationMembersRecord;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAdminnotificationMembersDO;

/**
* @author xxx
* ConfigAdminnotificationMembersDao 实现类 （groovy 生成）
* 2017-05-05
*/
@Repository
public class ConfigAdminnotificationMembersDao extends JooqCrudImpl<ConfigAdminnotificationMembersDO, ConfigAdminnotificationMembersRecord> {

   public ConfigAdminnotificationMembersDao() {
        super(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS, ConfigAdminnotificationMembersDO.class);
   }
}
