package com.moseeker.warn.service.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.warn.dto.Event;
import com.moseeker.warn.service.send.SendFactory;
import com.moseeker.warn.service.send.impl.EmailSend;
import com.moseeker.warn.service.send.impl.SmsSend;

public class SendManager {
	private List<String> listmsg;
	private RedisClient redisClient = RedisClientFactory.getCacheClient();
	public static  ExecutorService executorService;
	public SendManager(){
		if(this.executorService==null){
			this.executorService = new ThreadPoolExecutor(3, 10,
	                60L, TimeUnit.MILLISECONDS,
	                new LinkedBlockingQueue<Runnable>());
		}
	}
	
	public void getMsgByRedis(){
		listmsg=redisClient.brpop(Constant.APPID_ALPHADOG, "NEW_WARNING_REDIS_KEY");
	}
	public void getSend(){		
			if(listmsg!=null&&listmsg.size()>0){
				String msg=listmsg.get(1);				
				JSONObject obj=(JSONObject) JSONObject.parse(msg);
				String location=obj.getString("location");
				Event config=JSONObject.toJavaObject(obj.getJSONObject("config"), Event.class);
				Map<String ,Object> map=new HashMap<String,Object>();
				map.put("config", config);
				map.put("location", location);
				SendFactory send=new SendFactory(map);
				List<String> list=config.getNotifyChannels();
				for(String name:list){
					
					if("email".equals(name)){
						executorService.submit(()->{
							send.createSend(EmailSend.class);
						});
						
					}else if("sms".equals(name)){
						executorService.submit(()->{
							send.createSend(SmsSend.class);
						});
					}else if("wechat".equals(name)){
						
					}
				}
			}
	}
	public void start(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					getMsgByRedis();
					getSend();
				}
				
			}
		}).start();;
	}

}
