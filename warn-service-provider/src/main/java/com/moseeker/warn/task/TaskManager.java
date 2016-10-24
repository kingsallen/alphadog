package com.moseeker.warn.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.moseeker.warn.service.EventConfigService;

/**
 * @author ltf
 * spring 任务调度管理 <br/>
 * EnableScheduling: 启用task任务调度
 */
@Component
@EnableScheduling
public class TaskManager {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EventConfigService eventConfigService;
	
	/**
	 * 加载event配置信息（每天23:00执行）
	 */
	@Scheduled(cron="0 0 23 * * ?")
	public void loadEventConfig() {
		eventConfigService.init();
	}

}
