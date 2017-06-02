namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserUserTmpDO {

	1: optional i32 id,	//主key
	2: optional string username,	//用户名，比如手机号、邮箱等
	3: optional string password,	//密码
	4: optional i8 isDisable,	//是否禁用，0：可用，1：禁用
	5: optional i32 rank,	//用户等级
	6: optional string registerTime,	//注册时间
	7: optional string registerIp,	//注册IP
	8: optional string lastLoginTime,	//最近登录时间
	9: optional string lastLoginIp,	//最近登录IP
	10: optional i32 loginCount,	//登录次数
	11: optional i32 mobile,	//user pass mobile registe
	12: optional string email,	//user pass email registe
	13: optional i8 activation,	//is not activation 0:no 1:yes
	14: optional string activationCode,	//activation code
	15: optional string token,	//null
	16: optional string name,	//真实姓名
	17: optional string headimg,	//头像
	18: optional i32 nationalCodeId,	//国际电话区号ID
	19: optional i32 wechatId,	//注册用户来自于哪个公众号, 0:默认为来自浏览器的用户
	20: optional string unionid,	//存储仟寻服务号的unionid
	21: optional double source,	//来源：0:手机注册 1:聚合号一键登录 2:企业号一键登录
	22: optional string company,	//点击我感兴趣时填写的公司
	23: optional string position,	//点击我感兴趣时填写的职位
	24: optional i32 parentid,	//合并到了新用户的id
	25: optional string nickname	//用户昵称

}
