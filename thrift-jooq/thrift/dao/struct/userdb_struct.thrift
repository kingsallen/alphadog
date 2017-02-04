# file:userdb_struct.thrift 

namespace java com.moseeker.thrift.gen.dao.struct

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
typedef string Timestamp;

struct UserFavPositionPojo  {
    1: optional i32 sysuserId,          //用户编号 userdb.user_user.id
    2: optional i32 positionId,         //职位编号 jobdb.job_position.id
    3: optional i8 favorite,            //职位编号
    4: optional Timestamp createTime,   //推荐用户编号user_user.id
    5: optional Timestamp update_time,  //申请时间
    6: optional string mobile,          //申请状态ID（可能已经废弃）
    7: optional i32 id,                 //编号 数据库表标志
    8: optional i32 recomUserId        //推荐者C端账号 userdb.user_user.id
}
/*
 * 员工数据
 */
struct UserEmployeePojo {
    1: optional i32 id,                     //数据库标志 
    2: optional i32 employeeid,             //员工ID，hr上传
    3: optional i32 companyId,              //公司编号 hrdb.hr_company.id
    4: optional i32 roleId,                 //角色编号，暂时没有使用起来
    5: optional i8 sex,                     //性别 0：未知 1：男 2：女
    6: optional string ename,               //英文名
    7: optional string efname,              //英文姓
    8: optional string cname,               //中文名
    9: optional string cfname,              //中文姓
    10: optional string password,           //如果为管理员，存储登陆密码 应该已经废弃
    11: optional bool isAdmin,              //是否是管理员。应该已经废弃
    12: optional i32 status,                //员工状态。不知道在哪使用
    13: optional string companybody,        //应该已经废弃
    14: optional string departmentname,     //应该已经废弃
    15: optional string groupname,          //应该已经废弃
    16: optional string position,           //应该已经废弃
    17: optional Timestamp employdate,      //应该已经废弃
    18: optional string managername,        //应该已经废弃
    19: optional string city,               //应该已经废弃
    20: optional Timestamp birthday,        //生日
    21: optional Timestamp retiredate,      //离职日期
    22: optional string education,          //
    23: optional string address,            //地址
    24: optional string idcard,             //身份证号码
    25: optional string mobile,             //手机号码
    26: optional i32 award,                 //员工积分
    27: optional Timestamp bindingTime,     //员工绑定时间
    28: optional string email,              //邮箱
    29: optional i8 activation,             //是否激活员工认证，0：认证成功 1：未认证 2：认证失败 default 1
    30: optional string activation_code,    //激活码
    31: optional i8 disable,                //是否禁用0：可用1：禁用
    32: optional Timestamp createTime,      //创建时间
    33: optional Timestamp updateTime,      //更新时间
    34: optional i8 authLevel,              //雇主认证：0代表刚注册, 1代表提交了基本信息, 2代表邮箱认证通过, 3代表通过人工审核 应该已经废弃
    35: optional Timestamp registerTime,    //注册时间
    36: optional string registerIp,         //注册IP
    37: optional Timestamp lastLoginTime,   //最近登录的时间
    38: optional string lastLoginIp,        //最近登录的IP
    39: optional i32 loginCount,            //登录次数
    40: optional i8 source,                 //来源，0:默认 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 8:hr导入员工 9:hr添加的员工
    41: optional string downloadToken,      //下载行业报告的校验token
    42: optional i32 hrWxuserId,            //hr招聘助手的微信ID，user_wx_user.id
    43: optional string customField,        //配置的自定义认证名称对应的内容
    44: optional i8 isRpSent,               //是否拿过员工认证红包 0: 没有 1:有
    45: optional i32 sysuserId,             //userdb.user_user.id, 用户ID
    46: optional i32 positionId,            //职位编号 应该已经废弃
    47: optional i32 sectionId,             //部门编号 应该已经废弃
    48: optional i8 emailIsvalid,           //是否认证了1：是, 0：否
    49: optional i8 authMethod,             //员工认证途径 0:使用邮箱认证 1:使用自定义认证 2:使用问答认证
    50: optional string customFieldValues  //自定 义字段键值, 结构[{<id>: "<value>"},{...},...]
}
