package com.moseeker.warn.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.configdb.tables.ConfigAdminnotificationChannel;
import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationChannelRecord;

@Repository
public class NotifyChannelDao extends BaseDaoImpl<ConfigAdminnotificationChannelRecord, ConfigAdminnotificationChannel> {
	
	private  Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	protected void initJOOQEntity() {
		this.tableLike = ConfigAdminnotificationChannel.CONFIG_ADMINNOTIFICATION_CHANNEL;
	}
	
	/**
	 * 获取发送渠道列表
	 * @param eventId
	 * @return
	 */
	public List<String> getChannels(Integer eventId) {
		DSLContext jooqDSL;
		List<String> list = new ArrayList<String>();
		try(Connection conn = DBConnHelper.DBConn.getConn()) {
			jooqDSL = DBConnHelper.DBConn.getJooqDSL(conn);
			list = jooqDSL.selectFrom(ConfigAdminnotificationChannel.CONFIG_ADMINNOTIFICATION_CHANNEL).where(ConfigAdminnotificationChannel.CONFIG_ADMINNOTIFICATION_CHANNEL.ENVENT_ID.eq(eventId)).fetch(ConfigAdminnotificationChannel.CONFIG_ADMINNOTIFICATION_CHANNEL.CHANNEL);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

}
