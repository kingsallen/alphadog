package com.moseeker.warn.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jooq.DSLContext;
import org.jooq.SelectWhereStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.db.configdb.tables.ConfigAdminnotificationGroupmembers;
import com.moseeker.db.configdb.tables.ConfigAdminnotificationMembers;
import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationEventsRecord;
import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationGroupmembersRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.warn.dao.EventDao;
import com.moseeker.warn.dto.Event;
import com.moseeker.warn.dto.Member;

/**
 * @author lucky8987
 * 事件配置服务
 */
@Service
public class EventConfigService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EventDao dao;
	
	/**
	 * 事件信息集合［K=projectAppid_eventKey, V={@link Event}］
	 */
	public static HashMap<String, Event> eventMap = new HashMap<String, Event>();
	
	/**
	 * 初始化事件信息
	 */
	@PostConstruct
	public void init() {
		eventMap.clear();
		try {
			List<ConfigAdminnotificationEventsRecord> resources = dao.getResources(new CommonQuery());
			resources.forEach(record -> {
				// 封装事件信息
				Event event = new Event(record.getProjectAppid(), record.getEventKey(), record.getEventName(),
						record.getEventDesc(), record.getThresholdValue(), record.getThresholdInterval(), record.getEnableNotifybyEmail() == 1 ? true : false, 
						record.getEnableNotifybySms() == 1 ? true : false, record.getEnableNotifybyWechattemplatemessage() == 1 ? true : false);
				// 查询事件通知人员
				try {
					DSLContext jooqDSL = DBConnHelper.DBConn.getJooqDSL(DBConnHelper.DBConn.getConn());
					SelectWhereStep<ConfigAdminnotificationGroupmembersRecord> selectFrom = jooqDSL.selectFrom(ConfigAdminnotificationGroupmembers.CONFIG_ADMINNOTIFICATION_GROUPMEMBERS);
					event.setMembers(jooqDSL.selectFrom(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS).whereExists(selectFrom).fetch().into(Member.class));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				eventMap.put(event.getProjectAppid().concat("_").concat(event.getEventKey()), event);
			});
		} catch (Exception e) {
			log.info("load event error ...");
			log.error(e.getMessage(), e);
		}
	}
	
}
