package com.moseeker.position.service.position.base.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJsonResultHandler implements JsonResultHandler {
    Logger logger=LoggerFactory.getLogger(AbstractJsonResultHandler.class);

    @Override
    public void handle(String json) {
        try {
            JSONObject obj=JSONObject.parseObject(json);
            handle(obj.getJSONObject("data"));
        }catch (Exception e){
            logger.info("VeryEast parse json exception : {}",json);
            throw e;
        }
    }

    protected abstract void handle(JSONObject obj);
}
