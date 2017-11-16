package com.moseeker.position.service.position.veryeast;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.service.position.base.JsonParamRefreshHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractVeryEastParamHandler implements JsonParamRefreshHandler {
    Logger logger=LoggerFactory.getLogger(AbstractVeryEastParamHandler.class);

    @Override
    public void handler(String json) {
        try {
            JSONObject obj=JSONObject.parseObject(json);
            handler(obj.getJSONObject("data"));
        }catch (Exception e){
            logger.info("VeryEast parse json exception : {}",json);
            throw e;
        }
    }

    public abstract void handler(JSONObject obj);
}
