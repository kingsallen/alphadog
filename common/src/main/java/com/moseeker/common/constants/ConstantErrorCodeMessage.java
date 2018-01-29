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

    public static final String COMPANY_SUPER_REPEART_NAME = "{'status':33001, 'message':'和超级账号重名'}";

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
    public static final int NO_BIND_THIRD_PARTY_ACCOUNT_STATUS = 51018;


    //APPBS错误代码61000


    // 工具类错误
    public static final String USER_SMS_LIMITED = "{'status':80001,'message':'短信发送异常!'}";
}
