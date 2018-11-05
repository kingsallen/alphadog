package com.moseeker.mq.service.schedule;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * 每周五下午5点，清空点赞数
 * @Author: jack
 * @Date: 2018/8/23
 */
@Component
public class ClearUpVote {

    @Autowired
    private Environment env;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private volatile LocalDateTime operationDateTime;

    public void clearUpVote() {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime currentFriday = now.with(DayOfWeek.FRIDAY).withHour(17).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime preFriday = currentFriday.minusDays(7);

        logger.info("clearUpVote, now:{}, currentFriday:{}, preFriday:{}", now, currentFriday, preFriday);

        //如果第一次执行，并且超过这周周五，那么执行；或者上一次执行的时间在上周五之前，那么执行。执行后更新执行时间
        if ((operationDateTime == null && now.isAfter(currentFriday)) ||
                (operationDateTime != null && operationDateTime.isBefore(preFriday))) {
            operationDateTime = now;
            String clearUpVoteURL = env.getProperty("message.clear_up_vote_url.link");
            // 创建Httpclient对象
            // 创建Httpclient对象
            CloseableHttpClient httpclient = HttpClients.createDefault();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(clearUpVoteURL);
            CloseableHttpResponse response = null;
            try {
                // 执行请求
                response = httpclient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    String content = EntityUtils.toString(response.getEntity(),
                            "UTF-8");
                    logger.info("clearUpVote result:{}", content);
                } else {
                    logger.error("clearUpVote status code:{}", response.getStatusLine().getStatusCode());
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                    // 相当于关闭浏览器
                    httpclient.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }


        } else {
            logger.info("不满足执行条件！ 当前时间：{}， 本周五：{}， 上周五：{}, 操作时间:{}", now, currentFriday, preFriday, operationDateTime);
            logger.info("当前时间和本周五的前后顺序比较：{}", now.isAfter(currentFriday));
            logger.info("当前时间和上周五的前后顺序比较：{}", now.isBefore(preFriday));
        }
    }
}
