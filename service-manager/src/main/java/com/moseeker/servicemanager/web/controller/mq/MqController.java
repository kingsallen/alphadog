package com.moseeker.servicemanager.web.controller.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 消息队列服务
 *
 * Created by zzh on 16/8/3.
 */
@Controller
public class MqController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(MqController.class);

    MqService.Iface mqService = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);

    @RequestMapping(value = "/message/template", method = RequestMethod.POST)
    @ResponseBody
    public String messageTemplateNotice(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取MessageTemplateNoticeStruct实体对象
            MessageTemplateNoticeStruct messageTemplateNoticeStruct = ParamUtils.initModelForm(request, MessageTemplateNoticeStruct.class);

            Map<String, Map<String, MessageTplDataCol>> tmpMessageTplDataColMap = messageTemplateNoticeStruct.getData();

            Map<String, MessageTplDataCol> tmpMessageTplDataColMap1 = new HashMap<String, MessageTplDataCol>();

            Set set = tmpMessageTplDataColMap.keySet();
            for(Iterator iterator = set.iterator(); iterator.hasNext();)
            {
                String s1 = (String)iterator.next();
                MessageTplDataCol col = new MessageTplDataCol();
                col.setColor(tmpMessageTplDataColMap.get(s1).get("color").toString());
                col.setValue(tmpMessageTplDataColMap.get(s1).get("value").toString());
                tmpMessageTplDataColMap1.put(s1, col);
            }
            tmpMessageTplDataColMap.replace("data", tmpMessageTplDataColMap1);

            System.out.println(JSON.toJSONString(tmpMessageTplDataColMap));


                    
//            for (Map.Entry<String, Map<String, MessageTplDataCol>> entry: tmpMessageTplDataColMap.entrySet()) {
//
//
////                for (Map.Entry<String, MessageTplDataCol> entry1: entry.getValue().entrySet()){
////                    Map<String, Object> mapObject = new HashMap<String, Object>();
////                    mapObject.put(entry1.getKey(), entry1.getValue());
////                    mapObject.put("appid", request.getParameter("appid"));
////
////                    //MessageTplDataCol col = ParamUtils.initModelForm((Map<String, Object>)entry.getValue(), MessageTplDataCol.class);
////                    //entry1.setValue(col);
////                }
//                //MessageTplDataCol col = ParamUtils.initModelForm((Map<String, Object>)entry.getValue(), MessageTplDataCol.class);
//
//            }

            // 发送消息模板
            Response result = mqService.messageTemplateNotice(messageTemplateNoticeStruct);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
