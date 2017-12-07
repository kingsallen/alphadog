namespace java com.moseeker.thrift.gen.dao.struct.thirdpartydb
namespace py thrift_gen.gen.dao.struct.thirdpartydb


struct ThirdpartyJob1001PositionDO {

	1: optional i32 id,	//主键
	2: optional i32 pid,	//关联职位
	3: optional string subsite,	//第三方账号对应的发布网站
	4: optional i8 status,	//只能状态 0 是有效 1是无效
	6: optional string createTime,	//创建时间

}
