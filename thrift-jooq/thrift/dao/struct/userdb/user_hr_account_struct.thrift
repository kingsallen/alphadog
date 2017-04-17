namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserHrAccountDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//company.id
	3: optional string mobile,	//手机号码
	4: optional string email,	//邮箱
	5: optional i32 wxuserId,	//绑定的微信 账号
	6: optional string password,	//登录密码
	7: optional string username,	//企业联系人
	8: optional i32 accountType,	//0 超级账号；1：子账号; 2：普通账号
	9: optional i8 activation,	//账号是否激活，1：激活；0：未激活
	10: optional i32 disable,	//1：可用账号；0禁用账号 ） 遵循数据库整体的设计习惯，1表示可用，0表示不可用
	11: optional string registerTime,	//注册时间
	12: optional string registerIp,	//注册时的IP地址
	13: optional string lastLoginTime,	//最后的登录时间
	14: optional string lastLoginIp,	//最后一次登录的IP
	15: optional i32 loginCount,	//登录次数
	16: optional i32 source,	//来源1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号)
	17: optional string downloadToken,	//下载行业报告校验码
	18: optional string createTime,	//创建时间
	19: optional string updateTime,	//修改时间
	20: optional string headimgurl	//头像 url

}
