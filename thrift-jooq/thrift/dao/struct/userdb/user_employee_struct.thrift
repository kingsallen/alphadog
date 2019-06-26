namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserEmployeeDO {

	1: optional i32 id,	//null
	2: optional string employeeid,	//员工ID
	3: optional i32 companyId,	//null
	4: optional i32 roleId,	//sys_role.id
	5: optional i32 wxuserId,	//userdb.user_wx_user.id 微信账号编号。现在已经废弃。关于员工绑定的C端账号，请参考sysuser_id
	6: optional double sex,	//0：未知，1：男，2：女
	7: optional string ename,	//英文名
	8: optional string efname,	//英文姓
	9: optional string cname,	//null
	10: optional string cfname,	//中文姓
	11: optional string password,	//如果为管理员，存储登陆密码
	12: optional i8 isAdmin,	//null
	13: optional i32 status,	//null
	14: optional string companybody,	//null
	15: optional string departmentname,	//null
	16: optional string groupname,	//null
	17: optional string position,	//null
	18: optional string employdate,	//null
	19: optional string managername,	//null
	20: optional string city,	//null
	21: optional string birthday,	//null
	22: optional string retiredate,	//null
	23: optional string education,	//null
	24: optional string address,	//null
	25: optional string idcard,	//null
	26: optional string mobile,	//null
	27: optional i32 award,	//员工积分
	28: optional string bindingTime,	//null
	29: optional string email,	//email
	30: optional double activation,	//员工认证，0：认证成功 1：未认证 2：认证失败 
	31: optional string activationCode,	//激活码
	32: optional double disable,	//是否禁用0：可用1：禁用
	33: optional string createTime,	//创建时间
	34: optional string updateTime,	//修改时间
	35: optional double authLevel,	//雇主认证：0代表刚注册, 1代表提交了基本信息, 2代表邮箱认证通过, 3代表通过人工审核
	36: optional string registerTime,	//注册时间
	37: optional string registerIp,	//注册IP
	38: optional string lastLoginTime,	//最近登录时间
	39: optional string lastLoginIp,	//最近登录IP
	40: optional double loginCount,	//登录次数
	41: optional double source,	//来源，0:默认 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 8:hr导入员工 9:hr添加的员工
	42: optional string downloadToken,	//下载行业报告的校验token
	43: optional i32 hrWxuserId,	//hr招聘助手的微信ID，wx_group_user.id
	44: optional string customField,	//配置的自定义认证名称对应的内容
	45: optional i8 isRpSent,	//是否拿过员工认证红包 0: 没有 1:有
	46: optional i32 sysuserId,	//sysuser.id, 用户ID
	47: optional i32 positionId,	//hr_employee_position.id, 职能ID
	48: optional i32 sectionId,	//hr_employee_section.id, 部门ID
	49: optional i8 emailIsvalid,	//是否认证了1：是, 0：否
	50: optional i8 authMethod,	//员工认证途径 0:使用邮箱认证 1:使用自定义认证 2:使用问答认证
	51: optional string customFieldValues,	//自定 义字段键值, 结构[{<id>: "<value>"},{...},...]
	52: optional i32 bonus,//自定 义字段键值, 结构[{<id>: "<value>"},{...},...]
	53: optional i32 teamId,
	54: optional i8 jobGrade,
	55: optional i32 cityCode,
	56: optional i8 degree,
	57: optional string unbindTime,
	58: optional string importTime,
}
