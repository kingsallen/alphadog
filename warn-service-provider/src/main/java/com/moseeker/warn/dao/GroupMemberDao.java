package com.moseeker.warn.dao;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.configdb.tables.ConfigAdminnotificationGroupmembers;
import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationGroupmembersRecord;

@Repository
public class GroupMemberDao extends BaseDaoImpl<ConfigAdminnotificationGroupmembersRecord, ConfigAdminnotificationGroupmembers> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ConfigAdminnotificationGroupmembers.CONFIG_ADMINNOTIFICATION_GROUPMEMBERS;
	}
}
