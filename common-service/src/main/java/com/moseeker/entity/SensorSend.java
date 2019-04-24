package com.moseeker.entity;

import com.moseeker.common.thread.ThreadPool;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensorSend {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private ThreadPool tp = ThreadPool.Instance;
    private final static String SA_SERVER_URL = "https://service-sensors.moseeker.com/sa?project=ToCTest";
    private final static boolean SA_WRITE_DATA = true;
    private static SensorsAnalytics sa;
    @Autowired
    public SensorSend() throws IOException {
        sa = new SensorsAnalytics(
                new SensorsAnalytics.ConcurrentLoggingConsumer("/data/alphadog_sa/service_log", null, 10));
        Map<String, Object> properties = new HashMap<String, Object>(){{put("$project", "ToCTest");}};
        sa.registerSuperProperties(properties);

        Runtime.getRuntime().addShutdownHook(new Thread(()-> {
            sa.shutdown();
        }));
    }

    public void send(String distinctId,String eventName){
        tp.startTast(()->{
            try {
                Map<String, Object> properties = new HashMap<>();
                properties.put("$project", "ToCTest");
                sa.track(distinctId, true, eventName, properties);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
            return 0;
        });
    }

    public void send(String distinctId, String eventName, Map<String, Object> properties){
        tp.startTast(()->{
            try {
                properties.put("$project", "ToCTest");
                sa.track(distinctId, true, eventName,properties);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
            return 0;
        });
    }

    public void profileSet(String distinctId, String property, String value){
        tp.startTast(()->{
            try {
                Map<String, Object> properties = new HashMap<>();
                properties.put("$project", "ToCTest");
                properties.put(property, value);
                sa.profileSet(distinctId, true, properties);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
            return 0;
        });
    }
}

