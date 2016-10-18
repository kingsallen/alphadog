package com.moseeker.warn.dao;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.configdb.tables.ConfigAdminnotificationMembers;
import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationMembersRecord;

@Repository
public class MemberDao extends BaseDaoImpl<ConfigAdminnotificationMembersRecord, ConfigAdminnotificationMembers> {
	
	@Override
	protected void initJOOQEntity() {
		this.tableLike = ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS;
	}
	

}
