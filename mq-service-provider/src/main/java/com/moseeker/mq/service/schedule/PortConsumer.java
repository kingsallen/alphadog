package com.moseeker.mq.service.schedule;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;

/**
 * 
 * 负责从延迟队列中查找符合要求的消息模版，将其转移到消息模版的执行队列中 
 * <p>Company: MoSeeker</P>  
 * <p>date: Oct 27, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class PortConsumer implements Runnable {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void run() {
		RedisClient redisClient = RedisClientFactory.getCacheClient();
		long now = System.currentTimeMillis();
		Set<String> tasks = redisClient.rangeByScore(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MQ_MESSAGE_NOTICE_TEMPLATE_DELAY.toString(), 0l, now);
		if(tasks != null && tasks.size() > 0) {
			redisClient.zRemRangeByScore(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MQ_MESSAGE_NOTICE_TEMPLATE_DELAY.toString(), 0l, now);
			tasks.forEach(task -> {
				redisClient.lpush(Constant.APPID_ALPHADOG,
						Constant.REDIS_KEY_IDENTIFIER_MQ_MESSAGE_NOTICE_TEMPLATE, task);
			});
		}
	}

}
