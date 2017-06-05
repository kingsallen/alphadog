namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrResourceOnlineDO {

	1: optional i32 id,	//null
	2: optional string resUrl,	//资源链接
	3: optional i32 resType,	//0: image  1: video
	4: optional string remark	//备注资源

}
