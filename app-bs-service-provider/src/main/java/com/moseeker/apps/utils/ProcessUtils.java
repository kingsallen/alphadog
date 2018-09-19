package com.moseeker.apps.utils;

public class ProcessUtils {

	public static final int DB_OPREATE_SUCCESS = 0;		
	public static final int DB_PARAM_REQUIRED = -1;		//参数校验
	public static final int DB_FIND_NOTHING = -2;		//没找到
	public static final int DB_PARAM_NOTASEXPECTED = -3;  //参数类型不对
	
	// other
	public static final String PAGE_CURRENT_NUMBER_ERR = "{\"errcode\":1901, \"errmsg\":\"当前页码错误\"}";
	public static final String PAGE_LIMIT_NUMBER_ERR = "{\"errcode\":1902, \"errmsg\":\"分页限制量错误\"}";
	public static final String HOME_POSITION_MANAGE_ERR = "{\"errcode\":1903, \"errmsg\":\"主页职位管理查询失败\"}";
	public static final String HAVE_NO_PERMISSION_TO_ACCESS = "{\"errcode\":1904, \"errmsg\":\"没有权限执行该操作\"}";
	
	
	public static final int ABLE = 0;
	public static final int DISABLE = 1;
	
	public static final int ACCOUNT_ABLE = 1;
	public static final int ACCOUNT_DISABLE = 0;
	
	public static final int ABLE_NEW = 1;
	public static final int DISABLE_NEW = 0;

	public static final int BONUS = 2; // 红包金额
	public static final int APPLICATION_EXPORT_DATE_BEFORE = -10;
	
	public static final int SMS_UPPER_LIMIT = 40;

	// 职位置顶标记
	public static final int POSITION_TOP_LEVEL_0 = 0;
	// 申请被查看标记
	public static final int APPLICATION_IS_VIEWED = 0;
	// 申请未被查明标记
	public static final int APPLICATION_IS_NOT_VIEWED = 1;
	// 简历未查阅标记
	public static final int RESUME_IS_NOT_VIEWED = 1;
	// 简历不合适标记
	public static final int APPLICATION_NOT_SUIT = 1;
	// 简历合适标记
	public static final int APPLICARIOIN_IS_SUIT = 0;
	// 首页在招职位显示数
	public static final int HOME_POSITION_LIST_NUM = 4;
	// 招聘新增职位显示数
	public static final int HOME_RECRUIT_LIST_NUM = 4;
	// 招聘新增职位显示数
	public static final int HOME_NEW_ADD_POSITION_NUM = 4;
	//批量下载盐
	public static final String DOWNLOAD_SALT = "DQ-MoSeeker";
	//HR招聘操作进度类型
	public static final int HRRECRUITRECORD_TYPE = 1;
	
	//子账号类型
	public static final int SON_ACCOUNT_TYPE = 1;
	
	public static final String PASS = "通过";
	
	public static final String FORBID = "未通过";
	
	public static final String HR_SESSION = "user_hr_account_";
	
	public static final int ACCOUNT_TYPE_SUPERACCOUNT = 0;
	public static final int ACCOUNT_TYPE_INFERERACCOUNT = 1;
	public static final int ACCOUNT_TYPE_NORMALACCOUNT = 2;
	
	//招聘进度全状态/招聘进度状态和ID一起表示该招状态对应的ID
	public static final int RECRUIT_STATUS_RECOMCLICK = 1;//转发被点击
	public static final int RECRUIT_STATUS_FULL_RECOM_INFO = 2;//完善被推荐人
	public static final int RECRUIT_STATUS_APPLY = 3;//提交简历成功
	public static final int RECRUIT_STATUS_CVCHECKED = 4;//简历被查看
	public static final int RECRUIT_STATUS_CVFORWARDED = 5;//转发简历MGR评审
	public static final int RECRUIT_STATUS_CVPENDING = 6;//MGR评审后表示先等待
	public static final int RECRUIT_STATUS_CVPASSED = 7;//评审通过要求面试
	public static final int RECRUIT_STATUS_INTERVIEW = 8;//HR安排面试
	public static final int RECRUIT_STATUS_INTERVIEWPENDING = 9;//MGR面试后表示先等待
	public static final int RECRUIT_STATUS_OFFERED = 10;//发出录取通知
	public static final int RECRUIT_STATUS_OFFERACCEPTED = 11;//接受录取通知
	public static final int RECRUIT_STATUS_HIRED = 12;//入职
	public static final int RECRUIT_STATUS_REJECT = 13;//拒绝
	
	public static final String RECRUIT_STATUS_FULL_RECOM_INFO_MESSAGE = "完善被推荐人信息"; //完善被推荐人
	
	public static final int RECRUIT_STATUS_APPLY_ID = 1;//提交简历成功
	public static final int RECRUIT_STATUS_EMPLOYEE_REFERRAL = 15;//内推
	public static final int RECRUIT_FULL_RECOM_ID = 13;//完善被推荐人信息
	public static final int RECRUIT_STATUS_CVCHECKED_ID = 6;//简历被查看
	public static final int RECRUIT_STATUS_REJECT_ID = 4;//简历被查看
	
	//错误总览
	public static final String GET_PARAM_ERR = "{\"errcode\":9999, \"errmsg\":\"获取参数错误!\"}";
	public static final String CAN_NOT_FIND_DATA_ERR = "{\"errcode\":9998, \"errmsg\":\"无法根据参数查找到数据!\"}";
	
	// 普通类型
	public static final String POSITION_SHARE_TYPE_EMPLOYER = "1";
	public static final String POSITION_SHARE_TYPE_EMPLOYEE = "2";
	public static final String POSITION_SHARE_TYPE_EMP_ADD = "3";
	public static final String POSITION_SHARE_TYPE_EMP_DEFAULT = "4";
	
	// 职位招聘中标记(数据库标记:有效的)
	public static final int POSITION_VISIABLE_STATUS = 0;
	// 职位已关闭标记(数据库标记:无效的)
	public static final int POSITION_NOVISIABLE_STATUS = 1;
	// 职位撤销(数据库字段说明:撤销)
	public static final int POSITION_CANCEL_STATUS = 2;

	// 开启被动求职者状态
	public static final String WECHAT_PASSIVE_SEEKER_ON = "0";
	public static final String WECHAT_PASSIVE_SEEKER_OFF = "1";

	//错误总配置
	public static final String PARAM_NOT_SUPPORT_ERR = "{\"errcode\":9901, \"errmsg\":\"参数入职!\"}";
	public static final String PARAM_CONTAIN_SPECIALCHARACTOR = "{\"errcode\":9902, \"errmsg\":\"包含特殊字符!\"}";
	// 以下是错误配置信息，公司的错误从1201开始，职位的从1301，申请的从1401, 简历的从1501.
	// company begin wit 1201
	public static final String COMPANY_ID_CANNOT_BE_NULL_ERR = "{\"errcode\":1201, \"errmsg\":\"公司ID不能为空\"}";
	public static final String COMPANY_DEPARTMENT_ID_CANNOT_BE_NULL_ERR = "{\"errcode\":1202, \"errmsg\":\"部门ID不能为空\"}";
	public static final String COMPANY_UPDATE_ERR = "{\"errcode\":1203, \"errmsg\":\"公司信息修改失败\"}";
	public static final String COMPANY_UPDATELOGO_ERR = "{\"errcode\":1204, \"errmsg\":\"公司LOGO地址错误\"}";
	public static final String COMPANY_OCCURERR = "{\"errcode\":1205, \"errmsg\":\"程序异常\"}";
	public static final String COMPANY_PARAM_ERR = "{\"errcode\":1206, \"errmsg\":\"无法根据参数查找到数据\"}";
	public static final String COMPANY_SUPER_REPEART_NAME = "{\"errcode\":1207, \"errmsg\":\"和超级账号重名\"}";
	public static final String COMPANY_SUPER_OVER_MERGE_LIMIT = "{\"errcode\":1208, \"errmsg\":\"合并公司过长\"}";
	
	public static final String LOGIN_ACCOUNT_ILLEGAL = "{\"errcode\":1003, \"errmsg\":\"手机号码或登录密码不正确\"}";
	public static final String LOGIN_ACCOUNT_DISABLED = "{\"errcode\":1004, \"errmsg\":\"用户被禁用\"}";
	public static final String LOGIN_ACCOUNT_REPEAT = "{\"errcode\":1005, \"errmsg\":\"账号信息重复\"}";
	public static final String COMPANY_NAME_BUSINESS_UPDATE_ERR = "{\"errcode\":1207, \"errmsg\":\"公司名称和所属行业修改太频繁\"}";

	// position begin with 1301

	public static final String POSITION_ID_CANNOT_BE_NULL_ERR = "{\"errcode\":1301, \"errmsg\":\"职位ID不能为空\"}";
	public static final String POSITION_JOBNUM_CANNOT_BE_NULL_ERR = "{\"errcode\":1302, \"errmsg\":\"职位编号不能为空\"}";
	public static final String POSITION_TITLE_CANNOT_BE_NULL = "{\"errcode\":1303, \"errmsg\":\"职位名称不能为空\"}";
	public static final String POSITION_PROVINCE_CANNOT_BE_NULL_ERR = "{\"errcode\":1304, \"errmsg\":\"该职位所属省份不能为空\"}";
	public static final String POSITION_CITY_CANNOT_BE_NULL_ERR = "{\"errcode\":1305, \"errmsg\":\"该职位所属城市不能为空\"}";
	public static final String POSITION_DEPARTMENT_CANNOT_BE_NULL_ERR = "{\"errcode\":1306, \"errmsg\":\"该职位所属部门不能为空\"}";
	public static final String POSITION_REQUIRED_EDUCATION_CANNOT_BE_NULL_ERR = "{\"errcode\":1307, \"errmsg\":\"教育经历不能为空\"}";
	public static final String POSITION_REQUIRED_EXPERIENCE_CANNOT_BE_NULL_ERR = "{\"errcode\":1308, \"errmsg\":\"工作经历不能为空\"}";
	public static final String POSITION_SALART_CANNOT_BE_NULL_ERR = "{\"errcode\":1309, \"errmsg\":\"职位薪水不能为空\"}";
	public static final String POSITION_OCCUPATION_CANNOT_BE_NULL_ERR = "{\"errcode\":1310, \"errmsg\":\"职位类别不能为空\"}";
	public static final String POSITION_INDUSTRY_EXPERIENCE_CANNOT_BE_NULL_ERR = "{\"errcode\":1311, \"errmsg\":\"所属行业不能为空\"}";
	public static final String POSITION_STATUS_CANNOT_BE_NULL_ERR = "{\"errcode\":1312, \"errmsg\":\"职位状态不能为空\"}";
	public static final String POSITION_SHARE_TPL_ID_CANNOT_BE_NULL_ERR = "{\"errcode\":1312, \"errmsg\":\"职位分享ID为空\"}";

	public static final String POSITION_PREVIEW_INFO_ERR = "{\"errcode\":1351, \"errmsg\":\"职位查询失败\"}";
	public static final String POSITION_INSERT_ERR = "{\"errcode\":1352, \"errmsg\":\"职位发布失败\"}";
	public static final String POSITION_CANNOT_BE_FOUND_ERR = "{\"errcode\":1353, \"errmsg\":\"查询不到此职位\"}";
	public static final String POSITION_UPDATE_ERR = "{\"errcode\":1354, \"errmsg\":\"职位更新失败!\"}";
	public static final String POSITION_DATA_VIEW_ERR = "{\"errcode\":1355, \"errmsg\":\"职位统计失败\"}";
	public static final String POSITION_LIST_ERR = "{\"errcode\":1356, \"errmsg\":\"查询职位列表失败\"}";
	public static final String POSITION_TOP_ERR = "{\"errcode\":1357, \"errmsg\":\"该次职位置顶失败\"}";
	public static final String POSITION_REVOKE_ERR = "{\"errcode\":1358, \"errmsg\":\"该次职位撤销失败\"}";
	public static final String POSITION_NOT_SUITABLE_ERR = "{\"errcode\":1359, \"errmsg\":\"申请不合适更新失败\"}";
	public static final String POSITION_UPDATE_SHARETEMPLATE_ERR = "{\"errcode\":1360, \"errmsg\":\"修改职位分享模板失败\"}";
	public static final String POSITION_ADD_SHARETEMPLATE_ERR = "{\"errcode\":1361, \"errmsg\":\"添加职位分享模板失败\"}";
	public static final String POSITION_SHARETEMPLATE_ID_ERR = "{\"errcode\":1362, \"errmsg\":\"职位分享模板ID不能为空\"}";
	public static final String POSITION_SHARETEMPLATE_STAUTUS_ERR = "{\"errcode\":1363, \"errmsg\":\"职位分享模板可用状态不能为空\"}";
	public static final String POSITION_SHARETEMPLATE_TITLE_ERR = "{\"errcode\":1363, \"errmsg\":\"职位分享模板标题不能为空\"}";
	public static final String POSITION_SHARETEMPLATE_DESCRIPTION_ERR = "{\"errcode\":1363, \"errmsg\":\"职位分享模板描述不能为空\"}";
	public static final String POSITION_SHARETEMPLATE_UPDATE_ERR = "{\"errcode\":1364, \"errmsg\":\"职位分享模板更新失败\"}";
	public static final String POSITION_SHARETEMPLATE_FIND_ERR = "{\"errcode\":1364, \"errmsg\":\"职位分享模板查询失败\"}";
	public static final String POSITION_ERR = "{\"errcode\":1365, \"errmsg\":\"未获取到职位的信息\"}";
	public static final String POSITION_EMPLOYEE_ID_CANNOT_BE_NULL_ERR = "{\"errcode\":1366, \"errmsg\":\"员工号不能为空\"}";
	public static final String POSITION_SHARETEMPLATE_PARAM_ERR = "{\"errcode\":1367, \"errmsg\":\"职位分享模板类型标题内容为必填\"}";
	// application begin with 1401
	public static final String APPLICATION_ID_CANNOT_BE_NULL_ERR = "{\"errcode\":1410, \"errmsg\":\"申请ID不能为空\"}";
	public static final String APPLIER_ID_CANNOT_BE_NULL_ERR = "{\"errcode\":1410, \"errmsg\":\"申请者ID不能为空\"}";
	public static final String APPLICATION_NOT_SUITABLE_CANNOT_BE_NULL_ERR = "{\"errcode\":1411, \"errmsg\":\"申请的不合适标记不能为空\"}";
	public static final String APPLICATION_NOT_EXIST_CAN_NOT_GO_NEXTERR = "{\"errcode\":1412, \"errmsg\":\"您的公司没有该申请，不能进行下一步操作\"}";
	public static final String APPLICATION_DOWNLOAD_FREQUENTLY = "{\"errcode\":1413, \"errmsg\":\"您刚执行过批量下载，请稍后再试\"}";
	public static final String APPLICATION_DOWNLOAD_FREQUENTLY_WIEH_SAME_APP = "{\"errcode\":1414, \"errmsg\":\"您下载相同简历过于频繁，请稍后再试\"}";
	public static final String APPLICATION_DOWNLOAD_ACCESS_ERR = "{\"errcode\":1415, \"errmsg\":\"您没有权限执行此次下载\"}";

	public static final String APPLICATION_LIST_ERR = "{\"errcode\":1451, \"errmsg\":\"查询申请列表失败\"}";
	public static final String APPLICATION_DATA_VIEW_ERR = "{\"errcode\":1452, \"errmsg\":\"数据总览查询失败\"}";
	public static final String APPLICATION_VIEW_STATUS_UPDATE_ERR = "{\"errcode\":1453, \"errmsg\":\"查看数据状态更新失败\"}";
	public static final String APPLICATION_NOT_SUITABLE_UPDATE_ERR = "{\"errcode\":1454, \"errmsg\":\"申请是否合适状态更新失败\"}";
	public static final String APPLICATION_VIEW_ERR = "{\"errcode\":1455, \"errmsg\":\"查询申请失败\"}";

	// resume begin with 1501
	public static final String RESUME_ID_CANNOT_BE_NULL_ERR = "{\"errcode\":1551, \"errmsg\":\"简历ID不能为空\"}";
	public static final String RESUME_VIEW_COUNT_UPDATE_ERR = "{\"errcode\":1552, \"errmsg\":\"简历查询数据更新失败\"}";
	public static final String RESUME_VIEW_COUNT_UPDATE_SUCC = "{\"errcode\":1453, \"errmsg\":\"简历查看数据更新成功\"}";

	// employee
	public static final String EMPLOYEE_ID_CAN_NOT_BE_NULL = "{\"errcode\":1601, \"errmsg\":\"员工ID不能为空\"}";
	public static final String EMPLOYEE_PASSWORD_NOT_EXACT = "{\"errcode\":1602, \"errmsg\":\"密码不正确\"}";
	public static final String EMPLOYEE_NOT_BIDING_THIS_WECHAT = "{\"errcode\":1603, \"errmsg\":\"当前用户不是绑定的该微信\"}";
	public static final String EMPLOYEE_MOBILE_EXIST = "{\"errcode\":1604, \"errmsg\":\"手机号码已存在\"}";
	public static final String EMPLOYEE_REPEAT_EMAIL= "{\"errcode\":1605, \"errmsg\":\"员工邮箱信息重复！\"}";
	public static final String EMPLOYEE_REPEAT_CUSTOM = "{\"errcode\":1606, \"errmsg\":\"员工姓名及自定义信息重复！\"}";

	// CompanyReport
	public static final String COMPANYREPORT_ADD_EXCEPTION = "{\"errcode\":1701, \"errmsg\":\"行业报告出现异常\"}";

	// wechat begin with 1801
	public static final String WECHAT_ID_CAN_NOT_BE_NULL = "{\"errcode\":1801, \"errmsg\":\"微信ID不能为空\"}";
	public static final String WECHAT_PASSIVE_SEEKER_CAN_NOT_BE_NULL = "{\"errcode\":1802, \"errmsg\":\"微信公众号被动求职者开关不能为空\"}";
	public static final String WECHAT_USER_NAME_CAN_NOT_BE_NULL = "{\"errcode\":1802, \"errmsg\":\"微信公众号被动求职者开关不能为空\"}";
	public static final String WECHAT_PASSIVE_SEEKER_UPDATE_ERR = "{\"errcode\":1802, \"errmsg\":\"微信公众号被动求职者开关修改失败\"}";
	public static final String WECHAT_UPDATE_ERR = "{\"errcode\":1802, \"errmsg\":\"微信公众号信息修改失败\"}";
	public static final String WECHAT_TOKEN_ERR = "{\"errcode\":1802, \"errmsg\":\"微信公众号随机生成密钥修改失败\"}";
	public static final String WECHAT_NAME_CAN_NOT_BE_NULL_ERR = "{\"errcode\":1802, \"errmsg\":\"微信公众号名称不能为空\"}";
	public static final String WECHAT_THEME_CAN_NOT_BE_NULL_ERR = "{\"errcode\":1802, \"errmsg\":\"微信公众号页面主题不能为空\"}";
	public static final String WECHAT_ID_CAN_NOT_BE_NULL_ERR = "{\"errcode\":1802, \"errmsg\":\"微信公众号页面主题不能为空\"}";
	public static final String WECHAT_FIND_ERR = "{\"errcode\":1802, \"errmsg\":\"微信公众号信息查找失败为空\"}";
	public static final String WECHAT_ADD_ERR = "{\"errcode\":1802, \"errmsg\":\"微信公众号信息添加失败\"}";

	// 挖掘被动求职者
	public static final String PASSIVESEEKER_NO_PERMISSION = "{\"errcode\":2101, \"errmsg\":\"没有操作权限，请先开启挖掘被动求职者！\"}";
	public static final String PASSIVESEEKER_SORT_NORECORD = "{\"errcode\":2102, \"errmsg\":\"未能找到用户的推荐信息，无法获取排名！\"}";
	public static final String PASSIVESEEKER_SORT_NOCOMPANY = "{\"errcode\":2103, \"errmsg\":\"，无法获取公司其他成员信息，无法获取排名！\"}";
	public static final String PASSIVESEEKER_SORT_NOCOLLEAGUA = "{\"errcode\":2104, \"errmsg\":\"，无法获取公司其他成员信息，无法获取排名！\"}";
	public static final String PASSIVESEEKER_RECOMPID_PARAMILLEGAL = "{\"errcode\":2105, \"errmsg\":\"是否推荐参数不合法！\"}";
	public static final String PASSIVESEEKER_CANDIDATES_POSITIONNOTEXIST = "{\"errcode\":2106, \"errmsg\":\"没有合适的职位信息！\"}";
	public static final String PASSIVESEEKER_CANDIDATES_RECORDNOTEXIST = "{\"errcode\":2107, \"errmsg\":\"没有推荐记录！\"}";
	public static final String PASSIVESEEKER_APPLYPOSITION_ALREADYAPPLY = "{\"errcode\":2108, \"errmsg\":\"重复申请职位！\"}";
	public static final String PASSIVESEEKER_ALREADYAPPLIEDORRECOMMEND = "{\"errcode\":2108, \"errmsg\":\"已经申请或者被推荐，无法忽略！\"}";

	// mp迁移
	public static final String MP_MIGRATE_TOPIC_DEFAULTTOPICNOTEXIST = "{\"errcode\":2151, \"errmsg\":\"系统没有默认专题！\"}";
	public static final String MP_MIGRATE_TOPIC_COVER_NOTEXIST = "{\"errcode\":2152, \"errmsg\":\"未找到专题封面信息！\"}";
	public static final String MP_MIGRATE_TOPIC_ADD_FAILED = "{\"errcode\":2153, \"errmsg\":\"添加专题失败\"}";
	public static final String MP_MIGRATE_TOPIC_NOTEXIST = "{\"errcode\":2154, \"errmsg\":\"未找到专题信息！\"}";
	public static final String MP_MIGRATE_TOPIC_NOT_PERMITTED = "{\"errcode\":2159, \"errmsg\":\"没有权限查看该专题！\"}";
	public static final String MP_MIGRATE_EMPLOYEE_ALREADYUNBINDING = "{\"errcode\":2156, \"errmsg\":\"该用户未被绑定或者已经解绑！\"}";
	public static final String MP_MIGRATE_RCRUITMENT_ILLEGALRECRUITSTATUS = "{\"errcode\":2157, \"errmsg\":\"所选择的申请不合法或者招聘进度状态不一致！\"}";
	public static final String MP_MIGRATE_RCRUITMENT_ILLEGALRECRUITFLOW = "{\"errcode\":2158, \"errmsg\":\"招聘进度流程异常！\"}";
	
	public static final String ACCOUNT_NOTEXISTS = "{\"errcode\":2501, \"errmsg\":\"用户不存在！\"}";
	
	public static final String WARNING_MP_MIGRATE_TOPIC_POSITIONNOTEXIST = "{\"errcode\":1, \"errmsg\":\"职位信息有误！\"}";
	public static final String WARNING_MP_MIGRATE_TOPIC_POSITIONOUTOFRANGE = "{\"errcode\":1, \"errmsg\":\"职位数超过允许的数量，超过的部分将不保存！\"}";
	public static final String WARNING_MP_MIGRATE_TOPIC_ALLREADYDISABLED = "{\"errcode\":1, \"errmsg\":\"专题已经被置为不可用！\"}";
	
	//招聘进度
	public static final String RECRUIT_STATUS_CAN_NOT_BE_NULL = "{\"errcode\":2401, \"errmsg\":\"招聘状态不能为空！\"}";
	
	//超级账号
	public static final String SUPERACCOUNT_MIGRATE_OVER_LIMIT = "{\"errcode\":2351, \"errmsg\":\"一次迁移选择的子账号数量超出了允许范围！\"}";
	
	// 微信回复
	public static final String WELCOME_RULE_EMPTY = "{\"errcode\":0, \"errmsg\":\"系统没有欢迎回复！\"}";
	public static final String DEFAULT_RULE_EMPTY = "{\"errcode\":0, \"errmsg\":\"系统没有默认回复！\"}";
	
	// 校验规则
	public static final String VALIDATE_ERROR = "{\"errcode\":2201, \"errmsg\":\"{MESSAGE}\"}";
	public static final String PARAM_ERR = "{\"errcode\":2002, \"errmsg\":\"无法根据参数查找到数据\"}";
	public static final String PROGRAM_ERR = "{\"errcode\":2001, \"errmsg\":\"程序异常，操作失败\"}";
	public static final String PROGRAM_PARAM_NOTEXIST = "{\"errcode\":2003, \"errmsg\":\"数据不正确\"}";
	public static final String PROGRAM_PARAM_ALREADYEXIST = "{\"errcode\":2004, \"errmsg\":\"重复的数据！\"}";
	public static final String PROGRAM_UPPERLIMIT = "{\"errcode\":2005, \"errmsg\":\"当日获取短信次数已用完！\"}";
	public static final String GET_PARAMS_ERROR = "{\"errcode\":2006, \"errmsg\":\"未获取到参数！\"}";
	
	public static final String LETTERS_RECRUITMENT_APPLY = "被推荐人投递简历";
	public static final String LETTERS_RECRUITMENT_CVCHECKED = "简历被查看/简历被下载";
	public static final String LETTERS_RECRUITMENT_CVPASSED = "简历初筛合格";
	public static final String LETTERS_RECRUITMENT_OFFERED = "面试通过";
	public static final String LETTERS_RECRUITMENT_HIRED = "入职";
	public static final String LETTERS_RECRUITMENT_APPLYREJECT = "提交简历后被拒绝";
	public static final String LETTERS_RECRUITMENT_CVCHECKEDREJECT = "提交简历后被拒绝";
	public static final String LETTERS_RECRUITMENT_CVPASSEDREJECT = "面试邀请后被拒绝";
	public static final String LETTERS_RECRUITMENT_OFFEREDREJECT = "入职失败";
	public static final String LETTERS_RECRUITMENT_BATCHREJECT = "批量职位不合适";
	public static final String LETTERS_RECRUITMENT_CANCELILLEGAL = "取消不合适";
	public static final String LETTERS_RECRUITMENT_REOFFERED = "重新面试";
	public static final String LETTERS_RECRUITMENT_RECVPASSED = "重新初筛";
	public static final String LETTERS_RECRUITMENT_RECVCHECKED = "重新浏览";

	// Success flags.
	public static final String SUCCESS = "{\"errcode\":0, \"errmsg\":\"成功!\"}";
	public static final String COMPANY_ADD_SUCC = "{\"errcode\":0, \"errmsg\":\"公司信息添加成功\", \"company_created\":1}";
	public static final String COMPANY_UPDATE_SUCC = "{\"errcode\":0, \"errmsg\":\"公司信息修改成功!\",\"company_created\":1}";
	public static final String APPLICATION_VIEW_STATUS_UPDATE_SUCC = "{\"errcode\":0, \"errmsg\":\"申请状态更新成功\"}";
	public static final String APPLICATION_NOT_SUITABLE_UPDATE_SUCC = "{\"errcode\":0, \"errmsg\":\"申请是否合适状态更新成功\"}";
	public static final String AUTH_SECCESS = "{\"errcode\":0, \"errmsg\":\"权限校验成功\"}";
	public static final String HR_READ_RESUME_RECORD_ADD_SUCC = "{\"errcode\":0, \"errmsg\":\"权限校验成功\"}";
	public static final String POSITION_TOP_SUCC = "{\"errcode\":0, \"errmsg\":\"该次职位置顶成功\"}";
	public static final String POSITION_REVOKE_SUCC = "{\"errcode\":0, \"errmsg\":\"职位撤销成功\"}";
	public static final String POSITION_UPDATE_SHARETEMPLATE_SUCC = "{\"errcode\":0, \"errmsg\":\"修改职位分享模板成功\"}";
	public static final String POSITION_NOT_SUITABLE_SUCC = "{\"errcode\":0, \"errmsg\":\"申请不合适更新成功\"}";
	public static final String POSITION_SHARETPL_STATUS_UPDATE_SUCC = "{\"errcode\":0, \"errmsg\":\"职位模板更新成功\"}";
	public static final String POSITION_SET_SHARETEMPLATE_STATUS_ERR = "{\"errcode\":0, \"errmsg\":\"职位模板状态更新成功\"}";
	public static final String COMPANYREPORT_ADD_SUCC = "{\"errcode\":0, \"errmsg\":\"行业报告添加成功\"}";
	public static final String COMPANYREPORT_DOWNLOAD_SUCC = "{\"errcode\":0, \"errmsg\":\"行业报告下载成功\"}";
	public static final String EMPLOYEE_PASSWORD_RESEAT = "{\"errcode\":0, \"errmsg\":\"密码修改成功\"}";
	public static final String WECHAT_PASSIVE_SEEKER_UPDATE_SUCC = "{\"errcode\":0, \"errmsg\":\"被动求职者开关修改成功\"}";
	public static final String WECHAT_UPDATE_SUCC = "{\"errcode\":0, \"errmsg\":\"微信信息修改成功\"}";
	public static final String WECHAT_ADD_SUCC = "{\"errcode\":0, \"errmsg\":\"微信信息添加成功\"}";
	public static final String PASSIVESEEKER_RECOMMEND_SUCCESS = "{\"errcode\":0, \"errmsg\":\"推荐信息填写成功!\"}";
	public static final String PASSIVESEEKER_APPLYPOSITION_SUCCESS = "{\"errcode\":0, \"errmsg\":\"候选人申请职位成功!\"}";
	public static final String WECHAT_UNAUTHORIZED_SUCCESS = "{\"errcode\":0, \"errmsg\":\"成功解除绑定\"}";
	public static final String PASSIVESEEKER_RECOMMMEND_SUCCESS = "{\"errcode\":0, \"errmsg\":\"候选人选中成功!\"}";
	public static final String MP_MIGRATE_TOPIC_GETCOVER_SUCCESS = "{\"errcode\":0, \"errmsg\":\"成功获取专题封面信息!\"}";
	public static final String MP_MIGRATE_TOPIC_ADD_SUCCESS = "{\"errcode\":0, \"errmsg\":\"成功添加专题信息!\"}";
	public static final String MP_MIGRATE_TOPIC_GETTOPIC_SUCCESS = "{\"errcode\":0, \"errmsg\":\"成功!\"}";
	public static final String MP_MIGRATE_TOPIC_DELETE_SUCCESS = "{\"errcode\":0, \"errmsg\":\"成功!\"}";
	public static final String MP_MIGRATE_TOPIC_UPDATE_SUCCESS = "{\"errcode\":0, \"errmsg\":\"成功!\"}";
	public static final String MP_MIGRATE_SUCCESS = "{\"errcode\":0, \"errmsg\":\"成功!\"}";
	public static final String RECRUIT_SUCCESS = "{\"errcode\":0, \"errmsg\":\"成功!\"}";

	/*********************************************************************/
	public static final String LOG_TOPIC_DEFAULTTOPICNOTEXIST = "Not provide default topics!";

	/*********************************************************************/

	public static final String V2_PROGRAM_ERR_MESSAGE = "程序异常，操作失败";
	public static final int V2_PROGRAM_ERR_CODE = 2001;

	// error message
	public static final String DASVALIDATE_SENSITIVEWORDS_ILLEGAL = "包含敏感词!";
	
	
	/**  员工认证红包	 */
	public static final int HB_EMPLOYEE_TYPE = 0;
	/**  推荐评价红包	 */
	public static final int HB_RECOMMEND_TYPE = 1;
	/**  转发被点击红包	 */
	public static final int HB_CLICK_TYPE = 2;
	/**  转发被申请红包	 */
	public static final int HB_APPLICATION_TYPE = 3;
	/**	 发送红包公共号  */
	public static final int HB_WECHAT_ID = 16;
	/**	 红包活动错误信息  */
	public static final String HB_CONFIG_START = "{\"errcode\":2222, \"errmsg\":\"已开始红包活动不可以预埋红包！\"}";
	
	/**  主公司	 */
	public static final int COMPANY_PARENT = 0;
	/**  子公司	 */
	public static final int COMPANY_SUB = 1;

	// CONST TYPE DEFINE
	public static final int CONST_DEGREE = 3104;
}
