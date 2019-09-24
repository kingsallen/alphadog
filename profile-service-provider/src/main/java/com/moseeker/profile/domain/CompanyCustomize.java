package com.moseeker.profile.domain;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.HtmlUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import org.elasticsearch.common.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Created by huangxia on 2019/9/17.
 */
public enum CompanyCustomize {
    // 定制化
    Baiji;

    private static Logger logger = LoggerFactory.getLogger(CompanyCustomize.class);
    protected static ConfigPropertiesUtil SETTING_PROPERTIES = ConfigPropertiesUtil.getInstance();
    static {
        try {
            SETTING_PROPERTIES.loadResource("setting.properties");
        } catch (Exception e1) {
            throw new RuntimeException("找不到配置文件setting.properties");
        }
    }

    private final static String BAIJI_URL = SETTING_PROPERTIES.get("BAIJI_URL",String.class);
    private final static String BAIJI_USERNAME = SETTING_PROPERTIES.get("BAIJI_USERNAME",String.class);
    private final static String BAIJI_PASSWORD = SETTING_PROPERTIES.get("BAIJI_PASSWORD",String.class); ;
    private final static MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
    static {
        headers.add("Content-Type", "application/json");
        //headers.add("Accept-Encoding", "gzip, deflate, br");
        headers.add("Connection", "keep-alive");
        headers.add("Authorization", "Basic " + Base64.encodeBytes((BAIJI_USERNAME + ":" + BAIJI_PASSWORD).getBytes()));
        headers.add("Accept-Charset", "utf-8");
    }

    public boolean checkRepeatRecommend(String name,String mobile,String email){
        if(this == Baiji){

            MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
            params.add("phoneNumber",mobile);
            params.add("Email_Address",email);
            logger.info("百济查重 url: {} ,param:{},header: {}",BAIJI_URL,params,headers);
            String restText = null;
            try {
                restText = HttpClient.httpsTrustAllGet(BAIJI_URL,headers,params);
                logger.info("百济查重结果：{}",restText);
                if(StringUtils.isNotNullOrEmpty(restText)){
                    if(HtmlUtils.isHtml(restText)){
                        String title = HtmlUtils.getTitle(restText);
                        logger.error("百济查重错误：{}",title);
                        return false ;
                    }
                    BaijiCheckResult rest = JSONObject.parseObject(restText,BaijiCheckResult.class);
                    if( rest != null && rest.reportEntry != null && rest.reportEntry.size()> 0 ){
                        return true ;
                    }
                }
            } catch (Exception e) {
                logger.error("百济查重错误 http error",e);
            }
        }
        return false ;
    }

    public static void main(String[] args){
        //Baiji.checkRepeatRecommend(null,"18209212004","18209212003@qq.com");
        for(int i=0;i<20;i++){
            new Thread(()->{System.out.println(Baiji.checkRepeatRecommend(null,"18375331858","893207466@qq.com"));}).start();
        }
        //System.out.println(AlphaCloudProvider.Position.buildURL("/test"));
    }

    public static class BaijiCheckResult {
        public class ReportEntry {
            public String candidate; //Wangxisoy Wangxiaoya （王小燕） (C9495)",
            public String phoneNumber; //15755188130",
            public String candidate_ID; //C9495",
            public String emailAddress; //1733613962@qq.com"
        }
        public List<ReportEntry> reportEntry ;
    }
}
