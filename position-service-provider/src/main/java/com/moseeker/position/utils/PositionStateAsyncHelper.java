package com.moseeker.position.utils;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.moseeker.common.constants.PositionSyncVerify.*;

@Component
public class PositionStateAsyncHelper {

    private Logger logger = LoggerFactory.getLogger(PositionStateAsyncHelper.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void downShelf(CountDownLatch batchHandlerCountDown,List<Integer> ids) {
        if(StringUtils.isEmptyList(ids)) {
            return;
        }
        ThreadPool.Instance.startTast(()-> {
            try{
                if(batchHandlerCountDown.await(600, TimeUnit.SECONDS)){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", ids);
                    amqpTemplate.send(POSITION_OPERATE_LIEPIN_EXCHANGE,
                            POSITION_OPERATE_ROUTEKEY_DOWNSHELF,
                            MessageBuilder.withBody(jsonObject.toJSONString().getBytes()).build());
                } else {
                    throw new RuntimeException("rabbitmq线程等待超时");
                }

            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            }
            return true;
        });
    }

    public void resync(CountDownLatch batchHandlerCountDown,List<Integer> ids) {
        if(StringUtils.isEmptyList(ids)) {
            return;
        }
        ThreadPool.Instance.startTast(()-> {
            try{
                if(batchHandlerCountDown.await(600, TimeUnit.SECONDS)){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", ids);
                    amqpTemplate.send(POSITION_OPERATE_LIEPIN_EXCHANGE,
                            POSITION_OPERATE_ROUTEKEY_RESYNC,
                            MessageBuilder.withBody(jsonObject.toJSONString().getBytes()).build());
                } else {
                    throw new RuntimeException("rabbitmq线程等待超时");
                }

            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            }
            return true;
        });
    }

    public void edit(CountDownLatch batchHandlerCountDown, List<JobPositionRecord> jobPositionUpdateRecordList, Map<Integer, JobPositionRecord> oldJobMap) {
        if(StringUtils.isEmptyList(jobPositionUpdateRecordList)) {
            return;
        }
        ThreadPool.Instance.startTast(()-> {
            try{
                if(batchHandlerCountDown.await(600, TimeUnit.SECONDS)){
                    for(JobPositionRecord position:jobPositionUpdateRecordList) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id", position.getId());
                        jsonObject.put("params", BeanUtils.DBToStruct(JobPositionDO.class,position));
                        jsonObject.put("oldPosition", BeanUtils.DBToStruct(JobPositionDO.class,oldJobMap.get(position.getId())));
                        if(position.getStatus() == 2) {
                            jsonObject.put("positionFlag", true);
                        } else{
                            jsonObject.put("positionFlag", false);
                        }

                        amqpTemplate.send(POSITION_OPERATE_LIEPIN_EXCHANGE,
                                POSITION_OPERATE_ROUTEKEY_EDIT,
                                MessageBuilder.withBody(jsonObject.toJSONString().getBytes()).build());
                    }
                } else {
                    throw new RuntimeException("rabbitmq线程等待超时");
                }

            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            }
            return true;
        });
    }
}
