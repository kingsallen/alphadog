namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserPositionEmailDO {

	1: optional i32 id,	//null
	2: optional i32 userId,	//用户id
	3: optional string conditions,	//订阅的职位条件
	4: optional i32 status,	//状态，0是正常，1是取消
	5: optional string createTime,	//创建时间
	6: optional string updateTime	//更新时间

}
