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

    public static final int BONUS = 2;  //默认红包金额
    public static final String POINTS_CONF_EMPLOYEE_VERIFIED = "完成员工认证";
    public static final String POINTS_CONF_REFINE_CANDIDATE= "完善被推荐人信息";
    public static final String EMPLOYEE_PARSE_PROFILE_DOCUMENT = "员工简历简历数据";
    public static final String USER_PARSE_PROFILE_DOCUMENT = "用户简历数据";
    public static int MAX_SIZE = 60000;

    //身份证识别组件
    public static final String IDCARD_RECOG = "id_card";
    public static final String IDPHOTO_FRONT = "id_card_front";
    public static final String IDPHOTO_BACK = "id_card_back";

    private Constant() throws AssertionError {
        throw new AssertionError();
    };

    public static final String CONST_APPID   = "appid";      // appid
    public static final String CONST_INTERFACEID   = "interfaceid";      // interfaceid

    /** 系统appid 常量定义 && 共同常量定义 START **/
    public static final int APPID_ALPHADOG = 0;     // 基础服务本身
    public static final int APPID_ALPHACLOUD = 301; // 基础服务
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
    public static final byte elkConfigType = 5;

    public static final int ENABLE = 1;
    public static final int DISABLE = 0;

    public static final int ENABLE_OLD = 0;
    public static final int DISABLE_OLD = 1;

    public static final int FIVE_THOUSAND = 5000;
    public static final int ONE = 1;
    public static final int FIVE_HUNDRED = 500;
    public static final int ONE_HUNDRED = 1000;

    // status ok状态
    public static final int OK = 0;

    public static final int REDIS_CONNECT_ERROR_APPID = 0;
    public static final String REDIS_CONNECT_ERROR_EVENTKEY = "REDIS_CONNECT_ERROR";
    public static final String REDIS_CACHE_CONFIG_NOTEXIST_ERROR_EVENTKEY = "REDIS_CACHE_CONFIG_NOTEXIST_ERRO";
    // 消息模板通知 KEY_IDENTIFIER
    public static final String REDIS_KEY_IDENTIFIER_MQ_MESSAGE_NOTICE_TEMPLATE = "MQ_MESSAGE_NOTICE_TEMPLATE";

    public static final String EMPLOYEE_REGISTER_EXCHNAGE = "employee_verification_exchange";
    public static final String EMPLOYEE_FIRST_REGISTER_EXCHNAGE_ROUTINGKEY = "employee_verification_exchange.#";

    public static final String EMPLOYEE_FIRST_REGISTER_ADD_REDPACKET_EXCHANGE = "redpacket_exchange";
    public static final String EMPLOYEE_FIRST_REGISTER_ADD_REDPACKET_ROUTINGKEY = "employee_bind.red_packet";
    public static final String POSITION_SYNC_FAIL_ROUTINGKEY = "messagetemplate.position_sync_fail";

    //延时交换机常量,以及队列常量
    public static final String ACTIVITY_DELAY_EXCHANGE = "activity_delay_exchange_dog";
    public static final String EXCHANGE_TYPE_DELAY = "x-delayed-type";
    public static final String MESSAGE_TYPE_DELAY = "x-delayed-message";
    public static final String ACTIVITY_DELAY_QUEUE = "activity_delay_queue_dog";
    public static final String ACTIVITY_DELAY_ROUTING_KEY = "activity_delay_routing_key";



    public static final String EMPLOYEE_SEEK_REFERRAL_TEMPLATE = "seek_referral.referral_template";
    public static final String EMPLOYEE_REFERRAL_EVALUATE = "referral_evaluate.referral_template";
    public static final String REFERRAL_RADAR_TEMPLATE = "referral_radar.referral_radar_template";

    public static final String BALANCE_CHARGE_ROUTINGKEY = "balance_charge.redpacket_template";
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
    public static final int RECRUIT_STATUS_EMPLOYEE_REGISTER  = 14;    // 完成员工员工认证
    public static final int RECRUIT_STATUS_UPLOAD_PROFILE   = 15;      // 员工上传人才简历
    public static final int RECRUIT_STATUS_EMPLOYEE_RECOMMEND   = 16;      // 员工完成联系内推推荐评价
    public static final int RECRUIT_STATUS_WRITTEN_EXAMINATION   = 17;      // 笔试
    public static final int RECRUIT_STATUS_FINAL_INTERVIEW   = 18;      // 终极面试

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
    public static final int PROFILE_SOURCE_MOBILE_BROWSER 					= 153;     // 移动浏览器
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
	
	public static final int EVENT_TYPE_EMAIL_VERIFIED = 1;						//邮件认证邮件模版
	public static final int EVENT_TYPE_EMPLOYEE_AUTH = 2; 						//员工认证邮件模板
	public static final int EVENT_TYPE_RECOMMEND_VALID_EMAIL = 3;               //推荐职位时验证邮箱
	public static final int EVENT_TYPE_RECOMMEND_POSITION_EMAIL = 4;              //推荐职位邮箱

    public static final byte AUTH_METHON_TYPE_CUSTOMIZE = 1;

	public static final String MQ_MESSAGE_EMAIL_BIZ = "MQ_MESSAGE_EMAIL_BIZ";	//业务邮件消息队列的key_identifier
	public static final String EMAIL_VERIFIED_SUBJECT = "邮箱认证";				//业务邮件消息队列的key_identifier
	
	public static final String MQ_MESSAGE_EMAIL_WARNING = "MQ_MESSAGE_EMAIL_WARNING";	//报警邮件消息队列的key_identifier
	public static final String MQ_MESSAGE_EMAIL_MANDRILL = "MQ_MESSAGE_EMAIL_MANDRILL";	//mandrill 邮件消息队列的key_identifier	
	
	public static final String EMPLOYEE_AUTH_CODE = "EMPLOYEE_AUTH_CODE"; // 员工认证 （邮箱激活链接）
    public static final String EMPLOYEE_AUTH_INFO = "EMPLOYEE_AUTH_INFO"; // 员工认证（员工认证信息）
    public static final String EMPLOYEE_DEFAULT_CUSTOM_FIELD_VALUE = "[]"; // user_employee.custom_field_value默认值

    // 认证状态 0：认证成功，1：认证后取消认证 2：认证失败 3：未认证 4：认证后又认证了其他公司导致本条数据变成未认证
    // 5:取消关注公众号   6：员工离职取消认证
    public static final byte EMPLOYEE_ACTIVATION_UNBIND = 1;
    public static final byte EMPLOYEE_ACTIVATION_UNEMPLOYEE = 6;

    public static final byte LOG_SMS_SENDRECORD_SYS_ALPHADOG = 5;
	
	public static final int READ_TIME_OUT = 300*1000;
	public static final int CONNECTION_TIME_OUT = 300*1000;
	
	
	public static final String WORDPRESS_POST_CUSTOMFIELD_VERSION = "version";
	public static final String WORDPRESS_POST_CUSTOMFIELD_PLATFORM = "platform";
	
	public static final int WORDPRESS_NEWSLETTER_VALUE 		= 2; 	//博客类别编号为2定义为版本更新
	
	public static final String POSITION_SYNCHRONIZATION_FAILED = "同步失败！";
	public static final String POSITION_REFRESH_FAILED = "刷新失败！";
	
	public static final int BRPOP_BLOCKING_TIMEOUT 		= 5; 	//brpop超时时间
	public static final int DESCRIPTION_LENGTH 		= 900; 	//描述长度
	
    public static final String WORDPRESS_POST_POSTSTATUS_PUBLISH = "publish";

    public static final int SMS_UPPER_LIMIT = 10; //暂时限制2条短信
    public static final int PROFILE_IMPORT_UPPER_LIMIT = 3; //没人每天简历导入次数

    public static final String HR_HEADIMG = "weixin/images/default-HR.png";
    public static final long BIND_GET_REDIS_TIMEOUT=240000;


    public static final int TEMPLATES_APPLY_NOTICE_TPL = 3; //申请成功时的消息通知ID
    public static final int TEMPLATES_NEW_RESUME_TPL = 24; // 新简历通知的消息通知ID
    public static final int EMPLOYEE_EMAILVERIFY_NOT_VERIFY_NOTICE_TPL = 79; // 新简历通知的消息通知ID
    public static final int POSITION_SHARE_NOTICE_TPL = 74; // 新简历通知的消息通知ID
    public static final int POSITION_VIEW_TPL = 39; // 新简历通知的消息通知ID
    public static final int TEMPLATES_REFERRAL_BONUS_NOTICE_TPL = 81; // 内推入职奖金
    public static final int TEMPLATES_AWARD_CONSUME_NOTICE_TPL = 83; // 积分商城积分消费模板消息ID
    public static final int TEMPLATES_AWARD_RETURN_NOTICE_TPL = 84; // 积分商城积分返还模板消息ID
    public static final int REFERRAL_INVITE_APPLICATION = 85; // 员工邀请候选人进行职位投递
    public static final int REFERRA_RECOMMEND_EVALUATE = 86; // 员工完成推荐评价进行内推之后发送给候选人
    public static final int REFERRAL_SEEK_REFERRAL = 87; // 联系内推发送给员工的模板消息ID
    public static final int REDPACKET_CHARGE_AMOUNT = 89; // 联系内推发送给员工的模板消息ID

    public static final int POSITION_SYNC_FAIL_NOTICE_TPL = 88; // 新简历通知的消息通知ID

    public static final int TEMPLATES_SWITCH_APPLY_NOTICE_TPL = 29; //申请成功时 的消息通知ID
    public static final int TEMPLATES_SWITCH_NEW_RESUME_TPL = 41; //认证员工转发之后后有人投递简历 的消息通知ID

    public static final String RESUME_INFORM_HR = "resume-detail-v2"; //简历投递给HR发邮件没有附件模板   新简历模板
    public static final String ANNEX_RESUME_INFORM_HR = "annex-resume-delivery-to-hr-email";
    public static final String MISMATCH_NOTIFICATION = "mismatch-notification";//简历投递不匹配发送邮件
    public static final String DELIVERY_SUCCESS = "delivery-success";//简历投递

    public static final int DICT_CONSTANT_DEGREE_USER = 3104; 		//求职者学历常量表parent_code值
    public static final int DICT_CONSTANT_GENDER_USER = 3109;       //性别常量表parent_code值
    public static final int DICT_CONSTANT_LANGUAGE_FRUENCY =3108;   //语言-掌握程度常量表parent_code值

    public static final String CDN_URL = "https://cdn.moseeker.com/";

    //微信消息模板
    public static final int EMPLOYEE_PROFILE_COMPLETION=56;
    public static final int FANS_PROFILE_COMPLETION=58;
    public static final int FANS_RECOM_POSITION=59;
    public static final int EMPLOYEE_RECOM_POSITION=57;
    public static final int POSITION_SYNC_VERIFY_INFO=66;
    public static final int AWARD_RANKING=78;

    public static final int PAGE_SIZE=20;
    public static final int PAGE_=20;

    public static final String CHINA_ID ="43";

    public static final int DATABASE_PAGE_SIZE=500;

    public static final int RETRY_UPPER = 3;

    //小程序简历详情路径
    public static  final String WX_APP_PROFILE_INFO_URL = "pages/profile?user_id={}";
    //简历列表的索引名称
    public static final String ES_INDEX="users";
    public static final String ES_TYPE="users";
    //职位列表索引
    public static final String ES_POSITION_INDEX="index";
    public static final String ES_POSITION_TYPE="fulltext";

    public static final String POSITION_WX_TYPE="fulltext";
    public static final String POSITION_WX_INDEX="index";

    //talentpool邮件的config_id
    public static final int TALENTPOOL_EMAIL_PROFILE_FILTER_NOT_PASS = 70;
    public static final int TALENTPOOL_EMAIL_PROFILE_SEND = 69;

    public static final int RETRY_UPPER_LIMIT = 3;
    //
    public static final Integer[] TALENTPOOL_EMAIL_SWITCH_ID = {69,70,71,72,73};

    public static final String COMPANY_LOGO_URL ="/hr/common/images/default-company-logo.jpg";

    public static final int HKAMTW = 861;
    public static final int UPPER_LIMIT = 500;  //接口处理的数据量的上限

    public static final String LEADER_BOARD_UPVOTE_COUNT = "LEADER_BOARD_UPVOTE_COUNT";

    public static final String COMPANY_LOGO_DEFAULT = "http://o6yey4xgg.bkt.clouddn.com/common/images/default-company-logo.jpg";


    public static final String PROFILE_SEARCH_KEYWORD_COLUMS="recent_job.company_name;recent_job_company_new_name;recent_job.company_name_data;recent_job.position_name;recent_job.description;" +
            "recent_job.job;recent_job.job_name;recent_job.job_name_data;other_workexps.company_name_data;other_workexps.company_name;other_workexps.company_new_name;other_workexps.position_name;" +
            "other_workexps.description;educations.major_name;other_workexps.job_name;other_workexps.job;other_workexps.job_name_data;" +
            "educations.description_data;projectexps.name_data;projectexps.company_name;projectexps.role;projectexps.description_data;" +
            "languages.name;skills.name_data;awards.name;credentials.name_data;" +
            "others.other;basic.company_name;basic.industry_name;basic.current_job;basic.position_name;";

    public static final String USERNAME_IS_NULL="未填写";

    public static final String EMPLOYEE_BATCH_DELETE_FAILED = "EMPLOYEE_BATCH_DELETE_FAILED";

    public static final int IS_NOT_OPEN_GRPD=1;
    public static final String WORD_DOC="doc";
    public static final String WORD_DOCX = "docx";
    public static final String WORD_PDF = ".pdf";

    public static final int ELASTIC_LAYER_QRCODE = 0;
    public static final int ELASTIC_LAYER_PROFILE = 1;

    public static final int POSITION_STATUS_START = 0;

    public static final String KAFKA_GROUP_RADAR_TOPN = "radar_group_new_top";
    public static final String KAFKA_TOPIC_RADAR_TOPN = "radar_new_topn_result";
    public static final String KAFKA_TOPIC_ASK_REFERRAL = "radar_ask_for_referral";
    public static final String KAFKA_TOPIC_APPLICATION = "radar_application";
    public static final String KAFKA_TOPIC_INVITE_APPLY = "radar_invite_to_apply";
    public static final String KAFKA_TOPIC_FORWARD_VIEW = "radar_social_graph_change";
    public static final String KAFKA_TOPIC_POSITION_STATUS = "radar_position_status";
    public static final String KAFKA_TOPIC_EMPLOYEE_CERTIFICATION = "radar_employee_certification";
    public static final String KAFKA_TOPIC_RADAR_STATUS = "radar_button_status";
    public static final String KAFKA_TOPIC_IM_TPL_MSG = "jdpv_im_topic";

    /**
     * B端上传简历时，创建doc转pdf，存储pdf的目录地址
     */
    public static final String WORD_TO_PDF_2B_DIRCTORY="/mnt/nfs/attachment/candidate_upload";



    //简历上传小程序推荐来源的认领不需要校验手机号
    public static final String NO_VSCODE_CHECK = "no_vscode_check";

    //最新的隐私协议版本号
    public static final byte PRIVACY_POLICY_RELEASE_VERSION = 1;
    public static final byte PRIVACY_POLICY_DISAGREE_VERSION = 0;
}