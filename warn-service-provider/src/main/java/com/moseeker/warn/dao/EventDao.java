package com.moseeker.warn.dao;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.configdb.tables.ConfigAdminnotificationEvents;
import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationEventsRecord;

@Repository
public class EventDao extends BaseDaoImpl<ConfigAdminnotificationEventsRecord, ConfigAdminnotificationEvents> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS;
	}
}
