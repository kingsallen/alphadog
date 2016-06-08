package com.moseeker.common.util;

/**
 * 
 * Common项目常用配置信息
 * <p>
 *
 * @date Mar 26, 2016
 * @company moseeker
 * @author wjf
 * @email wjf2255@gmail.com
 */
public final class Constant {
    
    private Constant() throws AssertionError {
        throw new AssertionError();
    };

    /** 系统appid 常量定义 && 共同常量定义 START **/
    public static final int APPID_ALPHADOG = 0;     // 基础服务本身
    public static final int APPID_C = 1;            // c端
    public static final int APPID_QX = 2;           // weixin端(聚合号)
    public static final int APPID_PLATFORM = 3;     // weixin端（企业号）
    public static final int APPID_HR = 4;           // hr
    public static final int APPID_ATS = 20;         // ats
    public static final int APPID_SYSPLAT = 21;     // sysplat
    public static final int APPID_CRONJOB = 22;     // cronjob
    public static final int APPID_ANALYTICS = 101;  // 统计

    public static final String DASVALIDATE_SENSITIVEWORDS_ILLEGAL = "敏感词校验失败";

    public static final int logConfigType = 3;
    public static final int cacheConfigType = 1;
    public static final int sessionConfigType = 2;

    public static final int ENABLE = 1;
    public static final int DISABLE = 0;

    public static final int REDIS_CONNECT_ERROR_APPID = 0;
    public static final String REDIS_CONNECT_ERROR_EVENTKEY = "REDIS_CONNECT_ERROR";

    public static final String TIPS_SUCCESS = "success";

    public static final String NONE_JSON = "{}";
    public static final String TIPS_ERROR = "error";

    /** 系统appid 常量定义 && 共同常量定义 END  **/

    /** HR系统 区域 START **/

    // 招聘进度全状态(对应config_sys_points_conf_tpl中ID)
    public static final int RECRUIT_STATUS_APPLY            = 1 ;      // 被推荐人投递简历
    public static final int RECRUIT_STATUS_INTERVIEW        = 2 ;      // HR已经安排面试
    public static final int RECRUIT_STATUS_HIRED            = 3 ;      // 入职
    public static final int RECRUIT_STATUS_REJECT           = 4 ;      // 拒绝
    public static final int RECRUIT_STATUS_INTERVIEWPENDING = 5 ;      // MMGR面试后表示先等待
    public static final int RECRUIT_STATUS_CVCHECKED        = 6 ;      // 简历被HR查看/简历被下载
    public static final int RECRUIT_STATUS_RECOMCLICK       = 7 ;      // 转发职位被点击
    public static final int RECRUIT_STATUS_CVFORWARDED      = 8 ;      // HR将简历转给MGR评审
    public static final int RECRUIT_STATUS_CVPENDING        = 9 ;      // MMGR评审后表示先等待
    public static final int RECRUIT_STATUS_CVPASSED         = 10;      // 简历评审合格
    public static final int RECRUIT_STATUS_OFFERACCEPTED    = 11;      // 接受录取通知
    public static final int RECRUIT_STATUS_OFFERED          = 12;      // 面试通过
    public static final int RECRUIT_STATUS_FULL_RECOM_INFO  = 13;      // 完善被推荐人信息
    
    // profile来源
    public static final int PROFILE_SOURCE_WEIXIN_COMPANY_NOMAL            	= 1 ;      // 微信企业端(正常),
    public static final int PROFILE_SOURCE_WEIXIN_COMPANY_DILIVER        	= 2 ;      // 微信企业端(我要投递)
    public static final int PROFILE_SOURCE_WEIXIN_COMPANY_INTERESTED        = 3 ;      // 微信企业端(我感兴趣), 
    public static final int PROFILE_SOURCE_WEIXIN_TEGETHER_NOMAL           	= 4 ;      // 微信聚合端(正常),
    public static final int PROFILE_SOURCE_WEIXIN_TEGETHER_DILIVER			= 5 ;      // 微信聚合端(我要投递),
    public static final int PROFILE_SOURCE_WEIXIN_TEGETHER_INTERESTED       = 6 ;      // 微信聚合端(我感兴趣), 
    public static final int PROFILE_SOURCE_WEIXIN_COMPANY_EMAIL      		= 100 ;    // 微信企业端Email申请
    public static final int PROFILE_SOURCE_WEIXIN_TEGETHER_EMAIL     		= 101;     // 微信聚合端Email申请
    public static final int PROFILE_SOURCE_WEIXIN_COMPANY_IMPORT        	= 150;     // 微信企业端导入
    public static final int PROFILE_SOURCE_WEIXIN_TEGETHER_IMPORT         	= 151;     // 微信聚合端导入
    public static final int PROFILE_SOURCE_PC_IMPORT    					= 152;     // PC导入
    public static final int PROFILE_SOURCE_PC_CREATE          				= 200;     // PC(正常添加)
    public static final int PROFILE_SOURCE_PC_DILIVER  						= 201;     // PC(我要投递)
    public static final int PROFILE_SOURCE_PC_INTERESTED  					= 202;     // PC(我感兴趣)

    // HR用户注册来源 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号)
    public static final String[] HR_ACCOUNT_SIGNUP_SOURCE_ARRAY = new String[]{"HR", "WWW", "WXSCAN", "QX", "WX"};
    public static final int HR_ACCOUNT_SIGNUP_SOURCE_HR     = 1;      // 雇主
    public static final int HR_ACCOUNT_SIGNUP_SOURCE_WWW    = 2;      // 官网
    public static final int HR_ACCOUNT_SIGNUP_SOURCE_WXSCAN = 3;      // 微信扫描
    public static final int HR_ACCOUNT_SIGNUP_SOURCE_QX     = 4;      // 我也要招人(聚合号)
    public static final int HR_ACCOUNT_SIGNUP_SOURCE_WX     = 5;      // 我也要招人(企业号)


    /** HR系统 区域 END **/


    /** PROFILE 区域 START **/
    public static final int PROFILE_SOURCE_MOSEEKER_MOBILE = 1; //moseeker 手机
	public static final int PROFILE_SOURCE_PROFILE = 2; //PC PROFILE
	public static final int PROFILE_SOURCE_EMAIL = 3; //EMAIL
	public static final int PROFILE_SOURCE_IMPORT = 4; //导入
	
	 /** PROFILE 区域 END **/
	
	/** 公司类型 **/
	public static final int COMPANY_TYPE_FREE = 1; //免费用户
	public static final int COMPANY_TYPE_OTHER = 2; //其他
	public static final int COMPANY_TYPE_ENTERPRISE = 0; //企业用户
	/** 公司类型 **/
	
	/** 公司来源 **/
	public static final int COMPANY_SOURCE_PROFILE = 9; //profile添加
	public static final int COMPANY_SOURCE_HR = 0; //HR系统

}
