package com.moseeker.warn.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import com.moseeker.baseorm.dao.configdb.ConfigAdminnotificationChannelDao;
import com.moseeker.baseorm.dao.configdb.ConfigAdminnotificationEventsDao;
import com.moseeker.baseorm.dao.configdb.ConfigAdminnotificationMembersDao;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationEventsRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ltf
 * 事件配置服务
 */
@Service
public class EventConfigService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ConfigAdminnotificationEventsDao dao;

	@Autowired
	private ConfigAdminnotificationMembersDao memberDao;

	@Autowired
	private ConfigAdminnotificationChannelDao channelDao;
	
	/**
	 * 事件信息集合［K=projectAppid_eventKey, V={@link Event}］
	 */
	private HashMap<String, Event> eventMap = new HashMap<String, Event>();
	
	/**
	 * 初始化事件信息
	 */
	@PostConstruct
	public void init() {
		eventMap.clear();
		List<ConfigAdminnotificationEventsRecord> resources;
		try {
			resources = dao.getRecords(null);
			resources.forEach(record -> {
				// 封装事件信息
				Event event = new Event(record.getId(), record.getProjectAppid(), record.getEventKey(), record.getEventName(),
						record.getEventDesc(), record.getThresholdValue(), record.getThresholdInterval());
				// 查询事件通知人员
				event.setMembers(memberDao.getMembers(record.getGroupid()));
				eventMap.put(event.getProjectAppid().concat("_").concat(event.getEventKey()), event);
				// 查询通知渠道
				event.setNotifyChannels(channelDao.getChannels(event.getId()));
			});
		} catch (Exception e) {
			log.error("load event error ...", e);
		}
	}
	
	/**
	 * 获取event列表集合
	 * @return
	 */
	public HashMap<String, Event> getEvents(){
		return eventMap; 
	}
}
