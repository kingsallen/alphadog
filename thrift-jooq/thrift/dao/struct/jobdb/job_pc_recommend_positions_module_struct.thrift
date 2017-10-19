namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobPcRecommendPositionsModuleDO {

	1: optional i32 id,	//自增主键
	2: optional string name,	//null
	3: optional string createTime,	//创建时间
	4: optional i8 status,	//是否可用：1：可用，0：不可用
	5: optional string updateTime	//更新时间

}
