namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigCronjobsDO {

	1: optional i32 id,	//null
	2: optional string name,	//null
	3: optional string command,	//null
	4: optional string desc,	//null
	5: optional i32 checkDelay,	//job开始几分钟后开始检查结果
	6: optional string noticeEmail	//email通知地址, 为空使用 alarm@moseeker.com

}
