package com.moseeker.common.constants;

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

    // status ok状态
    public static final int OK = 0;

    public static final int REDIS_CONNECT_ERROR_APPID = 0;
    public static final String REDIS_CONNECT_ERROR_EVENTKEY = "REDIS_CONNECT_ERROR";
    // 消息模板通知 KEY_IDENTIFIER
    public static final String REDIS_KEY_IDENTIFIER_MQ_MESSAGE_NOTICE_TEMPLATE = "MQ_MESSAGE_NOTICE_TEMPLATE";

    public static final String TIPS_SUCCESS = "success";

    public static final String NONE_JSON = "{}";
    public static final String TIPS_ERROR = "error";
    //异常队列的key值
    public static final String EXCEPTION_LIST_KEY="EXCEPTION_LIST_WARN";
    //统计异常的数值用于报错
    public static final String EXCEPTION_WARN_NUM="EXCEPTION_WARN_NUM";

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
    public static final int PROFILE_SOURCE_UNKNOW           				= 0 ;      // 未知,
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
	//public static final int COMPANY_SOURCE_PROFILE = 9; //profile添加
	public static final int COMPANY_SOURCE_HR = 0; //HR系统
	public static final int COMPANY_SOURCE_DOWNLOAD = 1; //官网下载行业报告
	public static final int COMPANY_SOURCE_PC_EDITING = 7; //PC端添加
	public static final int COMPANY_SOURCE_WX_EDITING = 8; //微信端添加
	public static final int COMPANY_SOURCE_PC_IMPORT = 9; //PC端导入
	public static final int COMPANY_SOURCE_WX_IMPORT = 10; //微信端导入

    /** PROFILE 区域 END **/
	
	/** PROFILE 区域 START **/
    public static final int ACCOUNT_TYPE_SUPERACCOUNT 	= 0; 	//超级帐号
	public static final int ACCOUNT_TYPE_SUBORDINATE 	= 1; 	//子账号
	public static final int ACCOUNT_TYPE_NORMAL 		= 2; 	//普通帐号
	
	
	public static final String THRIFT_CONNECTION_LOST = "thrift 失去连接";
	
	public static final String EXCEPTION_USERRECORD_LOST = "计算用户帐号完整度时，用户帐号信息不能为空";
	public static final String EXCEPTION_PROFILEBASIC_LOST = "计算用户基本信息完整度时，用户基本信息不能为空";
	public static final String EXCEPTION_PROFILEWORKEXP_LOST = "计算工作经历完整度时，工作经历不能为空";
	public static final String EXCEPTION_PROFILEEDUCATION_LOST = "计算教育经历完整度时，教育经历不能为空";
	public static final String EXCEPTION_PROFILEPROJECTEXP_LOST = "计算项目经历完整度时，项目经历不能为空";
	public static final String EXCEPTION_PROFILELANGUAGE_LOST = "计算外语完整度时，外语能力不能为空";
	public static final String EXCEPTION_PROFILESKILL_LOST = "计算技能完整度时，技能不能为空";
	public static final String EXCEPTION_PROFILECREDENTIALS_LOST = "计算证书完整度时，证书不能为空";
	public static final String EXCEPTION_PROFILEAWARDS_LOST = "计算获得奖项完整度时，获得奖项不能为空";
	public static final String EXCEPTION_PROFILEWORKS_LOST = "计算作品完整度时，作品不能为空";
	public static final String EXCEPTION_PROFILINTENTION_LOST = "计算期望职位完整度时，期望职位不能为空";
	
	public static final int PROFILER_COMPLETENESS_USERUSER_MAXVALUE = 8; 		//用户信息完成度最大值
	public static final int PROFILER_COMPLETENESS_BASIC_MAXVALUE = 16; 			//简历基本信息完成度最大值
	public static final int PROFILER_COMPLETENESS_WORKEXP_MAXVALUE = 45; 		//工作经历最大值
	public static final int PROFILER_COMPLETENESS_EDUCATION_MAXVALUE = 10; 		//教育经历最大值
	public static final int PROFILER_COMPLETENESS_PROJECTEXP_MAXVALUE = 10; 	//项目经历最大值
	public static final int PROFILER_COMPLETENESS_LANGUAGE_MAXVALUE = 2; 		//外语技能最大值
	public static final int PROFILER_COMPLETENESS_SKILL_MAXVALUE = 1; 			//技能最大值
	public static final int PROFILER_COMPLETENESS_CREDENTIAL_MAXVALUE = 2;		//获得的证书完成度最大值
	public static final int PROFILER_COMPLETENESS_AWARD_MAXVALUE = 1;			//活得奖项完成度最大值
	public static final int PROFILER_COMPLETENESS_WORKS_MAXVALUE = 1; 			//作品完成度的最大值
	public static final int PROFILER_COMPLETENESS_INTENTION_MAXVALUE = 4;		//求职意愿完成度最大值
	
	public static final int DICT_CONSTANT_COMPANY_SCAL = 1102; 					//公司规模常量表parent_code值
	public static final int DICT_CONSTANT_COMPANY_PROPERTY = 1103; 				//公司性质常量表parent_code值
	
	public static final int EVENT_TYPE_EMAIL_VERIFIED = 1;								//邮件认证邮件模版
	
	public static final String MQ_MESSAGE_EMAIL_BIZ = "MQ_MESSAGE_EMAIL_BIZ";	//业务邮件消息队列的key_identifier
	public static final String EMAIL_VERIFIED_SUBJECT = "邮箱认证";				//业务邮件消息队列的key_identifier
	
	public static final String MQ_MESSAGE_EMAIL_WARNING = "MQ_MESSAGE_EMAIL_WARNING";	//报警邮件消息队列的key_identifier
	
	public static final byte LOG_SMS_SENDRECORD_SYS_ALPHADOG = 5;
	
	public static final int READ_TIME_OUT = 60*1000;
	public static final int CONNECTION_TIME_OUT = 60*1000;
	
	
	public static final String WORDPRESS_POST_CUSTOMFIELD_VERSION = "version";
	public static final String WORDPRESS_POST_CUSTOMFIELD_PLATFORM = "platform";
	
	public static final int WORDPRESS_NEWSLETTER_VALUE 		= 2; 	//博客类别编号为2定义为版本更新
	
	public static final String POSITION_SYNCHRONIZATION_FAILED = "同步失败！";
	public static final String POSITION_REFRESH_FAILED = "刷新失败！";
	
	public static final int BRPOP_TTL 		= 5; 	//brpop超时时间
	public static final int DESCRIPTION_LENGTH 		= 900; 	//描述长度
	
	 public static final String WORDPRESS_POST_POSTSTATUS_PUBLISH = "publish";
}