package com.moseeker.warn.server;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationEventsRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.warn.dao.EventDao;

/**
 * @author lucky8987
 * 
 */
public class WarnServer {
	
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext springContext = initSpring();
		EventDao eventDao = springContext.getBean(EventDao.class);
		CommonQuery query = new CommonQuery();
		List<ConfigAdminnotificationEventsRecord> list = eventDao.getResources(query);
		list.forEach(record -> System.out.println(record.getValue("event_name")));
	}
	
	/**
	 * 加载spring容器
	 * @return
	 */
	public static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
		annConfig.scan("com.moseeker.warn");
		annConfig.refresh();
		annConfig.start();
		return annConfig;
	}
}
