package com.moseeker.application.service.impl;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.common.exception.RedisClientException;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.warn.service.WarnSetService;
import com.moseeker.thrift.gen.warn.struct.WarnBean;

/**
 * @author ltf
 * 警告服务【单例】
 * 2016年11月8日
 */
public class WarnService {
	
	private static  Logger log = LoggerFactory.getLogger(WarnService.class);
	
	private static WarnSetService.Iface warn = null;
	
	/**
	 * @return
	 */
	public static WarnSetService.Iface getInstance(){
		synchronized (WarnService.class) {
			if (warn == null) {
				warn = ServiceManager.SERVICEMANAGER.getService(WarnSetService.Iface.class);
			}
		}
		return warn;
	}
	
	/**
	 * 预警通知
	 * @param e
	 */
	public static void notify(RedisClientException e) {
		try {
			getInstance().sendOperator(new WarnBean(String.valueOf(e.getAppid()), e.getEventKey(), e.getMessage(), e.getLocation()));
		} catch (TException te) {
			log.error("sendOperator error:", te);
		}
	}
}
