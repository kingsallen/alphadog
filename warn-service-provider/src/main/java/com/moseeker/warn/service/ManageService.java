package com.moseeker.warn.service;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.moseeker.warn.dto.Event;
import com.moseeker.warn.dto.WarnMsg;
import com.moseeker.warn.utils.SendChannel;

@Service
public class ManageService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	public String environment;
	
	@PostConstruct
	public void init(){
		Properties properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("warn.properties"));
			environment = properties.getProperty("environment");
		} catch (IOException e) {
			log.error("load warn.properties error:", e);
		}
	}
	
	/**
	 * 发送信息
	 */
	public void sendMessage(Event event, WarnMsg msg) {
		// 匹配通知渠道
		event.getNotifyChannels().forEach(channel -> {
			SendChannel sendChannel;
			try {
				sendChannel = SendChannel.valueOf(channel.toUpperCase());
				sendChannel.send(event.getMembers(), msg == null ? "信息出错" : "当前环境:".concat(environment).concat(msg.toString()));
			} catch (IllegalArgumentException e) {
				log.error("not found the sendChannel:{0}", channel);
			}
		});
	}

}
