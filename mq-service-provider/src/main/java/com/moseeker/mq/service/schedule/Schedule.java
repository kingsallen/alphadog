package com.moseeker.mq.service.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.mq.service.impl.TemplateMsgHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 
 * 定时任务，用于定时获取消息模版延迟队列的任务，
 * 并将符合条件的任务移到消息模版的执行队列中 
 * <p>Company: MoSeeker</P>  
 * <p>date: Oct 27, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Component
@EnableScheduling
public class Schedule {
	
	@Resource(name = "cacheClient")
    private RedisClient redisClient;

	@Autowired
    private TemplateMsgHttp temlateMsgHttp;

	@Autowired
    private ClearUpVote clearUpVote;

	ThreadPool threadPool = ThreadPool.Instance;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 负责从延迟队列中查找符合要求的消息模版，将其转移到消息模版的执行队列中
     * 每分钟执行一次
     */
    @Scheduled(cron="0 */1 * * * ?")
	public void startListeningMessageDelayQueue() {
        long now = System.currentTimeMillis();
        Set<String> tasks = redisClient.rangeByScore(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MQ_MESSAGE_NOTICE_TEMPLATE_DELAY.toString(), 0l, now);
        if(tasks != null && tasks.size() > 0) {
            redisClient.zRemRangeByScore(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MQ_MESSAGE_NOTICE_TEMPLATE_DELAY.toString(), 0l, now);
            tasks.forEach(task -> {
                redisClient.lpush(Constant.APPID_ALPHADOG,
                        Constant.REDIS_KEY_IDENTIFIER_MQ_MESSAGE_NOTICE_TEMPLATE, task);
            });
        }

        Set<String> employeeEmailVerifyNotices = redisClient.rangeByScore(AppId.APPID_ALPHADOG.getValue(),
                KeyIdentifier.MQ_MESSAGE_NOTICE_VERIFY_EMAIL.toString(), 0l, now);

        logger.info("startListeningMessageDelayQueue employeeEmailVerifyNotices:{}", employeeEmailVerifyNotices);
        if (employeeEmailVerifyNotices != null && employeeEmailVerifyNotices.size() > 0) {
            redisClient.zRemRangeByScore(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.MQ_MESSAGE_NOTICE_VERIFY_EMAIL.toString(), 0l, now);

            threadPool.startTast(() -> {
                sendNotice(employeeEmailVerifyNotices);
                return true;
            });
        }

        //执行清空点赞校验
        threadPool.startTast(() -> {
            clearUpVote.clearUpVote();
            return true;
        });
	}

	private void sendNotice(Set<String> employeeEmailVerifyNotices) {

        logger.info("sendNotice employeeEmailVerifyNotices:{}", employeeEmailVerifyNotices);
            employeeEmailVerifyNotices.forEach(content -> {
                JSONObject jsonObject = JSON.parseObject(content);
                int userId = jsonObject.getInteger("userId");
                int companyId = jsonObject.getInteger("companyId");
                String company = jsonObject.getString("companyName");
                temlateMsgHttp.noticeEmployeeVerify(userId, companyId, company);

            });
    }
}
