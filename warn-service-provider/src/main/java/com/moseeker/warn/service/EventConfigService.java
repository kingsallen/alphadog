package com.moseeker.warn.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationEventsRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.warn.dao.EventDao;
import com.moseeker.warn.dao.MemberDao;
import com.moseeker.warn.dao.NotifyChannelDao;
import com.moseeker.warn.dto.Event;

/**
 * @author ltf
 * 事件配置服务
 */
@Service
public class EventConfigService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EventDao dao;
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private NotifyChannelDao channelDao;
	
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
			resources = dao.getResources(new CommonQuery());
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
