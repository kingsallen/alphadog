namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobPcAdvertisementDO {

	1: optional i32 id,	//自增主键
	2: optional string img,	//图片地址
	3: optional string name,	//推荐模块名称
	4: optional string href,	//null
	5: optional i8 status,	//是否可以用，0：不可用 1：可用
	6: optional string description,	//广告位说明
	7: optional string createTime,	//创建时间
	8: optional string updateTime	//更新时间

}
