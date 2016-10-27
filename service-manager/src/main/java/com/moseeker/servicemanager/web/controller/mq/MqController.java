package com.moseeker.servicemanager.web.controller.mq;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.EmailStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;

/**
 * 消息队列服务
 *
 * Created by zzh on 16/8/3.
 */
@Controller
public class MqController {

    Logger logger = LoggerFactory.getLogger(MqController.class);

    MqService.Iface mqService = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);

    @RequestMapping(value = "/message/template", method = RequestMethod.POST)
    @ResponseBody
    public String messageTemplateNotice(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 发送消息模板
            Response result = mqService.messageTemplateNotice(this.getMessageTemplateNoticeStruct(request));
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    
    @RequestMapping(value = "/email/sendEMail", method = RequestMethod.POST)
    @ResponseBody
    public String sendEMail(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 发送消息模板
        	EmailStruct emailStruct = ParamUtils.initModelForm(request, EmailStruct.class);
        	
            Response result = mqService.sendEMail(emailStruct);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 转换消息模板通知的thrift MessageTemplateNoticeStruct 对象
     *
     * @param request
     * @return
     * @throws Exception 
     */
    private MessageTemplateNoticeStruct getMessageTemplateNoticeStruct(HttpServletRequest request) throws Exception{
        MessageTemplateNoticeStruct messageTemplateNoticeStruct = new MessageTemplateNoticeStruct();
        Map<String, Object> paramMap = null;
		paramMap = ParamUtils.parseRequestParam(request);
        if(paramMap != null){
            messageTemplateNoticeStruct.setUser_id((int)paramMap.get("user_id"));
            messageTemplateNoticeStruct.setSys_template_id((int)paramMap.get("sys_template_id"));
            if(paramMap.get("url") != null) {
            	messageTemplateNoticeStruct.setUrl(paramMap.get("url").toString());
            }
            messageTemplateNoticeStruct.setCompany_id((int)paramMap.get("company_id"));
            messageTemplateNoticeStruct.setData(this.getMessagetplData((Map<String, Map<String, JSONObject>>)paramMap.get("data")));
            messageTemplateNoticeStruct.setEnable_qx_retry((byte)(int)paramMap.getOrDefault("enable_qx_retry", 1)); // Integer->int->byte
            if(paramMap.get("delay") != null) {
            	 messageTemplateNoticeStruct.setDelay((Integer)paramMap.get("delay") * 1000);
            }
            if(paramMap.get("validators") != null) {
            	messageTemplateNoticeStruct.setValidators((String)paramMap.get("validators"));
            }
            return messageTemplateNoticeStruct;
        }
        return null;
    }

    /**
     * 转换消息模板通知的thrift MessageTplDataCol struct 对象
     * @param dataMap
     * @return
     */
    private Map<String, MessageTplDataCol> getMessagetplData(Map<String, Map<String, JSONObject>> dataMap){
        Map<String, MessageTplDataCol> data = new HashMap<String, MessageTplDataCol>();
        for(Map.Entry dataEntry : dataMap.entrySet()){
            MessageTplDataCol messageTplDataCol = new MessageTplDataCol();
            messageTplDataCol.setColor(((HashMap)dataEntry.getValue()).get("color").toString());
            messageTplDataCol.setValue(((HashMap)dataEntry.getValue()).get("value").toString());
            data.put(dataEntry.getKey().toString(), messageTplDataCol);
        }
        return data;
    }
}
