package com.moseeker.warn.service.validate;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.Constant;
import com.moseeker.thrift.gen.warn.struct.WarnBean;
import com.moseeker.warn.dto.Event;
import com.moseeker.warn.service.EventConfigService;
/**
 * 验证异常警告，获得结果，判断是否要插入数据库
 * @author zztaiwll
 *
 */
@Service
public class ValidationService {
	@Autowired
	private EventConfigService config;										
	private RedisClient redisClient = RedisClientFactory.getCacheClient();
	private String IDENTIFIKEY="NEW_WARNING_REDIS_KEY";                      //队列的IDENTIFIKEY
	/**
	 * 
	 * @param bean
	 * project_appid; 异常发生的模块
     * event_key;  异常的key值
     * event_name; 异常的名称
     * event_desc; 异常的描述
     * event_local;异常的位置
	 * @throws Exception 
	 * 
	 */
	
	public  void valid(WarnBean bean){
		try{
			int flag=0;
			Map<String,Event> map=config.getEvents();//获取预警配置信息
			String name=bean.getEvent_key();
			String appid=bean.getProject_appid();
			String mapkey=appid+"_"+name;
			Event config=map.get(mapkey);
			if(config!=null){
				int interval=config.getThresholdInterval();
				String key=appid+name;
				synchronized(IDENTIFIKEY){//加锁，避免出现并发读取redis
					String oldtime=redisClient.get(Constant.APPID_ALPHADOG,IDENTIFIKEY, key);
					if(oldtime==null){//如果不存在该类报警信息，则添加，并且发送邮件
						redisClient.set(Constant.APPID_ALPHADOG,IDENTIFIKEY, key,System.currentTimeMillis()+"");
						flag=1;
					}else{
						Long retimePeriod=System.currentTimeMillis()-Long.parseLong(oldtime);
						if(retimePeriod>=interval*1000){//redis中存储的上次报警时间与这次报警时间之差大于配置时间，则发送邮件，并且更新时间，否则只更新时间即可
							redisClient.set(Constant.APPID_ALPHADOG,IDENTIFIKEY, key,System.currentTimeMillis()+"");
							flag=1;
						}
					}
					if(flag==1){//如果触发成功，则将信息放在队列
						JSONObject sendResult=new JSONObject();
						sendResult.put("config", config);
						sendResult.put("location",bean.getEvent_local() );
						redisClient.lpush(Constant.APPID_ALPHADOG, IDENTIFIKEY, sendResult.toString());
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

}
