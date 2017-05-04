namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserSysAuthGroupDO {

	1: optional i32 id,	//null
	2: optional string name,	//权限分组名
	3: optional i32 authcode	//权限二进制值的十进制表示

}
