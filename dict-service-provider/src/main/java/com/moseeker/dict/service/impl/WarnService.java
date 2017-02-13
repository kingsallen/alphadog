package com.moseeker.dict.service.impl;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.common.exception.RedisException;
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
	public static void notify(RedisException e) {
		try {
			getInstance().sendOperator(new WarnBean(String.valueOf(e.getAppid()), e.getEventKey(), null, e.getMessage(), e.getLocation().concat(":").concat(String.valueOf(e.getStackTrace()[0].getLineNumber()))));
		} catch (TException te) {
			log.error("sendOperator error:", te);
		}
	}
}
