package com.moseeker.servicemanager.consistency;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.consistencysuport.common.ParamConvertTool;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * 测试业务的参数处理工具
 *
 * Created by jack on 2018/4/18.
 */
@Component("test")
public class TestParamConvertTool implements ParamConvertTool {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String convertParamToStorage(Object[] params) {
        Param param = new Param();
        param.setId((Integer) params[1]);
        param.setMessageId((String) params[0]);
        return JSONObject.toJSONString(param);
    }

    @Override
    public Object[] convertStorageToParam(String param) {
        Param p = null;
        try {
            p = JSONObject.parseObject(param, Param.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ConsistencyException.CONSISTENCY_PRODUCER_PROTECTOR_PARAMETER_NOT_LEGAL;
        }
        Object[] result = new Object[2];
        result[0] = p.getMessageId();
        result[1] = p.getId();
        return result;
    }
}
