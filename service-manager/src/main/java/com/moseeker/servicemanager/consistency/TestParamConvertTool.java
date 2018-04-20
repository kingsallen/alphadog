package com.moseeker.servicemanager.consistency;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.consistencysuport.common.ParamConvertTool;
import org.springframework.stereotype.Component;

/**
 *
 * 测试业务的参数处理工具
 *
 * Created by jack on 2018/4/18.
 */
@Component("test")
public class TestParamConvertTool implements ParamConvertTool {

    @Override
    public String convertParamToStorage(Object[] params) {
        Param param = new Param();
        param.setId((Integer) params[1]);
        param.setMessageId((String) params[0]);
        return JSONObject.toJSONString(param);
    }

    @Override
    public Object[] convertStorageToParam(String param) {
        Param p = JSONObject.parseObject(param, Param.class);
        Object[] result = new Object[2];
        result[0] = p.getMessageId();
        result[1] = p.getId();
        return result;
    }

    class Param {
        private String messageId;
        private int id;

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
