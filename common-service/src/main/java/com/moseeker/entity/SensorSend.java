package com.moseeker.entity;

import com.moseeker.common.thread.ThreadPool;
import com.moseeker.entity.pojos.SensorProperties;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.moseeker.common.util.ConfigPropertiesUtil;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource("classpath:common.properties")
public class SensorSend {
    @Autowired
    private Environment env;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private ThreadPool tp = ThreadPool.Instance;
    private final static String SA_SERVER_URL = "https://service-sensors.moseeker.com/sa?project=ToCTest";
    private final static boolean SA_WRITE_DATA = true;
    private static SensorsAnalytics sa;

    private static ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();

    @Autowired
    public SensorSend() throws IOException {
        sa = new SensorsAnalytics(
           new SensorsAnalytics.ConcurrentLoggingConsumer(configUtils.get("sensor_path",String.class).trim(),null,configUtils.get("sensor_size",Integer.class)));
       // Map<String, Object> properties = new HashMap<String, Object>(){{put("$project", "ToCProduction");}};//线上环境专用
       // Map<String, Object> properties = new HashMap<String, Object>(){{put("$project", "ToCTest");}};//沙盒环境专用
        Map<String, Object> properties = new HashMap<String, Object>(){{put("$project", configUtils.get("sensor_env", String.class).trim());}};//动态加载环境
        sa.registerSuperProperties(properties);


        Runtime.getRuntime().addShutdownHook(new Thread(()-> {
            sa.shutdown();
        }));
    }

    public void send(String distinctId,String eventName){
        tp.startTast(()->{
            try {
                Map<String, Object> properties = new HashMap<>();
                properties.put("$project", configUtils.get("sensor_env",String.class).trim());//动态加载环境
                sa.track(distinctId, true, eventName, properties);
                logger.info("sensorSend send successful distinctId:{} eventName:{} properties:{}",distinctId,eventName,properties);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
            return 0;
        });
    }

    public void send(String distinctId, String eventName, Map<String, Object> properties){
        tp.startTast(()->{
            try {
                // properties.put("$project", "ToCProduction");//线上环境专用
                //properties.put("$project", "ToCTest");//沙盒环境专用
                properties.put("$project", configUtils.get("sensor_env",String.class).trim());//动态加载环境
                sa.track(distinctId, true, eventName,properties);
                logger.info("sensorSend send successful distinctId:{} eventName:{} properties:{}",distinctId,eventName,properties);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
            return 0;
        });
    }

    public void send(String distinctId, String eventName, SensorProperties properties){
        tp.startTast(()->{
            try {
                properties.put("$project", configUtils.get("sensor_env",String.class).trim());//动态加载环境
                sa.track(distinctId, true, eventName,properties);
                logger.info("sensorSend send successful distinctId:{} eventName:{} properties:{}",distinctId,eventName,properties);
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
             //   properties.put("$project", "ToCProduction");//线上环境专用
             //   properties.put("$project", "ToCTest");//沙盒环境专用
                properties.put("$project", configUtils.get("sensor_env",String.class).trim());//动态加载环境
                properties.put(property, value);
                logger.info("SensorSend send");
                sa.profileSet(distinctId, true, properties);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
            return 0;
        });
    }
}
