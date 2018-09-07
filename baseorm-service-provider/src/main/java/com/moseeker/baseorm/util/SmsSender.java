package com.moseeker.baseorm.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.constant.SMSScene;
import com.moseeker.baseorm.dao.logdb.LogSmsSendrecordDao;
import com.moseeker.baseorm.db.logdb.tables.records.LogSmsSendrecordRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.SmsNationUtil;
import com.moseeker.common.exception.CacheConfigNotExistException;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.request.AlibabaAliqinFcTtsNumSinglecallRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.taobao.api.response.AlibabaAliqinFcTtsNumSinglecallResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * 短信发送客户端
 * <p>
 *
 * */
@Service
public class SmsSender {

    private static TaobaoClient taobaoclient;
    private static Logger logger = LoggerFactory.getLogger(SmsSender.class);
    private static final String SMS_LIMIT_KEY = "SMS_LIMIT";
    
    @Autowired
	protected LogSmsSendrecordDao smsRecordDao;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

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
    public boolean sendSMS(String mobile, String templateCode, Map<String, String> params){
        initTaobaoClientInstance();
        
        if (StringUtils.isNullOrEmpty(mobile)){
            return false;
        }

        if (!isMoreThanUpperLimit(mobile.trim())) {
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
            	LogSmsSendrecordRecord record = new LogSmsSendrecordRecord();
            	record.setMobile(Long.valueOf(mobile));
            	record.setSys(Constant.LOG_SMS_SENDRECORD_SYS_ALPHADOG);
            	JSONObject json = new JSONObject();
            	json.put("extend", mobile);
            	json.put("sms_type", "nomal");
            	json.put("sms_free_sign_name", "仟寻");
            	json.put("template_code", templateCode);
            	json.put("params", params);
            	record.setMsg(json.toJSONString());
            	smsRecordDao.addRecord(record);
            	logger.info(json.toJSONString());
                return true;
            }
            else{
            	logger.warn("短信发送失败:{}   手机号码:{}",rsp.getBody(), mobile);
            }
        } catch (ApiException e) {
        	logger.warn("短信发送失败:{},  手机号码:{}", e.getMessage(), mobile);
        } catch (Exception e) {
        	logger.warn("短信发送失败:" + e.getMessage(), e);
		}
        return false;    
    }

    /**
     *      SMS_5755096
     *      您的验证码是：${code}。请不要把验证码泄露给其他人。    
     * @param mobile
     * @return
     */
//    public boolean sendSMS_signup(String mobile){
//        HashMap<String, String> params = new HashMap<String, String>();
//        String signupcode = getRandomStr();
//        params.put("code", signupcode);
//        try {
//			redisClient.set(0, "SMS_SIGNUP", mobile, signupcode);
//		} catch (CacheConfigNotExistException e) {
//			logger.error(e.getMessage(), e);
//		}
//        return sendSMS(mobile,"SMS_5755096",params);
//    }

    /**
     *      SMS_5755096
     *      您的验证码是：${code}。请不要把验证码泄露给其他人。    
     * @param mobile
     * @return
     */
//    public boolean sendSMS_passwordforgot(String mobile){
//        HashMap<String, String> params = new HashMap<String, String>();
//        String passwordforgotcode = getRandomStr();
//        params.put("code", passwordforgotcode);
//        redisClient.set(0, "SMS_PWD_FORGOT", mobile, passwordforgotcode);
//        return sendSMS(mobile,"SMS_5755096",params);
//    }
    

    /**
     *          SMS_5895237
    感谢您使用仟寻，为方便您再次访问并了解求职进展，我们已为您开通个人账号：账号为${name}，密码为${code}，
    请及时到个人中心账号设置中修改您的登录密码，保障账号安全。
     * @param mobile
     * @return
     */
    public boolean sendSMS_signupRandomPassword(String mobile, String randompassword, String countryCode){

        if (StringUtils.isNullOrEmpty(countryCode) ||  countryCode == "86"){
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("name", mobile);
            params.put("code", randompassword);
            return sendSMS(mobile,"SMS_5895237",params);
        }
        return false;

    }
    
    /**
     *      SMS_5755096  修改手机号时， 向现有手机号发送验证码。
     *      您的验证码是：${code}。请不要把验证码泄露给其他人。    
     * @param mobile
     * @return
     */
//    public boolean sendSMS_changemobilecode(String mobile){
//        HashMap<String, String> params = new HashMap<String, String>();
//        String code = getRandomStr();
//        params.put("code", code);
//        redisClient.set(0, "SMS_CHANGEMOBILE_CODE", mobile, code);
//        return sendSMS(mobile,"SMS_5755096",params);
//    }
    
    /**
     *      SMS_5755096  重置手机号时， 向新手机号发送验证码。
     *      您的验证码是：${code}。请不要把验证码泄露给其他人。    
     * @param mobile
     * @return
     */
//    public boolean sendSMS_resetmobilecode(String mobile){
//        HashMap<String, String> params = new HashMap<String, String>();
//        String code = getRandomStr();
//        params.put("code", code);
//        redisClient.set(0, "SMS_RESETMOBILE_CODE", mobile, code);
//        return sendSMS(mobile,"SMS_5755096",params);
//    }

    /**
     * SMS_5755096 手机发送验证码。
     *    您的验证码是：${code}。请不要把验证码泄露给其他人。
     *
     * @param mobile 手机号码
     * @param redisKey 存储验证码的redis key
     * @param source 系统区分
     *               1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号)
     *
     * @return
     */
    public boolean sendHrMobileVertfyCode(String mobile, String redisKey, int source){
        HashMap<String, String> params = new HashMap<String, String>();
        String code = getRandomStr();
        params.put("code", code);
        redisClient.set(Constant.APPID_ALPHADOG, redisKey,
                Constant.HR_ACCOUNT_SIGNUP_SOURCE_ARRAY[source-1], mobile, code);

        return sendSMS(mobile, "SMS_5755096", params);
    }
    
    public boolean sendSMS(String mobile, int scene,String countryCode) throws Exception {
        SMSScene smsScene = SMSScene.instanceFromValue(scene);
    	if (smsScene == null) {
    	    throw CommonException.PROGRAM_PARAM_NOTEXIST;
        }
        HashMap<String, String> params = new HashMap<String, String>();
        String passwordforgotcode = getRandomStr();
        params.put("code", passwordforgotcode);
        if(StringUtils.isNullOrEmpty(countryCode) || "86".equals(countryCode)){
            smsScene.saveVerifyCode("", mobile, passwordforgotcode, redisClient);
            return sendSMS(mobile,"SMS_5755096",params);
        }else{
            smsScene.saveVerifyCode(countryCode, mobile, passwordforgotcode, redisClient);
            return sendNationalSMS(countryCode,mobile,"SMS_5755096",params);
        }
    }

    /**
     * SMS_5855001
     * 感谢您访问仟寻并下载行业报告，为方便您再次访问并使用仟寻移动招聘，我们已免费为您开通雇主权限，账号${name}，密码为${code}，
     * 请及时登录hr.moseeker.com修改您的登录密码，保障账号安全。
     *
     **/
    public boolean sendHrSmsSignUpForDownloadIndustryReport(String mobile, String password){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", mobile);
        params.put("code", password);
        return sendSMS(mobile, "SMS_5855001", params);
    }

    /**
     * 生成数字随机验证码
     *
     * */
    public String getRandomStr(){
        return String.valueOf((int) (Math.random()*9000+1000));
    }

    /**
     * 发送短信次数校验
     * @param mobile
     * @return
     */
    private boolean isMoreThanUpperLimit(String mobile) {
        try {
            return redisClient.isAllowed(SMS_LIMIT_KEY, mobile, Constant.SMS_UPPER_LIMIT, "1");
        } catch (CacheConfigNotExistException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public Boolean sendSMS_signup_voice(String mobile, String code) {
        initTaobaoClientInstance();

        if (StringUtils.isNullOrEmpty(mobile)) {
            return false;
        }

        if (StringUtils.isNullOrEmpty(code)) {
            return false;
        }

//        if (!isMoreThanUpperLimit(mobile.trim())) {
//            return false;
//        }

        try {
            AlibabaAliqinFcTtsNumSinglecallRequest req = new AlibabaAliqinFcTtsNumSinglecallRequest();
            req.setExtend("");
            req.setTtsParamString("{\"product\":\"仟寻\",\"code\":\"" + code + "\"}");
            req.setCalledNum(mobile);
            req.setCalledShowNum("02160554686");
            req.setTtsCode("TTS_5555419");
            AlibabaAliqinFcTtsNumSinglecallResponse rsp = taobaoclient.execute(req);

            System.out.println(rsp.getBody());
            if (rsp.getBody().indexOf("success") > -1) {
                return true;
            }

        } catch (ApiException e) {
            e.printStackTrace();
        }
        return  false;
    }

    /*
    发送国际短信验证
     */
//    public boolean sendNationSMS(String mobile, int scene,String countryCode)throws Exception{
//        String event = null;
//        switch(scene) {
//            case 1:event= "SMS_SIGNUP"; break;
//            case 2:event = "SMS_PWD_FORGOT"; break;
//            case 3:event = "SMS_CHANGEMOBILE_CODE"; break;
//            case 4:event = "SMS_RESETMOBILE_CODE"; break;
//        }
//        HashMap<String, String> params = new HashMap<String, String>();
//        String passwordforgotcode = getRandomStr();
//        params.put("code",passwordforgotcode);
//        redisClient.set(0, event, countryCode+mobile, passwordforgotcode);
//        return sendNationalSMS(countryCode,mobile,"SMS_5755096",params);
//    }
    /*
       发送国际短信验证码
      */
    public boolean sendNationalSMS(String countryCode,String mobile,String smsType,Map<String,String >map) throws Exception{
        boolean result=false;
        String content=getNationalSmsContent(smsType,map);
        if(StringUtils.isNullOrEmpty(content)){
            return result;
        }
        result=this.sendRequestSms(countryCode+mobile,content);
        if(result){
            this.addNationRecord(mobile,content,countryCode);
        }
        return result;
    }

    /*
      插入短信发送记录
     */
    private  boolean addNationRecord(String mobile,String content,String countryCode ){
        LogSmsSendrecordRecord record = new LogSmsSendrecordRecord();
        record.setMobile(Long.valueOf(mobile));
        record.setSys(Constant.LOG_SMS_SENDRECORD_SYS_ALPHADOG);
        JSONObject json = new JSONObject();
        json.put("content", content);
        record.setMsg(json.toJSONString());
        record.setCountryCode(countryCode);
        smsRecordDao.addRecord(record);
        return true;
    }
    //组装短信拼接内容
    private String getNationalSmsContent(String smsType,Map<String,String> params){
        String content="";
        if("SMS_5755096".equals(smsType)){
            content= SmsNationUtil.SMS_5755096;
            content=content.replace("${code}",params.get("code"));
        }
        return content;
    }
    //发送短讯请求
    private boolean sendRequestSms(String mobile,String content) throws Exception{
        StringBuffer buffer = new StringBuffer();
        String encode = "GBK"; //页面编码和短信内容编码为GBK。重要说明：如提交短信后收到乱码，请将GBK改为UTF-8测试。如本程序页面为编码格式为：ASCII/GB2312/GBK则该处为GBK。如本页面编码为UTF-8或需要支持繁体，阿拉伯文等Unicode，请将此处写为：UTF-8
        ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
        String password_md5 = propertiesUtils.get("sms.nation.password_md5",String.class);
        String apikey = propertiesUtils.get("sms.nation.apikey",String.class);
        String username = propertiesUtils.get("sms.nation.username",String.class);  //用户名
        String contentUrlEncode = URLEncoder.encode(content,encode);  //对短信内容做Urlencode编码操作。注意：如            //把发送链接存入buffer中，如连接超时，可能是您服务器不支持域名解析，请将下面连接中的：【m.5c.com.cn】修改为IP：【115.28.23.78】
        buffer.append("http://m.5c.com.cn/api/send/index.php");
        buffer.append("?username="+username+"&password_md5="+password_md5+"&apikey="+apikey);
        buffer.append("&mobile="+mobile+"&content="+contentUrlEncode+"&encode="+encode);
//        System.out.println(buffer); //调试功能，输入完整的请求URL地址
        URL url = new URL(buffer.toString());
        HttpURLConnection connection= (HttpURLConnection)url.openConnection();     //打开URL链接
        connection.setRequestMethod("POST");     //使用POST方式发送
        connection.setRequestProperty("Connection", "Keep-Alive");    //使用长链接方式
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(3000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));    //发送短信内容
        String result = reader.readLine();//获取返回值
        if(result.indexOf("success")>-1){
            return true;
        }
        return false;
    }
}
