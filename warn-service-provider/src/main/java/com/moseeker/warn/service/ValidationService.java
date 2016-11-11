package com.moseeker.warn.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.warn.struct.WarnBean;
import com.moseeker.warn.dto.Event;

/**
 * @author ltf
 * 预警消息校验 - 频率控制
 * 2016年11月10日
 */
@Service
public class ValidationService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 发送记录
	 */
	private static Map<String, Long> sendLog = new HashMap<String, Long>();
	
	@Autowired
	private EventConfigService evenConfig;
	
	@Autowired
	private ManageService manageService;
	
	public void valid(WarnBean warnInfo) {
		if (warnInfo != null) {
			String warnKey = warnInfo.getProject_appid()+"_"+warnInfo.getEvent_key();
			if (evenConfig.getEvents().containsKey(warnKey)) {
				Event event = evenConfig.getEvents().get(warnInfo);
				// 
			} else {
				log.info("the warnKey={} dot exit events", warnKey);
			}
			
		}
	}
}
