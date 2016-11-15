package com.moseeker.warn.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.warn.struct.WarnBean;
import com.moseeker.warn.dto.Event;
import com.moseeker.warn.dto.WarnMsg;

/**
 * @author ltf
 * 预警消息校验 - 频率控制
 * 2016年11月10日
 */
@Service
public class ValidationService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 发送记录[appid_eventkey:send_time]
	 */
	private static Map<String, Long> sendLog = new HashMap<String, Long>();
	
	private static final Object SEND_LOCK = new Object();
	
	@Autowired
	private EventConfigService evenConfig;
	
	@Autowired
	private ManageService manageService;
	
	public  void valid(WarnBean warnInfo) {
		if (warnInfo != null) {
			String warnKey = warnInfo.getProject_appid()+"_"+warnInfo.getEvent_key();
			if (evenConfig.getEvents().containsKey(warnKey)) {
				Event event = evenConfig.getEvents().get(warnKey);
				/*
				 * 如果发送记录为空 or 当前时间-发送时间 > 冷却时间
				 */
				synchronized (SEND_LOCK) {
					Long currentTime = System.currentTimeMillis();
					if (!sendLog.containsKey(warnKey) || (sendLog.containsKey(warnKey) && (currentTime - sendLog.get(warnKey) > event.getThresholdInterval()*1000))) {
						manageService.sendMessage(event, new WarnMsg(event.getProjectAppid(), warnInfo.getEvent_local(), event.getEventKey(), warnInfo.getEvent_desc()));
						sendLog.put(warnKey, currentTime);
					}
				}
			} else {
				log.info("the warnKey={} dot exit events", warnKey);
			}
			
		}
	}
}
