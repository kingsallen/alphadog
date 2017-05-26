namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrWxImageReplyDO {

	1: optional i32 id,	//null
	2: optional i32 rid,	//wx_rule.id, 规则ID
	3: optional string image,	//回复图片的相对路径
	4: optional string createTime,	//null
	5: optional string updateTime	//null

}
