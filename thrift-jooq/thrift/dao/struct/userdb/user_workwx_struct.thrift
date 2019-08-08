namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserWorkwxDO {

	1: optional i32 id,	//主key
    2: optional i32 companyId , // 关联hr_company.id
    3: optional i32 sysuserId , //关联仟寻用户id
    4: optional string workWechatCorpid , //企业微信公司ID，这样设计的好处是以防止HR改变了企业微信账号
    5: optional string workWechatUserid  , //企业微信用户ID
    6: optional string name  , //员工姓名
    7: optional string headimg  , //头像
    8: optional string mobile  ,//手机号
    9: optional string email , //邮箱
    10: optional string department  , //部门id列表
    11: optional string department_names  ,//部门名称列表
    12: optional string position , //职位
    14: optional string address  , //地址
    15: optional i8 gender  , //性别 1：男 2：女 0：未知，默认值0
    17: optional string telephone  ,//座机
    18: optional i8 enable  , //成员启用状态。1表示启用的成员，0表示被禁用
    19: optional string extattr  ,//扩展属性
    20: optional i8 status  , //激活状态: 1=已激活，2=已禁用，4=未激活
    21: optional string external_profile  ,//成员对外属性
    22: optional string external_position   //对外职位
	23: optional i8 isDisable,	//是否禁用，0：可用，1：禁用
	24: optional string createTime,	//创建时间
    25: optional string updateTime,	//更新时间
}



