package com.moseeker.mq.service.sms;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 短信发送客户端
 * <p>
 *
 * */
public class SmsSender {

    private static TaobaoClient taobaoclient;
    private static Logger LOGGER = LoggerFactory.getLogger(SmsSender.class);

    public static TaobaoClient initTaobaoClientInstance() {
        if (taobaoclient == null) {
            synchronized (SmsSender.class) {
                if (taobaoclient == null) {
                    ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil
                            .getInstance();
                    String smsAccesskeyId = propertiesUtils.get("sms.accesskeyid",
                            String.class);
                    String smsAccesskeySecret = propertiesUtils.get("sms.accesskeysecret",
                            String.class);
                    taobaoclient = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", smsAccesskeyId, smsAccesskeySecret);
                }
            }
        }
        return taobaoclient;
    }    

    /**
     * 发送短信
     *
     * @param mobile 手机号
     * @param templateCode 模板code
     * @param params 需要的变量map
     *
     * */
    public static boolean sendSMS(String mobile, String templateCode, Map params){
        initTaobaoClientInstance();
        
        if (mobile==null){
            return false;
        }

        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend(mobile);
        req.setSmsType("normal");
        req.setSmsFreeSignName("仟寻");

        if (params != null){
            req.setSmsParamString(JSON.toJSONString(params));
        }

        req.setRecNum(mobile);
        req.setSmsTemplateCode(templateCode);
        AlibabaAliqinFcSmsNumSendResponse rsp;

        try {
            rsp = taobaoclient.execute(req);
            if (rsp.getBody().indexOf("success")>-1) {
            	
                return true;
            }
            else{
                LOGGER.warn("短信发送失败:" + rsp.getBody());
            }
        } catch (ApiException e) {
            LOGGER.warn("短信发送失败:" + e.getMessage());
        }
        return false;    
    }

    /**
     * 生成数字随机验证码
     *
     * */
    private static String getRandomStr(){
        return String.valueOf((int) (Math.random()*9000+1000));
    }

}
