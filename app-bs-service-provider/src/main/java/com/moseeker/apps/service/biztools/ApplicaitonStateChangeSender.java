package com.moseeker.apps.service.biztools;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.Constant;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jack
 * @Date: 2018/9/28
 */
@Component
public class ApplicaitonStateChangeSender {

    private static final String APLICATION_STATE_CHANGE_EXCHNAGE = "application_state_change_exchange";
    private static final String APLICATION_STATE_CHANGE_ROUTINGKEY = "application_state_change_routingkey.#";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发布申请状态变更的消息
     * @param appId 申请编号
     * @param stage 变更前的状态
     * @param nextStage 变更后的状态
     * @param applierId 申请人
     * @param positionId 职位编号
     * @param move 1表示向前操作；0表示向后操作
     * @param operationTime 操作时间
     */
    public void publishStateChangeEvent(int appId, int stage, int nextStage, int applierId, int positionId, byte move, DateTime operationTime) {

        if (nextStage == Constant.RECRUIT_STATUS_HIRED || stage == Constant.RECRUIT_STATUS_HIRED) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("applicationId", appId);
            jsonObject.put("nowStage", stage);
            jsonObject.put("nextStage", nextStage);
            jsonObject.put("applierId", applierId);
            jsonObject.put("positionId", positionId);
            jsonObject.put("move", move);
            jsonObject.put("operationTime", operationTime.getMillis());
            amqpTemplate.sendAndReceive(APLICATION_STATE_CHANGE_EXCHNAGE,
                    APLICATION_STATE_CHANGE_ROUTINGKEY, MessageBuilder.withBody(jsonObject.toJSONString().getBytes())
                            .build());
        }
    }
}
