package com.moseeker.warn.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.ws.handler.LogicalHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.proxy.Log;
import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationEventsRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.warn.dao.EventDao;
import com.moseeker.warn.dto.Event;

/**
 * @author lucky8987
 * 事件配置服务
 */
@Service
public class EventConfigService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EventDao dao;
	
	public static HashMap<String, Event> eventMap = new HashMap<String, Event>();
	
	/**
	 * 初始化事件信息
	 */
	@PostConstruct
	public void init() {
		try {
			List<ConfigAdminnotificationEventsRecord> resources = dao.getResources(new CommonQuery());
			resources.forEach(record -> {
				// 封装事件信息
				Event event = new Event(record.getProjectAppid(), record.getEventKey(), record.getEventName(),
						record.getEventDesc(), record.getThresholdValue(), record.getThresholdInterval(), record.getEnableNotifybyEmail() == 1 ? true : false, 
						record.getEnableNotifybySms() == 1 ? true : false, record.getEnableNotifybyWechattemplatemessage() == 1 ? true : false);
				// 查询事件通知人员
				
				
			});
		} catch (Exception e) {
			log.info("load event error ...");
			log.error(e.getMessage(), e);
		}
	}
	
}
