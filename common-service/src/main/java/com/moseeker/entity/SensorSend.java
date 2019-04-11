package com.moseeker.entity;

import com.moseeker.common.thread.ThreadPool;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SensorSend {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private ThreadPool tp = ThreadPool.Instance;
    private final static String SA_SERVER_URL = "https://service-sensors.moseeker.com/sa?project=ToCTest";
    private final static boolean SA_WRITE_DATA = true;
    private final static SensorsAnalytics sa = new SensorsAnalytics(new SensorsAnalytics.DebugConsumer(SA_SERVER_URL, SA_WRITE_DATA));

    public void send(String distinctId,String eventName){
        tp.startTast(()->{
            try {
                sa.track(distinctId, true, eventName);
                sa.shutdown();
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
        },0);
    }
}
