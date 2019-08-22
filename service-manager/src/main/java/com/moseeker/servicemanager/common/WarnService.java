package com.moseeker.servicemanager.common;

import java.io.PrintWriter;
import java.io.StringWriter;

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
				warn = ServiceManager.SERVICE_MANAGER.getService(WarnSetService.Iface.class);
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
			String stackTrace = "";
			try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)){
				e.printStackTrace(pw);
				stackTrace = sw.toString();
			} catch (Exception e2) {
				log.error(e2.getMessage(), e2);
			}
			log.error(e.getMessage(), e);
			getInstance().sendOperator(new WarnBean(String.valueOf(e.getAppid()), e.getEventKey(), null, stackTrace, e.getLocation().concat(":").concat(String.valueOf(e.getStackTrace()[0].getLineNumber()))));
		} catch (TException te) {
			log.error("sendOperator error:", te);
		}
	}
}
