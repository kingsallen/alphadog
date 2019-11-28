package com.moseeker.common.constants;

import com.moseeker.common.util.ConfigPropertiesUtil;

/**
 * Created by qiancheng on 2019/9/17.
 */
public enum AlphaCloudProvider {

    //alphacloud的所有服务的appid，interfaceid
    /*
    * A11001: provider-jooq
    A11003: provider-dict
    A11005: provider-user
    A11007: provider-company
    A11009: provider-position
    A11011: provider-profile
    A11013: provider-quartz
    A11015: provider-cache
    A11017: provider-application
    A11019: provider-ats
    A11021: provider-search
    A11023: provider-account
    A11025: provider-talentpool
    A11027: provider-parsing
    A11029: provider-messaging
    A11031: provider-careerstory
    A11033: provider-jobboard
    A11035: provider-statistics
    A11037: provider-im
    A10001: facade-hr
    A10003: facade-wechat
    A11039: provider-redpacket
    A11041: provider-airobot
    A11042: provider-employee
    A11045: provider-rbac*/

    Position("position",11009,"A11009"),
    User("user",11005,"A11005"),
    Company("company",11007,"A11007"),
    Application("application",11017,"A11017"),
    Parsing("parsing",11027,"A11027");

    public static ConfigPropertiesUtil SETTING_PROPERTIES = ConfigPropertiesUtil.getInstance();
    static {
        try {
            SETTING_PROPERTIES.loadResource("setting.properties");
        } catch (Exception e1) {
            throw new RuntimeException("找不到配置文件setting.properties");
        }
    }
    public static final String CLOUD_HOST = SETTING_PROPERTIES.getInstance().get("alphacloud_host", String.class);


    private String name ;
    private String appid ;
    private String interfaceid ;
    private int port ;

    AlphaCloudProvider(String name, int port, String appid) {
        this.name = name;
        this.appid = appid;
        this.interfaceid = appid + "001";
        this.port = port;
    }

    public String getAppIdAndInterfaceId(){
        return String.format("appid=%s&interfaceid=%s",appid,interfaceid);
    }

    public String buildURL(String host,String path){
        String url = host.endsWith("/")? host.substring(0,host.length()-1) : host ;

        if(host.contains("127.0.0.1") || host.contains("localhost")){
            url += ":" + port ;
        }else {
            url += "/" + name  ;
        }
        if(!path.startsWith("/")){
            url += "/" ;
        }
        url += path  + (path.contains("?")?"&":"?") + getAppIdAndInterfaceId();
        return url;
    }

    public String buildURL(String path){
        return buildURL(CLOUD_HOST,path);
    }

}
