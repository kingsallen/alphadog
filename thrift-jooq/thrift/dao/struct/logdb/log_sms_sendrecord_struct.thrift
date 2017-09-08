namespace java com.moseeker.thrift.gen.dao.struct.logdb
namespace py thrift_gen.gen.dao.struct.logdb


struct LogSmsSendrecordDO {

	1: optional i32 id,	//null
	2: optional i8 sys,	//来自系统，0:未知 1:platform 2:qx 3:hr 4:官网 9:script
	3: optional double mobile,	//null
	4: optional string msg,	//发送内容
	5: optional string ip,	//IP
	6: optional string createTime,	//null
	7: optional string countryCode	//null

}
