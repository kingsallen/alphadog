package com.moseeker.common.constants;

public final class ConstantErrorCodeMessage {

    public static final String SUCCESS = "{'status':0, 'message':'success'}";

    // 系统共通ERRCODE说明定义 9字头
    public static final String PROGRAM_EXHAUSTED = "{'status':-1,'message':'系统繁忙，请稍候再试!'}";
    public static final String PROGRAM_EXCEPTION = "{'status':99999 ,'message':'发生异常，请稍候再试!'}";
    public static final int PROGRAM_EXCEPTION_STATUS = 99999;

    public static final String PROGRAM_DATA_EMPTY = "{'status':90010,'message':'请求数据为空！'}";
    public static final String PROGRAM_VALIDATE_REQUIRED = "{'status':90014,'message':'{0}是必填项！'}";
    public static final String PROGRAM_POST_FAILED = "{'status':90011,'message':'添加失败！'}";
    public static final String PROGRAM_PUT_FAILED = "{'status':90012,'message':'保存失败!'}";
    public static final String PROGRAM_DEL_FAILED = "{'status':90013,'message':'删除失败!'}";
    public static final String VALIDATE_FAILED = "{'status':90014, 'message':'{MESSAGE}'}";
    public static final String PROGRAM_PARAM_NOTEXIST = "{'status':90015,'message':'参数不正确!'}";
    public static final String PROGRAM_CONFIG_INCOMPLETE = "{'status':90016,'message':'配置信息丢失!'}";
    public static final String LOAD_CONFIG_ERROR = "{'status':91001,'message':'加载配置信息错误!'}";

    // 用户服务ERRCODE说明定义 1字头
    public static final String LOGIN_ACCOUNT_ILLEAGUE = "{'status':10010,'message':'用户名密码不匹配!'}";
    public static final String INVALID_SMS_CODE = "{'status':10011,'message':'无效验证码！'}";
    public static final String LOGIN_PASSWORD_UNLEGAL = "{'status':10012,'message':'修改密码失败：原密码错误!'}";
    public static final String LOGIN_UPDATE_PASSWORD_FAILED = "{'status':10013,'message':'修改密码失败！'}";
    public static final String LOGIN_MOBILE_NOTEXIST = "{'status':10014,'message':'手机号不存在！'}";
    public static final String USERACCOUNT_BIND_NONEED = "{'status':10015,'message':'手机号已经注册'}";

    public static final String USERACCOUNT_EXIST = "{'status':10016,'message':'帐号已存在!'}";
    public static final String USERACCOUNT_NOTEXIST = "{'status':10017,'message':'帐号不存在!'}";
    public static final String USER_FAV_POSITION_FAILED = "{'status':10018,'message':'获取我感兴趣失败!'}";
    public static final String HRCOMPANY_NOTEXIST = "{'status':10020,'message':'公司不存在!'}";
    public static final String USERACCOUNT_BIND_REPEATBIND = "{'status':10021,'message':'手机号码已经绑定！'}";
    public static final String USERACCOUNT_PASSWORD_REPEATPASSWORD = "{'status':10022,'message':'新密码和旧密码不能一致！'}";
    public static final String WEIXIN_HASBEEN_BIND = "{'status':10023,'message':'该微信已经被绑定！'}";
    public static final String MOBILE_IS_INVALID = "{'status':10024,'message':'该手机号码未绑定任何微信账号！'}";
    public static final String WEXIN_IS_SAME = "{'status':10025,'message':'该手机号码绑定的微信和之前微信一样，不需要重新绑定！'}";
    public static final String WEXIN_IS_INVALID = "{'status':10026,'message':'unionid 有误'}";
    public static final String MOBILE_WECHAT_IS_INVALID = "{'status':10027,'message':'该手机号码或微信账号已经注册'}";
    public static final String HRCOMPANY_CONF_NOTEXIST = "{'status':10028,'message':'公司配置不存在!'}";
    public static final String MALL_STATE_UNSUPPORTED = "{'status':10029,'message':'不支持的商城操作状态!'}";
    public static final String MALL_GOODS_UNEXISTS = "{'status':10030,'message':'商品不存在!'}";
    public static final String MALL_GOODS_NEED_DOWNSHELF = "{'status':10031,'message':'必须先下架商品才能编辑!'}";
    public static final String MALL_LIMIT_SUPER_ACCOUNT = "{'status':10032,'message':'非主账号权限不能操作!'}";
    public static final String MALL_VISIT_LIMIT = "{'status':10033,'message':'未认证员工不能进入积分商城!'}";
    public static final String MALL_ORDER_OPERATION_LIMIT = "{'status':10034,'message':'非本公司订单不能操作!'}";
    public static final String MALL_ORDER_TYPE_UNEXISTS = "{'status':10035,'message':'不存在的订单类型!'}";
    public static final String USER_NOTEXIST = "{'status':10036,'message':'员工信息不存在!'}";
    public static final String MALL_STOCK_LIMIT = "{'status':10037,'message':'库存范围是1~99999，不能超出此范围!'}";
    public static final String MALL_STOCK_LACK = "{'status':10038,'message':'库存不足!'}";
    public static final String MALL_AWARD_LACK = "{'status':10039,'message':'当前积分不足!'}";
    public static final String MALL_ORDER_REPEATED_COMMIT = "{'status':10040,'message':'订单处理中，请勿重复提交!'}";
    public static final String MALL_ORDER_UNSUPPORTED_STATE = "{'status':10041,'message':'不支持的订单处理状态!'}";
    public static final String MALL_TEMPLATE_SWITCH_CLOSE = "{'status':10042,'message':'商城积分变动消息模板已关闭!'}";
    public static final String MALL_GOODS_NEED_UPSHELF = "{'status':10043,'message':'商品已下架!'}";
    public static final String USEREMPLOYEE_NONEXIST = "{'status':10029,'message':'查不到该员工!'}";
    public static final String MALL_GOODS_DETAIL_TOO_LARGE = "{'status':10044,'message':'商品详情过长!'}";
    public static final String MALL_GOODS_RULE_TOO_LARGE = "{'status':10045,'message':'默认规则过长!'}";
    public static final String USER_USER_NOTEXIST = "{'status':10046,'message':'用户不存在!'}";

    public static final String HR_ACCOUNT_SIGNUP_VALIDATE_SOURCE = "{'status':12001,'message':'source >=1 && <=5!'}";

    // 职位服务ERRCODE说明定义 2字头

    // PROFILE服务ERRCODE说明定义 3字头
    public static final String PROFILE_REPEAT_DATA = "{'status':30010,'message':'重复数据!'}";
    public static final String PROFILE_DICT_CITY_NOTEXIST = "{'status':31011,'message':'城市字典不存在!'}";
    public static final String PROFILE_DICT_POSITION_NOTEXIST = "{'status':31012,'message':'职位职能字典不存在!'}";
    public static final String PROFILE_DICT_INDUSTRY_NOTEXIST = "{'status':31013,'message':'行业字典不存在!'}";
    public static final String PROFILE_USER_NOTEXIST = "{'status':31014,'message':'用户信息不正确!'}";
    public static final String PROFILE_ALLREADY_EXIST = "{'status':31015,'message':'个人profile已存在!'}";
    public static final String PROFILE_ILLEGAL = "{'status':31016,'message':'profile数据不正确!'}";
    public static final String PROFILE_POSITION_NOTEXIST = "{'status':31017,'message':'职位信息不正确!'}";
    public static final String PROFILE_DICT_NATIONALITY_NOTEXIST = "{'status':31018,'message':'国家字典不存在!'}";
    public static final String PROFILE_DATA_NULL = "{'status':31019,'message':'数据不存在!'}";
    public static final String PROFILE_DICT_COLLEGE_NOTEXIST = "{'status':31020,'message':'院校字典不存在!'}";
    public static final String PROFILE_DICT_MAJOR_NOTEXIST = "{'status':31021,'message':'专业字典不存在!'}";
    public static final String PROFILE_OUTPUT_FAILED = "{'status':31022,'message':'导出失败!'}";
    public static final String PROFILE_ALLREADY_NOT_EXIST = "{'status':31023,'message':'个人profile不存在!'}";
    public static final String PROFILE_MOVE_DATA_NOT_EXIST = "{'status':31024,'message':'简历搬家数据不存在!'}";
    public static final String PROFILE_MOVE_DATA_UPDATE_FAILED = "{'status':31025,'message':'简历搬家数据修改失败!'}";
    public static final String PROFILE_ORIGIN_NULL = "{'status':31026,'message':'简历来源不能为空!'}";
    public static final String PROFILE_MOVING = "{'status':31027,'message':'简历搬家正在进行中，请不要重复提交!'}";
    public static final String PROFILE_MOVING_MAIN_ACCOUNT = "{'status':31028,'message':'简历搬家只能主账号操作!'}";
    public static final String PROFILE_ORIGIN_UPSUPORT = "{'status':31029,'message':'简历搬家不支持的简历来源!'}";
    public static final String REFERRAL_DATA_OVERTIME = "{'status':31030,'message':'推荐信息已失效，请重新推荐!'}";

    public static final String PROFILE_CALL_RESUMESDK_RESULT_ERROR = "{'status':31024,'message':'调用ResumeSDK返回结果错误,log id:{}'}";

    // Company服务提示信息说明
    public static final String COMPANY_NAME_REPEAT = "{'status':33001,'message':'不允许和拥有超级帐号的公司的公司名称重名!'}";
    public static final String COMPANY_SCALE_ELLEGAL = "{'status':33002,'message':'公司规模不合法!'}";
    public static final String COMPANY_PROPERTIY_ELLEGAL = "{'status':33002,'message':'公司属性不合法!'}";

    public static final String CRAWLER_USER_NOPERMITION = "{'status':32001,'message':'账号密码错误!'}";
    public static final String CRAWLER_IMPORT_FAILED = "{'status':32002,'message':'导入失败!'}";
    public static final String CRAWLER_LOGIN_FAILED = "{'status':32003,'message':'简历导入失败，请重新尝试!'}";
    public static final String CRAWLER_PARAM_ILLEGAL = "{'status':32004,'message':'参数不正确!'}";
    public static final String CRAWLER_LOGIN2_FAILED = "{'status':32005,'message':'简历导入失败，请重新尝试!'}";
    public static final String CRAWLER_SERVICE_TIMEOUT = "{'status':32006,'message':'服务超时!'}";
    public static final String CRAWLER_SERVICE_PARAM_ERROR = "{'status':32007,'message':'服务超时!'}";
    public static final String CRAWLER_SERVICE_IMPORT_UPPER_LIMIT = "{'status':32008,'message':'导入次数过多!'}";
    public static final String CRAWLER_RESUME_EMPTY = "{'status':32009,'message':'简历导入失败，请重新尝试!'}";
    public static final String CRAWLER_SERVICE_PROFILE_EMPTY = "{'status':32010,'message':'简历为空'}";
    public static final String CRAWLER_SERVICE_NEED_VERIFY_CODE = "{'status':32011,'message':'需要验证码！'}";
    public static final String CRAWLER_SERVICE_ACCOUNT_LIMIT = "{'status':32012,'message':'账号被限制！'}";
    public static final String CRAWLER_SERVICE_VERIFY_CODE_WRONG = "{'status':32013,'message':'验证码错误！'}";
    public static final String CRAWLER_SERVICE_TIME_OUT = "{'status':32014,'message':'授权超时，请重试'}";
    public static final String CRAWLER_SERVICE_MUCH_PASSWORD_WRONG = "{'status':32015,'message':'密码错误次数超限，请稍后重试或使用其他方式导入'}";
    //talentpool
    public static final String COMPANY_STATUS_NOT_AUTHORITY = "{'status':34001,'message':'免费公司没有权限'}";
    public static final String COMPANY_CONF_TALENTPOOL_NOT = "{'status':34002,'message':'该公司还没有开启智能人才库'}";
    public static final String HR_NOT_IN_COMPANY = "{'status':34003,'message':'该hr不属于该company_id'}";
    public static final String TALENT_POOL_ACCOUNT_STATUS = "{'status':34005,'message':'子账号没有操作权限'}";
    public static final String COMPANY_NOT_MU = "{'status':34006,'message':'company_id不为母公司编号'}";
    public static final String COMPANY_SUPER_REPEART_NAME = "{'status':33001, 'message':'和超级账号重名'}";
    public static final String TALENTPOOL_EMAIL_BALANCE = "{'status':34007, 'message':'邮件的额度不足'}";
    public static final String TALENTPOOL_EMAIL_NOUSEREMPLOYEE = "{'status':34008, 'message':'没有选中收件的员工'}";
    public static final String TALENTPOOL_EMAIL_NOCONFIGEMAIL = "{'status':34009, 'message':'该企业没有配置邮件'}";
    public static final String TALENTPOOL_EMAIL_NOUSERPROFILE = "{'status':34010, 'message':'没有符合条件的简历'}";
    public static final String EMAIL_SWITCH_FAILED = "{'status':34011,'message':'手动触发，开关不能关闭!'}";
    public static final String EMAIL_CONTEXT_FAILED = "{'status':34012,'message':'正文内容含有敏感词或超过1000个字!'}";
    public static final String EMAIL_INSCRIBE_FAILED = "{'status':34012,'message':'落款含有敏感词或超过50个字!'}";

    // 申请服务ERRCODE说明定义 4字头
    public static final String APPLICATION_VALIDATE_COUNT_CHECK = "{'status':41001,'message':'本月您已达到投递次数上限!'}";
    public static final String APPLICATION_POSITION_DUPLICATE = "{'status':41002,'message':'该职位已经申请过!'}";
    public static final String APPLICATION_ARCHIVE_FAILED = "{'status':41003,'message':'申请归档失败!'}";
    public static final String APPLICATION_POSITION_NOT_EXIST = "{'status':41004,'message':'申请的职位不存在!'}";
    public static final String APPLICATION_POSITION_STATUS_STOP = "{'status':41005,'message':'申请的职位已下线!'}";
    public static final String APPLICATION_USER_INVALID = "{'status':41006,'message':'申请人是无法校验的用户!'}";
    public static final String APPLICATION_SOURCE_NOTEXIST = "{'status':41007,'message':'申请来源不存在!'}";
    public static final String APPLICATION_VALIDATE_SCHOOL_COUNT_CHECK = "{'status':41008,'message':'本月您申请校招职位已达到投递次数上限!'}";
    public static final String APPLICATION_VALIDATE_SOCIAL_COUNT_CHECK = "{'status':41009,'message':'本月您申请社招已达到投递次数上限!'}";
    public static final String APPLICATION_NOTEXIST = "{'status':41010,'message':'申请不存在!'}";
    public static final String APPLICATION_STATE_ERROR = "{'status':41011,'message':'申请状态异常!'}";

    //HR帐号
    public static final String HRACCOUNT_ALREADY_BOUND_BYOTHER = "{'status':42001,'message':'该帐号已被其他人绑定！'}";
    public static final String HRACCOUNT_ELLEGLE_DATA = "{'status':42002,'message':'数据格式错误！'}";
    public static final String HRACCOUNT_BINDING = "{'status':42003,'message':'绑定中！'}";
    public static final String HRACCOUNT_BINDING_TIMEOUT = "{'status':42004,'message':'绑定超时！'}";
    public static final String HRACCOUNT_BINDING_LIMIT = "{'status':42005,'message':'无法绑定更多的账号了！'}";
    public static final String THIRD_PARTY_POSITION_UPSERT_FAILED = "{'status':42004,'message':'添加或者修改操作失败！'}";
    public static final String COMPANY_ID_ISNOTEXIST = "{'status':42005,'message':'公司不存在！'}";
    public static final String PERMISSION_DENIED = "{'status':42010, 'message':'员工ID和公司ID不匹配！'}";
    public static final String HRACCOUNT_ALREADY_BOUND = "{'status':42011, 'message':'已经绑定该帐号了！'}";
    public static final String HRACCOUNT_ALREADY_BOUNDIND = "{'status':42012, 'message':'该帐号已经在绑定中了！'}";
    public static final String BIND_STATUS_WRONG = "{'status':42013, 'message':'账号绑定状态错误！'}";
    public static final String DISPATCH_ACCOUNT_INVALID ="{'status': 42014,'message':'无效的帐号!'}";
    public static final String DISPATCH_ACCOUNT_BINDING ="{'status': 42015,'message':'帐号正在同步中,无法分配，请稍后再试！'}";
    public static final String DISPATCH_ACCOUNT_REFRESHING ="{'status': 42016,'message':'帐号正在刷新中，无法分配，请稍后再试'}";
    public static final String DISPATCH_ACCOUNT_INFOWRONG ="{'status': 42017,'message':'用户名密码错误,无法分配，请稍后再试！'}";
    public static final String DISPATCH_ACCOUNT_GETINGINFO ="{'status':42018,'message':'帐号正在同步第三方信息中,无法分配，请稍后再试!'}";
    public static final String DISPATCH_ACCOUNT_ERROR ="{'status':42019,'message':'帐号绑定程序发生错误,无法分配，请稍后再试!'}";
    public static final String SUBACCOUNT_REBIND_ERROR ="{'status':42020,'message':'子账号不能重新绑定第三方账号!'}";
    public static final String DEL_STATUS_ERROR ="{'status':42021,'message':'第三方账号状态错误，无法删除!'}";
    public static final String DEL_NO_AUTHORIZED ="{'status':42022,'message':'删除第三方账号失败，这个HR没有操作这个账号权限!'}";
    public static final String THIRD_PARTY_ACCOUNT_NOT_EXIST="{'status':43001,'message':'第三方账号不存在！'}";
    public static final String THIRD_PARTY_ACCOUNT_PWD_ERROR="{'status':43002,'message':'会员名、用户名或密码错误，请重新绑定账号！'}";
    public static final String HRACCOUNT_NOT_EXISTS="{'status':43003,'message':'沒有符合条件的hr账号！'}";
    public static final String HRACCOUNT_INFO_ERROR="{'status':43003,'message':'用戶信息错误！'}";
    public static final String NO_MATCH_AUTH_METHOD="{'status':43005,'message':'没有匹配的员工认证方式！'}";
    public static final String THIRD_PARTY_ACCOUNT_58_REQUEST_ERROR="{'status':43006,'message':'http请求58返回结果为空！'}";
    public static final String THIRD_PARTY_ACCOUNT_58_ADDRESS_FAILED="{'status':43007,'message':'获取工作地址失败！'}";
    public static final String THIRD_PARTY_ACCOUNT_58_TOKEN_EXPIRE="{'status':43008,'message':'用户信息过期，请解绑后重新绑定58账号！'}";

    //Position服务提示信息说明
    public static final String POSITION_NODELETE_BLANK = "{'status':51001,'message':'参数nodelete不能为空！'}";
    public static final String POSITION_DATA_BLANK = "{'status':51002,'message':'提交的数据为空！'}";
    public static final String POSITION_DATA_BATCH_DELETE_FAIL = "{'status':51003,'message':'批量职位数据删除失败！'}";
    public static final String POSITION_DATA_DELETE_FAIL = "{'status':51004,'message':'该职位不存在或者已经删除！'}";
    public static final String POSITION_DATA_DELETE_PARAM = "{'status':51005,'message':'删除职位参数有误,参数job_position_id必填或 company_id, jobnumber, source_id 必填'}";
    public static final String POSITION_DATA_DEPARTMENT_BLANK = "{'status':51006,'message':'部门不能为空'}";
    public static final String POSITION_DATA_DEPARTMENT_ERROR = "{'status':51007,'message':'该部门在该公司不存在，请联系管理员更新部门信息'}";
    public static final String POSITION_DATA_COMPANY_DATA_BLANK = "{'status':51008,'message':'该公司需要删除的数据为空'}";
    public static final String POSITION_COMPANY_DEPARTMENT_BLANK = "{'status':51009,'message':'该公司为设置任何部门信息，请联系管理员更新部门信息'}";
    public static final String POSTION_COMPANY_DEPARTMENTI_PARAMETER_BLANK = "{'status':51010,'message':'未设置部门名'}";
    public static final String POSITION_JOBPOSITION_REQUIREMENT_BLANK = "{'status':51011,'message':'职位要求不能为空'}";
    public static final String POSITION_JOBPOSITION_COMPANY_ID_BLANK = "{'status':51012,'message':'未找到该公司'}";
    public static final String POSITION_DATA_OCCUPATION_ERROR = "{'status':51013,'message':'该职位职能信息设置错误,未设置的职能为:{MESSAGE}'}";
    public static final String POSITION_DATA_CUSTOM_ERROR = "{'status':51014,'message':'该职位自定义字段设置错误,未设置的职位自定义字段为:{MESSAGE}'}";
    public static final String POSITION_DATA_ALLCITY_ERROR = "{'status':51015,'message':'全国职位暂不同步到支付宝'}";
    public static final String POSITION_DATA_NOCITYCODE_ERROR = "{'status':51016,'message':'无city code的职位暂不同步到支付宝'}";
    public static final String CITY_TOO_LONG = "{'status':51017,'message':'城市信息太长，无法入库'}";
    public static final String POSITION_UPDATE_FAIL = "{'status':51019,'message':'更新职位信息失败，这个HR没有操作这个职位权限'}";
    public static final String POSITION_ALREADY_EXIST = "{'status':51020,'message':'该职位已存在!'}";
    public static final String POSITION_ALREADY_ACTIVE = "{'status':51021,'message':'该职位已上线!'}";
    public static final String POSITION_ALREADY_BANNED = "{'status':51022,'message':'该职位已撤下!'}";
    public static final String POSITION_PUBLISHER_NOT_EXIST = "{'status':51023,'message':'发布者不存在'}";
    public static final String SOURCE_ERROR = "{'status':51024,'message':'来源错误！'}";
    public static final String POSITION_ALREADY_SYNC = "{'status':51025,'message':'职位已同步至第三方，请勿重复同步'}";
    public static final String REQUEST_SCRAPER_CONNECTION_ERROR="{'status':52001,'message':'访问scraper连接错误！'}";
    public static final String REQUEST_SCRAPER_URL_NOT_EXIST="{'status':52002,'message':'scraper服务URL不存在！'}";
    public static final String FEATURE_MUST_EXISTS="{'status':52003,'message':'福利特色不存在！'}";
    public static final String POSITION_CITY_MAPPING_NONEXIST="{'status':52004,'message':'城市字典不存在，无法发布该城市！'}";
    public static final int NO_BIND_THIRD_PARTY_ACCOUNT_STATUS = 51018;

    public static final String POSITION_TITLE_NOT_EMPTY="{'status':52003,'message':'职位标题不能为空!'}";
    public static final String POSITION_ACCOUNTABILITIES_NOT_EMPTY="{'status':52004,'message':'职位描述不能为空!'}";
    public static final String POSITION_REQUIREMENT_NOT_EMPTY="{'status':52005,'message':'任职条件不能为空!'}";
    public static final String POSITION_CITY_NOT_EMPTY="{'status':52006,'message':'工作地点不能为空!'}";
    public static final String POSITION_OCCUPATION_INVALID="{'status':52007,'message':'传入职能中没有有效的职能code!'}";
    public static final String POSITION_PUBLISHCITY_INVALID="{'status':52008,'message':'该职位查不到发布地区!'}";
    public static final String POSITION_CITYCODE_INVALID="{'status':52009,'message':'错误的仟寻citycode，查不到该code的所有城市level!'}";
    public static final String POSITION_REFERRAL_UPDATE_INVALID="{'status':52010,'message':'更新数量超过上线500条'}";
    public static final String POSITION_APPLY_FAILED="{'status':52011,'message':'非本公司职位不能投递'}";

    //职位同步
    public static final String POSITION_SYNC_VERIFY_TIMEOUT = "{'status':61001,'message':'验证已超时，职位同步失败'}";
    public static final String POSITION_SYNC_ALREADY_VERIFY = "{'status':61002,'message':'该同步职位已经在验证了'}";
    public static final String POSITION_SYNC_WRONG_CHANNEL = "{'status':61003,'message':'验证处理--渠道错误'}";
    public static final String POSITION_SYNC_NO_POSITION = "{'status':61004,'message':'验证处理--职位不存在'}";
    public static final String POSITION_SYNC_NO_THIRD_POSITION = "{'status':61005,'message':'验证处理--第三方职位不存在'}";
    public static final String POSITION_SYNC_EMPTY_MOBILE = "{'status':61006,'message':'验证处理--手机号为空'}";
    public static final String POSITION_SYNC_SEND_MOBILE_TEMPLATE_ERROR = "{'status':61007,'message':'发送手机验证码模板消息失败'}";
    public static final String POSITION_SYNC_EMPTY_MOBILE_CODE = "{'status':61008,'message':'验证处理--验证码为空'}";
    public static final String POSITION_SYNC_NOT_FIND_THIRD_PARTY_POSITION = "{'status':61009,'message':'找不到第三方职位同步信息'}";
    public static final String POSITION_SYNC_INFO_SENDED = "{'status':61010,'message':'验证信息发送成功'}";
    public static final String WRONG_SYNC_CHANNEL = "{'status':61011,'message':'第三方渠道不存在！'}";
    public static final String SUB_COMPANY_CANT_CONF_CHANNEL = "{'status':61012,'message':'不能给子公司配置同步渠道！'}";
    public static final String ONLY_PAY_COMPANY_CAN_CONF_CHANNEL = "{'status':61015,'message':'只能给付费公司配置同步渠道！'}";

    public static final String NO_AUTH_IMPORT_VERYEAST_PROFILE = "{'status':61016,'message':'该公司没有导入最佳东方简历的权限！'}";
    public static final String NO_SYNC_QX_POSITION = "{'status':61017,'message':'查询不到要同步的职位！'}";
    public static final String NO_SYNC_THIRD_PARTY_ACCOUNT = "{'status':61018,'message':'查询不到可发布的第三方账号！'}";
    public static final String LIEPIN_REQUEST_RESPONSE_NULL = "{'status':61019,'message':'猎聘同步职位暂无响应，请稍后再试！'}";
    public static final String LIEPIN_REQUEST_LIMIT = "{'status':61020,'message':'今日职位同步次数已达上限！'}";
    public static final String POSITION_SALARY_NULL = "{'status':61021,'message':'职位薪资不能为空！'}";
    public static final String POSITION_JOB58_TOKEN_EXPIRE = "{'status':61022,'message':'用户58账号信息过期，需解绑后重新绑定！'}";


    //mq服务提示信息说明
    public static final String MQ_TEMPLATE_NOTICE_CLOSE = "{'status':71001,'message':'模板消息开关关闭！'}";
    public static final String MQ_WECHAT_NOT_INFO = "{'status':71001,'message':'公众号信息不存在！'}";

    //mq服务提示信息说明
    public static final String CONSISTENCY_TOOL = "{'status':81001,'message':'重复的参数转换器！'}";


    //useraccount服务提示信息说明
    public static final String MCD_USER_EMAIL_AND_CUSTOM_FIELD = "{'status':111001,'message':'邮箱或者用工号已经注册过！'}";
    public static final String MCD_USER_BIND_DONE = "{'status':111002,'message':'用户已经认证过！'}";

    public static final String MCD_IS_EXIT = "{'status':111003,'message':'员工已经绑定过！'}";


    //IM服务
    public static final String CHAT_ROOM_NOT_EXIST = "{'status':91001,'message':'聊天室不存在！'}";
    public static final String CANT_SET_LEAVE_TO_MOBOT = "{'status':91002,'message':'尚未采购该功能，详情请联系您的客户成功顾问。'}";
    public static final String NO_AUTH_SET_LEAVE_TO_MOBOT = "{'status':91002,'message':'子账号没有权限设置该功能。'}";

    public static final String FILE_TOO_BIG = "{'status':91004,'message':'文件长度过大。'}";

    //APPBS错误代码61000

    //searchengine 100001

    // 工具类错误
    public static final String USER_SMS_LIMITED = "{'status':80001,'message':'短信发送异常!'}";
    public static final String HTTP_REQUEST_FAILED = "{'status':80002,'message':'http请求失败，未获取到返回结果!'}";

    public static final String DB_UPDATE_FAILED = "{'status':-1,'message':'数据库更新失败!'}";
}
