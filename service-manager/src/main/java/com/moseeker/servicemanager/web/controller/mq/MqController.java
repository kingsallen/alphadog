package com.moseeker.servicemanager.web.controller.mq;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.mq.struct.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.common.annotation.iface.CounterIface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.mq.service.MqService;

/**
 * 消息队列服务
 *
 * Created by zzh on 16/8/3.
 */
@Controller
@CounterIface
public class MqController {

    Logger logger = LoggerFactory.getLogger(MqController.class);

    MqService.Iface mqService = ServiceManager.SERVICE_MANAGER.getService(MqService.Iface.class);

    @RequestMapping(value = "/message/template", method = RequestMethod.POST)
    @ResponseBody
    public String messageTemplateNotice(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 发送消息模板
            Response result = mqService.messageTemplateNotice(this.getMessageTemplateNoticeStruct(request));
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
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
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/sms/sendSms", method = RequestMethod.POST)
    @ResponseBody
    public String sendSms(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 发送消息模板
            Params<String, Object> param = ParamUtils.parseRequestParam(request);
            logger.info("sendSms param:{}",param);
            Map<String, String> data = (Map<String, String>) param.get("data");
            int smsType = param.getInt("smsType");
            String mobile = param.getString("mobile");
            String sys = param.getString("sys");
            String ip = param.getString("ip");


            logger.info("sendSms smsType:{},mobile:{},sys:{},ip:{},data:{}", smsType, mobile, sys,ip, data);
            Response result = mqService.sendSMS(SmsType.findByValue(smsType),mobile, data, sys, ip);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/v4/email/sendAuthEMail", method = RequestMethod.POST)
    @ResponseBody
    public String sendAuthEMail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Params<String, Object> param = ParamUtils.parseRequestParam(request);

            Map<String, String> params = (Map<String, String>) param.get("params");
            int eventType = param.getInt("eventType");
            String email = param.getString("email");
            String subject = param.getString("subject");
            String senderName = param.getString("senderName");
            String senderDisplay = param.getString("senderDisplay");

            logger.info("send auth email params:{},eventType:{},email:{},subject:{},senderName:{},senderDisplay:{}",
                    param,eventType,email,subject,senderName,senderDisplay);

            Response result = mqService.sendAuthEMail(params, eventType, email, subject, senderName, senderDisplay);
            return ResponseLogNotification.success(request, result);
        }catch (Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }


    @RequestMapping(value = "/email/sendMandrillEmail", method = RequestMethod.POST)
    @ResponseBody
    public String sendMandrillEmail(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 发送消息模板
        	MandrillEmailStruct mandrillEmailStruct = new MandrillEmailStruct();
        	Params<String, Object> params = ParamUtils.parseRequestParam(request);

        	String templateName = (String)params.get("templateName");
        	if (StringUtils.isNotNullOrEmpty(templateName)){
            	mandrillEmailStruct.setTemplateName(templateName);
        	}

        	String to_email = (String)params.get("to_email");
        	if (StringUtils.isNotNullOrEmpty(to_email)){
            	mandrillEmailStruct.setTo_email(to_email);
        	}

        	String to_name = (String)params.get("to_name");
        	if (StringUtils.isNotNullOrEmpty(to_name)){
            	mandrillEmailStruct.setTo_name(to_name);
        	}

        	String strMergeVars = (String)params.get("mergeVars");
        	if (StringUtils.isNotNullOrEmpty(strMergeVars)){
        		HashMap mergeVars = JSON.parseObject(strMergeVars, HashMap.class);
            	mandrillEmailStruct.setMergeVars(mergeVars);
        	}
        	String subject = (String)params.get("subject");
        	if (StringUtils.isNotNullOrEmpty(subject)){
            	mandrillEmailStruct.setSubject(subject);
        	}
        	String from_email = (String)params.get("from_email");
        	if (StringUtils.isNotNullOrEmpty(from_email)){
            	mandrillEmailStruct.setFrom_email(from_email);
        	}

        	String from_name = (String)params.get("from_name");
        	if (StringUtils.isNotNullOrEmpty(from_name)){
            	mandrillEmailStruct.setFrom_name(from_name);
        	}

            Response result = mqService.sendMandrilEmail(mandrillEmailStruct);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/email/sendMandrillEmail/list", method = RequestMethod.POST)
    @ResponseBody
    public String sendMandrillEmailList(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 发送消息模板
            MandrillEmailListStruct mandrillEmailStruct = new MandrillEmailListStruct();
            Params<String, Object> params = ParamUtils.parseRequestParam(request);

            String templateName = (String)params.get("templateName");
            if (StringUtils.isNotNullOrEmpty(templateName)){
                mandrillEmailStruct.setTemplateName(templateName);
            }

            List to = (List)params.get("to");
            mandrillEmailStruct.setTo(to);
            List strMergeVars = (List)params.get("mergeVars");
            String mergeVars = JSON.toJSONString(strMergeVars);
            mandrillEmailStruct.setMergeVars(mergeVars);

            String subject = (String)params.get("subject");
            if (StringUtils.isNotNullOrEmpty(subject)){
                mandrillEmailStruct.setSubject(subject);
            }
            String from_email = (String)params.get("from_email");
            if (StringUtils.isNotNullOrEmpty(from_email)){
                mandrillEmailStruct.setFrom_email(from_email);
            }

            String from_name = (String)params.get("from_name");
            if (StringUtils.isNotNullOrEmpty(from_name)){
                mandrillEmailStruct.setFrom_name(from_name);
            }

            mqService.sendMandrilEmailList(mandrillEmailStruct);
            Response res = ResponseUtils.success("");
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
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
            if(paramMap.get("type") != null) {
            	messageTemplateNoticeStruct.setType((byte)(int)paramMap.get("type"));
            }
            messageTemplateNoticeStruct.setSys_template_id((int)paramMap.get("sys_template_id"));
            if(paramMap.get("url") != null) {
            	messageTemplateNoticeStruct.setUrl(paramMap.get("url").toString());
            }
            messageTemplateNoticeStruct.setCompany_id((int)paramMap.get("company_id"));
            messageTemplateNoticeStruct.setData(this.getMessagetplData((Map<String, Map<String, JSONObject>>)paramMap.get("data")));
            messageTemplateNoticeStruct.setEnable_qx_retry((byte)(int)paramMap.getOrDefault("enable_qx_retry", 1)); // Integer->int->byte
            if(paramMap.get("delay") != null) {
            	 messageTemplateNoticeStruct.setDelay((Integer)paramMap.get("delay"));
            }
            if(paramMap.get("validators") != null) {
            	messageTemplateNoticeStruct.setValidators((String)paramMap.get("validators"));
            }
            if(paramMap.get("validators_params") != null) {
                messageTemplateNoticeStruct.setValidators_params((String)paramMap.get("validators_params"));
            }
            return messageTemplateNoticeStruct;
        }
        return null;
    }
    /*
     * @Author zztaiwll
     * @Description  申请职位发送邮件
     * @Date 下午5:58 18/12/11
     * @Param
     * @return
     **/
    @RequestMapping(value = "/email/app", method = RequestMethod.POST)
    @ResponseBody
    public String sendEMailApp(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 发送消息模板
            MessageEmailStruct emailStruct = ParamUtils.initModelForm(request, MessageEmailStruct.class);

            Response result = mqService.sendMessageAndEmailToDelivery(emailStruct);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/v4/notification/config/{wechatId}", method = RequestMethod.GET)
    @ResponseBody
    public String listNotificationConfig(HttpServletRequest request, @PathVariable Integer wechatId) throws Exception {
        try {
            List<MessageBody> messageBodies = mqService.listMessages(wechatId);
            return ResponseLogNotification.successJson(request, messageBodies);
        }catch (Exception e){
            return ResponseLogNotification.fail(request, e);
        }
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
