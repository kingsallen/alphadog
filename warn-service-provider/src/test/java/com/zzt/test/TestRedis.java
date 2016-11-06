package com.zzt.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.Constant;
import com.moseeker.thrift.gen.warn.struct.WarnBean;
import com.moseeker.warn.service.manager.SendManager;
import com.moseeker.warn.service.validate.ValidationException;

public class TestRedis {
	private RedisClient redisClient = RedisClientFactory.getCacheClient();
	//校验数据是否放在redis队列当中
	@Test
	public void getList(){
		List<String> list=redisClient.brpop(Constant.APPID_ALPHADOG, "NEW_WARNING_REDIS_KEY");
		if(list.size()>0){
			for(String msg:list){
				System.out.println("=============================");
				System.out.println(msg);
			}
		}else{
			System.out.println("no list================");
		}
//		synchronized (TestRedis.class) {
//			while(true){
//				getList();
//				System.out.println("11111");
//			}
//		}
	}
	//校验测试
	@Test
	public void senRedis(){
		WarnBean bean=new WarnBean();
		bean.setEvent_key("RESTFUL_API_ERROR");
		bean.setEvent_desc("api 返回非正常数据");
		bean.setEvent_name("api 返回非正常数据");
		bean.setEvent_local("ssshk");
		bean.setProject_appid("1");
		AnnotationConfigApplicationContext ss=initSpring();
		ValidationException valids=ss.getBean(ValidationException.class);
		valids.valid(bean);
//		List<String> list=redisClient.brpop(Constant.APPID_ALPHADOG, "NEW_WARNING_REDIS_KEY");
//		if(list.size()>0){
//			for(String msg:list){
//				System.out.println("=============================");
//				System.out.println(msg);
//			}
//		}else{
//			System.out.println("no list*******************************");
//		}
		
	}
	public static AnnotationConfigApplicationContext initSpring() {
		AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
		annConfig.scan("com.moseeker.warn");
		annConfig.refresh();
		return annConfig;
	}
	//发送邮件测试
	@Test
	public void sendMessage(){
		SendManager send=new SendManager();
		send.start();
	}
}
