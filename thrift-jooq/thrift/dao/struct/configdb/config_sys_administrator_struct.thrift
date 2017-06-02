namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigSysAdministratorDO {

	1: optional i32 id,	//null
	2: optional string name,	//null
	3: optional string email,	//null
	4: optional string mobile,	//null
	5: optional string password,	//null
	6: optional i32 isDisable,	//是否禁用，0：可用；1：禁用
	7: optional i32 loginCount,	//登录次数
	8: optional string lastLoginTime,	//最近登录时间
	9: optional string createTime,	//创建时间
	10: optional i32 authGroupId	//null

}
