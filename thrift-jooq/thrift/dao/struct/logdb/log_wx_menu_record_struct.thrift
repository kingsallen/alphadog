namespace java com.moseeker.thrift.gen.dao.struct.logdb
namespace py thrift_gen.gen.dao.struct.logdb


struct LogWxMenuRecordDO {

	1: optional i32 id,	//null
	2: optional i32 wechatId,	//null
	3: optional string name,	//null
	4: optional string json,	//菜单的json数据
	5: optional string createTime,	//null
	6: optional i32 errcode,	//微信调用返回的errcode
	7: optional string errmsg	//微信调用返回的errmsg

}
