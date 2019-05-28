# file: useraccounts.struct

namespace java com.moseeker.thrift.gen.useraccounts.struct
include "../../dao/struct/userdb/user_employee_struct.thrift"
include "../../dao/struct/userdb/user_hr_account_struct.thrift"

typedef string Timestamp

struct UserHRAccountAddAccountForm {
    1: optional i32 id,
    2: optional user_hr_account_struct.UserHrAccountDO hrAccount 
}

struct Userloginreq {
    1: optional string unionid,
    2: optional string mobile,
    3: optional string password,
    4: optional string code,
    5: optional string countryCode="86"
}

struct Usersetting {
    1: optional i32 id,
    2: optional i32 user_id,
    3: optional string banner_url,
    4: optional i32 privacy_policy
}

/*
  用户实体
*/
struct User {
     1: optional i64        id              ,    // 主key
     2: optional string     username        ,    // 用户名，比如手机号、邮箱等
     3: optional string     password        ,    // 密码
     4: optional i8       is_disable      ,    // 是否禁用，0：可用，1：禁用
     5: optional i64        rank            ,    // 用户等级
     6: optional Timestamp  register_time   ,    // 注册时间
     7: optional string     register_ip     ,    // 注册IP
     8: optional Timestamp  last_login_time ,    // 最近登录时间
     9: optional string     last_login_ip   ,    // 最近登录IP
    10: optional i64        login_count     ,    // 登录次数
    11: optional i64        mobile          ,    // user pass mobile registe
    12: optional string     email           ,    // user pass email registe
    13: optional i8       activation      ,    // is not activation 0:no 1:yes
    14: optional string     activation_code ,    // activation code
    15: optional string     token           ,    // 用户校验token
    16: optional string     name            ,    // 姓名或微信昵称
    17: optional string     headimg         ,    // 头像
    18: optional i64        country_id      ,    // 国家字典表ID, dict_country.id
    19: optional i64        wechat_id       ,    // 注册用户来自于哪个公众号, 0:默认为来自浏览器的用户
    20: optional string     unionid         ,    // 存储仟寻服务号的unionid
    21: optional i8       source          ,    // 来源：0:手机注册, 1:聚合号一键登录, 2:企业号一键登录, 7:PC(正常添加), 8:PC(我要投递), 9: PC(我感兴趣)
    22: optional string     company         ,    // 点击我感兴趣时填写的公司
    23: optional string     position        ,    // 点击我感兴趣时填写的职位
    24: optional i64        parentid             // 合并到了新用户的id
    25: optional i32        email_verified  ,    // 邮箱是否认证
    26: optional string     nickname,       	 // 用户昵称
    27: optional string     countryCode
}


/*
  我感兴趣/职位收藏关系表
*/
struct UserFavoritePosition {
    1: optional i64       id              ,       // ID
    2: optional i32       sysuser_id      ,       // 用户ID
    3: optional i32       position_id     ,       // 职位ID
    4: optional i8      favorite        ,       // 0:收藏, 1:取消收藏, 2:感兴趣
    5: optional string    mobile          ,       // 感兴趣的手机号
    6: optional i64       wxuser_id       ,       // wx_user.id
    7: optional i32       recom_id        ,       // 推荐者 fk:wx_user.id
    8: optional Timestamp create_time     ,       //
    9: optional Timestamp update_time             //
}

/*
HR用户实体
*/
struct UserHrAccount{
     1: optional i64               id              , //
     2: optional i64               company_id      , // company.id
     3: optional string            mobile          , // 手机号码
     4: optional string            email           , // 邮箱
     5: optional i64               wxuser_id       , // 绑定的微信账号
     6: optional string            password        , // 登录密码
     7: optional string            username        , // 企业联系人
     8: optional i8              account_type    , // 0 超级账号；1：子账号; 2：普通账号
     9: optional i8              activation      , // 账号是否激活，1：激活；0：未激活
    10: optional i8              disable         , // 1：可用账号；0禁用账号 ） 遵循数据库整体的设计习惯，1表示可用，0表示不可用
    11: optional Timestamp         register_time   , // 注册时间
    12: optional string            register_ip     , // 注册时的IP地址
    13: optional Timestamp         last_login_time , // 最后的登录时间
    14: optional string            last_login_ip   , // 最后一次登录的IP
    15: optional i32               login_count     , // 登录次数
    16: optional i32               source          , // 来源1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号)
    17: optional string            download_token  , // 下载行业报告校验码
    18: optional Timestamp         create_time     , // 创建时间
    19: optional Timestamp         update_time     , // 修改时间
    20: optional string            headimgurl      , // 头像url
    21: optional i8                leave_to_mobot    // IM聊天开关，0：不开启，1：开启，2：开启+chatbot
}

struct DownloadReport {
   1: string code,									//验证码
   2: string company_name,							//公司名称
   3: string mobile,								//手机号码
   4: string name,							//联系人
   5: optional i32 source,							//来源
   6: optional string register_ip,					//注册IP
   7: optional string last_login_ip,					//最后登录IP
   8: optional string countryCode="86"
}

/*
* 帐号绑定
*/
struct BindAccountStruct {
    1: string username,
    2: string password,
    3: optional string member_name,
    4: i8 channel,
    5: i32 appid,
    6: i32 user_id,
    7: i32 company_id,
    8: i32 remainNum,
    9: i32 binding,
    10: i32 remainProfileNum,
}

/*
* hr常用筛选条件
*/
struct SearchCondition {
	1: i32 id,
	2: string name, // 名称
	3: string publisher, // 发布人
	4: string position_id, // 职位id
	5: string keyword, // 关键字
	6: string submit_time, // 发布时间
	7: string work_years, // 工作年限
	8: string city_name, // 城市
	9: string degree, // 学历
   10: string past_position, // 曾任职位
   11: i32 in_last_job_search_position, // 是否只在最近一份工作中搜索曾任职务(0:否，1:是)
   12: i32 min_age, // 最小年龄
   13: i32 max_age, // 最大年龄
   14: string intention_city_name, // 期望城市
   15: i32 sex, // 性别
   16: string intention_salary_code, // 期望薪资
   17: string company_name, // 城市名称
   18: i32 in_last_job_search_company, // 是否只在最近一份工作中搜索公司名称（0:否，1:是）
   19: i32 hr_account_id, // 创建人id(user_hr_account.id)
   20: i32 update_time, // 简历更新时间选项（0：不限，1：最近一周，2：最近两周，3：最近一个月）
   21: i32 type,// 类型（0：候选人列表筛选条件，1：人才库列表筛选条件）
   22: string candidate_source,//招聘类型（0，社招，1，校招，2定向招聘）
   23: i32 is_public,//是否公开  1 公开
   24: string origins,//简历来源，
   25: i32 is_recommend,//是否内推  1是内推
   26: string tag_id,//标签id
   27: string favorite_hrs,//收藏人id
   28: string city_code,//现居住地城市code
   29: string intention_city_code,//期望城市
   30: i32 position_status,//职位状态
   31: string position_key_word,//职位关键字
   32: string past_position_key_word,//曾任职位的关键字
   33: string past_company_key_word,// 曾任公司的关键字
   34: string start_submit_time,//开始时间
   35: string end_submit_time,//结束时间
   36: string has_attachment,
   37: string department_ids,
   38: string department_names

}

struct UserEmployeeStruct {
	1: optional i32 id,
	2: optional string employeeid,
	3: optional i32 company_id,
	4: optional i32 role_id,
	5: optional i32 wxuser_id,
	6: optional i8 sex,
	7: optional string ename,
	8: optional string efname,
	9: optional string cname,
	10: optional string cfname,
	11: optional string password,
	12: optional i8 is_admin,
	13: optional i32 status,
	14: optional string companybody,
	15: optional string groupname,
	16: optional string position,
	17: optional Timestamp employdate,
	18: optional string managername,
	19: optional string city,
	20: optional Timestamp birthday,
	21: optional Timestamp retiredate,
	22: optional string education,
	23: optional string address,
	24: optional string idcard,
	25: optional string mobile,
	26: optional i64 award,
	27: optional Timestamp binding_time,
	28: optional string email,
	29: optional string activation_code,
	30: optional i8 disable,
	31: optional Timestamp create_time,
	32: optional Timestamp update_time,
	33: optional i8 auth_level,
	34: optional Timestamp register_time,
	35: optional string register_ip,
	36: optional Timestamp last_login_time,
	37: optional string last_login_ip,
	38: optional i32 login_count,
	39: optional i8 source,
	40: optional string download_token,
	41: optional i32 hr_wxuser_id,
	42: optional string custom_field,
	43: optional i8 is_rp_sent,
	44: optional i32 sysuser_id,
	45: optional i32 position_id,
	46: optional i32 section_id,
	47: optional i8 email_isvalid,
	48: optional i8 auth_method,
	49: optional string custom_field_values,
	50: optional string departmentname,
	51: optional i32 team_id,
	52: optional i8 job_grade,
	53: optional i32 city_code,
	54: optional i8 degree,
	55: optional list<i32> updateIds
}

struct UserEmployeeBatchForm{
    1:list<UserEmployeeStruct> data,//批量处理的数据
    2:i32 company_id,//公司
    3:bool del_not_include,//是否将不在此数据集中的数据从数据库中删除
    4:bool as_task//是否作为一个task处理，如果作为task那么会后台处理这一批数据
    5:optional bool cancel_auth //是否取消认证，true：取消认证，false：物理删除，默认false
    6:optional i32 auth_method //取消认证或者删除的认证类型，0："使用邮箱认证",1："使用自定义认证",2："使用问答认证"
}

struct UserEmployeePointStruct {
   1: optional i32 id,
   2: optional double employee_id,
   3: optional string reason,
   4: optional i32 award,
   5: optional double application_id,
   6: optional double recom_wxuser,
   7: optional double position_id,
   8: optional double berecom_wxuser_id,
   9: optional double award_config_id
}
struct UserEmployeePointSum{
	1: optional i64 award,
	2: optional i64 employee_id
}

struct ThirdPartyUser{
    1: optional i64 id,
    2: optional i32 user_id,
    3: optional i32 source_id,
    4: optional string username,
    5: optional string password,
    6: optional Timestamp create_time,
    7: optional Timestamp update_time
}

/*
 * 个人中心推荐记录
 */
struct ApplicationRecordsForm {
    1: optional i32 id,
    2: optional string position_title,
    3: optional string company_name,
    4: optional string status_name,
    5: optional Timestamp time,
    6: optional string signature
}
/*
 * 个人中心职位收藏列表
 */
struct FavPositionForm {
    1: optional i32 id,                 //职位编号
    2: optional string title,           //职位名称
    3: optional string department,      //招聘部门
    4: optional Timestamp time,         //收藏的更新时间
    5: optional string city,            //再招城市
    6: optional i32 salary_top,          //薪资上限
    7: optional i32 salary_bottom,       //薪资下限
    8: optional i8 status,              //薪资下限
    9: optional Timestamp update_time    //职位的更新时间
}

struct RecommendationScoreVO {
    1:i32 link_viewed_count,
    2:i32 interested_count,
    3:i32 applied_count
}

/*
 * 个人中心推荐历史记录信息主体
 */
struct RecommendationRecordVO {
    1: optional i8 recom_status,                //职位编号
    2: optional string applier_name,            //申请人姓名
    3: optional string applier_rel,             //转发者姓名
    4: optional string position,                //职位名称
    5: optional Timestamp click_time,           //点击事件
    6: optional i16 status,                     //招聘进度状态
    7: optional i8 is_interested,               //是否推荐 0没有推荐 1推荐
    8: optional i32 view_number,                //点击次数
    9: optional string headimgurl,              //头像
    10: optional i32 id                         //编号
}

/*
 * 查询推荐信息(只有用户是员工时才具备该功能)
 */
struct RecommendationVO {
    1: optional bool hasRecommends,                     //职位编号
    2: optional RecommendationScoreVO score,            //申请编号
    3: optional list<RecommendationRecordVO> recommends //头像
}
/*
 * 个人中心积分记录（只有用户是员工时才具备）
 */
struct AwardRecordForm {
    1: optional i32 id,                //积分记录表编号
    2: optional string reason,         //申请编号
    3: optional string title,          //职位名称
    4: optional Timestamp create_time  //创建时间
}

/*
 * 操作记录
 */
struct ApplicationOperationRecordVO {
    1: optional string date,                    // 操作日期
    2: optional string event,                   // 描述
    3: optional i32 hide,                       // 是否隐藏
    4: optional i32 step_status                 // 状态 2表示拒绝
}

/*
 * 求职记录详情
 */
struct ApplicationDetailVO {
    1: optional i32 pid,                                //积分记录表编号
    2: optional string position_title,                  //申请编号
    3: optional string company_name,                    //职位名称
    4: optional i8 step,                                //进度
    5: optional i8 step_status,                         //状态
    6: optional list<ApplicationOperationRecordVO> status_timeline  //操作记录
}

struct HrNpsRecommendDO {

	1: optional i32 id,
	2: optional i32 hr_nps_id,	//nps.id
	3: optional string username,	//推荐的用户
	4: optional string mobile,	// 推荐的用户的手机号
	5: optional string company,	//推荐的用户所属公司
	6: optional string create_time	//推荐的时间

}

struct HrNpsDO {

	1: optional i32 id,
	2: optional i32 hr_account_id,	//hr帐号
	3: optional i8 intention,	//推荐同行的意愿【0-10】
	4: optional i8 accept_contact,	// 是否愿意接听电话 0-未确认，1-愿意，2-不愿意
	5: optional string create_time,	//分配账号的时间
	6: optional string update_time	//更新时间

}

struct HrNpsResult {
    1: optional HrNpsDO hr_nps,
    2: optional HrNpsRecommendDO hr_nps_recommend
}

struct HrNpsUpdate {
    1: optional i32 user_id,
    2: optional string start_date,
    3: optional string end_date,
    4: optional i8 intention,
    5: optional i8 accept_contact,
    6: optional string username,
    7: optional string mobile,
    8: optional string company
}

struct HrNpsInfo {
    1: optional i32 id,
    2: optional string date,
    3: optional i32 hr_account_id,
    4: optional string hr_mobile,
    5: optional i8 hr_account_type,
    6: optional string company,
    7: optional i8 intention,
    8: optional i8 accept_contact,
    9: optional string recommend_user,
    10: optional string recommend_mobile,
    11: optional string recommend_company,
    12: optional i32 company_id,
    13: optional i32 company_type
}

struct HrNpsStatistic {
    1: optional i32 total,
    2: optional i32 page,
    3: optional i32 page_size,
    4: optional list<HrNpsInfo> data
}

struct UserEmployeeNumStatistic{
    1: optional i32 unregcount,
    2: optional i32 regcount,
    3: optional i32 cancelcount 
}

struct UserEmployeeVO{
    1: optional i32 id, // ID
    2: optional string username, // 姓名
    3: optional string mobile, // 电话
    4: optional string email, // 邮箱
    5: optional string customField, // 自定义
    6: optional string nickName, // 微信账号
    7: optional i32 award, // 积分
    8: optional i32 activation, // 认证状态
    9: optional i32 companyId, // 公司ID
    10: optional string companyName, // 公司名称
    11: optional string companyAbbreviation, // 公司简称
    12: optional string bindingTime, // 认证时间
    13: optional list<map<string,list<string>>> customFieldValues, // 公司员工认证后补填字段配置信息
    14: optional string bonus, // 奖金
    15: optional i32 authMethod, //认证方式 
}
// 员工列表分页实体
struct UserEmployeeVOPageVO{
    1:optional i32 pageNumber, // 当前第几页
    2:optional i32 pageSize,// 每页多少条
    3:optional i32 totalRow,// 总共多少条
    4:optional list<UserEmployeeVO> data,
}
// 员工详情
struct UserEmployeeDetailVO{
    1: optional i32 id, // ID
    2: optional string username, // 姓名
    3: optional string mobile, // 电话
    4: optional string email, // 邮箱
    5: optional string customField, // 自定义
    6: optional string nickName, // 微信账号
    7: optional i32 activation, // 认证状态
    8: optional string companyName, // 公司名称
    9: optional string headImg, // 头像
    10: optional i32 companyId, //
    11: optional string companyAbbreviation, // 公司简称
    12: optional string bindingTime // 绑定时间
    13: optional i32 award, // 积分
    14: optional list<map<string,list<string>>> customFieldValues // 公司员工认证后补填字段配置信息,
    15: optional string bonus //员工当前的奖金总额,
    16: optional i32 authMethod //认证方式,
    17: optional string unbindingTime //解除绑定时间
}
// 员工导入统计数据
struct ImportErrorUserEmployee{
    1: optional i32 rowNum, // 第几条数据
    2: optional string message, // 错误原因
    3: optional user_employee_struct.UserEmployeeDO userEmployeeDO // 员工实体
}

struct ImportUserEmployeeStatistic{
    1: optional i32 repetitionCounts;
    2: optional i32 errorCounts;
    3: optional string message, // 错误原因
    4: optional list<ImportErrorUserEmployee> userEmployeeDO, // 员工实体
    5: optional i32 totalCounts, //总条数
    6: optional bool insertAccept // 是否允许插入
}

struct ThirdPartyAccountHrInfo{
    1: optional i32 id,
    2: optional string username,
    3: optional string mobile
}

struct ThirdPartyAccountInfo{
    1: optional i32 id,
    2: optional i32 bound,
    3: optional i32 channel,
    4: optional string username,
    5: optional string ext,
    6: optional i32 remain_profile_num,
    7: optional i32 remain_num,
    8: optional string sync_time,
    9: optional i32 company_id,
    10: optional list<ThirdPartyAccountHrInfo> hrs
}
//员工一周的转发分享数据
struct EmployeeReferralContribution {
    1: optional i32 userId,
    2: optional i32 companyId,
    3: optional string openid,
    4: optional i32 point,
    5: optional i32 rank,
    6: optional i32 forwardCount,
    7: optional i32 deliverCount,
    8: optional string accessToken
}

//分页信息
struct Pagination {
    1: optional i32 pageNum,
    2: optional i32 pageSize,
    3: optional i32 totalRow,
    4: optional list<EmployeeReferralContribution> details, 
}

//HR信息
struct HRInfo {
    1: optional i32 id,
    2: optional string name,
    3: optional string nickname,
    4: optional string company,
    5: optional string companyAbbreviation,
    6: optional string accountType,
    7: optional string mobile, 
    8: optional string email,
    9: optional string headImg,
    10: optional i32 companyId
}

struct ClaimReferralCardForm {
    1 : optional i32 userId,
    2 : optional i32 referralRecordId,
    3 : optional string name,
    4 : optional string mobile,
    5 : optional string verifyCode,
}

struct CenterUserInfo {
    1 : optional i32 userId,
    2 : optional i32 employeeId,
    3 : optional string name,
    4 : optional string headimg
}
struct PositionReferralInfo {
    1:optional i32 userId,
    2:optional string employeeName,
    3:optional i32 employeeId,
    4:optional string positionName,
    5:optional i32 positionId,
    6:optional string employeeIcon,
    7:optional string nickname

}

struct RadarUserInfo {
    1:optional i32 userId,
    2:optional string nickname,
    3:optional i32 viewCount,
    4:optional bool seekRecommend,
    5:optional i32 depth,
    6:optional string headimgurl,
    7:optional string positionTitle,
    8:optional i32 positionId,
    9:optional string forwardName,
    10:optional bool forwardSourceWx,
    11:optional i32 referralId,
    12:optional string clickTime
    13:optional i32 status
}

struct RadarInfo{
    1:optional i32 totalCount,
    2:optional i32 page,
    3:optional list<RadarUserInfo> userList
}

struct EmployeeForwardView {
    1:optional i32 userId,
    2:optional string nickname,
    3:optional i32 viewCount,
    4:optional i32 connection,
    5:optional i32 depth,
    6:optional string headimgurl,
    7:optional string positionTitle,
    8:optional i32 positionId,
    9:optional string forwardName,
    10:optional bool forwardSourceWx,
    11:optional string clickTime,
    12:optional i32 invitationStatus,
    13:optional list<Connection> chain,
    14:optional i32 status,
    15:optional i32 chainStatus
}

struct Connection{
    1:optional list<i32> pnodes,
    2:optional i32 uid,
    3:optional i32 degree,
    4:optional string avatar
}

struct EmployeeForwardViewPage{
    1:optional i32 totalCount,
    2:optional i32 page,
    3:optional list<EmployeeForwardView> userList
}